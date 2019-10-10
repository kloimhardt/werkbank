(ns sicmutils-cljs.generic
  (:refer-clojure :exclude [partial zero? + - * / ref] :rename {* clojure* + clojure+})
  (:require [sicmutils-cljs.value :as v]
            [sicmutils-cljs.expression :as x])
  (:require-macros [sicmutils-cljs.generic-cljs-macros :refer [def-generic-function]]))


(defn numerical-quantity? [& a]
  (let [erg nil]
    (println "klm jvm generic 1 never call" erg)))

(defn abstract-quantity? [& a]
  (println "klm jvm generic 2 never call"))

#_(defn + [& a]
  (let [erg 1]
    (if (= erg 1)
      1
      (do
        (println "klm jvm generic 3 never call " erg)
        erg))))

(defmulti partial-derivative v/argument-kind)

(comment
  (defmacro ^:private def-generic-function
    "Defines a mutlifn using the provided symbol. Arranges for the multifn
  to answer the :arity message, reporting either [:exactly a] or
  [:between a b], according to the arguments given."
    [f a & b]
    (let [arity (if b `[:between ~a ~@b] [:exactly a])
          docstring (str "generic " f)]
      `(do
         (defmulti ~f ~docstring v/argument-kind)
         (defmethod ~f [Keyword] [k#] ({:arity ~arity :name '~f} k#))))))

(defmulti add v/argument-kind)
(defmethod add [Keyword] [k] ({:arity 2 :name 'add} k))
#_(def-generic-function add 2)

(defmulti mul v/argument-kind)
(defmethod mul [Keyword] [k] ({:arity 2 :name 'mul} k))
#_(def-generic-function mul 2)

(defn ^:private bin* [a b]
  (cond (and (number? a) (number? b)) (clojure* a b) ;;klm changed from *' to clojure*
        (and (number? a) (v/nullity? a)) (v/zero-like b)
        (and (number? b) (v/nullity? b)) (v/zero-like a)
        (v/unity? a) b
        (v/unity? b) a
        :else (mul a b)))

(defn * [& args]
  (reduce bin* 1 args))

(defn ^:private bin+ [a b]
  (cond (and (number? a) (number? b)) (clojure+ a b) ;;klm changed from +' to clojure+
        (v/nullity? a) b
        (v/nullity? b) a
        :else (add a b)))

(defn + [& args]
  (reduce bin+ 0 args))

(defn literal-number?
  [x]
  (= (:type x) ::x/numerical-expression))
