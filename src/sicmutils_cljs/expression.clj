(ns sicmutils-cljs.expression
  (:require [sicmutils-cljs.value :as v]))

(defrecord Expression [type expression]
  Object
  (toString [_] (str expression))
  v/Value
  (nullity? [_] false)  ;; XXX what if it's a wrapped zero? one?
  (unity? [_] false)
  (zero-like [_] 0)
  (numerical? [_] (= type ::numerical-expression))
  (exact? [_] false)
  (freeze [_] (v/freeze expression))
  (kind [_] type))

(defn literal-number
  [expression]
  (if (number? expression)
    expression
    (Expression. ::numerical-expression expression)))
