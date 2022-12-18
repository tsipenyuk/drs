(ns cljs-spa.add-index)

(defn add-x [one-dim-array]
  (vec (map-indexed (fn [idx itm] {:val itm :x idx}) one-dim-array)))

(defn add-constant-y [one-dim-array-with-x constant-y]
   (mapv (fn [x] (conj x {:y constant-y})) one-dim-array-with-x))

(defn add-xy [two-dim-array]
  (vec
   (map-indexed
    (fn [y one-dim-array]
      (add-constant-y (add-x one-dim-array) y))
    two-dim-array)))
