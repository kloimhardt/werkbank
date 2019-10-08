(ns sicmutils-cljs.generic-cljs-macros)

(defmacro ^:private def-generic-function
  "Defines a mutlifn using the provided symbol. Arranges for the multifn
  to answer the :arity message, reporting either [:exactly a] or
  [:between a b], according to the arguments given."
  [f a & b]
  (let [arity (if b `[:between ~a ~@b] [:exactly a])
        docstring (str "generic " f)]
    `(do
       (defmulti ~f ~docstring sicmutils-cljs.value/argument-kind)
       (defmethod ~f [Keyword] [k#] ({:arity ~arity :name '~f} k#)))))
