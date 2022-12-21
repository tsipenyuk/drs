(ns cljs-spa.core-test
  (:require [clojure.core]
            [clojure.test :refer-macros
             [deftest testing is async use-fixtures]]
            [cljs-spa.add-index :as ai]
            [cljs-spa.colormaps :as c]
            [cljs-spa.draw :as d]
            [cljs-spa.draw-coordinate-conversion :as dcc]
            [cljs-spa.select-color :as sc]
            [cljs-spa.util :as u]
            ))

(deftest rel2abs
  (is (= [400 300] (dcc/rel2abs [0 0 800 600] [0.5 0.5])))
  (is (= [400 360] (dcc/rel2abs [0 0 800 600] [0.5 0.6]))))

(deftest add-index
  (is (= [{:val 1, :x 0} {:val 2, :x 1}]
         (ai/add-x [1 2])))
  (is (= [{:val 1, :x 0, :y 5} {:val 2, :x 1, :y 5}]
         (ai/add-constant-y (ai/add-x [1 2]) 5))
      (is (= [[{:val 1, :x 0, :y 0} {:val 2, :x 1, :y 0}]
              [{:val 3, :x 0, :y 1} {:val 4, :x 1, :y 1}]]
             (ai/add-xy [[1 2] [3 4]])))))

(deftest two-dim-index2coord
  (let [two-dim-index-array
        [[{:val 1, :x 0, :y 0} {:val 2, :x 1, :y 0}]
         [{:val 3, :x 0, :y 1} {:val 4, :x 1, :y 1}]]
        canvas-box [0 0 10 20]
        expected
        [[{:val 1, :x 2.5, :y 5, :width 5, :height 10} {:val 2, :x 7.5, :y 5, :width 5, :height 10}]
         [{:val 3, :x 2.5, :y 15, :width 5, :height 10} {:val 4, :x 7.5, :y 15, :width 5, :height 10}]]]
    (is (= expected
           (dcc/two-dim-index2coord canvas-box two-dim-index-array)))))

(deftest select-color
  (is (= "#f7f7f7" (sc/select-color c/red-blue-8 0)))
  (is (= "#b2182b" (sc/select-color c/red-blue-8 -10)))
  (is (= "#b2182b" (sc/select-color c/red-blue-8 10))))


(deftest draw-heatmap-pt-rect
  (is (= [:rect {:x 0.5 :y 0.5 :style "fill: rgb(211, 233, 228);"}] (d/draw-heatmap-pt-rect {:x 0.5 :y 0.5 :style "fill: rgb(211, 233, 228);" :val 3}))))

(deftest flatten-one-layer
  (is (= [1 2 3 4] (u/flatten-one-layer [[1] [2] [3] [4]]))))

(deftest draw-heatmap-g
  (is (= [:g {:width 5, :height 5, :style {:transform "translate(0.5px,0px)"}} '([:rect {:val 0.1, :x 3.5, :y 2.5, :width 5, :height 5, :style {:fill "#d6604d"}}] [:rect {:val 0.2, :x 8.5, :y 2.5, :width 5, :height 5, :style {:fill "#d6604d"}}] [:rect {:val 1, :x 3.5, :y 7.5, :width 5, :height 5, :style {:fill "#b2182b"}}] [:rect {:val 2, :x 8.5, :y 7.5, :width 5, :height 5, :style {:fill "#b2182b"}}])]
         (d/draw-heatmap-g [1 0 11 10] [[0.1 0.2] [1 2]]))))
