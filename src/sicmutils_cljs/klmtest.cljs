(ns sicmutils-cljs.klmtest
  (:refer-clojure :rename {zero? core-zero?})
  (:require [sicmutils-cljs.calculus.derivative :as d]
            [sicmutils-cljs.function-a]))

(enable-console-print!)
(defn a [t] t)
(defn b [a] (d/D a))
(println "klm scr test -------- " (b a))
(println "klm output2 -------- " ((d/D a) 1))

