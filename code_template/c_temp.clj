(ns abc.code
  (:refer-clojure :exclude [partial zero? + - * / ref])
  (:require [clojure.spec.alpha :as s]
            [clojure.spec.test.alpha :as ts]
            [sicmutils.env :refer :all :as env]
            [sicmutils.mechanics.lagrange :as lg]
            [sicmutils.numerical.minimize :as mn]
            [abc.app-server :as app-server]
            [abc.writecode :as writecode]))

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

(defmacro la-habra [reagent-component-vector]
  ;;(app-server/write-la-habra reagent-component-vector)
  (writecode/write-la-habra reagent-component-vector)
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
(la-habra [(do "blinking screen yellow black gery" (let [colors [midnight midnight midnight midnight yellow yellow white white]] (draw (style {:opacity 0.9} (gen-rect (val-cyc frame colors) 0 0 "100vw" "100%"))))) (comment "black grid" (when-not (nth-frame 8 frame) (gen-line-grid midnight 4 80 80 {:col 20, :row 20}))) (comment "blinking grey circle" (->> (gen-circ white (* 0.5 (clojure.core/deref width)) (* 0.5 (clojure.core/deref height)) (val-cyc frame (vct [100 100 100 100 200 200 200 200] [200 50 50 50 50 50 200 200 200 200] [200 200 200 200 100 100 100 100]))) (draw) (when (nth-frame 4 frame)))) (comment "grey oct" (->> (gen-shape (pattern (:id navy-lines)) oct) (style {:transform (str "translate(70vw, 10vh) scale(" (val-cyc frame (vct [.5 .5 .5 .5 .5 .5] [1 1 1 1 1 1] [.9 .9 .9 .9 .9 .9])) ")")}) (draw) (when (nth-frame 4 frame)))) (comment "moving green oct" (deref (def klmbb2 (->> (gen-shape mint oct) (style {:transform "translate(10vw, 30vh) scale(2) rotate(45deg)"}) (style {:mix-blend-mode "luminosity"}) (anim "woosh" "4s" "infinite") (draw) (atom))))) (comment "fast moving yellow oct" (deref (def klmbb4 (->> (gen-shape yellow oct) (style {:transform "translate(10vw, 30vh) scale(2) rotate(45deg)"}) (style {:mix-blend-mode "color-dodge"}) (anim "woosh" "2s" "infinite") (draw) (atom))))) (comment "lots of points" (when (nth-frame 7 frame) (freak-out (clojure.core/deref width) (clojure.core/deref height) 4 1000 gray))) (comment "blue circles" (when (nth-frame 6 frame) (deref (defonce klmb (scatter 10 (->> (gen-circ navy 10 10 60) (draw))))))) (comment "yellow circles -screen-" (when (nth-frame 8 frame) (deref (defonce klmd (scatter 10 (->> (gen-circ yellow 10 10 60) (style {:mix-blend-mode "screen"}) (draw))))))) (comment "yellow circles -overlay-" (when (nth-frame 6 frame) (deref (defonce klme (scatter 10 (->> (gen-circ yellow 10 10 60) (style {:mix-blend-mode "overlay"}) (draw))))))) (comment "red circles" (when (nth-frame 6 frame) (deref (defonce klmf (scatter 10 (->> (gen-circ pink 10 10 60) (style {:mix-blend-mode "multiply"}) (draw))))))) (comment "grey dotted circle" (->> (gen-circ (pattern (:id white-dots)) (* 0.5 (clojure.core/deref width)) (* 0.5 (clojure.core/deref height)) 200) (draw) (when (nth-frame 1 frame)))) (do "red shape fullscreen" (->> (gen-shape pink b2) (style {:transform "translate(20vw, 30vh) scale(2)"}) (draw))) (comment "reddish art" (deref (def klmbb (->> (->> (gen-grid 40 28 {:col 40, :row 40} (gen-shape mint oct)) (map (fn [x] (style {:mix-blend-mode "difference"} x))) (map (fn [x] (anim "supercolor" (str (rand-int 100) "s") "infinite" x))) (map draw) (map (fn [x] (gen-group {:style {:transform-origin "center", :transform (str "rotate(" (rand-int 360) "deg)" "scale(60) translate(-20vh, -20vh)")}} x)))) (atom))))) (do "blueish art" (doall (map deref (def levels1 (map-indexed (fn [idx color] (->> (gen-rect color -100 -100 "120%" "120%" (url "cutout")) (style {:opacity 0.4, :transform-origin "center", :transform (str (str "translate(" (- (rand-int 200) 100) "px, " (- (rand-int 300) 100) "px)") (str "rotate(" (- 360 (rand-int 720)) "deg)"))}) (anim "fade-in-out" "10s" "infinite" {:delay (str (* .1 idx) "s")}) (draw) (atom))) (take 10 (repeatedly (fn [] (nth [mint navy navy mint] (rand-int 6))))))))))])
