(ns abc.page
  (:require [hiccup.page :as h]
            [abc.setup :as setup]))

(defn view []
  (let [{:keys [iframeheight]} @setup/setup]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body
     (h/html5
       [:head
        [:title "abc"]
        [:meta {:charset "utf-8"}]
        [:meta {:name "viewport" :content "width=device-width, initial-scale=1, user-scalable=no"}]
        [:link {:rel "stylesheet" :href "/css/katex.min.css"}]
        [:style ".table_wrapper {display: block; overflow-x: auto; white-space: nowrap;}"]
        [:body
         [:script {:src "katex.min.js" :type "text/javascript"}]
         [:script {:src "vega.js" :type "text/javascript"}]
         [:script {:src "vega-lite.min.js" :type "text/javascript"}]
         [:script {:src "vega-embed.min.js" :type "text/javascript"}]
         [:iframe {:src "/abc.html" :id "iframeid" :name "klmiframe" :height iframeheight :width "100%"}]
         ;;to acess variables in iframe
         ;;document.getElementById("iframeid").contentWindow.workspace;
         ;;(.. js/document (getElementById "iframeid") -contentWindow -workspace)
         [:div#app]
         [:script {:src "/cljs-out/dev-main.js" :type "text/javascript"}]
         [:script {:type "text/javascript"} "abc.view.start();"]]])}))

(defn habra []
  (let [{:keys [iframeheight]} @setup/setup]
    {:status 200
     :headers {"Content-Type" "text/html"}
     :body
     (h/html5
       [:head
        [:title "abc"]
        [:meta {:charset "utf-8"}]
        [:meta {:name "viewport" :content "width=device-width, initial-scale=1, user-scalable=no"}]
        [:link {:rel "stylesheet" :href "/css/katex.min.css"}]
        [:style ".table_wrapper {display: block; overflow-x: auto; white-space: nowrap;}"]
        [:body
         [:script {:src "katex.min.js" :type "text/javascript"}]
         [:script {:src "vega.js" :type "text/javascript"}]
         [:script {:src "vega-lite.min.js" :type "text/javascript"}]
         [:script {:src "vega-embed.min.js" :type "text/javascript"}]
         [:div#app]
         [:script {:src "/cljs-out/dev-main.js" :type "text/javascript"}]
         [:script {:type "text/javascript"} "abc.view.starthabra();"]]])}))

