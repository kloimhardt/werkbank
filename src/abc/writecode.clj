(ns abc.writecode
  (:require [clojure.java.io :as io]
            [clojure.walk :as w]))

(w/prewalk #(if (number? %) % nil) '(u (lahatom 3)))

(def matrix [[1 2 3]
             [4 5 6]
             [7 8 9]])
(def matrix '(u (lahatom 3)))
(w/prewalk #(if (number? %) (inc %) %) matrix)
(w/postwalk #(if (number? %) (inc %)) matrix)

(defn condition [b]
  (and (seq? b) (= 'lahatom (first b))))

(defn filter-lahatom [u]
  (if (and (coll? u) (seq u))
    (let [w (first u)]
      (if (seq? w)
        (if (= 'lahatom (first w))
          (conj (filter-lahatom (rest u))
                (list 'def (second w) (list 'atom (last w))))
          (concat (filter-lahatom w) (filter-lahatom (rest u)) ))
        (filter-lahatom (rest u))))
    '()))

#_(filter-lahatom '[(lahatom aa 9) (when 4 (lahatom bb 4))])

(defn replace-lahatom [u]
  (w/prewalk #(if (condition %) (list 'deref (second %)) %) u))

#_(replace-lahatom '[(lahatom aa 9) (when 4 (lahatom bb 4))])

(defn fu [a]
  (reduce #(if (condition %2)
             (conj %1 (list 'def (second %2) (list 'atom (last %2))))
             %1)
          [] a))
(comment
  (defn fo [a]
    (map #(if (condition %)
            (list 'deref (second %)) %)
         a))

  (defn fa [a]
    (conj (fu a) (list 'defn 'cx ['frame] (conj (fo a) 'list)))))

(defn build-lahabra-code [a]
  (conj (vec (filter-lahatom a))
        (list 'defn 'cx ['frame] (conj (seq (replace-lahatom a)) 'list))))

#_(build-lahatom-code '[(lahatom aa 9) (when 4 (lahatom bb 4))])

;;modeled after app-server/write-xml
(defn write-la-habra [la-habra-vector]
  (let [a (build-lahabra-code la-habra-vector)
        d "code_template/h_temp.clj"]
    (spit d ";;this is a compter generated file")
    (spit d (slurp "code_template/la_habra_trunk_1.cljs"))
    (doseq [x a]
      (spit d "\n" :append true)
      (spit d x :append true))
    (spit d "\n" :append true)
    (spit d (slurp "code_template/la_habra_trunk_2.cljs") :append true)
    (copy-file d "src/abc/la_habra.cljs")))
