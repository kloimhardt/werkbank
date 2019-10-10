(ns sicmutils-cljs.klmtest
  (:refer-clojure :rename {zero? core-zero?})
  (:require [sicmutils-cljs.calculus.derivative :as d]
            [sicmutils-cljs.function-a]
            [sicmutils-cljs.numsymb]
            [sicmutils-cljs.generic :as g]))

(enable-console-print!)
(comment
  (defn a [t] t)

  (println ((d/D a) 1) 1)
  (println (g/* 3 4) 2)
  (println (g/* 3 'x) 3)
  (println (g/+ 3 4) 4)
  (println (g/+ 3 'x) 5)
  (println (g/+ (g/* 2 'x) 3) 6))

(defn b [t]
  (g/+ (g/* t t) 3))
(println ((d/D b) 't))
