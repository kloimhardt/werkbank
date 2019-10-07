(ns sicmutils-cljs.structure
  (:require [sicmutils.structure :as self]))

(defn structure? [& a]
  (let [erg (apply self/structure? a)]
    (if (= erg false)
      false
      (do
        (println "klm jvm structure 1 never" erg)
        erg))))

(defn opposite [& a] (println "klm jvm structure 2 never") (apply self/opposite a))

(defn structure-assoc-in [& a]
  (println "klm jvm structure 3 never")
  (apply self/structure-assoc-in a))

(defn mapr [& a] (println "klm jvm structure 4 never") (apply self/mapr a))
