(ns styx-client.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/register-sub
 :messages
 (fn [db]
   (reaction (:messages @db))))

(re-frame/register-sub
  :auto-scroll-on
  (fn [db]
    (reaction (get-in @db [:chat-feed-state :auto-scroll-on]))))

(re-frame/register-sub
  :auto-scroll-id
  (fn [db]
    (reaction (get-in @db [:chat-feed-state :auto-scroll-id]))))

(re-frame/register-sub
  :open-view
  (fn [db]
    (reaction (:open-view @db))))