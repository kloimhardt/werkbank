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
   [block-cljs.tutorials-e :as t-e]
   [block-cljs.tutorials-f :as t-f]
   [clojure.walk :as w]
   [tubax.core :as sax]
   [reagent.core :as r]
   [zprint.core :as zp]))


(def menu false)

(def tutorials (vec (concat t-a/vect t-b/vect t-c/vect t-d/vect t-e/vect
                            t-f/vect)))
(def chapters (vec (concat
                     (repeat (count t-a/vect) "I")
                     (repeat (count t-b/vect) "II")
                     (repeat (count t-c/vect) "III")
                     (repeat (count t-d/vect) "IV")
                     (repeat (count t-e/vect) "V")
                     (repeat (count t-f/vect) "VI"))))

(defn load-workspace [xml-text]
  (.. blockly/Xml
      (clearWorkspaceAndLoadFromXml (.. blockly/Xml (textToDom xml-text))
                                    (.getMainWorkspace blockly))))

(defonce state (r/atom nil))

(defonce app-state (r/atom nil))

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
              {:stdout nil :result nil :code nil :tutorial-no idx})
      (reset! app-state 0))))

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
            (if (nil? x) "nil" (str x)))]
    (if (seq? e)
      (part-str width (apply str (interpose " " (map f e))))
      (part-str width (f e)))))

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
(def timer (atom nil))
(def counter (atom 0))

(defn stop-timer [msg]
  (js/clearInterval @timer)
  (reset! timer nil)
  (reset! counter 0)
  msg)


(defn start-timer [fu ms max msg]
  (when-not @timer
    (reset! timer
            (js/setInterval (fn []
                              (swap! counter inc)
                              (if (< @counter max)
                                (fu)
                                (stop-timer nil))) ms))
    msg))

(defn run-code [edn-code]
  (let [aug-edn-code (augment-code edn-code)
        theout (atom "")
        str-width 41
        bindings {'println (fn [& x]
                             (swap! theout str (my-str x str-width) "\n") nil)
                  'print (fn [& x]
                           (swap! theout str (my-str x str-width)) nil)
                  'app-state app-state
                  'start-timer start-timer
                  'stop-timer stop-timer}
        erg (try (sci/eval-string (code->break-str str-width
                                                   (:code aug-edn-code))
                                  {:bindings bindings})
                 (catch js/Error e (.-message e)))]
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
                   (code->break-str str-width (:code aug-edn-code)))
           :edn-code (:code aug-edn-code))))

(defn ^:export startsci []
(let [xml-str (->> (.-mainWorkspace blockly)
                   (.workspaceToDom blockly/Xml)
                   (.domToPrettyText blockly/Xml))
      edn-xml (sax/xml->clj xml-str)
      edn-code (if (seq (:content edn-xml))
                 (try {:code (edn->code/parse edn-xml)}
                      (catch js/Error e {:error (.-message e)})) "")]
  (reset! thexml xml-str)
  (run-code edn-code)))

(defn tutorials-comp []
  (if (zero? (:tutorial-no @state))
    [:div
     [:button {:on-click (tutorial-fu inc)} "Go to next example."]
     [:button
      {:on-click (tutorial-fu (fn [_] (dec (count tutorials))))}
      "Show me the cool stuff!"]]
    [:div
     [:button {:on-click (tutorial-fu #(- % 5))} "<<"]
     [:button {:on-click (tutorial-fu #(+ % 5))} ">>"]
     " " (inc (:tutorial-no @state)) "/" (count tutorials) " "
     "(" (get chapters (:tutorial-no @state)) ")" " "
     [:button {:on-click (tutorial-fu dec)} "<"]
     [:button {:on-click (tutorial-fu inc)} ">"]
     ]))


(defn filter-defns [edn-code fu]
  (let [ec (if (:dat edn-code) edn-code {:dat [edn-code]})]
    {:dat
     (conj
       (vec (filter #(= (symbol "defn") (first %)) (:dat ec)))
       (list fu)
       (last (:dat ec)))}))

(defn to-kw [edn-code sy]
  (cond
    (symbol? sy)
    (let [s (str sy)]
      (cond
        (= ":" (first s)) (keyword (subs s 1 (count s)))
        (= "nil" s) nil
        (= "@app-state" s) @app-state
        :else sy))
    (map? sy)
    (if (:on-click sy)
      (assoc sy
             :on-click
             #(run-code
                {:code
                 (filter-defns edn-code (:on-click sy))}))
      sy)
    (list? sy)
    (do
      (println "tl " sy)
      (println (augment-code edn-code))
      (try (sci/eval-string (pr-str sy))
           (catch js/Error e (.-message e))))
    :else sy))

(defn transform-vec [vect edn-code]
  (w/postwalk #(to-kw edn-code %)
              vect))

(defn reagent-comp []
  (let [last-vec
        (cond
          (vector? (:edn-code @state)) (:edn-code @state)
          (and (map? (:edn-code @state))
               (vector? (last (:dat (:edn-code @state)))))
          (last (:dat (:edn-code @state)))
          :else [nil])]
    (when (= (symbol ":div") (first last-vec))
      [:div
       (transform-vec last-vec (:edn-code @state))])))

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
          [reagent-comp]
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
