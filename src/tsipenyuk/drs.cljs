(ns ^:figwheel-hooks tsipenyuk.drs
  (:require
   [goog.dom :as gdom]
   [reagent.core :as r]
   [reagent.dom :as rdom]))

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
           :auto-focus true
           :on-key-press
           (if-enter-was-pressed
            (fn []
              (do
                (update-term-history @value)
                (reset! value ""))))
           :on-change #(reset! value (-> % .-target .-value))}])

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

(defn app []
  [term-c])

(defn mount [el]
  (rdom/render [app] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^:after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
  )
