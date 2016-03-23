(ns styx-client.utils)

(defmacro handler-fn
  "https://github.com/Day8/re-frame/wiki/Beware-Returning-False"
  ([& body]
   `(fn [~'event] ~@body nil)))  ;; force return nil

(defonce lastId (atom -1))

(defn get-random-key
  []
  (-> lastId
      (swap! inc)))