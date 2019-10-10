(ns sicmutils-cljs.klmtest
  (:refer-clojure :rename {zero? core-zero?} :exclude [partial zero? + - * / ref])
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.tools.trace :as tr]

            [sicmutils.rational-function]
            [sicmutils.structure]
            [sicmutils.series]
            [sicmutils.polynomial]
            [sicmutils.numsymb :as nusy]

            [sicmutils-cljs.generic :as g]
            [sicmutils-cljs.calculus.derivative :as d]
            [sicmutils-cljs.function]
            [sicmutils-cljs.numsymb]

            [sicmutils.generic :as sug]
            [sicmutils.calculus.derivative :as sud]
            [sicmutils.function]))
#_(refresh)

(defn print-compar [x y n]
  (println (str "klm" n) x)
  (println (str "klm" n) y))

(defn a [t] t)

(comment)
(tr/trace-ns sicmutils.structure)
(tr/trace-ns sicmutils.rational-function)
(tr/trace-ns sicmutils.generic)
(tr/trace-ns sicmutils.polynomial)
(tr/trace-ns sicmutils.numsymb)
(tr/trace-ns sicmutils.series)
(tr/trace-ns sicmutils.calculus.derivative)

(comment
  (print-compar ((d/D a) 1) ((sud/D a) 1) 1)
  (print-compar (g/* 3 4) (sug/* 3 4) 2)
  (print-compar (g/* 3 'x) (sug/* 3 'x) 3)
  (print-compar (g/+ 2 'x) (sug/+ 2 'x) 4)
  (print-compar (g/+ (g/* 2 'x) 3) (sug/+ (sug/* 2 'x) 3) 5))

#_(defn sub [t]
  (sug/+ (sug/* 2 t) 3))
#_(println ((sud/D sub) 4))

(defn b [t]
  (g/* (g/* t t) 3))
(println ((d/D b) 't))
