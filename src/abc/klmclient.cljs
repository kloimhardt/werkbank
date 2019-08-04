(ns abc.klmclient
  (:require [cljs.reader :as edn]
            [reagent.core :as r]))

(defn make-websocket! [url write-fun]
 (.log js/console "attempting to connect websocket")
 (if-let [chan (js/WebSocket. url)]
   (do
     (set! (.-onmessage chan)
          (fn [msg] (write-fun (edn/read-string (.-data msg)))))
     (.log js/console "Websocket connection established with: " url))
   (throw (js/Error. "Websocket connection failed!"))))

(defn app-db [init url]
  (let [state-ref (r/atom init)
        schreib (fn [data] (reset! state-ref data))]
    (do
      (make-websocket! url schreib)
      (fn [action & args]
        (condp = action
          :read state-ref
          :write (schreib (first args)))))))

;(make-websocket! (str "ws://" (.-host js/location) "/ws")) ;for actual host
;defonce is necessary, a simple def is not sufficient for hot reloading
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

(defn page []
  (letfn [(cmp [[kw-id [typ kmdo input]]]
             (let [id (name kw-id)]
               [:div {:key id}
                [:hr] [:p id] [:code (str kmdo)] [:p]
                (condp = typ
                  :tex [(comp-dom tex-renderings) id input]
                  :vega [(comp-dom vega-renderings) id input]
                  :plotly [(comp-dom plotly-renderings) id input]
                  :string [:p (str input)])]))]
    (fn []
     [:div (doall (map cmp @(server-state :read)))])))

(defn ^:export start []
  (r/render [page]
            (.getElementById js/document "app")))
