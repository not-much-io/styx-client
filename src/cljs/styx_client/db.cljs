(ns styx-client.db
  (:require [styx-client.utils :refer [get-random-message]]))

(def default-db
  {:messages        (for [i (range 8)]
                      (get-random-message))
   :loading?        true
   :open-view       :chat})
