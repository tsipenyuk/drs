(ns cljs-spa.draw-coordinate-conversion)

(defn rel2abs [[xmin ymin xmax ymax] [xrel yrel]]
  (let [
        delta-x (* (- xmax xmin) xrel)
        delta-y (* (- ymax ymin) yrel)
        ]
    [(+ xmin delta-x) (+ ymin delta-y)]))
