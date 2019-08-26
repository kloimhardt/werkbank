(ns abc.setup)
(def default {:iframeheight "555px"})
(def setup (atom default))

(defn set-default []
  (reset! setup default))

(defn set-iframeheight [iframeheight]
  (swap! setup assoc :iframeheight iframeheight)
  iframeheight)
