(ns styx-client.views
  (:require [re-frame.core :as re-frame]
            [goog.dom :as gdom]))

(defn top-bar []
  [:div#top-bar.flex.main-color.border-bottom.card-4
   [:img.circle.m1.profile-picture.card-2 {:src    "https://avatars.slack-edge.com/2015-05-30/5147732825_d04b8e601de9c90bed00_192.jpg"
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
             16)
        from-msg (fn [i m]
                   [:div.flex.flex-wrap.ml2
                    ^{:key i}
                    [:div.col-12
                     [:div.message1.secondary-color1.main-text.p2.rounded.card-3 m]]
                    [:div.col-12
                     [:h6.secondary-text.my1 "Sent 9.15"]]])
        user-msg   (fn [i m]
                     [:div.flex.flex-wrap.mr2
                      ^{:key i}
                      [:div.col-12
                       [:div.message2.secondary-color2.main-text.p2.rounded.ml-auto.card-3 m]]
                      [:div.col-12
                       [:h6.secondary-text.my1.right-align "Sent 9.15"]]])]
    [:div#chat-feed
     (map-indexed
       (fn [idx msg]
         (if (even? idx)
           (from-msg idx msg)
           (user-msg idx msg)))
       messages)]))

(defn message-box []
  [:div#bottom-bar.border-top.main-color
   [:div.flex
    [:div.col-1
     [:i.material-icons.m2.c-s2-text "attachment"]]
    [:div.col-7
     [:textarea#chat-box.secondary-text.main-color.m2
      {:rows 1}
      "Type your message..."]]
    [:div.col-1]
    [:div.col-1
     [:i.material-icons.m2.c-s2-text "insert_emoticon"]]
    [:div.col-1
     [:i.material-icons.m2.m2.c-s2-text "send"]]
    [:div.col-1]]])

(defn main-panel []
  (let [messages (re-frame/subscribe [:messages])]
    (fn []
      [:div#wrapping-div
       [top-bar]
       [chat-box @messages]
       [message-box]])))
