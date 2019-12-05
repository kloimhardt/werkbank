(ns abc.app-server
  (:require [ring.util.request :as r]
            [hiccup.page :as h]
            [clojure.java.io :as io]
            [org.httpkit.server :as http-kit]
            [compojure.core :as compojure]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [abc.xmlparse :as x]
            [abc.page :as page]))

(defonce channels (atom #{}))

(defn connect! [channel]
  (swap! channels conj channel))

(defn disconnect! [channel status]
  (swap! channels #(remove #{channel} %)))

(defn notify-clients [msg]
  (doseq [channel @channels]
    (http-kit/send! channel msg)))

(defn ws-handler [request]
  (http-kit/with-channel request channel
    (connect! channel)
    (http-kit/on-close channel (partial disconnect! channel))
    (http-kit/on-receive channel #(notify-clients %))))

(defn copy-file [source-path dest-path]
  (io/copy (io/file source-path) (io/file dest-path)))

(defn write-xml [xml]
  (let [a (x/parse xml)
        code (if (:dat a) (:dat a) [a])]
    (println "start writexml")
    (when (not (empty? a))
      (spit "code_template/workspace.xml" xml)
      (let [d "code_template/c_temp.clj"]
        (spit d ";;this is a compter generated file")
        (spit d (slurp "code_template/code_trunk.clj"))
        (spit d (first code) :append true)
        (doseq [x (subvec code 1)]
          (spit d "\n" :append true)
          (spit d x :append true))
        (spit d "\n" :append true)
        ;;(spit d '(print-text "_" "<") :append true)
        (copy-file d "src/abc/code.clj")))
    (println "end writexml")))

(defn xml-resp [req]
  (let [xml (r/body-string req)
        ;;a (x/parse (r/body-string req))
        ;;b (if (:dat a) (:dat a) [a])
        ]
    #_(println "klm" b)
    #_(write-code b)
    (def klmx xml)
    (write-xml xml)
    {:status 200
     :headers {"Content-Type" "text/xml"}
     :body "<xml><query><author>Johniii</author></query></xml>"}))

(compojure/defroutes home-routes
  (compojure/POST "/thexml" request (xml-resp request))
  (compojure/GET "/wspace" request (page/view :wspace))
  (compojure/GET "/vegatex" request (page/view :vegatex))
  (compojure/GET "/habra" request (page/view :habra))
  (compojure/GET "/int" request (page/view :int))
  (compojure/GET "/ws" request (ws-handler request))
  (route/resources "/")
  (route/not-found "Server is started, but page not found"))

(defonce server (atom nil))

(defn start-server []
  (reset! server (http-kit/run-server (handler/site #'home-routes) {:port 3000}))
  {:status 200
   :headers {"Content-Type" "text/xml"}
   :body "<xml>http-kit server started</xml>"})

(defn stop-server []
  (reset! server nil)
  {:status 200
   :headers {"Content-Type" "text/xml"}
   :body "<xml>http-kit server stopped</xml>"})

(defn send-to-page-2 [thevec] (notify-clients (pr-str thevec)))

(defn handler-figwheel [req]
  (condp = (:uri req)
    "/thexml" (xml-resp req)
    "/test" (page/view :wspace)
    "/http-kit" (start-server)
    "/http-kit-stop" (stop-server)
    {:status 404
     :headers {"Content-Type" "text/html"}
     :body "Yep the server failed to find it."}))
