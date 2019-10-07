(ns sicmutils-cljs.structure)

(defn structure? [& a]
  (let [erg false]
    (if (= erg false)
      false
      (do
        (println "klm jvm structure 1 never" erg)
        erg))))

(defn opposite [& a] (println "klm jvm structure 2 never") nil)

(defn structure-assoc-in [& a]
  (println "klm jvm structure 3 never")
  nil)

(defn mapr [& a] (println "klm jvm structure 4 never") nil)
