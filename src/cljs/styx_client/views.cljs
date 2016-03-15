(ns styx-client.views
  (:require [re-frame.core :as re-frame]))

(defn top-bar []
  [:div#top-bar.main-color
   [:div.flex
    [:div.col-2
     [:img.circle.left.my1.ml2 {:src    "https://avatars.slack-edge.com/2015-05-30/5147732825_d04b8e601de9c90bed00_192.jpg"
                                :height 60
                                :width  60}]]
    [:div.col-10.mt2.ml2
     [:h2#contact-name.m0 "Kristo Koert"]
     [:h5#status.m0 "online"]]]])

(defn chat-box []
  (let [messages (shuffle (flatten (repeat 4 ["Lorem impsum dolor sit amper consecreteur adipiscing"
                                              "lore impsum dolor"])))]
    [:div#chat-feed.overflow
     (map-indexed
       (fn [idx msg]
         (if (even? idx)
           ^{:key (rand-int 10000)}
           [:div.message1.p2.rounded.mr-auto.m3
            msg]
           [:div.message2.p2.rounded.ml-auto.m3
            msg]))
       messages)]))

(defn message-box []
  [:div#bottom-bar.p1.main-color
   [:div.flex
    [:div.col-2
     [:i.material-icons.m2 "attachment"]]
    [:div.col-6
     [:textarea#chat-box.main-color.m2 {:rows 1} "Type your message..."]]
    [:div.col-2
     [:i.material-icons.m2 "insert_emoticon"]]
    [:div.col-2
     [:i.material-icons.m2 "send"]]]])

(defn main-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div#wrapping-div
       [top-bar]
       [chat-box]
       [message-box]])))
