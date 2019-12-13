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
