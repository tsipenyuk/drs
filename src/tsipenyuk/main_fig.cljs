(ns tsipenyuk.main-fig
  (:require
   [cljsjs.d3 :as d3]
   [clojure.string :as str]
   [goog.dom :as gdom]
   [reagent.core :as r]
   [reagent.dom :as rdom]
   [tsipenyuk.solarized :as ts]))

(defn main-fig []
  [:div
   {:id "main-fig"
    :style {:margin-left 10
            :border-style "solid"
            :border-width "medium"
            :border-color (:base2 ts/solarized)
            :background-color (:base3 ts/solarized)
            :width "95%"}}
   ])

;; svg
(defn remove-svg []
  (-> js/d3
      (.selectAll "#main-fig div")
      (.remove)))

(defn append-svg [fig-props]
  (let [viewbox-size
        (str "0 0 " (:width @fig-props)
             " " (:height @fig-props))]
    (-> js/d3
        (.select "#main-fig")
        (.append "div")
        (.classed "svg-container" true)
        (.append "svg")
        (.attr "preserveAspectRatio" "xMinYMin meet")
        (.attr "viewBox" viewbox-size)
        (.classed "svg-content-responsive" true))))
