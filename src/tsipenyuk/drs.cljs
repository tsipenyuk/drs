(ns ^:figwheel-hooks tsipenyuk.drs
  (:require
   [cljsjs.d3 :as d3]
   [clojure.string :as str]
   [goog.dom :as gdom]
   [reagent.core :as r]
   [reagent.dom :as rdom]
   ;; [tsipenyuk.init-state :as tis]
   ))

;; multiply is an example for testing, see test folder
(defn multiply [a b] (* a b))

(defonce state
  (r/atom
   {:mode "drs>"}))

(defonce term
  (r/atom
   {:mode "drs>"
    :input "Input command here"
    :history ["New session has started." "Awaiting instructions..."]}))

(defn terminal [term]
  [:div
   {:style       {:width "100%"}}
   "Douglas-Rachford Sandbox"])

(defn term-textarea [state]
  [:textarea#term-textarea
   {:type        "text"
    :style       {:width "100%" :height "100%"}
    :placeholder "Type or load a script..."
    :value       @state
    :on-change   (fn [event]
                   (reset! state (-> event .-target .-value)))}])

(defn term-textarea-wrapper [term]
  (let [val (r/atom "")]
    (fn []
      [:div#term-textarea-wrapper
       [term-textarea val]])))

(defn term-button [state]
  [:button
   {:type        "button"
    :style       {:display "block"}}
   "Run"
   ])

;; app
(defn get-app-element [] (gdom/getElement "app"))
(defn app []
  [:div#drs-wrapper
   [:div#terminal
    [terminal term]
    [term-textarea-wrapper term]
    [term-button term]
    ]
   ])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Lifecycle
(defn mount [el]
  (rdom/render [app] el))

(defn mount-app-element []
  (when-let [el (get-app-element)]
    (mount el)
    ))

;; conditionally start your application based on the presence of an "app" element
;; this is particularly helpful for testing this ns without launching the app
(mount-app-element)

;; specify reload hook with ^:after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element)
  )
