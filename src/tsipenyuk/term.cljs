(ns tsipenyuk.term
  (:require
   [goog.dom :as gdom]
   [reagent.core :as r]
   [reagent.dom :as rdom]
   [clojure.string :as str]))

(defn if-enter-was-pressed [xfunc]
  (fn [e] (if (= 13 (.-charCode e)) (xfunc))))

(defn list-term-history [items]
  (let [numbered-items
        (map vector items (range 1 (inc (count items))))]
    [:div
     (for [item numbered-items]
       ^{:key (str item)} [:p.term-history-line (first item)])]))

(defn term-history [term]
  [:div.term-history
   [list-term-history (take-last 2 (:history @term))]])

(defn update-term-history [term line]
  (swap! term assoc :history
         (conj (:history @term) line)))

(defn term-input [term value]
  [:input {:type "text"
           :value @value
           ;; :auto-focus true
           :on-key-press
           (if-enter-was-pressed
            (fn []
              (do
                (update-term-history term @value)
                (reset! value ""))))
           :on-change #(reset! value (-> % .-target .-value))}])

(defn term-duplicate-input []
  [:p.term-duplicate-input
   (:value (first (take-last 1 (tsipenyuk.term/term-input []))))])

(defn term-input-wrapper [term]
  (let [val (r/atom "")]
    (fn []
      [:div.term-input-wrapper                                      ;; <1>
       [:label (:mode @term)]
       [term-input term val]])))

(defn terminal [term]
  [:div.term
   [term-history term]
   [term-input-wrapper term]])
