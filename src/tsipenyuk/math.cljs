(ns tsipenyuk.math)

;; add index to each vector entry
;; [5 7 12] -> [[0 5] [1 7] [2 12]]
(defn- add-index [v] (map vector (range (count v)) v))
(defn- print-and-ret [v]
  (do
    (println "print-and-ret")
    (println v)
    v))

(defn argvmin [v]
  (let [min-v (apply min v)
        ind-v (add-index v)
        is-min (fn [[ind val]] (= val min-v))]
  (filterv is-min ind-v)))

(defn argmin [v] (do (argvmin v) 0))
;; (defn abs2 [[x y]] (+ (* x x) (* y y)))
(defn abs2 [v]
  (let [square (fn [x] (* x x)) v2 (map square v)]
    (reduce + v2)))
(defn abs [v] (Math/sqrt (abs2 v)))
