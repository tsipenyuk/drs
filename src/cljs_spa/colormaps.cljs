(ns cljs-spa.colormaps)

;; from colorbrewer2.org
(defonce red-blue-8
  [{:color "#b2182b" :lower-bd ##-Inf}
   {:color "#ef8a62" :lower-bd -1e-3}
   {:color "#fddbc7" :lower-bd -1e-6}
   {:color "#f7f7f7" :lower-bd -1e-9}
   {:color "#fddbc7" :lower-bd 1e-3}
   {:color "#f4a582" :lower-bd 1e-2}
   {:color "#d6604d" :lower-bd 1e-1}
   {:color "#b2182b" :lower-bd 1e-0}])
