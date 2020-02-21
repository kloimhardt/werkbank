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
   [tubax.core :as sax]
   [reagent.core :as r]))

(def menu true)

(def tutorials (vec (concat t-a/vect t-b/vect)))

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
      (load-workspace tut #_(if (seq tut) (get tut 0) tut))
      (gforms/setValue el idx)
      (reset! state {:stdout nil :result nil :code nil :tutorial-no idx}))))

(defonce workspace
  (do
    (js/initblocks blockly)
    (.inject blockly
             "blocklyDiv"
             (when menu (clj->js {:toolbox (gdom/getElement "toolbox")
                                  :media "/blockly/media/"})))))

((tutorial-fu identity))

(def thexml (atom ""))

(defn print-code [edn-code]
  (if-let [code  (:dat edn-code)]
    (doseq [c code]
      (println (pr-str c)))
    (println (pr-str edn-code))))

(defn code->break-str [edn-code]
  (if-let [code  (:dat edn-code)]
    (apply str (interpose "\n" (map pr-str code)))
    (pr-str edn-code)))

(defn code->str [edn-code]
  (if-let [code  (:dat edn-code)]
    (apply pr-str code)
    (pr-str edn-code)))

(defn my-str [e]
  (let [f (fn [x] (if (nil? x) "nil" (str x)))]
    (if (seq? e)
      (apply str (interpose " " (map f e)))
      (f e))))

(defn ^:export startsci []
  (let [xml-str (->> (.-mainWorkspace blockly)
                     (.workspaceToDom blockly/Xml)
                     (.domToPrettyText blockly/Xml))
        edn (sax/xml->clj xml-str)
        edn-code (if (seq (:content edn))
                   (edn->code/parse edn) "")
        theout (atom "")
        bindings {'println (fn [& x]
                             (swap! theout str (my-str x) "\n") nil)
                  'print (fn [& x]
                           (swap! theout str (my-str x)) nil)}
        erg (try (sci/eval-string (code->break-str edn-code)
                                  {:bindings bindings})
                 (catch js/Error e (.-message e)))]
    (reset! thexml xml-str)
    (when menu
      (println "edn: " edn)
      (println "-------")
      (print-code edn-code)
      (println "-------")
      (when @theout (println @theout))
      (println erg))
    (swap! state assoc
           :stdout @theout
           :result (my-str erg)
           :code (code->break-str edn-code))))

(defn tutorials-comp []
  [:div
   [:button {:on-click (tutorial-fu dec)} "<"]
   [:button {:on-click (tutorial-fu inc)} ">"]])

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
