(ns block-cljs.view
  (:require
   [goog.dom :as gdom]
   [sci.core :as sci]
   ["blockly" :as blockly]
   [block-cljs.xmlparse :as xml]))

(defonce workspace
  (do
    (js/initblocks blockly)
    (.inject blockly
             "blocklyDiv"
             (clj->js {:toolbox (gdom/getElement "toolbox")
                       :media "/blockly/media/"}))))
(defn xml->code-str [xml-str]
  (let [edn (xml/parse xml-str)]
    (if (:dat edn)
      (apply str (interpose "\n" (:dat edn)))
      (str edn))))

(defn ^:export startsci []
  (let [b-xml (.-Xml blockly)
        xml-str (->> (.-mainWorkspace blockly)
                     (.workspaceToDom b-xml)
                     (.domToPrettyText b-xml))
        code-str (xml->code-str xml-str)
        theout (atom "")
        bindings {'println (fn [& x]
                             (swap! theout str (apply str x) "\n") nil)
                  'print (fn [& x]
                           (swap! theout str (apply str x)) nil)}
        erg (try (sci/eval-string code-str {:bindings bindings})
                 (catch js/Error e (.-message e)))]
    #_(println "xml:" xml-str)
    (println "-------")
    (println code-str)
    (println "-------")
    (println @theout)
    (println erg)))
