(ns sicmutils-cljs.value
  (:refer-clojure :rename {zero? core-zero?})
  (:require [sicmutils.value :as self])
  (:import (clojure.lang RestFn MultiFn Keyword Symbol)))

(defn reflect-on-arity [f]
  (let [erg  (self/reflect-on-arity f)]
    (if (= [:exactly 1] erg)
      [:exactly 1]
      (do (println "klm jvm value 1")
          erg))))

(defn arity
  "Return the cached or obvious arity of the object if we know it.
  Otherwise delegate to the heavy duty reflection, if we have to."
  [f]
  (or (:arity f)
      (:arity (meta f))
      (cond (symbol? f) [:exactly 0]
            ;; If f is a multifunction, then we expect that it has a multimethod
            ;; responding to the argument :arity, which returns the arity.
            (instance? MultiFn f) (f :arity)
            (fn? f) (reflect-on-arity f)
            ;; Faute de mieux, we assume the function is unary. Most math functions are.
            :else [:exactly 1])))

(def object-name-map (atom {})) ;;klm ^:private removed

(defprotocol Value
  (numerical? [this])
  (nullity? [this])
  (unity? [this])
  (zero-like [this])
  (one-like [this])
  (exact? [this])
  ;; Freezing an expression means removing wrappers and other metadata
  ;; from subexpressions, so that the result is basically a pure
  ;; S-expression with the same structure as the input. Doing this will
  ;; rob an expression of useful information fur further computation; so
  ;; this is intended to be done just before simplification and printing, to
  ;; simplify those processes.
  (freeze [this])
  (kind [this]))

(defn ^:private primitive-kind
  [a]
  (let [erg (cond
              (or (fn? a) (= (class a) MultiFn)) ::function
              :else (or (:type a)
                        (type a)))]
    erg))

(extend-type Object
  Value
  (nullity? [o] (and (number? o) (core-zero? o)))
  (numerical? [_] false)
  (unity? [o] (and (number? o) (== o 1)))
  (exact? [o] (or (integer? o) (ratio? o)))
  (zero-like [o] (cond (number? o) 0
                       (instance? Symbol o) 0
                       (or (fn? o) (instance? MultiFn o)) (with-meta
                                                            (constantly 0)
                                                            {:arity (arity o)
                                                             :from :object-zero-like})
                       :else (throw (UnsupportedOperationException. (str "zero-like: " o)))))
  (one-like [o] (cond (number? o) 1
                      :else (throw (UnsupportedOperationException. (str "one-like: " o)))))
  (freeze [o] (cond
                (vector? o) (mapv freeze o)
                (sequential? o) (map freeze o)
                :else (or (and (instance? MultiFn o)
                               (if-let [m (get-method o [Keyword])]
                                 (m :name)))
                          (@object-name-map o)
                          o)))
  (kind [o] (primitive-kind o)))

(def argument-kind
  (fn [& more]
    (mapv kind more))
  )
