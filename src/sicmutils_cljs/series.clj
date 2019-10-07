(ns sicmutils-cljs.series
  (:require [sicmutils.series :as self]))

(defn fmap [& a]
  (let [erg (apply self/fmap a)]
    (println "klm jvm series 1 never call" erg)
    erg))

(defn series? [& a]
  (let [erg (apply self/series? a)]
    (if (= erg false)
      false
      (do
        (println "klm jvm series 3 never call" erg)
        erg))))
