(ns styx-client.db
  (:require [styx-client.test-data :refer [get-random-message]]))

(def default-db
  {:messages [(get-random-message)]})
