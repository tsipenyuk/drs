(ns cljs-spa.page.scripts.test-gaussian
  (:require  [cljs.test :as t :include-macros true]))

(defonce value
"const init = gaussian({
    nPts: [64,64],
    sigma: [.05, .15],
    thetaInDegrees: 15
  })

return {
  plaintext: [{name: \"four\", value: 4}],
  heatmaps: [{name: \"A lone gaussian\", value: init}],
}
")
