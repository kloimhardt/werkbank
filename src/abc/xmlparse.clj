(ns abc.xmlparse
  (:require [clojure.xml :as xml]))

(defn l-block [x]
  (let [type (get-in x [:attrs :type])
        s (get-in x [:content 0 :content 0])
        t (get-in x [:content 1 :content 0])]
    (condp = (subs (str type "___") 0 4)
      "vari" {:var s} ;;"variables_get"
      "num_" {:num s}
      "sym_" {:sym s}
      "text" {:text s}
      "funs"  {:fun s}
      "inli" {:inli s}
      "list" {:list s}
      "idfu" {:idfun [s t]}
      "pair" {:pair "p"}
      "map_" {:map "m"}
      "vect" {:vec "v"}
      "let_" {:let "l"}
      "args" {:args "args"}
      {})))

;;generate EDN with the keywords coded in l-block + :dat
(defn level1b [x]
  (if (map? x)
    (if (#{:block :xml :value :next :statement} (:tag x))
      (let [a (filter #(not= {} %) (mapv level1b (:content x)))
            b (if (= :block (:tag x)) (l-block x) {})]
        (if (empty? a) b (assoc b :dat (into [] a))))
      {})
    x))

;;delete empty nodes in the middle of tree
(defn level2a [x]
  (let [a (mapv level2a (:dat x))]
    (if (and (= 1 (count x)) (= 1 (count a)))
      (first a)
      (if (empty? a)
        (dissoc x :dat)
        (assoc x :dat a)))))

;;unnest pairs, make defn argument vector
(defn level3a [x]
  (let [a (mapv level3a (:dat x))]
    (if (empty? a)
      (dissoc x :dat)
      (cond
        (and (:pair x) (:pair (last a)))
        (assoc x :dat (vec (concat (butlast a) (:dat (last a)))))
        (and (= (:fun x) "defn") (:var (a 1)))
        (assoc x :dat [(a 0) {:args "args-1" :dat [(a 1)]} (a 2)])
        (and (= (:fun x) "fn") (:var (a 0)))
        (assoc x :dat [{:args "args-1" :dat [(a 0)]} (a 1)])
        :else (assoc x :dat a)))))

(defn level4a [x] ;;generate code
  (let [a (mapv level4a (:dat x))]
    (cond
      (:var x) (symbol (:var x))
      (:num x) (read-string (:num x))
      (:sym x) (symbol (:sym x))
      (:text x) (:text x)
      (:fun x) (cons (symbol (:fun x)) a)
      (:inli x) (cons (symbol (:inli x)) a)
      (:list x) (apply list a)
      (:idfun x) (let [v (:idfun x)] (cons (symbol (v 0)) (cons (v 1) a)))
      (:pair x) a
      (:map x) (into {} (map vec (partition 2 (first a))))
      (:vec x) (first a)
      (:let x) (cons 'let a)
      (:args x) a
      (empty? a) (dissoc x :dat)
      :else  (assoc x :dat a))))

(defn parse [xml-str]
  (-> (java.io.ByteArrayInputStream. (.getBytes xml-str))
      xml/parse
      level1b
      level2a
      level3a
      level4a))
