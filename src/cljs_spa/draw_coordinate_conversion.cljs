(ns cljs-spa.draw-coordinate-conversion
  (:require
   [cljs-spa.util :as u]))

(defn rel2abs [[xmin ymin xmax ymax] [xrel yrel]]
  (let [
        delta-x (* (- xmax xmin) xrel)
        delta-y (* (- ymax ymin) yrel)
        ]
    [(+ xmin delta-x) (+ ymin delta-y)]))


(defn two-dim-index2coord [canvas-box two-dim-array]
  (let [[xmin ymin xmax ymax] canvas-box
        delta-x (- xmax xmin)
        delta-y (- ymax ymin)
        nx (count (peek two-dim-array))
        ny (count two-dim-array)
        h-x (/ delta-x nx)
        h-y (/ delta-y ny)
        hh-x (/ h-x 2)
        hh-y (/ h-y 2)]
    (u/mapv-2d
     (fn [item]
       {
        :val (:val item)
        :x (+ xmin hh-x (* h-x (:x item)))
        :y (+ ymin hh-y (* h-y (:y item)))
        :width h-x
        :height h-y
        })
     two-dim-array)))
