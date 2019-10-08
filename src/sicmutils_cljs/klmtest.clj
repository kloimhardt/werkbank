(ns sicmutils-cljs.klmtest
  (:refer-clojure :rename {zero? core-zero?} :exclude [partial zero? + - * / ref])
  (:require [clojure.tools.namespace.repl :refer [refresh]]
            [clojure.tools.trace :as tr]

            [sicmutils.rational-function]
            [sicmutils.structure]
            [sicmutils.series]
            [sicmutils.polynomial]
            [sicmutils.numsymb]

            [sicmutils-cljs.generic :as g]
            [sicmutils-cljs.calculus.derivative :as d]
            [sicmutils-cljs.function]
            [sicmutils.generic :as sug]
            [sicmutils.calculus.derivative :as sud]
            [sicmutils.function]))
#_(refresh)

(defn print-compar [x y n]
  (println (str "klm" n) x (= x y)))

(defn a [t] t)

(comment
  (tr/trace-ns sicmutils.structure)
  (tr/trace-ns sicmutils.rational-function)
  (tr/trace-ns sicmutils.generic)
  (tr/trace-ns sicmutils.polynomial)
  (tr/trace-ns sicmutils.numsymb)
  (tr/trace-ns sicmutils.series)
  (tr/trace-ns sicmutils.calculus.derivative ))

(print-compar ((d/D a) 1) ((sud/D a) 1) 1)
#_(print-compar (g/* 3 4) (sug/* 3 4) 2)
#_(print-compar (g/* 3 'x) (tr/trace (sug/* 3 'x)) 3)

