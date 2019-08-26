(ns abc.code
  (:refer-clojure :exclude [partial zero? + - * / ref])
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as ts]
            [sicmutils.env :refer :all :as env]
            [sicmutils.mechanics.lagrange :as lg]
            [sicmutils.numerical.minimize :as mn]
            [abc.app-server :as app-server]))

(defn points->plot [paths x-axis-name y-axis-name]
  (let [coord-encoding (fn [coord] {:field coord :type "quantitative" :scale {:zero false}})
        paths (if (map? paths) paths (partition 2 paths))
        paths-to-data (flatten
                        (map (fn [[id data]]
                               (map (fn [[t x y z]]
                                      {:id id :t t :x x :y y :z z}) data)) paths))]
    {:$schema "https://vega.github.io/schema/vega-lite/v2.json"
     :data {:values paths-to-data}
     :encoding
     {:x (coord-encoding x-axis-name)
      :y (coord-encoding y-axis-name)}
     :layer
     [{:mark "line"
       :encoding
       {:order {:field "t" :type "temporal"}
        :color {:field "id" :type "nominal"}}}
      {:mark {:type "point" :filled true}}]}))

(defn alt-range [t0 t1 nofsteps]
    (map float (flatten [t0 (linear-interpolants t0 t1 (- nofsteps 2)) t1])))

(defn make-path-txyz [fn_t t0 t1 nofsteps]
  (mapv #(cons % (.v (fn_t %)))
        (alt-range t0 t1 nofsteps)))

(defn make-path-tz [fn_t t0 t1 nofsteps]
  (map #(vector % 0 0 (fn_t %)) (alt-range t0 t1 nofsteps)))

(defn points-xy->plot [paths]
  (points->plot paths "x" "y"))

(defn points-xz->plot [paths]
  (points->plot paths "x" "z"))

(defn points-tz->plot [paths]
  (points->plot paths "t" "z"))

(comment
  (def werte {"sb" [[0 7 5 1] [1 11 8 10]]
              "uv" [[2 9 2 4] [3 3  9 7]]})

  (-> (points-xz->plot werte)
      vega-lite))

(defn send-to-page [id typ data]
  (let [cmd-text "" ]
    (app-server/send-to-page-2 [[id [typ cmd-text data]]])))

(def ids (atom #{}))

(defn check-ids! [id]
  (let [new-id (cond-> id (@ids id) (str "(" (inc (count @ids)) ")"))]
    (do (swap! ids conj new-id) new-id)))

(defn vega-lite [id vega-data]
  (send-to-page (check-ids! id) :vega vega-data))

(defn div [id html-data]
  (send-to-page (check-ids! id) :div html-data))

(defmacro la-habra-code [] [:div])

(defmacro la-habra [reagent-component-vector]
  (app-server/write-la-habra reagent-component-vector)
  (send-to-page "la-habra" :la-habra (str reagent-component-vector))
  nil)

(defn vega [id points-fn data]
  (send-to-page (check-ids! id) :vega (points-fn data)))

(defn tex [id exp]
  (let [t (tex$ exp)]
    (send-to-page (check-ids! id) :tex (subs t 1 (dec (count t))))))

(defn tex-matrix [id lst]
  (send-to-page (check-ids! id)
                :tex
                (str "\\begin{matrix} "
                     (->> lst
                          (map tex$)
                          (map #(subs % 1 (dec (count %))))
                          (map #(str % " \\\\ "))
                          (apply str))
                     "\\end{matrix}")))

(defn print-text [id exp]
  (send-to-page (check-ids! id) :string exp))

(defmacro mute-print [id x] nil)
(defmacro mute-tex [id x] nil)
(defmacro mute-vega [id path-fn x] nil)

(print-text "reset-7236491003" "") ;;clear plot area in front-end
;;(print-text ">" "")
;;----------------------------------------------------------------------
(defn test-path [t] (up (+ (* 4 t) 7) (+ (* 3 t) 5) (+ (* 2 t) 1)))
(print-text "Lagran" (Lagrangian-action (lg/L-free-particle 3) test-path 0 10))
(def *-III *)
(defn make-eta [nu t1 t2] (fn [t] (*-III (- t t1) (- t t2) (nu t))))
(defn varied-free-particle-action [mass q nu t1 t2] (fn [epsilon] (let [eta (make-eta nu t1 t2)] (Lagrangian-action (lg/L-free-particle mass) (+ q (* epsilon eta)) t1 t2))))
(def varied-action-fn (varied-free-particle-action 3 test-path (up sin cos square) 0.0 10.0))
(print-text "varied-action" (varied-action-fn 0.01))
(print-text "minimized varied-action" (minimize varied-action-fn -2 1))
(defn path-along-x [t] (up (+ (* 5 t) 1) (+ (* 0 t) 0) (+ (* 0 t) 0)))
(vega "points1" points-xy->plot ["a straight x" (make-path-txyz path-along-x 0 10 8) "a book" (make-path-txyz test-path 0 10 8)])
(defn make-varied-path [epsilon t0 t1] (+ path-along-x (* epsilon (make-eta (up (fn [t] (* 0 t)) identity (fn [t] (* 0.5 (sin t)))) t0 t1))))
(def small-varied-path (make-varied-path 0.01 0 10))
(def large-varied-path (make-varied-path 0.02 0 10))
(print-text "LA compar" [(Lagrangian-action (lg/L-free-particle 3) path-along-x 0 10) (Lagrangian-action (lg/L-free-particle 3) small-varied-path 0 10) (Lagrangian-action (lg/L-free-particle 3) large-varied-path 0 10)])
(vega "points2" points-xy->plot ["path-along-x" (make-path-txyz path-along-x 0 10 8) "small-varied-path" (make-path-txyz small-varied-path 0 10 24) "large-varied-path" (make-path-txyz large-varied-path 0 10 32)])
(def pi-half (* 0.5 Math/PI))
(def initial-qs [0.1 0.1 0.2])
(def initial-path (lg/make-path 0 1.0 pi-half 0.0 initial-qs))
(defn parametric-path-action [Lagrangian t0 q0 t1 q1] (fn [qs] (let [path (lg/make-path t0 q0 t1 q1 qs)] (Lagrangian-action Lagrangian path t0 t1))))
(defn fnd-path [Lagrangian t0 q0 t1 q1 qs] (let [minimizing-qs (mn/multidimensional-minimize (parametric-path-action Lagrangian t0 q0 t1 q1) initial-qs)] (lg/make-path t0 q0 t1 q1 minimizing-qs)))
(defn free-path [mass] (fnd-path (lg/L-free-particle mass) 0.0 1.0 pi-half 0.0 initial-qs))
(defn harmonic-path [mass] (fnd-path (lg/L-harmonic mass 1.0) 0.0 1.0 pi-half 0.0 initial-qs))
(vega "tz" points-tz->plot ["initial-path" (make-path-tz initial-path 0 pi-half 16) "free-path" (make-path-tz (free-path 1.0) 0 pi-half 16) "harmonic-path" (make-path-tz (harmonic-path 1.0) 0 pi-half 16)])
(vega "diff" points-tz->plot ["diff" (make-path-tz (- cos (harmonic-path 1.0)) 0 pi-half 35)])
(def differential-operator-h (Lagrange-equations (lg/L-harmonic (quote m) (quote k))))
(def differential-equation-h (differential-operator-h (literal-function (quote q))))
(tex "motion harmonic:" (differential-equation-h (quote t)))
(def differential-operator-f (Lagrange-equations (lg/L-free-particle (quote m))))
(def differential-equation-f (differential-operator-f (literal-function (quote q))))
(tex "motion free:" (differential-equation-f (quote t)))
(def differential-equation-f (differential-operator-f (fn [t] (up (+ (* (quote a) t) (quote a0)) (+ (* (quote b) t) (quote b0)) (+ (* (quote c) t) (quote c0))))))
(tex "solution straight:" (differential-equation-f (quote t)))
(def differential-equation-h (differential-operator-h (fn [t] (* (quote A) (cos (+ (* t (quote omega)) (quote phi)))))))
(tex "solution sinusoid:" (differential-equation-h (quote t)))
(defn gravitational-energy [G M_1 m_2] (fn [r] (- (/ (*-III G M_1 m_2) r))))
(def differential-operator-p (Lagrange-equations (lg/L-central-polar (/ (* (quote M_1) (quote m_2)) (+ (quote M_1) (quote m_2))) (gravitational-energy (quote G) (quote M_1) (quote m_2)))))
(def differential-equation-p (differential-operator-p (fn [t] (up (quote a) (* (quote n) t)))))
(tex "Kepler:" (differential-equation-p (quote t)))
(def differential-operator-u (Lagrange-equations (lg/L-uniform-acceleration (quote m) (quote g))))
(def differential-equation-u (differential-operator-u (up (literal-function (quote x)) (literal-function (quote y)))))
(def differential-operator-r (Lagrange-equations (lg/L-central-rectangular (quote m) (literal-function (quote U)))))
(def differential-equation-r (differential-operator-r (up (literal-function (quote x)) (literal-function (quote y)))))
(def differential-operator-p (Lagrange-equations (lg/L-central-polar (quote m) (literal-function (quote U)))))
(def differential-equation-p (differential-operator-p (up (literal-function (quote r)) (literal-function (quote phi)))))
(tex-matrix "motions:" ["Uni:" (differential-equation-u (quote t)) "Rec:" (differential-equation-r (quote t)) "Pol:" (differential-equation-p (quote t))])
(def rectangular<-polar (F->C p->r))
(def local-polar (->local (quote t) (up (quote r) (quote phi)) (up (quote rdot) (quote phidot))))
(tex "velocity" (rectangular<-polar local-polar))
(def L-my-central-polar (lg/L-central-polar (quote m) (literal-function (quote U))))
(def L-my-alternate-central-polar (compose (lg/L-central-rectangular (quote m) (literal-function (quote U))) rectangular<-polar))
(tex-matrix "Lagrangian" ["lcp" (L-my-central-polar local-polar) "lacp" (L-my-alternate-central-polar local-polar)])
(def L-my-free-polar (compose (lg/L-free-particle (quote m)) rectangular<-polar))
(def polar<-rotating-polar (F->C (fn [[t [r theta]]] (up r (+ theta (* (quote Omega) t))))))
(def L-my-rotating-polar (compose L-my-free-polar polar<-rotating-polar))
(def polar<-rectangular (F->C (fn [[t [x y]]] (up (sqrt (+ (square x) (square y))) (atan (/ y x))))))
(def L-my-rotating-rectangular (compose L-my-rotating-polar polar<-rectangular))
(def local-rectangular (->local (quote t) (up (quote x) (quote y)) (up (quote xdot) (quote ydot))))
(def L-my-r-r-s (simplify (L-my-rotating-rectangular local-rectangular)))
(tex "Rotating Lagrange" L-my-r-r-s)
(defmacro make-fn [args1 args2] `(fn ~args1 (fn ~args2 ~L-my-r-r-s)))
(def L-my-rotating-rectangular (make-fn [m Omega] [[t [x y] [xdot ydot]]]))
(def differential-operator-r (Lagrange-equations (L-my-rotating-rectangular (quote m) (quote Omega))))
(def differential-equation-r (differential-operator-r (up (literal-function (quote x)) (literal-function (quote y)))))
(tex "eq" (differential-equation-r (quote t)))
(defn test-path-2d [t] (up (+ (* 3.0 t) 7.0) (+ (* 5.0 t) 11.0)))
(def path-2d-gamma (Gamma test-path-2d))
(def rotate-path (simplify (rectangular<-polar (polar<-rotating-polar (polar<-rectangular (path-2d-gamma (quote t)))))))
(def xy (nth rotate-path 2))
(def x (nth xy 1))
(def y (nth xy 2))
(defmacro P-fn [args1 args2] `(fn ~args1 (up (fn ~args2 ~x) (fn ~args2 ~y))))
(def rotationg-path-2d (P-fn [Omega] [t]))
(vega "id" points-xy->plot ["rotating path 2d" (make-path-txyz (rotationg-path-2d -3.0) 0 3 25)])
(def differential-operator-r (Lagrange-equations (L-my-rotating-rectangular 4.0 3.0)))
(def differential-equation-r (differential-operator-r (rotationg-path-2d -3.0)))
(tex "eq" (differential-equation-r 5.3))
(defn T-pend [m l gg ys] (fn [[t theta thetadot]] (let [vys (D ys)] (*-III 1/2 m (+ (square (* l thetadot)) (square (vys t)) (* 2 l (vys t) thetadot (sin theta)))))))
(defn V-pend [m l gg ys] (fn [[t theta thetadot]] (* m gg (- (ys t) (* l (cos theta))))))
(def L-pend (- T-pend V-pend))
(tex-matrix "id" (let [L-pend (L-pend (quote m) (quote l) (quote g) (literal-function (quote y))) differential-operator-p (Lagrange-equations L-pend) differential-equation-p (differential-operator-p (literal-function (quote theta)))] ["eq" (differential-equation-p (quote t)) "Lg" (L-pend (->local (quote t) (quote theta) (quote thetadot)))]))
(def rectangular-state (up (quote t) (up (quote x) (quote y) (quote z)) (up (quote xdot) (quote ydot) (quote zdot))))
(def spherical-state (up (quote t) (up (quote r) (quote theta) (quote phi)) (up (quote rdot) (quote thetadot) (quote phidot))))
(def ang-momentum-rectangular (fn [[t xyz v]] (nth (cross-product xyz (* (quote m) v)) 2)))
(def ang-momentum-spherical (compose ang-momentum-rectangular (F->C s->r)))
(tex-matrix "ang-momentum" ["rectangular" (ang-momentum-rectangular rectangular-state) "spherical" (ang-momentum-spherical spherical-state)])
(def T3-spherical (compose (lg/L-free-particle (quote m)) (F->C s->r)))
(def U (literal-function (quote U)))
(defn V3-spherical [[t [r] v]] (U r))
(def L3-central (- T3-spherical V3-spherical))
(tex-matrix "id" (let [D1 (partial 1) D2 (partial 2) D1-L3-central (D1 L3-central) D2-L3-central (D2 L3-central)] ["D1" (D1-L3-central spherical-state) "D2" (D2-L3-central spherical-state)]))
(tex-matrix "id" (let [Lagrangian->energy_L3-central (Lagrangian->energy L3-central)] ["T3-spherical" (T3-spherical spherical-state) "L3-energy" (Lagrangian->energy_L3-central spherical-state)]))
