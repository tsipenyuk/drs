(ns cljs-spa.draw
  (:require
   [cljs-spa.data.gauss5x5 :as gdata]
   [cljs-spa.add-index :as ai]
   [cljs-spa.draw-coordinate-conversion :as dcc]
   [cljs-spa.select-color :as sc]
   [cljs-spa.colormaps :as c]
   [cljs-spa.util :as u]
   [cljs-spa.solarized :as ts]))


(defonce tdata [[0 1] [2 3]])

(defn draw-heatmap-pt-rect [{x :x y :y style :style}]
    [:rect {:x x :y y :style style}])

(defn draw-heatmap-g [canvas-box v-2d]
  (let [[xmin ymin xmax ymax] canvas-box
        delta-x (- xmax xmin)
        delta-y (- ymax ymin)
        nx (count (peek v-2d))
        ny (count v-2d)
        h-x (/ delta-x nx)
        h-y (/ delta-y ny)
        v-2d-with-index (ai/add-xy v-2d)
        v-2d-with-coord (dcc/two-dim-index2coord canvas-box v-2d-with-index)
        v-2d-with-color (sc/add-color c/red-blue-8 v-2d-with-coord)
        v-2d-with-rect (u/mapv-2d draw-heatmap-pt-rect v-2d-with-color)
        flatten-rect (u/flatten-one-layer v-2d-with-rect)
        listed-rect (apply list flatten-rect)
        ]
    [:g
     {:width h-x
      :height h-y}
     listed-rect]))

(defn svg-heatmap-ui []
  [:div.svg-container
   [:svg.svg-content
    {:preserveAspectRatio "xMinYMin meet"
     :viewBox "0 0 800 600"
     }
    [:text.slopegraph-header
     {:x "50"
      :y "15"}
     "I welcome thee."
     ]
    [:rect
     {:x 400
      :y 300
      :width 40
      :height 50
      :style "fill: rgb(211, 233, 228);"}
     ]
    ]
   ]

  )
