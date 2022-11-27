(ns tsipenyuk.init-state
 (:require
   [reagent.core :as r]
   [tsipenyuk.algorithms :as ta]
   ))

(defn initial-state []
  {:main-svg-props {:height 600
                    :width 800
                    :xmin -10
                    :xmax 10
                    :ymid 0}
   :term {:mode "drs>"
          :input "Input command here"
          :history ["New session has started." "Awaiting instructions..."]}
   :sets {:x-set {:balls [[-3.5 5.0 1.5] [-1.5 -4 2]]}
          :y-set {:balls [[ 3.5 4.5 1.0] [ 1.5 -4 2]]}}
   :alg-shared-props {:starting-pt [0.0 7.0]
                      :evolution-time 5}
   :algs {:er {:step-size 1.0}}
   })

(defn initialize-algorithms [state]
  (let [sets (r/cursor state [:sets])]
    (assoc-in state [:algs :er :update]
              (ta/get-er (:x-set @sets) (:y-set @sets)))
    ))
