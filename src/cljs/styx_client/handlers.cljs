(ns styx-client.handlers
  (:require [re-frame.core :as re-frame]
            [styx-client.db :as db]
            [styx-client.test-data :refer [get-random-message]]))

(re-frame/register-handler
  :initialize-db
  (fn  [_ _]
    db/default-db))

(re-frame/register-handler
  :set-loading?
  (fn [db state]
    (assoc db :loading? state)))

(re-frame/register-handler
  :add-fake-msg
  (fn [db msg]
    (update-in db [:messages] #(conj % msg))))

(re-frame/register-handler
  :set-auto-scroll
  (fn [db [_ state]]
    (assoc-in db [:chat-feed-state :auto-scroll-on] state)))

(re-frame/register-handler
  :set-auto-scroll-id
  (fn [db [_ id]]
    {:pre [(nil?
             (get-in [:chat-feed-state :auto-scroll-id] db))]}
    (assoc-in db [:chat-feed-state :auto-scroll-id] id)))

(re-frame/register-handler
  :set-view
  (fn [db [_ view]]
    (assoc db :open-view view)))

(comment (defonce _ (js/setInterval
                      #(re-frame/dispatch [:add-fake-msg (get-random-message)])
                      5000)))