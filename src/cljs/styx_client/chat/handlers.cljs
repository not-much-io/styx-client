(ns styx-client.chat.handlers
  (:require [re-frame.core :as re-frame]
            [styx-client.test-data :refer [get-random-message]]))

(re-frame/register-handler
  :add-fake-msg
  (fn [db [_ msg]]
    (update-in db [:messages] #(conj % msg))))

(defonce _ (js/setInterval
             #(re-frame/dispatch [:add-fake-msg (get-random-message)])
             5000))