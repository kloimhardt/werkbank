(ns block-cljs.view
  (:require
   [goog.dom :as gdom]
   [sci.core :as sci]
   ["blockly" :as blockly]
   [block-cljs.xmlparse :as edn->code]
   [tubax.core :as sax]
   [reagent.core :as r]))

(defonce workspace
  (do
    (js/initblocks blockly)
    (.inject blockly
             "blocklyDiv"
             (clj->js {:toolbox (gdom/getElement "toolbox")
                       :media "/blockly/media/"}))))

(defonce state (r/atom {:stdout nil :result nil :code nil}))

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

(defn ^:export startsci []
  (let [b-xml (.-Xml blockly)
        xml-str (->> (.-mainWorkspace blockly)
                     (.workspaceToDom b-xml)
                     (.domToPrettyText b-xml))
        edn (sax/xml->clj xml-str)
        edn-code (if (seq (:content edn))
                   (edn->code/parse edn) "")
        theout (atom nil)
        bindings {'println (fn [& x]
                             (swap! theout str (apply str x) "\n") nil)
                  'print (fn [& x]
                           (swap! theout str (apply str x)) nil)}
        erg (try (sci/eval-string (code->str edn-code)
                                  {:bindings bindings})
                 (catch js/Error e (.-message e)))]
    (println "xml: " xml-str)
    (println "edn: " edn)
    (println "-------")
    (print-code edn-code)
    (println "-------")
    (when @theout (println @theout))
    (println erg)
    (reset! state {:stdout (subs @theout 0 (dec (count @theout))) :result (str erg)
                   :code (code->break-str edn-code)})))

(defn out-comp []
  #_[:table {:style {:width "100%"}}
     [:tbody
      [:tr
       [:td [:pre (:stdout @state)]]
       [:td {:row-span 2} [:pre (:code @state)]]]
      [:tr
       [:td [:pre (:result @state)]]]]]
  [:table {:style {:width "100%"}}
   [:thead
    [:tr {:align :left}
     [:th {:style {:width "50%"}} "out"] [:th "code"]]]
   [:tbody
    [:tr
     [:td {:align :top}
      (when-let [so (:stdout @state)]
        [:pre so])
      [:pre (:result @state)]]
     [:td {:align :top} [:pre (:code @state)]]]]]


  )

(defn ^{:export true :dev/after-load true} output []
  (r/render [out-comp] (gdom/getElement "out")))
