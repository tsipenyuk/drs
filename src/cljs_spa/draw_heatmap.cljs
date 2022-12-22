(ns cljs-spa.draw-heatmap
  (:require
   [cljs-spa.select-color :as sc]))

(defn draw-heatmap-ui [v-2d canvas-box colormap]
  (let [[xmin ymin xmax ymax] canvas-box
        delta-x (- xmax xmin)
        delta-y (- ymax ymin)
        nx (count (peek v-2d))
        ny (count v-2d)
        h-x (/ delta-x nx)
        h-y (/ delta-y ny)
        hh-x (/ h-x 2)
        hh-y (/ h-y 2)
        bg-color (sc/select-bg-color colormap)]
    [:g
     (for [ix (range nx) iy (range ny)]
       (let [x (+ xmin hh-x (* h-x ix))
             y (- ymax hh-y (* h-y iy))
             v (nth (nth v-2d iy) ix)
             c (sc/select-color colormap v)]
         (if (not (= bg-color c))
           ^{:key (str "ix-" ix "--iy-" iy)}
           [:rect
            {:x x :y y
             :width h-x :height h-y
             :style {:fill c}
             }])))]))
