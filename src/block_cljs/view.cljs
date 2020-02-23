(ns block-cljs.view
  (:require
   [goog.dom :as gdom]
   [goog.string :as gstring]
   [goog.dom.forms :as gforms]
   [sci.core :as sci]
   ["blockly" :as blockly]
   [block-cljs.xmlparse :as edn->code]
   [block-cljs.tutorials-a :as t-a]
   [block-cljs.tutorials-b :as t-b]
   [block-cljs.tutorials-c :as t-c]
   [block-cljs.tutorials-d :as t-d]
   [clojure.walk :as w]
   [tubax.core :as sax]
   [reagent.core :as r]
   [zprint.core :as zp]))

(def menu true)

(def tutorials (vec (concat t-a/vect t-b/vect t-c/vect t-d/vect)))
(def chapters (vec (concat (repeat (count t-a/vect) "I")
                           (repeat (count t-b/vect) "II")
                           (repeat (count t-c/vect) "III")
                           (repeat (count t-d/vect) "IV"))))

(defn load-workspace [xml-text]
  (.. blockly/Xml
      (clearWorkspaceAndLoadFromXml (.. blockly/Xml (textToDom xml-text))
                                    (.getMainWorkspace blockly))))

(defonce state (r/atom nil))

(defn tutorial-fu [inc-or-dec]
  (fn []
    (let [el (gdom/getElement "tutorial_no")
          idx-old  (gstring/toNumber (gforms/getValue el))
          idx-new (inc-or-dec idx-old)
          idx (cond
                (< -1 idx-new (count tutorials)) idx-new
                (< -1 idx-old (count tutorials)) idx-old
                :else 0)
          tut (get tutorials idx)]
      (load-workspace tut)
      (gforms/setValue el idx)
      (reset! state
              {:stdout nil :result nil :code nil :tutorial-no idx}))))

(defonce workspace
  (do
    (js/initblocks blockly)
    (.inject blockly
             "blocklyDiv"
             (when menu (clj->js {:toolbox (gdom/getElement "toolbox")
                                  :media "/blockly/media/"})))))

((tutorial-fu identity))

(def thexml (atom ""))

(defn code->break-str [width edn-code]
  (if-let [code  (:dat edn-code)]
    (apply str (interpose "\n" (map #(zp/zprint-str % width) code)))
    (zp/zprint-str edn-code width)))

(defn part-str [width s]
  (apply str
         (interpose "\n"
                    (map (partial apply str)
                         (partition-all width s)))))

(defn my-str [e width]
  (let [f (fn [x]
            (if (nil? x) "nil" (part-str width (str x))))]
    (if (seq? e)
      (apply str (interpose " " (map f e)))
      (f e))))

(defn augment-code-fu [edn-code flat-code fn-code]
  (if (and (seq (filter #{(second fn-code)} flat-code))
           (:code edn-code))
    (if (:dat (:code edn-code))
      (update-in edn-code [:code :dat] #(cons fn-code %))
      (update edn-code :code (fn [c] {:dat [fn-code c]})))
    edn-code))

(defn augment-code [edn-code]
  (let [flat-code (flatten (w/postwalk #(if (map? %) (vec %) %) edn-code))]
    (-> edn-code
        (augment-code-fu flat-code
                         '(defn vec-rest "added by Blockly parser" [x]
                            (let [r (rest x)] (if (seq? r) (vec r) r))))
        (augment-code-fu flat-code
                         '(defn vec-cons "added by Blockly parser" [x coll]
                            (let [c (cons x coll)] (if (seq? c) (vec c) c)))))))

(defn ^:export startsci []
  (let [xml-str (->> (.-mainWorkspace blockly)
                     (.workspaceToDom blockly/Xml)
                     (.domToPrettyText blockly/Xml))
        edn-xml (sax/xml->clj xml-str)
        edn-code (if (seq (:content edn-xml))
                   (try {:code (edn->code/parse edn-xml)}
                        (catch js/Error e {:error (.-message e)})) "")
        aug-edn-code (augment-code edn-code)
        theout (atom "")
        str-width 41
        bindings {'println (fn [& x]
                             (swap! theout str (my-str x str-width) "\n") nil)
                  'print (fn [& x]
                           (swap! theout str (my-str x str-width)) nil)}
        erg (try (sci/eval-string (code->break-str str-width
                                                   (:code aug-edn-code))
                                  {:bindings bindings})
                 (catch js/Error e (.-message e)))]
    (reset! thexml xml-str)
    (when menu
      ;;(println "edn: " edn)
      (println "-------")
      (print (code->break-str str-width (:code aug-edn-code)))
      (println (:error aug-edn-code))
      (println "-------")
      (when @theout (println @theout))
      (println erg))
    (swap! state assoc
           :stdout @theout
           :result (my-str erg str-width)
           :code (if (:error aug-edn-code)
                   "Cannot even parse the blocks"
                   (code->break-str str-width (:code aug-edn-code))))))

(defn tutorials-comp []
  [:div
   [:button {:on-click (tutorial-fu #(- % 5))} "<<"]
   [:button {:on-click (tutorial-fu #(+ % 5))} ">>"]
   " " (inc (:tutorial-no @state)) "/" (count tutorials) " "
   "(" (get chapters (:tutorial-no @state)) ")" " "
   [:button {:on-click (tutorial-fu dec)} "<"]
   [:button {:on-click (tutorial-fu inc)} ">"]
   ]
  )

(defn out-comp []
(r/create-class
(merge
  {:reagent-render
   (fn []
     [:div
      (when menu
        [:input {:type "text" :value (pr-str @thexml) :id "xmltext"
                 :read-only true}])
      [tutorials-comp]
      (when (:result @state)
        [:table {:style {:width "100%"}}
         [:thead
          [:tr {:align :left}
           [:th {:style {:width "50%"}} "Output"]
           (when (< 1 (:tutorial-no @state)) [:th "Code"])]]
         [:tbody
          [:tr
           [:td {:align :top}
            (when-let [so (:stdout @state)]
              [:pre so])
            [:pre (:result @state)]]
           (when (< 1 (:tutorial-no @state))
             [:td {:align :top} [:pre (:code @state)]])]]])])}
  (when menu
    {:component-did-update (fn []
                             (.select (gdom/getElement "xmltext"))
                             (.execCommand js/document "copy"))})

  )))

(defn theview []
  [:div
   [out-comp]
   ])

(defn ^{:export true :dev/after-load true} output []
  (r/render [theview] (gdom/getElement "out")))
