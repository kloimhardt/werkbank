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

(ns sicmutils.mechanics.lagrange
  (:refer-clojure :exclude [+ - * / zero?])
  (:require [sicmutils
             [generic :refer :all]
             [structure :refer :all]
             [function :refer :all]]
            [sicmutils.numerical.integrate :refer :all]
            [sicmutils.numerical.minimize :refer :all]
            [sicmutils.calculus.derivative :refer :all]))

(defn state->t
  "Extract the time slot from a state tuple"
  [s]
  (nth s 0))

(defn coordinate
  "A convenience function on local tuples. A local tuple describes
  the state of a system at a particular time: [t, q, D q, D^2 q]
  representing time, position, velocity (and optionally acceleration
  etc.) Returns the q element, which is expected to be a mapping
  from time to a structure of coordinates"
  [local]
  (nth local 1))

(defn coordinate-tuple
  [& xs]
  (apply up xs))

(defn velocity
  "See coordinate: this returns the velocity element of a local
  tuple (by convention, the 2nd element)."
  [local]
  (nth local 2))

(def velocity-tuple up)

;; The following are the functions that are defined in the SICM
;; book, but NOT in MIT Scmutils.  Marked here for possible future
;; relocation
;; ---------------------------------------------------------------

(defn L-free-particle
  "The lagrangian of a free particle of mass m. The Lagrangian
  returned is a function of the local tuple. Since the particle
  is free, there is no potential energy, so the Lagrangian is
  just the kinetic energy."
  [mass]
  (fn [[_ _ v]]
    (* 1/2 mass (square v))))

(defn L-harmonic
  "The Lagrangian of a simple harmonic oscillator (mass-spring
  system). m is the mass and k is the spring constant used in
  Hooke's law. The resulting Lagrangian is a function of the
  local tuple of the system."
  [m k]
  (fn [[_ q v]]
    (- (* 1/2 m (square v)) (* 1/2 k (square q)))))

(defn L-uniform-acceleration
  "The Lagrangian of an object experiencing uniform acceleration
  in the negative y direction, i.e. the acceleration due to gravity"
  [m g]
  (fn [[_ [_ y] v]]
    (- (* 1/2 m (square v)) (* m g y))))

(defn L-central-rectangular [m U]
  (fn [[_ q v]]
    (- (* 1/2 m (square v))
       (U (sqrt (square q))))))

(defn L-central-polar [m U]
  (fn [[_ [r] [rdot φdot]]]
    (- (* 1/2 m
          (+ (square rdot)
             (square (* r φdot))))
       (U r))))

;; ---- end of functions undefined in Scmutils --------

(defn ->L-state
  "Constructs a Lagrangian state, also knows as a local tuple"
  [t q v & as]
  (apply up t q v as))

(def ->local ->L-state)

(defn F->C [F]
  (fn [[t _ v :as local]]
    (->L-state t
               (F local)
               (+ (((∂ 0) F) local)
                  (* (((∂ 1) F) local) v)))))

(defn p->r
  "SICM p. 47. Polar to rectangular coordinates of state."
  [[_ [r φ]]]
  (up (* r (cos φ)) (* r (sin φ))))

(defn Gamma
  "Gamma takes a path function (from time to coordinates) to a state
  function (from time to local tuple)."
  ([q]
   (let [Dq (D q)]
     (with-meta
       (fn [t]
         (up t (q t) (Dq t)))
       {:arity [:exactly 1]})))
  ([q n]
   (let [Dqs (->> q (iterate D) (take (- n 1)))]
     (fn [t]
       (->> Dqs (map #(% t)) (cons t) (apply up))))))

(def Γ Gamma)

(defn Lagrangian-action
  [L q t1 t2]
  (definite-integral (compose L (Γ q)) t1 t2 :compile true))

(defn Lagrange-equations
  [Lagrangian]
  (fn [q]
    (- (D (compose ((∂ 2) Lagrangian) (Γ q)))
       (compose ((∂ 1) Lagrangian) (Γ q)))))

(defn linear-interpolants
  [x0 x1 n]
  (let [n+1 (inc n)
        dx (/ (- x1 x0) n+1)]
    (for [i (range 1 n+1)]
      (+ x0 (* i dx)))))

(defn Lagrange-interpolation-function
  [ys xs]
  (let [n (count ys)]
    (assert (= (count xs) n))
    (with-meta
      (fn [x]
        (reduce + 0
                (for [i (range n)]
                  (/ (reduce * 1
                             (for [j (range n)]
                               (if (= j i)
                                 (nth ys i)
                                 (- x (nth xs j)))))
                     (let [xi (nth xs i)]
                       (reduce * 1
                               (for [j (range n)]
                                 (cond (< j i) (- (nth xs j) xi)
                                       (= j i) (if (odd? i) -1 1)
                                       :else (- xi (nth xs j))))))))))
      {:arity [:exactly 1]})))

(defn Lagrangian->acceleration
  [L]
  (let [P ((∂ 2) L)
        F ((∂ 1) L)]
    (/ (- F
          (+ ((∂ 0) P)
             (* ((∂ 1) P) velocity)))
       ((∂ 2) P))))

(defn Lagrangian->state-derivative
  "The state derivative of a Lagrangian is a function carrying a state
  tuple to its time derivative."
  [L]
  (let [acceleration (Lagrangian->acceleration L)]
    (fn [[_ _ v :as state]]
      (up 1 v (acceleration state)))))

(defn qv->state-path
  [q v]
  #(up % (q %) (v %)))

(defn Lagrange-equations-first-order
  [L]
  (fn [q v]
    (let [state-path (qv->state-path q v)]
      (- (D state-path)
         (compose (Lagrangian->state-derivative L)
                  state-path)))))

(defn Lagrangian->energy
  [L]
  (let [P ((∂ 2) L)]
    (- (* P velocity) L)))

(defn osculating-path
  "Given a state tuple (of finite length), reconstitutes the initial
  segment of the Taylor series corresponding to the state tuple data
  as a function of t.  Time is measured beginning at the point of time
  specified in the input state tuple."
  [state0]
  (let [[t0 q0] state0
        k (count state0)]
    (fn [t]
      (let [dt (- t t0)]
        (loop [n 2 sum q0 dt-n:n! dt]
          (if (= n k)
            sum
            (recur (inc n)
                   (+ sum (* (nth state0 n) dt-n:n!))
                   (/ (* dt-n:n! dt) n))))))))

(defn Gamma-bar
  [f]
  (fn [local]
    ((f (osculating-path local)) (first local))))

(def Γ-bar Gamma-bar)

(defn Dt
  [F]
  (let [G-bar (fn [q]
                (D (compose F (Γ q))))]
    (Γ-bar G-bar)))

(defn Euler-Lagrange-operator
  [L]
  (- (Dt ((∂ 2) L)) ((∂ 1) L)))

(defn L-rectangular
  "Lagrangian for a point mass on with the potential energy V(x, y)"
  [m V]
  (fn [[_ [q0 q1] qdot]]
    (- (* 1/2 m (square qdot))
       (V q0 q1))))

(defn make-path
  "SICM p. 23n"
  [t0 q0 t1 q1 qs]
  (let [n (count qs)
        ts (linear-interpolants t0 t1 n)]
    (Lagrange-interpolation-function
      `[~q0 ~@qs ~q1]
      `[~t0 ~@ts ~t1])))

(defn parametric-path-action
  "SICM p. 23"
  [Lagrangian t0 q0 t1 q1]
  (fn [qs]
    (let [path (make-path t0 q0 t1 q1 qs)]
      (Lagrangian-action Lagrangian path t0 t1))))

(defn find-path
  "SICM p. 23. The optional parameter values is a callback which will report
  intermediate points of the minimization."
  [Lagrangian t0 q0 t1 q1 n & {:keys [observe]}]
  (let [initial-qs (linear-interpolants q0 q1 n)
        minimizing-qs
        (multidimensional-minimize
          (parametric-path-action Lagrangian t0 q0 t1 q1)
          initial-qs observe)]
    (make-path t0 q0 t1 q1 minimizing-qs)))

(defn s->r
  "SICM p. 83"
  [[_ [r θ φ] _]]
  (up (* r (sin θ) (cos φ))
      (* r (sin θ) (sin φ))
      (* r (cos θ))))
