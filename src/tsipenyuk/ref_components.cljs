(ns tsipenyuk.ref-components)

(println "This text is printed from src/tsipenyuk/drs.cljs. Go ahead and edit it and see reloading in action!")
(defn multiply [a b] (* a b))

;; define your app data so that it doesn't get over-written on reload
(defonce app-state (r/atom {:text "Hello world!"}))
(defonce arseniy (r/atom {:text "Arseniy Tsipenyuk"}))

(defn hello-world []
  [:div
   [:h1 "I say: " (:text @app-state)]
   [:h3 "Edit this in src/tsipenyuk/drs.cljs and watch it change."]
   ])

(def input (.createElement js/document "input"))           ;; <2>
;; #'learn-cljs.weather/input                              ;; <3>

;; (.appendChild (.-body js/document) input)
;; #object[HTMLInputElement [object HTMLInputElement]]
;;

(set! (.-placeholder input) "Enter something")             ;; <4>
;; "Enter something"

(defn event-value [e] (-> e .-target .-value))
;; #'learn-cljs.weather/event-value

(defn update-text [value]
  (swap! app-state assoc :text value))
;; #'learn-cljs.weather/update-text

(defn handle-input [e]
  (update-text (event-value e)))

(set! (.-onkeyup input) handle-input)

(defn date-input []
  [:div.input-wrapper                                      ;; <1>
    [:label "Day"]
    [:input {:type "date"}]])                              ;; <2>

(defn time-input []
  [:div.input-wrapper
    [:label "Time (minutes)"]
    [:input {:type "number" :min 0 :step 1}]])

(defn submit-button []
  [:div.actions
    [:button {:type "submit"} "Submit"]])

(defn term-input [state]
  [:input {:type        "text"
           :style       {:display "block"}
           :placeholder "Username"
           :value       @state
           :on-change   (fn [event]
                          (reset! state (-> event .-target .-value)))}])

(defn loginForm [someUrl]
  (let [unVal (atom "") pwVal (atom "")]
    (fn []
      [:div
        [:p "Username: " @unVal]
        [:p "Password: " @pwVal]

        ; Reagent does "magic" here to transform any vector beginning
        ; with a function into a real function call
        [atom-input unVal]             ; line 32
        [atom-pw pwVal]                ; line 33

        [:button "to-do submit button"]])))

(defn atom-input [value]
  [:input {:type "text"
           :value @value
           :on-change #(reset! value (-> % .-target .-value))}])

(defn shared-state []
  (let [val (r/atom "foo")]
    (fn []
      [:div
       [:p "The value is now: " @val]
       [:p "Change it here: " [atom-input val]]])))

(defn temp-input []
  [:input
   {
    :type "text"
    :placeholder "hello"
    :value "hi f"
    :auto-focus true
    }])

(defn lister [items]
  [:ul
   (for [item items]
     ^{:key item} [:li "Item " item])])

(defn lister-user []
  [:div
   "Here is a list:"
   [lister (range 3)]])

:on-key-press (fn [e]
                         (println "key press" (.-charCode e))
                         (if (= 13 (.-charCode e))
                           (println "ENTER")
                           (println "NOT ENTER")))
