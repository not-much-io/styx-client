(ns styx-client.handlers
  (:require [re-frame.core :as re-frame]
            [styx-client.db :refer [default-db]]))

(re-frame/register-handler
  :initialize-db
  (fn  [_ _]
    default-db))

(re-frame/register-handler
  :set-loading?
  (fn [db [_ state]]
    (assoc db :loading? state)))

(re-frame/register-handler
  :set-view
  (fn [db [_ view]]
    (assoc db :open-view view)))