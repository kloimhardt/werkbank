(ns sicmutils-cljs.numsymb
  (:require [sicmutils-cljs.expression :as x]
            [sicmutils-cljs.value :as v]
            [sicmutils-cljs.generic :as g])
  (:require-macros [sicmutils-cljs.numsymb-cljs-macros :refer [define-binary-operation]])
  #_(:import (clojure.lang Symbol)))


(derive Symbol ::x/numerical-expression)
(derive js/Number ::x/numerical-expression)
(defn ^:private numerical-expression
  [expr]
  (cond (number? expr) expr
        (symbol? expr) expr
        ;;(c/complex? expr) expr ;;klm
        (g/literal-number? expr) (:expression expr)
        :else (throw (js/Error. (str "unknown numerical expression type " expr)))))

(defn ^:private make-numsymb-expression [operator operands]
  (->> operands (map numerical-expression) (apply operator) x/literal-number))

(comment
  (defmacro ^:private define-binary-operation
    [generic-operation symbolic-operation]
    `(defmethod ~generic-operation [::x/numerical-expression
                                    ::x/numerical-expression]
       [a# b#]
       (make-numsymb-expression ~symbolic-operation [a# b#]))))

(defn ^:private is-expression?
  "Returns a function which will decide if its argument is a sequence
  commencing with s."
  [s]
  (fn [x] (and (seq? x) (= (first x) s))))

(def product? (is-expression? '*))

(def operands rest)

(defn mul [a b]
  (cond (and (number? a) (number? b)) (* a b)
        (number? a) (cond (v/nullity? a) a
                          (v/unity? a) b
                          (product? b) `(~'* ~a ~@(operands b))
                          :else `(~'* ~a ~b)
                          )
        (number? b) (cond (v/nullity? b) b
                          (v/unity? b) a
                          (product? a) `(~'* ~@(operands a) ~b)
                          :else `(~'* ~a ~b)
                          )
        (product? a) (cond (product? b) `(~'* ~@(operands a) ~@(operands b))
                           :else `(~'* ~@(operands a) ~b))
        (product? b) `(~'* ~a ~@(operands b))
        :else `(~'* ~a ~b)))

(define-binary-operation g/mul mul)

