(ns cljs-spa.core-test
  (:require [clojure.core]
            [clojure.test :refer-macros
             [deftest testing is async use-fixtures]]
            [cljs-spa.colormaps :as c]
            [cljs-spa.draw-coordinate-conversion :as dcc]
            [cljs-spa.select-color :as sc]))

(deftest rel2abs
  (is (= [400 300] (dcc/rel2abs [0 0 800 600] [0.5 0.5])))
  (is (= [400 360] (dcc/rel2abs [0 0 800 600] [0.5 0.6]))))

(deftest select-color
  (is (= "#f7f7f7" (sc/select-color c/red-blue-8 0)))
  (is (= "#b2182b" (sc/select-color c/red-blue-8 -10)))
  (is (= "#b2182b" (sc/select-color c/red-blue-8 10))))

(deftest select-bg-color
  (is (= "#f7f7f7" (sc/select-bg-color c/red-blue-8))))
