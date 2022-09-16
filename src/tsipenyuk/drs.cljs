(ns ^:figwheel-hooks tsipenyuk.drs
  (:require
   [cljsjs.d3 :as d3]
   [clojure.string :as str]
   [goog.dom :as gdom]
   [reagent.core :as r]
   [reagent.dom :as rdom]
   [tsipenyuk.draw :as td]
   [tsipenyuk.main-fig :as tm]
   [tsipenyuk.solarized :as ts]
   [tsipenyuk.term :as tt]
   ))

;; helpers
(defn multiply [a b] (* a b))
(defn print-something [e] (println e))

;; state
(def main-fig-static-props
  {:height 600
   :width 800})

(defonce term
  (r/atom
   {:mode "drs>"
    :input "Input command here"
    :history ["New session has started." "Awaiting instructions..."]}))

(defonce main-fig-props
  (r/atom
   {:height 600
    :width 800
    :xmin -10
    :xmax 10
    :ymid 0}))

(defonce fp-sets
  (r/atom
   {:x-set {:balls [[-3.5 5.0 1.5] [-1.5 -4 2]]}
    :y-set {:balls [[ 3.5 4.5 1.0] [ 1.5 -4 2]]}}))


;; app
(defn get-app-element [] (gdom/getElement "app"))
(defn app []
  [:div
   [tt/terminal term]
   [tm/main-fig]])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; data
(def data {2005 {:natural-gas 0.2008611514256557
                 :coal        0.48970650816857986
                 :nuclear     0.19367190804075465
                 :renewables  0.02374724819670379}
           2015 {:natural-gas 0.33808321253456974
                 :coal        0.3039492492908485
                 :nuclear     0.1976276775179704
                 :renewables  0.08379872568702211}})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; helpers
(def scale [])

(def height-scale
  (-> js/d3
      (.scaleLinear)
      (.domain #js [0 1])
      (.range #js [(- (:height @main-fig-props) 15) 0])))

(defn format-percent [value]
  ((.format js/d3 ".2%") value))

(defn format-name [name-str]
  (-> name-str
      (str/replace "-" " ")
      (str/capitalize)))

(defn attrs [el m]
  (doseq [[k v] m]
    (.attr el k v)))

(defn data-join [parent tag class data]
  (let [join  (-> parent
                  (.selectAll (str tag "." class))
                  (.data (into-array data)))
        enter (-> join
                  (.enter)
                  (.append tag)
                  (.classed class true))]
    (-> join (.exit) (.remove))
    (.merge join enter)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; draw functions

(defn draw-column [svg data-col index custom-attrs]
  (-> svg
      (data-join "text" (str "text.slopegraph-column-" index) data-col)
      (.text (fn [[k v]] (str (format-name (name k)) " " (format-percent v))))
      (attrs (assoc custom-attrs
                    "y" (fn [[_ v]] (height-scale v))))))
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; ;; draw functions

;; (defn draw-column [svg data-col index custom-attrs]
;;   (-> svg
;;     (.selectAll (str "text.slopegraph-column-" index))
;;     (.data (into-array data-col))
;;     (.enter)
;;     (.append "text")
;;     (.classed (str "slopegraph-column" index) true)
;;     (.text (fn [[k v]] (str (format-name (name k)) " " (format-percent v))))
;;     (attrs (merge custom-attrs
;;             {"y" (fn [[_ v]] (height-scale v))}))))

(def column-1-start (/ (:width @main-fig-props) 4))
(def column-space (* 3 (/ (:width @main-fig-props) 4)))

(defn draw-header [svg years]
  (-> svg
      (data-join "text" "slopegraph-header" years)
      (.text (fn [data _] (str data)))
      (attrs {"x" (fn [_ index]
                    (+ 50 (* index column-space)))
              "y" 15})))

(defn draw-line [svg data-col-1 data-col-2]
  (-> svg
      (data-join "line" "slopegraph-line" data-col-1)
      (attrs {"x1" (+ 5 column-1-start)
              "x2" (- column-space 5)
              "y1" (fn [[_ v]]
                     (height-scale v))
              "y2" (fn [[k _]]
                     (height-scale (get data-col-2 k)))})))


(defn draw-slopegraph [svg data]
  (let [data-2005 (get data 2005)
        data-2015 (get data 2015)]

    (draw-column svg data-2005 1 {"x" column-1-start})
    (draw-column svg data-2015 2 {"x" column-space})

    (draw-header svg [2005 2015])
    (draw-line svg data-2005 data-2015)
    (td/draw-sets svg fp-sets main-fig-props)
    ;; (td/draw-set svg (:x-set @fp-sets) main-fig-props (:red ts/solarized) "0.5")
    ))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Lifecycle
(defn mount [el]
  (rdom/render [app] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (do
      (mount el)
      (let [svg (tm/append-svg main-fig-props)]
        (draw-slopegraph svg data)))))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^:after-load metadata
(defn ^:after-load on-reload []
  (tm/remove-svg)
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
