(ns styx-client.macros)

(defmacro handler-fn
  ;; REVIEW: https://github.com/reagent-project/reagent/wiki/Beware-Event-Handlers-Returning-False
  ;;         Is a macro really neccesary?
  ([& body]
   `(fn [~'event] ~@body nil)))  ; always return nil
