(ns tsipenyuk.main-svg
  (:require
   [reagent.core :as r]
   [tsipenyuk.solarized :as ts]
   ))

(defn main-svg [state]
  (let [props (r/cursor state [:main-svg-props])
        viewbox-size
        (str "0 0 " (:width @props) " " (:height @props))]
    (fn []
      [:div#main-fig
       {:style {:margin-left 10
                :border-style "solid"
                :border-width "medium"
                :border-color (:base2 ts/solarized)
                :background-color (:base3 ts/solarized)
                :width "95%"
                :max-width "700px"
                :margin "auto !important"
                }}
       [:div.svg-container
        {}
        [:svg.svg-content-responsive
         {:preserveAspectRatio "xMinYMin meet"
          :viewBox viewbox-size
          }
         ]
        ]])))
