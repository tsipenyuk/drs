(ns cljs-spa.draw
  (:require
   [cljs-spa.draw-heatmap :as dh]
   [cljs-spa.select-color :as sc]
   [cljs-spa.colormaps :as c]))

(defn svg-heatmap-ui [viewbox heatmaps]
  (let [data (:value (first @heatmaps))
        viewbox-css (apply str (map (fn [x] (str x " ")) viewbox))
        colormap c/red-blue-8
        bg-color (sc/select-bg-color colormap)]
      [:div.svg-container
       [:svg.svg-content
        {:preserveAspectRatio "xMinYMin meet"
         :viewBox viewbox-css
         :style {:background-color bg-color}}
        (dh/draw-heatmap-ui data viewbox colormap)
        [:text.slopegraph-header
         {:x (str (/ (nth viewbox 2) 2)) :y "10"
          :style {:text-anchor "middle" :dominant-baseline "hanging"}}
         (:name (first @heatmaps))]]]))

(defn svg-heatmap-test [a b]
  [:div.svg-container
   [:svg.svg-content
    {:preserveAspectRatio "xMinYMin meet"
     :viewBox "0 0 800 600"}
    '(
      ^{:key "text"}
      [:text.slopegraph-header
       {:x "50"
        :y "15"}
       "I welcome thee."]
      ^{:key "first"}
      [:rect#a {:x 440 :y 350 :width 40 :height 50
                :style {:fill "#fff"}}]
      ^{:key "second"}
      [:rect#b {:x 400 :y 300 :width 40 :height 50
                :style {:fill "#cb4b16"}}])]])
