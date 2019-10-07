(ns sicmutils-cljs.series)

(defn fmap [& a]
  (let [erg nil]
    (println "klm jvm series 1 never call" erg)
    erg))

(defn series? [& a]
  (let [erg false]
    (if (= erg false)
      false
      (do
        (println "klm jvm series 3 never call" erg)
        erg))))
