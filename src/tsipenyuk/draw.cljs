(ns tsipenyuk.draw
  (:require
   [cljsjs.d3 :as d3]
   [tsipenyuk.solarized :as ts]))

(defn attrs [el m]
  (doseq [[k v] m]
    (.attr el k v)))

(defn ymax [fig-props]
  (let [delta-x (- (:xmax @fig-props) (:xmin @fig-props))]
    (/ (* delta-x (:height @fig-props))
       (* 2 (:width @fig-props)))))

(defn ymin [fig-props] (- 0 (ymax fig-props)))

(defn scale-x [x fig-props]
  (let [xmin (:xmin @fig-props)
        xmax (:xmax @fig-props)]
    (* (:width @fig-props) (/ (- x xmin) (- xmax xmin)))))

(defn scale-y [y fig-props]
  (let [
        xmin (:xmin @fig-props)
        xmax (:xmax @fig-props)
        height (:height @fig-props)
        yminv (ymin fig-props)
        ymaxv (ymax fig-props)
        ;; invert y for plotting so higher vals are "above"
        yflip (- 0 y)
        ]
    (* height (/ (- yflip yminv) (- ymaxv yminv)))))

(defn scale-number [x fig-props]
  (* (:width @fig-props) (/ x (- (:xmax @fig-props) (:xmin @fig-props)))))


(defn draw-ball [svg ball fig-props ball-color opac]
  (let [[x y r] ball]
    (-> svg
        (.append "circle")
        (attrs {
                "cx" (scale-x x fig-props)
                "cy" (scale-y y fig-props)
                "r" (scale-number r fig-props)
                "stroke" ball-color
                "fill" ball-color
                "opacity" opac
                })
        )
    )
  )

(defn draw-set [svg set fig-props set-color opac]
    (dorun
     (for [ball (:balls set)]
        (draw-ball svg ball fig-props set-color opac))))

(defn draw-sets [svg fp-sets fig-props]
  (let [x-set (:x-set @fp-sets)
        y-set (:y-set @fp-sets)]
    (do
      (draw-set svg x-set fig-props (:red ts/solarized) "1.0")
      (draw-set svg y-set fig-props (:blue ts/solarized) "1.0")
      (draw-set svg x-set fig-props (:red ts/solarized) "0.5")
      )
    ))
