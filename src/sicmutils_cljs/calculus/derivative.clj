(ns sicmutils-cljs.calculus.derivative
  (:require [sicmutils-cljs.generic :as g]
            [sicmutils-cljs.operator :as o]
            [sicmutils-cljs.value :as v]
            [sicmutils-cljs.matrix :as matrix]
            [sicmutils-cljs.structure :as struct]
            [sicmutils-cljs.series :as series]
            [clojure.string :refer [join]]
            [sicmutils.calculus.derivative :as self])
  (:import (clojure.lang Sequential)))

(def ^:private make-differential-tag
  (let [next-differential-tag (atom 0)]
    #(swap! next-differential-tag inc)))

(defn ^:private tag-in?
  "Return true if t is in the tag-set ts"
  [ts t]
  (some #(= % t) ts))

(defn ^:private tag-without
  "Return the tag set formed by dropping t from ts"
  [ts t]
  (filterv #(not= % t) ts))

(def ^:private empty-differential [])

;; A differential term is implemented as a pair whose first element is
;; a set of tags and whose second is the coefficient.
(def ^:private tags first)
(def ^:private coefficient second)

(declare differential-of)

;; A differential is a sequence of differential terms, ordered by the
;; tag set.
(deftype Differential [terms]
  v/Value
  (nullity? [_] (every? v/nullity? (map coefficient terms)))
  (unity? [_] false)
  (zero-like [_] 0)
  (freeze [_] `[~'Differential ~@terms])
  (exact? [_] false)
  (numerical? [d] (g/numerical-quantity? (differential-of d)))
  (kind [_] ::differential)
  Object
  (equals [_ b]
    (and (instance? Differential b)
         (let [^Differential bd b]
           (= terms (.terms bd)))))
  (toString [_] (str "D[" (join " " (map #(join " → " %) terms)) "]")))

(defn differential-of
  "The differential of a quantity is, if we're a differential, the differential
  of the coefficient of the highest-order term part, or else the input itself."
  [^Differential dx]
  (loop [dx dx]
    (if (instance? Differential dx)
      (recur (coefficient (last (.terms dx))))
      dx)))

(defn make-differential
  "The input here is a mapping (loosely defined) between sets of
  differential tags and coefficients. The mapping can be an actual
  map, or just a sequence of pairs. The differential tag sets are
  sequences of integer tags, which should be sorted."
  [tags->coefs]
  (Differential.
    (into empty-differential
          (sort-by first
                   (for [[tags tags-coefs] (group-by tags tags->coefs)
                         :let [c (reduce #(g/+ %1 (coefficient %2)) 0 tags-coefs)]
                         :when (not (v/nullity? c))]
                     [tags c])))))


(defn canonicalize-differential
  [^Differential dx]
  (let [ts (.terms dx)]
    (cond (empty? ts) 0
          (and (= (count ts) 1)
               (empty? (tags (first ts)))) (coefficient (first ts))
          :else dx)))

(defn differential?
  [x]
  (cond (instance? Differential x) true
        (struct/structure? x) (some differential? x)
        (matrix/matrix? x) (matrix/matrix-some differential? x)
        :else false))

(defn ^:private extract-dx-part [tag obj]
  (letfn [(extract
            ;; Collect all the terms of the differential in which
            ;; dx is a member of the term's tag set; drop that
            ;; tag from each set and return the differential formed
            ;; from what remains.
            [obj]
            (if (differential? obj)
              (let [^Differential d obj]
                (canonicalize-differential
                 (make-differential
                   (for [[tags coef] (.terms d)
                         :when (tag-in? tags tag)]
                     [(tag-without tags tag) coef]))))
              0))
          (dist
            [obj]
            (cond (struct/structure? obj) (struct/mapr dist obj)
                  (matrix/matrix? obj) (matrix/fmap dist obj)
                  (series/series? obj) (series/fmap dist obj)
                  (o/operator? obj) (do (throw (IllegalArgumentException. "can't differentiate an operator yet"))
                                        (extract obj))      ;; TODO: tag-hiding
                  ;; (quaternion? obj) XXX
                  ;; ((operator? obj)
                  ;;  (hide-tag-in-procedure dx
                  ;;                         (g:* (make-operator dist 'extract (operator-subtype obj))
                  ;;                              obj)))
                  ;; note: we had ifn? here, but this is bad, since symbols and
                  ;; keywords implement IFn.
                  (fn? obj) #(dist (obj %))                ;; TODO: innocent of the tag-hiding business
                  :else (extract obj)))]
    (dist obj)))

(def ^:private empty-tags [])

(defn ^:private differential->terms
  "Given a differential, returns the vector of DifferentialTerms
  within; otherwise, returns a singleton differential term
  representing d with an empty tag list (unless d is zero, in
  which case we return the empty term list)."
  [dx]
  (cond (instance? Differential dx) (let [^Differential d dx] (.terms d))
        (v/nullity? dx) []
        :else [[empty-tags dx]]))

(defn dx+dy
  "Adds two objects differentially. (One of the objects might not be
  differential; in which case we lift it into a trivial differential
  before the addition.)"
  [dx dy]
  (Differential.
   ;; Iterate and build up the result while preserving order and dropping zero sums.
   (loop [dxs (differential->terms dx)
          dys (differential->terms dy)
          result []]
     (cond
       (empty? dxs) (into result dys)
       (empty? dys) (into result dxs)
       :else (let [[a-tags a-coef :as a] (first dxs)
                   [b-tags b-coef :as b] (first dys)
                   c (compare a-tags b-tags)]
               (cond (= c 0) (let [r-coef (g/+ a-coef b-coef)]
                               (recur (rest dxs) (rest dys)
                                      (if-not (v/nullity? r-coef)
                                        (conj result [a-tags r-coef])
                                        result)))
                     (< c 0) (recur (rest dxs) dys (conj result a))
                     :else (recur dxs (rest dys) (conj result b))))))))

(defn ^:private make-x+dx [x dx]
  (dx+dy x (Differential. [[[dx] 1]])))

(defn derivative
  [f]
  (fn [x]
    (let [dx (make-differential-tag)]
      (extract-dx-part dx (f (make-x+dx x dx))))))

(defn  euclidean-structure ;;klm ^:private removed
  [selectors f]
  (letfn [(structural-derivative [g v]
            (cond (struct/structure? v)
                  (struct/opposite v
                                   (map-indexed
                                    (fn [i v_i]
                                      (structural-derivative
                                       (fn [w]
                                         (g (struct/structure-assoc-in v [i] w)))
                                       v_i))
                                    v))


                  (or (g/numerical-quantity? v) (g/abstract-quantity? v))
                  ((derivative g) v)

                  :else
                  (throw (IllegalArgumentException. (str "bad structure " g ", " v)))))
          (a-euclidean-derivative [v]
            (cond (struct/structure? v)
                  (structural-derivative
                   (fn [w]
                     (f (if (empty? selectors) w (struct/structure-assoc-in v selectors w))))
                   (get-in v selectors))

                  (empty? selectors)
                  ((derivative f) v)

                  :else
                  (throw (IllegalArgumentException. (str "Bad selectors " f selectors v)))))]
    a-euclidean-derivative))

;;---------------
(defn multivariate-derivative ;;klm ^:private removed
  [f selectors]
  (let [a (v/arity f)
        d (partial euclidean-structure selectors)
        make-df #(with-meta % {:arity a :from :multivariate-derivative})]
    (condp = a
      [:exactly 0] (make-df (constantly 0))
      [:exactly 1] (make-df (d f))
      [:exactly 2] (make-df (fn [x y]
                                ((d (fn [[x y]] (f x y)))
                                 (matrix/seq-> [x y]))))
      [:exactly 3] (make-df (fn [x y z]
                                ((d (fn [[x y z]] (f x y z)))
                                 (matrix/seq-> [x y z]))))
      [:exactly 4] (make-df (fn [w x y z]
                                ((d (fn [[w x y z]] (f w x y z)))
                                 (matrix/seq-> [w x y z]))))
      [:between 1 2] (make-df (fn [x & y]
                                  (if (nil? y)
                                    ((d f) x)
                                    ((d (fn [[x y]] (f x y)))
                                     (matrix/seq-> (cons x y))))))
      (fn [& xs]
        (when (empty? xs) (throw (IllegalArgumentException. "No args passed to derivative?")))
        (if (= (count xs) 1)
          ((d f) (first xs))
          ((d #(apply f %)) (matrix/seq-> xs)))))))

(defmethod g/partial-derivative
  [:sicmutils-cljs.function/function Sequential]
  [f selectors]
  (multivariate-derivative f selectors))

(comment
  (isa? :sicmutils-cljs.value/function :sicmutils-cljs.function/function) ;;true
  (isa? :sicmutils-cljs.function/function :sicmutils-cljs.value/function) ;;false
  (isa? (type []) Sequential) ;;true
  #_end)

(def D
  "Derivative operator. Produces a function whose value at some point can
  multiply an increment in the arguments, to produce the best linear estimate
  of the increment in the function value."
  (o/make-operator #(g/partial-derivative % []) 'D))

(defn ^:private define-binary-operation
  [generic-operation differential-operation]
  (doseq [signature [[::differential ::differential]
                     [:sicmutils.expression/numerical-expression ::differential]
                     [::differential :sicmutils.expression/numerical-expression]]]
    (defmethod generic-operation signature [a b] (differential-operation a b))))

(defn max-order-tag
  "From each of the differentials in the sequence ds, find the highest
  order term; then return the greatest tag found in any of these
  terms; i.e., the highest-numbered tag of the highest-order term."
  [ds]
  (->> ds (mapcat #(-> % differential->terms last tags)) (apply max)))

(defn with-and-without-tag
  "Split the differential into the parts with and without tag and return the pair"
  [tag dx]
  (let [{finite-terms nil infinitesimal-terms true}
        (group-by #(tag-in? (tags %) tag) (differential->terms dx))]
    [(-> infinitesimal-terms make-differential canonicalize-differential)
     (-> finite-terms make-differential canonicalize-differential)]))

(defn ^:private tag-union
  "The union of the sorted vectors ts and us."
  [ts us]
  (-> ts (concat us) sort dedupe vec))

(defn ^:private tag-intersection
  "Intersection of sorted vectors ts and us."
  [ts us]
  (loop [ts ts
         us us
         result []]
    (cond (empty? ts) result
          (empty? us) result
          :else (let [t (first ts)
                      u (first us)
                      c (compare t u)]
                  (cond (= c 0) (recur (rest ts) (rest us) (conj result t))
                        (< c 0) (recur (rest ts) us result)
                        :else (recur ts (rest us) result))))))

(defn dx*dy
  "Form the product of the differentials dx and dy."
  [dx dy]
  (make-differential
    (for [[x-tags x-coef] (differential->terms dx)
          [y-tags y-coef] (differential->terms dy)
          :when (empty? (tag-intersection x-tags y-tags))]
      [(tag-union x-tags y-tags) (g/* x-coef y-coef)])))

(defn ^:private binary-op
  [f ∂f:∂x ∂f:∂y _kw]
  (fn [x y]
    (let [mt (max-order-tag [x y])
          [dx xe] (with-and-without-tag mt x)
          [dy ye] (with-and-without-tag mt y)
          a (f xe ye)
          b (if (and (number? dx) (zero? dx))
              a
              (dx+dy a (dx*dy dx (∂f:∂x xe ye))))
          c (if (and (number? dy) (zero? dy))
              b
              (dx+dy b (dx*dy (∂f:∂y xe ye) dy)))]
      (canonicalize-differential c))))

(def ^:private diff-+ (binary-op g/+ (constantly 1) (constantly 1) :plus))
(def ^:private diff-* (binary-op g/* (fn [_ y] y) (fn [x _] x) :times))

(define-binary-operation g/mul diff-*)
(define-binary-operation g/add diff-+)
