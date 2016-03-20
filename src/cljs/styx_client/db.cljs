(ns styx-client.db
  (:require [styx-client.test-data :refer [get-random-message]]))

(def default-db
  {:messages        []
   :loading?        true
   :open-view       :chat
   :chat-feed-state {:auto-scroll-on true
                     :auto-scroll-id nil}})
