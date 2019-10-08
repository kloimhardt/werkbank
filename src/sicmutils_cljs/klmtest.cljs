(ns sicmutils-cljs.klmtest
  (:refer-clojure :rename {zero? core-zero?})
  (:require [sicmutils-cljs.calculus.derivative :as d]
            [sicmutils-cljs.function-a]
            #_[sicmutils-cljs.numsymb]
            #_[sicmutils-cljs.generic]))

(enable-console-print!)
(defn a [t] t)
(defn b [a] (d/D a))
(println "klm scr test -------- " (b a))
(println "klm output2 -------- " ((d/D a) 1))

(type 's)
(isa? 's Symbol)
(type :k)
(isa? :k Keyword)
(isa? 3 number)
