(ns styx-client.views.app-bar
  (:require [re-frame.core :as re-frame]))

(defn back-button
  []
  [:div {:on-click #(do
                     (re-frame/dispatch [:set-view :other])
                     (re-frame/dispatch [:set-auto-scroll false]))}
   [:i.material-icons.mx1.mt1.text-color2
    "chevron_left"]])

(defn dp
  []
  [:div.mx1
   [:img.dp-size.circle.shadow2
    {:src "https://avatars.slack-edge.com/2015-05-30/5147732825_d04b8e601de9c90bed00_192.jpg"}]])

(defn contact-data
  []
  [:div
   [:h3.my0 "Kristo Koert"]
   [:h5.text-secondary.my0 "online"]])

(defn app-bar
  []
  [:div#top-bar.bg-color1.border-bottom.border-color1.shadow3
   [:div.flex.items-center.py1
    [back-button]
    [dp]
    [contact-data]]])