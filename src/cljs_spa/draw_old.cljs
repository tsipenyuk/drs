(ns cljs-spa.draw-old
  (:require
   [cljsjs.d3 :as d3]
   [cljs-spa.solarized :as ts]))

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


(defn draw-ball [svg ball settings]
  (let [[x y r] ball
        [fig-props ball-color opac] settings]
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

(defn draw-pt [svg pt settings]
  (let [[x y] pt
        [fig-props color opac] settings]
    (-> svg
        (.append "circle")
        (attrs {
                "cx" (scale-x x fig-props)
                "cy" (scale-y y fig-props)
                "r" "2"
                "stroke" color
                "fill" color
                "opacity" opac}))))

(defn draw-line [svg start end settings]
  (let [[x1 y1] start
        [x2 y2] end
        [fig-props color opac stroke-dasharray] settings]
    (-> svg
        (.append "line")
        (attrs {
                "x1" (scale-x x1 fig-props)
                "y1" (scale-y y1 fig-props)
                "x2" (scale-x x2 fig-props)
                "y2" (scale-y y2 fig-props)
                "stroke" color
                "opacity" opac
                "stroke-dasharray" stroke-dasharray}))))

;; draw a line with a little perp line ("hammer") at the end
(defn draw-hammer [svg start end h-size settings]
  (let [[x1 y1] start
        [x2 y2] end
        [fig-props color opac stroke-dasharray] settings
        [xdiff ydiff] [(- x2 x1) (- y2 y1)]
        [xrot yrot] [(- 0 ydiff) xdiff]
        rot-abs (Math/sqrt (+ (* xrot xrot) (* yrot yrot)))
        [xnrm ynrm] [(/ xrot rot-abs) (/ yrot rot-abs)]
        [xpt ypt] [(scale-x xnrm fig-props)
                   (scale-y ynrm fig-props)]
        hammer-size (Math/sqrt (+ (* xpt xpt) (* ypt ypt)))
        hammer-scale (/ hammer-size h-size)
        [xsc ysc] [(/ xnrm hammer-scale) (/ ynrm hammer-scale)]
        hammer-start [(+ x2 xsc) (+ y2 ysc)]
        hammer-end [(- x2 xsc) (- y2 ysc)]
        ]
    (do
      (draw-line svg start end settings)
      (draw-line svg hammer-start hammer-end (butlast settings))
      )
    )
  )

(defn draw-hammers [svg points hammer-size-in-pts settings]
  (let [repacked (map vector (butlast points) (rest points))]
    (dorun
     (for [point repacked]
       (let [[start end] point]
         (draw-hammer svg start end hammer-size-in-pts settings)
         )))))

(defn draw-set [svg set settings]
    (dorun
     (for [ball (:balls set)]
        (draw-ball svg ball settings))))

(defn draw-sets [svg fp-sets fig-props]
  (let [x-set (:x-set @fp-sets)
        y-set (:y-set @fp-sets)]
    (do
      (draw-set svg x-set [fig-props (:red ts/solarized) "1.0"])
      (draw-set svg y-set [fig-props (:blue ts/solarized) "1.0"])
      (draw-set svg x-set [fig-props (:red ts/solarized) "0.5"])
      )
    ))
