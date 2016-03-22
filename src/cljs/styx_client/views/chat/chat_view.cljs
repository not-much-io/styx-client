(ns styx-client.views.chat-view
  (:require [styx-client.views.app-bar :refer [app-bar]]
            [styx-client.views.chat-feed :refer [chat-feed]]
            [styx-client.views.bottom-bar :refer [bottom-bar]]))

(defn chat []
  [:div
   [app-bar]
   [chat-feed]
   [bottom-bar]])