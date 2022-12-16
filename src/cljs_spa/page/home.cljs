(ns cljs-spa.page.home
  (:require ;; See https://github.com/pesterhazy/cljs-spa-example/issues/13
   [react-select :refer [default] :rename {default react-select}]
   [cljs-spa.state :refer [!state]]
   [reagent.core :as r]
   [cljs-spa.draw :as draw]
   [cljs-spa.page.scripts.test-gaussian :as default-code]
   ))

(def options
  [{:value "simplicity", :label "simplicity"}
   {:value "immutable data", :label "immutable data"}
   {:value "lazy sequences", :label "lazy sequences"}])

(defn selector-ui []
  [:> react-select
   {:is-multi true,
    :options (clj->js options),
    :on-change (fn [xs]
                 (swap! !state assoc
                   :selection
                   (->> (js->clj xs :keywordize-keys true)
                        (map :label)
                        (into #{}))))}])

(defn result-ui []
  [:div [:h3 "So you like"]
   (let [selection (:selection @!state)]
     (if (seq selection) [:div (pr-str selection)] "Nothing yet"))])

;; START HERE
(defonce drs-state
  (r/atom
   {:mode "drs>"
    :current-output "<Script output will be here>"
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
      (println (:value (first (:heatmaps res))))
      (:value (first (:plaintext res))))))

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
  [:div (:current-output @state)])

(defn page-ui []
  [:div#drs-wrapper
   [:div#terminal
    [header-ui]
    [textarea-ui drs-state]
    [run-button-ui drs-state]
    ]
   [:div#output
    [current-output-ui drs-state]
    [draw/svg-heatmap-ui]
    ]])
