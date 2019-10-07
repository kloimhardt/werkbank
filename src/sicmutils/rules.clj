;
; Copyright © 2017 Colin Smith.
; This work is based on the Scmutils system of MIT/GNU Scheme:
; Copyright © 2002 Massachusetts Institute of Technology
;
; This is free software;  you can redistribute it and/or modify
; it under the terms of the GNU General Public License as published by
; the Free Software Foundation; either version 3 of the License, or (at
; your option) any later version.
;
; This software is distributed in the hope that it will be useful, but
; WITHOUT ANY WARRANTY; without even the implied warranty of
; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
; General Public License for more details.
;
; You should have received a copy of the GNU General Public License
; along with this code; if not, see <http://www.gnu.org/licenses/>.
;

(ns sicmutils.rules
  (:require [pattern.rule :refer [ruleset rule-simplifier]]))

(defn ^:private more-than-two? [x] (and (number? x) (> x 2)))
(defn ^:private at-least-two? [x] (and (number? x) (>= x 2)))
(defn ^:private even-integer? [x] (and (number? x) (even? x)))
(defn ^:private odd-integer? [x] (and (number? x) (not (even? x))))

(def sin-sq->cos-sq
  (rule-simplifier
   (ruleset
    (expt (sin :x) (:? n at-least-two?))
    => (* (expt (sin :x) (:? #(- (% 'n) 2)))
          (- 1 (expt (cos :x) 2))))))

(def ^:private split-high-degree-cosines
  (ruleset
   (* :f1* (expt (cos :x) (:? n more-than-two?)) :f2*)
   => (* (expt (cos :x) 2)
         (expt (cos :x) (:? #(- (% 'n) 2)))
         :f1*
         :f2*)

   (+ :a1* (expt (cos :x) (:? n more-than-two?)) :a2*)
   => (+ (* (expt (cos :x) 2)
            (expt (cos :x) (:? #(- (% 'n) 2))))
         :a1*
         :a2*)))

(def ^:private split-high-degree-sines
  (ruleset
   (* :f1* (expt (sin :x) (:? n more-than-two?)) :f2*)
   => (* (expt (sin :x) 2)
         (expt (sin :x) (:? #(- (% 'n) 2)))
         :f1*
         :f2*)

   (+ :a1* (expt (sin :x) (:? n more-than-two?)) :a2*)
   => (+ (* (expt (sin :x) 2)
            (expt (sin :x) (:? #(- (% 'n) 2))))
         :a1*
         :a2*)))

(def simplify-square-roots
  (rule-simplifier
   (ruleset
    (expt (sqrt :x) (:? n even-integer?))
    => (expt :x (:? #(/ (% 'n) 2)))

    (sqrt (expt :x (:? n even-integer?)))
    => (expt :x (:? #(/ (% 'n) 2)))

    ;; Following are the new rules we added to approach
    ;; the simplification of the time-invariant-canonical
    ;; test.
    (expt (sqrt :x) (:? n odd-integer?))
    => (* (sqrt :x) (expt :x (:? #(/ (- (% 'n) 1) 2))))

    ;; ... (sqrt a) ... (sqrt b) ... => ... (sqrt a b)
    (* :f1* (sqrt :a) :f2* (sqrt :b) :f3*)
    => (* :f1* :f2* :f3* (sqrt (* :a :b)))

    ;; (/ (* ... (sqrt a) ...)
    ;;    (* ... (sqrt b) ...)  => ... (sqrt (/ a b)) ... / ... ...
    (/ (* :f1* (sqrt :a) :f2*)
       (* :g1* (sqrt :b) :g2*))
    => (/ (* :f1* :f2* (sqrt (/ :a :b)))
          (* :g1* :g2*))


    ;; others to follow
    )))

(def complex-trig
  ;; TODO: clearly more of these are needed.
  (rule-simplifier
   (ruleset
    (cos (* :z (complex 0.0 1.0)))
    => (cosh :z)

    (sin (* :z (complex 0.0 1.0)))
    => (* (complex 0.0 1.0) (sinh :z))

    ;; Does this really belong here?
    ;; It works by reducing n mod 4 and then indexing into [1 i -1 -i].
    (expt (complex 0.0 1.0) (:? n integer?))
    => (:? #([1 '(complex 0 1) -1 '(complex 0 -1)] (mod (% 'n) 4))))))

(def divide-numbers-through
  (rule-simplifier
   (ruleset
    (* 1 :factor)
    => :factor

    (* 1 :factors*)
    => (* :factors*)

    (/ (:? n number?) (:? d number?))
    => (:? #(/ (% 'n) (% 'd)))

    (/ (+ :terms*) (:? d number?))
    => (+ (:?? #(map (fn [n] `(~'/ ~n ~(% 'd))) (% :terms*)))))))

(def ^:private flush-obvious-ones
  (ruleset
   (+ :a1* (expt (sin :x) 2) :a2* (expt (cos :x) 2) :a3*)
   => (+ 1 :a1* :a2* :a3*))
  ;; are sines always before cosines after we poly simplify?
  ;; they are in scmutils, so we should be alert for this.
  ;; in scmutils, there are a couple of others that involve rcf:simplify,
  ;; which we dont' have, and we don't know if pcf:simplify is an
  ;; acceptable substitute here; and we don't have a method for
  ;; pasting the value of a predicate into a rule, so this is far from
  ;; complete.
  )

(def trig->sincos
  (rule-simplifier
   (ruleset
    ;; GJS has other rules: to map cot, sec and csc to sin/cos, but
    ;; I don't think we need those since we transform those to sin/cos
    ;; in the env namespace.
    (tan :x) => (/ (sin :x) (cos :x)))))

;; note the difference in interface between rulesets and rule simplifiers.
;; rulesets return nil when they're not applicable (unless you specify a
;; custom fail continuation). Rule-simplifiers pass expressions through.

(def sincos->trig
  (rule-simplifier
   (ruleset
    ;; undoes the effect of trig->sincos
    (/ (sin :x) (cos :x))
    => (tan :x)

    (/ (sin :x) (* :d1* (cos :x) :d2*))
    => (/ (tan :x) (* :d1* :d2*))

    (/ (* :n1* (sin :x) :n1*)
       (* :d1* (cos :x) :d2*))
    => (/ (* :n1* (tan :x) :n2*)
          (* :d1* :d2*)))))

(def triginv
  (rule-simplifier
   (ruleset
    (sin (asin :x))          => :x
    (asin (sin :x))          => :x
    (sin (atan :y :x))       => (/ :y (sqrt (+ (expt :x 2) (expt :y 2))))
    (cos (atan :y :x))       => (/ :x (sqrt (+ (expt :x 2) (expt :y 2))))
    (cos (asin :t))          => (sqrt (- 1 (square :t)))
    )
   (ruleset
    (acos (cos :x))          => :x
    (atan (tan :x))          => :x
    (atan (sin :x) (cos :x)) => :x
    (atan (* :c (sin :x)) (* :c (cos :x))) => :x)))

(def sincos-flush-ones (rule-simplifier split-high-degree-cosines
                                        split-high-degree-sines
                                        flush-obvious-ones))

(defn universal-reductions
  [x]
  (triginv x))

(def canonicalize-partials
  (rule-simplifier
    (ruleset
      ;; example: (((∂ 2 1) ((∂ 1 1) FF)) (up t (up x y) (down p_x p_y)))
      ;; since the partial indices in the outer derivative are lexically
      ;; greater than those of the inner, we canonicalize by swapping the
      ;; order. This is the "equality of mixed partials."
      (((∂ :i*) ((∂ :j*) :x)) :y*)
      #(> 0 (compare (% :i*) (% :j*)))
      (((∂ :j*) ((∂ :i*) :x)) :y*))))
