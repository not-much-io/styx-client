(ns styx-client.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
  :open-view
  (fn [db]
    (reaction (:open-view @db))))

(re-frame/register-sub
  :messages
  (fn [db]
    (reaction (:messages @db))))