(ns cljs-spa.util)

(defn check-fetch [r]
  (if-not (.-ok r)
    (throw (ex-info "Could not get user"
                    {:status (.-status r), :load-error true}))
    r))

(defn safe-fetch [& args]
  (-> (.apply js/fetch js/window (into-array args))
      (.catch (fn [e]
                (throw (ex-info "Could not fetch"
                                {:cause e, :load-error true}))))
      (.then check-fetch)))

(defn singleton-fn [create-fn dispose-fn]
  (let [!state (atom nil)]
    (fn [& args]
      (when-let [state @!state] (dispose-fn state))
      (let [result (apply create-fn args)]
        (reset! !state (or result ::initialized))))))

(defn clear-interval [interval] (when interval (js/clearInterval interval)) nil)

(defn set-interval [interval & args]
  (when interval (js/clearInterval interval))
  (.apply js/setInterval nil (into-array args)))

;; apply func to all elems of a 2-dim vector
(defn mapv-2d [func arr-2d]
  (mapv
   (fn [arr-1d] (mapv func arr-1d))
   arr-2d))

(defn flatten-one-layer [nested]
  (reduce (fn [x y] (apply conj x y)) nested))
