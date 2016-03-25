(ns styx-client.chat.views.app-bar
  (:require [re-frame.core :as re-frame]))

(def id_top_bar "top-bar")

(defn- nav-button
  []
  [:div {:on-click #(re-frame/dispatch [:set-view :other])}
   [:i.material-icons.m1.text-color2
    "chevron_left"]])

(defn- display-pic
  []
  [:div.m1
   [:img.dp-size.circle.shadow2
    {:src "https://avatars.slack-edge.com/2015-05-30/5147732825_d04b8e601de9c90bed00_192.jpg"}]])

(defn- contact-signature
  [name status]
  [:div
   [:h3.my0 name]
   [:h5.text-secondary.my0 status]])

(defn app-bar
  []
  [:div#top-bar.bg-color1.border-bottom.border-color1.shadow3
   [:div.flex.items-center
    [nav-button]
    [display-pic]
    [contact-signature "Kristo Koert" "Away"]]])