(ns web-server.core
  (:require [ring.adapter.jetty :as raj]
            [ring.middleware.resource :as rmr]
            [ring.middleware.file :as rmf]))

(defn handler [request]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "File not found"})

(defn -main []
  (print "klm jetty starts")
  (raj/run-jetty
    (-> handler
        (rmr/wrap-resource "public")
        (rmf/wrap-file "target_shadow/public"))
    {:port 3000}))
