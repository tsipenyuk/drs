(ns cljs-spa.draw
  (:require
   [cljs-spa.data.gauss5x5 :as gdata]
   [cljs-spa.solarized :as ts]))


(defonce tdata [[0 1] [2 3]])

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
      :style="fill: rgb(211, 233, 228);"}
     ]
    ]
   ]

  )
