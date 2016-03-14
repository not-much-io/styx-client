(ns styx-client.handlers
    (:require [re-frame.core :as re-frame]
              [styx-client.db :as db]))

(re-frame/register-handler
 :initialize-db
 (fn  [_ _]
   db/default-db))
