(ns ^:figwheel-always abc.view
  (:require [cljs.reader :as edn]
            [reagent.core :as r]
            [abc.la-habra :as la-habra]
            [sicmutils-cljs.klmtest]))

(defn make-websocket! [url write-fun]
  (.log js/console "attempting to connect websocket")
  (if-let [chan (js/WebSocket. url)]
    (do
      (set! (.-onmessage chan)
            (fn [msg] (write-fun (edn/read-string (.-data msg)))))
      (.log js/console "Websocket connection established with: " url))
    (throw (js/Error. "Websocket connection failed!"))))

(defn app-db [init url]
  (let [state2-ref (r/atom init)
        schreib2 (fn [data]
                   (if (= (first (first data)) "reset-7236491003")
                     (reset! state2-ref [])
                     (swap! state2-ref concat data)))]
    (do
      (make-websocket! url schreib2)
      (fn [action & args]
        (condp = action
          :read state2-ref
          :write (schreib2 (first args)))))))

;;(make-websocket! (str "ws://" (.-host js/location) "/ws")) ;for actual host
;;defonce is necessary, a simple def is not sufficient for hot reloading
(defonce server-state
  (app-db [[:id1 [:string "noch kein kommando" "noch kein input"]]]
          "ws://localhost:3000/ws"))

(defn comp-dom [renderings-fn]
  (fn [id form]
    (r/create-class
     {:reagent-render (fn [id _] [:div {:id id}])
      :component-did-mount renderings-fn
      :component-did-update renderings-fn})))

(defn vega-renderings [this]
  (let [[_ id vega-exp] (r/argv this)]
    (if (map? vega-exp)
      (js/vegaEmbed (str "#" id) (clj->js vega-exp)))))

(defn tex-renderings [this]
  (let [[_ _id tex-string] (r/argv this)]
    (if tex-string
      (.render js/katex tex-string (r/dom-node this)))))

(defn plotly-renderings [this]
  (let [[_ _id plotly-form] (r/argv this)]
    (.plot js/Plotly (r/dom-node this) (clj->js plotly-form))))

(defn render-dispatch [id typ input]
  [:div
   ({:tex [(comp-dom tex-renderings) id input]
     :vega [(comp-dom vega-renderings) id input]
     :plotly [(comp-dom plotly-renderings) id input]
     :string [:p (str input)]
     :div input
     :la-habra [:p "la-habra"]}
    typ)])

(defn wspace []
  (letfn [(cmp [[kw-id [typ kmdo input]]]
            (let [id (name kw-id)]
              (list
                (.log js/console input)
                [:td {:key (str id "id") :style {:border-bottom "1pt solid black"
                                                 :border-left "1pt solid black"}} id]
                [:td {:key id :style {:border-bottom "1pt solid black"}}
                 [render-dispatch id typ input]])))]
    (fn []
      [:div.table_wrapper [:table [:tbody [:tr  (doall (map cmp @(server-state :read)))]]]])))

(defn vegatex []
  (letfn [(cmp [[kw-id [typ kmdo input]]]
            (let [id (name kw-id)]
              ^{:key (str id "id")}
              [:div id [render-dispatch id typ input]]))]
    (fn []
      [:div (doall (map cmp @(server-state :read)))])))

(defn ^:export startwspace []
  (r/render [wspace] (.getElementById js/document "app")))

(defn ^:export startvegatex []
  (r/render [vegatex] (.getElementById js/document "app")))

(defn ^:export starthabra []
  (r/render [la-habra/drawing] (.getElementById js/document "app")))

(defn ^:export xmltocode []
  (println "klm diiii"))
