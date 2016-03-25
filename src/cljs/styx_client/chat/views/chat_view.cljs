(ns styx-client.chat.views.chat-view
  (:require [styx-client.chat.views.app-bar :refer [app-bar]]
            [styx-client.chat.views.chat-feed :refer [chat-feed]]
            [styx-client.chat.views.bottom-bar :refer [bottom-bar]]))

(defn chat []
  [:div
   [app-bar]
   [chat-feed]
   [bottom-bar]])