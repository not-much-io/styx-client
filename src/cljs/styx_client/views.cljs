(ns styx-client.views
  (:require [re-frame.core :as re-frame]
            [goog.dom :as gdom]))

(defn top-bar []
  [:div#top-bar.flex.main-color.border-bottom
   [:img.circle.m1.profile-picture {:src    "https://avatars.slack-edge.com/2015-05-30/5147732825_d04b8e601de9c90bed00_192.jpg"
                    :height 55
                    :width  55}]
   [:div
    [:h3#contact-name.mb0 "Kristo Koert"]
    [:h5#status.m0 "online"]]])

(defn chat-box [messages]
  (let [_  (js/setInterval
             #(let [get-height        (fn [id]
                                        (.-offsetHeight (gdom/getElement id)))
                    top-bar-height    (get-height "top-bar")
                    bottom-bar-height (get-height "bottom-bar")
                    parent-height     (get-height "wrapping-div")
                    chat-feed-style   (.-style (gdom/getElement "chat-feed"))]
               (set! (.-height chat-feed-style)
                     (str (- parent-height
                             top-bar-height
                             bottom-bar-height)
                          "px")))
             16)]                                           ; Performance hit?
    [:div#chat-feed
     (map-indexed
       (fn [idx msg]
         (if (even? idx)
           ^{:key idx}
           [:div.message1.p2.rounded.mr-auto.m3
            msg]
           ^{:key idx}
           [:div.message2.p2.rounded.ml-auto.m3
            msg]))
       messages)]))

(defn message-box []
  [:div#bottom-bar.border-top.main-color
   [:div.flex
    [:div.col-2
     [:i.material-icons.m2 "attachment"]]
    [:div.col-6
     [:textarea#chat-box.main-color.m2
      {:rows 1}
      "Type your message..."]]
    [:div.col-2
     [:i.material-icons.m2 "insert_emoticon"]]
    [:div.col-2
     [:i.material-icons.m2 "send"]]]])

(defn main-panel []
  (let [messages (re-frame/subscribe [:messages])]
    (fn []
      [:div#wrapping-div
       [top-bar]
       [chat-box @messages]
       [message-box]])))
