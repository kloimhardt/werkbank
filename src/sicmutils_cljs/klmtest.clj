(ns sicmutils-cljs.klmtest
  (:refer-clojure :rename {zero? core-zero?} :exclude [partial zero? + - * / ref])
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [sicmutils-cljs.calculus.derivative :as d]
            [sicmutils-cljs.function]
            [sicmutils.generic :as g]
            [sicmutils.calculus.derivative :as sud]
            [sicmutils.function]))
#_(refresh)

(defn a [t] t)
(defn b [a] (d/D a))

(println "klm output1 -------- " ((b a) 3))
(println "klm output2 -------- " ((d/D a) 1))

(comment
  (println "output -------- "(b a))
  ((sud/D a) 1)
  ((d/D a) 1)
  #_end)

