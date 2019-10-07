(ns abc.writecode
  (:require [clojure.java.io :as io]
            [clojure.walk :as w]))

(comment ;klm delete
  (defn condition [b]
    (and (seq? b) (#{'def 'defonce} (first b)))))

(def symbol-set #{'def 'defonce})

(defn filter-lahatom [u]
  (if (and (coll? u) (seq u))
    (let [fu (first u)
          ru (rest u)]
      (if (coll? fu)
        (if (symbol-set (first fu))
          (cons fu (filter-lahatom ru))
          (concat (filter-lahatom fu) (filter-lahatom ru)))
        (filter-lahatom ru)))
    '()))

#_(filter-lahatom '[(def aa 9) (when 4 (defonce bb 4))])

#_(defn replace-lahatom [u]
    (w/prewalk #(if (and (coll? %) (symbol-set (first %)))
                  (list 'deref (second %)) %) u))
(defn replace-lahatom [u]
  (w/prewalk #(if (and (coll? %) (symbol-set (first %)))
                (second %) %) u))

#_(replace-lahatom '[(def aa 9) (when 4 (defonce bb 4))])

(comment ;;klm delete
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
      (conj (fu a) (list 'defn 'cx ['frame] (conj (fo a) 'list))))))

(defn build-lahabra-code [a]
  (conj (vec (filter-lahatom a))
        (list 'defn 'cx ['frame] (cons 'list (seq (replace-lahatom a))))))

#_(build-lahatom-code '[(lahatom aa 9) (when 4 (lahatom bb 4))])

(defn copy-file [source-path dest-path]
  (io/copy (io/file source-path) (io/file dest-path)))

;;modeled after app-server/write-xml
(defn write-la-habra [la-habra-vector]
  (let [a (build-lahabra-code la-habra-vector)
        d "code_template/h_temp.clj"]
    (do
      (println "start write-la-habra")
      (spit d ";;this is a computer generated file")
      (spit d (slurp "code_template/la_habra_trunk_1.cljs"))
      (doseq [x a]
        (spit d "\n" :append true)
        (spit d x :append true))
      (spit d "\n" :append true)
      (spit d (slurp "code_template/la_habra_trunk_2.cljs") :append true)
      (copy-file d "src/abc/la_habra.cljs")
      (println "end write-la-habra, waiting 10 seconds")
      (Thread/sleep 9999)
      (println "continue")
      )))
