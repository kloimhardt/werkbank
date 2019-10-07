(ns sicmutils-cljs.matrix)

(defn matrix? [& a]
  (let [erg false]
    (if (= erg false)
      false
      (do
        (println "klm jvm matrix 1" erg)
        erg))))

(defn matrix-some [& a] (println "klm jvm matrix 2 never call") nil)
(defn fmap [& a] (println "klm jvm matrix 3 never") nil)
(defn seq-> [s] (println "klm mock matrix/seq-> should be never called") nil)
