(ns cljs-spa.page.home
  (:require ;; See https://github.com/pesterhazy/cljs-spa-example/issues/13
   [react-select :refer [default] :rename {default react-select}]
   [cljs-spa.state :refer [!state]]
   [reagent.core :as r]
   [cljs-spa.draw :as draw]
   [cljs-spa.page.scripts.test-gaussian :as default-code]
   ))

(defonce drs-state
  (r/atom
   {:mode "drs>"
    :current-output
    {:plaintext ["<Script output will be here>"]
     :heatmaps []
     :graphs []}
    :code default-code/value
    }))

(defn header-ui [] [:div "Douglas-Rachford Sandbox"])

(defn inner-textarea-ui [state]
  ;; (let [code (r/cursor state [:code])]
  [:textarea#inner-textarea-ui
   {:type        "text"
    :style       {:width "100%" :height "70vh"}
    :placeholder "Type or load a script..."
    :value       @state
    :on-change   (fn [event]
                     (reset! state (-> event .-target .-value)))}])

(defn textarea-ui [state]
  (let [code (r/cursor state [:code])]
    (fn []
      [:div#term-textarea-wrapper
       [inner-textarea-ui code]])))

(defn eval-code [code]
  ((.. js/window -drsParser -parse) @code))

(defn parse-result [evaluated-code]
  (let [res (js->clj evaluated-code :keywordize-keys true)]
    (do
      (println res)
      res)))

(defn run-button-ui [state]
  (let [code (r/cursor state [:code])
        output (r/cursor state [:current-output])]
  [:button
   {:type        "button"
    :style       {:display "block"}
    :on-click (fn [] (reset! output (parse-result (eval-code code))))}
   "Run"
   ]))

(defn current-output-ui [state]
  (let [output (:current-output @state)
        heatmaps (r/cursor state [:current-output :heatmaps])]
      [:div
       [:div#plaintext (str (first (:plaintext output)))]
       [draw/svg-heatmap-ui [0 0 600 600] heatmaps]
       ;; [draw/svg-heatmap-test [100 0 700 600] heatmaps]
       [:div#heatmaps"hello, heatmaps"]
       ]))

(defn page-ui []
  [:div#drs-wrapper
   [:div#terminal
    [header-ui]
    [textarea-ui drs-state]
    [run-button-ui drs-state]
    ]
   [:div#output
    [current-output-ui drs-state]
    ;; [draw/svg-heatmap-ui]
    ]])
