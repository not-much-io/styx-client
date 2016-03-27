(ns styx-client.chat.views.chat-view
  (:require [styx-client.chat.views.top-bar :refer [top-bar]]
            [styx-client.chat.views.chat-feed :refer [chat-feed]]
            [styx-client.chat.views.bottom-bar :refer [bottom-bar]]))

(defn chat []
  [:div
   [top-bar]
   [chat-feed]
   [bottom-bar]])