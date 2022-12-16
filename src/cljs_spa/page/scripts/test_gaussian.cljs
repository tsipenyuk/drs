(ns cljs-spa.page.scripts.test-gaussian
  (:require  [cljs.test :as t :include-macros true]))

(defonce value
"const init = gaussian({
    nPts: [5,5],
    sigma: [0.5, 1.5],
    thetaInDegrees: 10
  })

return {
  plaintext: [{name: \"four\", value: 4}],
  heatmaps: [{name: \"gaussian\", value: init}],
}
")
