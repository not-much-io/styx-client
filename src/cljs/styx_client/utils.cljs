(ns styx-client.utils)

(defn handler-fn
  [f]
  ;; FIXME:  https://github.com/Day8/re-frame/wiki/Beware-Returning-False
  ;;         But I am trying to avoid usign a macro. Getting "not a function" in ratom..
  (fn [event]
    (let [res (f)]
      (if (= (type res) js/Boolean)
        nil
        res))))

(defonce lastId (atom -1))

(defn get-random-key
  []
  ;; HACK: This is just for temporary use in a testing environment.
  (swap! lastId inc))