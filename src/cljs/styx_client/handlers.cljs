(ns styx-client.handlers
    (:require [re-frame.core :as re-frame]
              [styx-client.db :as db]
              [styx-client.test-data :refer [get-random-message]]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/register-handler
  :add-fake-msg
  (fn [db msg]
    (update-in db [:messages] #(conj % msg))))

(js/setInterval
  #(re-frame/dispatch [:add-fake-msg (get-random-message)])
  9000)