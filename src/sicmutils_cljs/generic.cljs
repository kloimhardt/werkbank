(ns sicmutils-cljs.generic
  (:refer-clojure :exclude [partial zero? + - * / ref])
  (:require [sicmutils-cljs.value :as v]))

(defn numerical-quantity? [& a]
  (let [erg nil]
    (println "klm jvm generic 1 never call" erg)))

(defn abstract-quantity? [& a]
  (println "klm jvm generic 2 never call"))

(defn + [& a]
  (let [erg 1]
    (if (= erg 1)
      1
      (do
        (println "klm jvm generic 3 never call " erg)
        erg))))

(defmulti partial-derivative v/argument-kind)
