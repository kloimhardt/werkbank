(ns sicmutils-cljs.numsymb-cljs-macros)

(defmacro define-binary-operation
  [generic-operation symbolic-operation]
  `(defmethod ~generic-operation [:sicmutils-cljs.expression/numerical-expression
                                  :sicmutils-cljs.expression/numerical-expression]
     [a# b#]
     (make-numsymb-expression ~symbolic-operation [a# b#])))
