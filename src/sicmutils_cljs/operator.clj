(ns sicmutils-cljs.operator
  (:require [sicmutils-cljs.value :as v]
            [sicmutils.operator :as self])
  (:import (clojure.lang IFn)))

(defrecord Operator [o arity name context]
  v/Value
  (freeze [_] (v/freeze name))
  (kind [_] (:subtype context))
  (nullity? [_] false)
  (unity? [_] false)
  IFn
  (invoke [_ f] (o f))
  (invoke [_ f g] (o f g))
  (invoke [_ f g h] (o f g h))
  (invoke [_ f g h i] (o f g h i))
  (applyTo [_ fns] (apply o fns)))

(defn operator?
  [x]
  (instance? Operator x))

(defn make-operator
  [o name & {:keys [] :as context}]
  (->Operator o (or (:arity context) [:exactly 1]) name (into {:subtype ::operator} context)))
