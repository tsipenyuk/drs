(ns cljs-spa.select-color
  (:require
   [cljs-spa.util :as u])
  )

(defn select-color [colormap x]
  (:color
   (peek (filterv (fn [color] (>= x (:lower-bd color))) colormap))))

(defn select-bg-color [colormap]
  (:color
   (peek (filterv (fn [color] (true? (:bg-color color))) colormap))))
