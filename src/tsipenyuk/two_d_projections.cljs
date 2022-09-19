(ns tsipenyuk.two-d-projections
 (:require
   [tsipenyuk.math :as tmath]
   ))

;; projection and distance from v to the ball
(defn pd-ball [v ball]
  (let [[x y] v
        [xb yb r] ball
        [xr yr] (mapv - [x y] [xb yb])
        d (tmath/abs [xr yr])]
    (if (= d 0)
      [v 0]
      (let [rescale (fn [z] (* r (/ z d)))
            [xnew ynew] (mapv rescale [xr yr])
            p (mapv + [xb yb] [xnew ynew])]
        [p d]))))

(defn p-ball [v ball] ((pd-ball v ball) 0))
(defn d-ball [v ball] ((pd-ball v ball) 1))

(defn pd-balls [v balls]
  (let [pdv (fn [ball] (pd-ball v ball))
        pds (mapv pdv balls)
        ps (mapv first pds)
        ds (mapv second pds)
        i (tmath/argmin ds)]
      (pds i)))

(defn p-balls [v balls] ((pd-balls v balls) 0))
(defn p-set [v set] (p-balls v (:balls set)))
