(ns tsipenyuk.main-fig
  (:require
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

(defn scale-x [x main-fig-props]
  (let [xmin (:xmin @main-fig-props)
        xmax (:xmax @main-fig-props)]
    (* width (/ (- x xmin) (- xmax xmin)))))

(defn scale-y [y]
  (let [xmin (:xmin @main-fig-props)
        xmax (:xmax @main-fig-props)]
   (* height (/ (- y (ymin)) (- (ymax) (ymin)))))

(defn scale-number [x]
  (* width (/ x (- (:xmax @term) (:xmin @term)))))

(defn draw-ball [svg ball]
  (let [[x y r] ball]
    (-> svg
        (.append "circle")
        (attrs {
                "cx" (scale-x x)
                "cy" (scale-y y)
                "r" (scale-number r)
                "stroke" (:orange ts/solarized)
                "fill" (:orange ts/solarized)
                })
        )
    )
  )
