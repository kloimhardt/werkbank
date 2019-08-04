(ns abc.driven-pendulum
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
(print-text ">" "")
;;----------------------------------------------------------------------

(comment
  (defn hight-of-pivot [t]
    (+ Initial-Hight-of-Pivot (sin t)))

  (def Initial-Hight-of-Pivot (* 10 'meter))

  (defn hight-of-end [t y]
    (- (hight-of-pivot t) y))

  (defn V [t y]
    (* Mass g (hight-of-end t y)))

  (def Mass (* 5 'kg))
  (def g (* 9.81 'mpss))

  (defn T [xdot ydot]
    (* 1/2 Mass (+ (square xdot) (square ydot))))

  (defn Lagrangian [[t [x y] [xdot ydot]]]
    (- (T xdot ydot) (V t y)))) ;;fill the x last!!
;;---
(comment

  (def Initial-Hight-of-Pivot (* 10 'meter))
  (def Mass (* 5 'kg))
  (def g (* 9.81 'mpss))
  (def z (literal-function 'z))
  #_(def z (fn [t] 0))
  (defn hight-of-pivot [t]
    (+ Initial-Hight-of-Pivot (z t)))

  (defn hight-of-end [t y]
    (- (hight-of-pivot t) y))

  (defn V [t y]
    (* Mass g (hight-of-end t y)))

  (defn T [t xdot ydot]
    (* 1/2 Mass (+ (square xdot) (square (+ ydot ((D hight-of-pivot) t))))))

  (defn Lagrangian [[t [x y] [xdot ydot]]]
    (-  (T t xdot ydot)  (V t y)))

  ;; (((Lagrange-equations Lagrangian) (up (literal-function 'x) (literal-function 'y))) 't)

  (def Initial-Hight-of-Pivot 'h)
  (def Mass 'm)
  (def g 'g)
  (def g 0)
  (def local-rectangular (->local (quote t) (up (quote x) (quote y)) (up (quote xdot) (quote ydot))))
  (Lagrangian local-rectangular)

  (tex "huuu" (simplify (((Lagrange-equations Lagrangian) (up (literal-function 'x) (literal-function 'y))) 't)))

  (defn rectangular<-phi [[t [phi]]]
    (up (* 'l (sin phi))
        (* 'l (cos phi))))

  (tex "hi" (simplify (((Lagrange-equations (compose Lagrangian (F->C rectangular<-phi))) (up (literal-function 'phi))) 't)))

  (def local-polar (->local (quote t) (up (quote r) (quote phi)) (up (quote rdot) (quote phidot))))
  (tex "hu"   (simplify ((compose Lagrangian rectangular<-polar) local-polar)))
  )
