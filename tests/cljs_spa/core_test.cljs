(ns cljs-spa.core-test
  (:require [clojure.core]
            [clojure.test :refer-macros
             [deftest testing is async use-fixtures]]
            [cljs-spa.draw-coordinate-conversion :as dcc]
            [cljs-spa.add-index :as ai]
            ))

(deftest rel2abs
  (is (= [400 300] (dcc/rel2abs [0 0 800 600] [0.5 0.5])))
  (is (= [400 360] (dcc/rel2abs [0 0 800 600] [0.5 0.6]))))

(deftest add-index
  (is (= [{:val 1, :x 0} {:val 2, :x 1}]
         (ai/add-x [1 2])))
  (is (= [{:val 1, :x 0, :y 5} {:val 2, :x 1, :y 5}]
         (ai/add-constant-y (ai/add-x [1 2]) 5))
      (is (= [[{:val 1, :x 0, :y 0} {:val 2, :x 1, :y 0}]
              [{:val 3, :x 0, :y 1} {:val 4, :x 1, :y 1}]]
             (ai/add-y [[1 2] [3 4]])))))
