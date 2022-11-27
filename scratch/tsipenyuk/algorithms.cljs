(ns tsipenyuk.algorithms
  (:require
   [tsipenyuk.two-d-projections :as tp]
   ))

(defn get-er [x-set y-set]
  (fn [v] (tp/p-set (tp/p-set v x-set) y-set)))

(defn step-alg [alg-update]
  (fn [v]
      (do
        (println "yeah my yeah")
        (println v)
        (println (alg-update v))
        (alg-update v))))
