(ns sicmutils-cljs.matrix
  (:require [sicmutils.matrix :as self]))

(defn matrix? [& a]
  (let [erg (apply self/matrix? a)]
    (if (= erg false)
      false
      (do
        (println "klm jvm matrix 1" erg)
        erg))))

(defn matrix-some [& a] (println "klm jvm matrix 2 never call") (apply self/matrix-some a))
(defn fmap [& a] (println "klm jvm matrix 3 never") (apply self/fmap a))
(defn seq-> [s] (println "klm mock matrix/seq-> should be never called") (self/seq-> s))
