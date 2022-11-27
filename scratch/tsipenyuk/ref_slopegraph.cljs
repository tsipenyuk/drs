(ns tsipenyuk.ref-slopegraph
  (:require
   [goog.dom :as gdom]
   [reagent.core :as r]
   [reagent.dom :as rdom]
   [clojure.string :as str]
   [cljsjs.d3 :as d3]))

(defn multiply [a b] (* a b))
(defonce term
  (r/atom
   {:mode "drs>"
    :input "Input command here"
    :history ["New session has started." "Awaiting instructions..."]}))

(defn get-app-element [] (gdom/getElement "app"))

(defn list-term-history [items]
  (let [numbered-items
        (map vector items (range 1 (inc (count items))))]
    [:div
     (for [item numbered-items]
       ^{:key (str item)} [:p.term-history-line (first item)])]))

(defn term-history []
  [:div.term-history
   [list-term-history (take-last 2 (:history @term))]])

(defn if-enter-was-pressed [xfunc]
  (fn [e]
    (if (= 13 (.-charCode e))
      (xfunc))))

(defn print-something [e] (println e))
(defn update-term-history [line]
  (swap! term assoc :history
         (conj (:history @term) line)))

(defn term-input [value]
  [:input {:type "text"
           :value @value
           ;; :auto-focus true
           :on-key-press
           (if-enter-was-pressed
            (fn []
              (do
                (update-term-history @value)
                (reset! value ""))))
           :on-change #(reset! value (-> % .-target .-value))}])

(defn remove-svg []
  (-> js/d3
      (.selectAll "#slopegraph svg")
      (.remove)))


(defn term-duplicate-input []
  [:p.term-duplicate-input
   (:value (first (take-last 1 (tsipenyuk.drs/term-input []))))])

(defn term-input-wrapper []
  (let [val (r/atom "")]
    (fn []
      [:div.term-input-wrapper                                      ;; <1>
       [:label (:mode @term)]
       [term-input val]])))

(defn term-c []
  [:div.term
   [term-history]
   [term-input-wrapper]])

(defn main-fig []
  [:div
   {:id "main-fig"
    :style {:margin-left 10
            :background-color "white"
            :width "95%"}}
   ])

(defn app []
  [:div
   [term-c]
   [main-fig]
   ]
  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; data

(def height 450)
(def width 540)

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

(def height-scale
  (-> js/d3
      (.scaleLinear)
      (.domain #js [0 1])
      (.range #js [(- height 15) 0])))

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

(def column-1-start (/ width 4))
(def column-space (* 3 (/ width 4)))

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
    (draw-line svg data-2005 data-2015)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Lifecycle

(defn append-svg []
  (-> js/d3
      (.select "#main-fig")
      (.append "svg")
      (.attr "height" height)
      (.attr "width" width)))

(defn mount [el]
  (rdom/render [app] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (do
      (mount el)
      ;; (append-svg)
      (let [svg (append-svg)]
        (draw-slopegraph svg data))
      )))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^:after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  (remove-svg)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
