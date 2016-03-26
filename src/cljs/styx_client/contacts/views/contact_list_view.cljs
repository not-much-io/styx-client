(ns styx-client.contacts.views.contact-list-view
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [styx-client.common-components :as ccomp])
  (:require-macros [styx-client.macros :as macros]))

(defn- menu-button
  []
  [:i.material-icons.text-color2
   {:on-click #(re-frame/dispatch [:set-view :chat])}
   "menu"])

(defn- search-button
  []
  [:i.material-icons.text-color2
   "search"])

(defn- search-text-field
  []
  (let [has-focus (reagent/atom false)]
    (fn []
      [:div
       [:textarea.slim-text-box.bg-color1
        {:placeholder "Search.."
         :rows        1
         :style       {:width "100%"}
         :on-focus    (macros/handler-fn
                        (reset! has-focus true))
         :on-blur     (macros/handler-fn
                        (reset! has-focus false))}]
       (if @has-focus
         ^{:key "search-box-bar-active"}
         [:div.border-bottom.border-color2]
         ^{:key "search-box-bar-inactive"}
         [:div.border-bottom.border-color1])])))



(defn- search-bar
  []
  [:div#search-bar.bg-color1.border-bottom.border-color1.shadow3
   [:div.flex.items-center.p1
    [:div.m1
     [menu-button]]
    [:div.flex-auto
     [search-text-field]]
    [:div.m1
     [search-button]]]])

(defn- display-pic
  []
  [:div.m1
   [:img.dp-medium.circle.shadow2
    {:src "https://avatars.slack-edge.com/2015-05-30/5147732825_d04b8e601de9c90bed00_192.jpg"}]])

(defn- contact-signature
  [name status]
  [:div
   [:h3.my0 name]
   [:h5.text-secondary.my0 status]])

(defn- contact-item
  []
  [:div
   [:div.flex.items-center
    [display-pic]
    [contact-signature "Kristo" "Away"]]
   [ccomp/separator]])


(defn- list-of-contacts
  []
  [:div.px2.pt1
   [:h5.text-secondary.my1 "Today"]
   (repeat 5 [contact-item])
   [:h5.text-secondary.my1 "Yesterday"]
   (repeat 5 [contact-item])])

(defn contact-list
  []
  [:div#contact-list
   [search-bar]
   [list-of-contacts]])

