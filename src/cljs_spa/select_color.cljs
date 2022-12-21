(ns cljs-spa.select-color
  (:require
   [cljs-spa.util :as u])
  )

(defn select-color [colormap x]
  (:color
   (peek (filterv (fn [color] (>= x (:lower-bd color))) colormap))))

(defn add-color-to-item [item colormap]
  (conj
   item
   {:style {:fill (select-color colormap (:val item))}}))

(defn add-color [colormap v-2d]
  (u/mapv-2d
   (fn [item] (add-color-to-item item colormap))
   v-2d))
