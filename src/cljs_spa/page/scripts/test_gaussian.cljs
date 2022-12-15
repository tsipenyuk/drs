(ns cljs-spa.page.scripts.test-gaussian
  (:require  [cljs.test :as t :include-macros true]))

(defonce value
"const init = gaussian({nPts: [3,3]})

return {
  plaintext: [{name: \"four\", value: 4}],
  heatmaps: [{name: \"gaussian\", value: init}],
}
")
