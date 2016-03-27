(ns styx-client.contacts.views.contact-list
  (:require [re-frame.core :as re-frame]
            [styx-client.common-components :refer [separator]]))

(defn- display-pic
  []
  [:img.dp-small.circle.shadow2
   {:src "https://avatars.slack-edge.com/2015-05-30/5147732825_d04b8e601de9c90bed00_192.jpg"}])

(defn- contact-name
  [name]
  [:div
   [:h4.my0
    ;; HACK: Can't make do with basscss?
    {:style {:margin-top "5px"}}
    name]])

(defn- contact-last-seen
  [last-seen]
  [:div
   [:h6.text-secondary.my0
    last-seen]])


(defn- msg-preview
  []
  [:h5.m0.text-secondary
   "- Lorem ipsum dolor sit amet.."])

(defn- contact-item
  []
  [:div.flex
   {:on-click #(re-frame/dispatch [:set-view :chat])}
   [display-pic]
   [:div.flex.flex-auto
    [:div.flex-auto.ml1
     [contact-name "Kristo"]
     [msg-preview]]
    [:div
     [contact-last-seen "19:29"]]]])


(defn- list-of-contacts
  []
  [:div.px2
   [:h5.text-secondary "Today"]
   (repeat 5
           [:div
            [contact-item]
            [separator]])
   [:h5.text-secondary "Earlier"]
   (repeat 10
           [:div
            [contact-item]
            [separator]])])