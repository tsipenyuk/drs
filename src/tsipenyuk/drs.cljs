(ns ^:figwheel-hooks tsipenyuk.drs
  (:require
   [cljsjs.d3 :as d3]
   [clojure.string :as str]
   [goog.dom :as gdom]
   [reagent.core :as r]
   [reagent.dom :as rdom]
   [tsipenyuk.algorithms :as ta]
   [tsipenyuk.draw :as td]
   [tsipenyuk.main-fig :as tm]
   [tsipenyuk.solarized :as ts]
   [tsipenyuk.term :as tt]
   [tsipenyuk.two-d-projections :as tp]
   ))

;; helpers
(defn multiply [a b] (* a b))
(defn print-something [e] (println e))

;; state
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

(def starting-point (r/atom [0.0 7.0]))
(def alg-er (ta/get-er (:x-set @fp-sets) (:y-set @fp-sets)))

(defonce algorithms
  (let [x-set (:x-set @fp-sets)
        y-set (:y-set @fp-sets)]
    (r/atom {:er {:step-size 1
                  :num-of-steps 10
                  :rule alg-er
                  :history []}})))

;; run algorithms
(def m-history
  (vec (take 3 (iterate
           (ta/step-alg alg-er)
           @starting-point))))
(println "m-history")
(println m-history)

(def l-history
  (mapv (fn [v] (tp/p-set v (:x-set @fp-sets))) m-history))
(println "l-history")
(println l-history)

(def n-history
  (vec (apply concat (map vector m-history l-history))))
(println "n-history")
(println n-history)




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

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; ;; draw functions

(def column-1-start (/ (:width @main-fig-props) 4))
(def column-space (* 3 (/ (:width @main-fig-props) 4)))

(defn draw-header [svg years]
  (-> svg
      (data-join "text" "slopegraph-header" years)
      (.text (fn [data _] (str data)))
      (attrs {"x" (fn [_ index]
                    (+ 50 (* index column-space)))
              "y" 15})))

(defn draw-slopegraph [svg data]
  (let [data-2005 (get data 2005)
        data-2015 (get data 2015)]

    (draw-header svg ["set X: red" "set Y: blue"])
    (td/draw-sets svg fp-sets main-fig-props)
    (td/draw-pt svg [-6 4] [main-fig-props (:base03 ts/solarized) "1"])
    (td/draw-pt svg
                (tp/p-set [-6 4] (:x-set @fp-sets))
                [main-fig-props (:yellow ts/solarized) "1"])
    ;; (td/draw-hammers svg [[-0.3 0] [0 0] [1 1] [2 3] [4 5] [5 0] [7 0]] 50 [main-fig-props (:base03 ts/solarized) "1" "5"])
    ;; (td/draw-hammers svg (:history (:er @algorithms)) 50 [main-fig-props (:base03 ts/solarized) "1" "5"])
    (td/draw-hammers svg n-history 50 [main-fig-props (:base03 ts/solarized) "1" "500"])
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
