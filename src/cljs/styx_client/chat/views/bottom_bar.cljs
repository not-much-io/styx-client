(ns styx-client.chat.views.bottom-bar
  (:require [reagent.core :as reagent]
            [styx-client.animation :refer [css-trans-group]])
  (:require-macros [styx-client.macros :as macros]))

(def id_bottom_bar "bottom-bar")

(defn attachment-button
  []
  [:i.material-icons.text-color2.circle "attachment"])

(defn message-box
  []
  ;; TODO: Add focus and blur animation
  (let [has-focus (reagent/atom false)]
    (fn []
      [:div
       [:textarea.slim-text-box.bg-color1
        {:placeholder "Write a message.."
         :rows         1
         :style        {:width "100%"}
         :on-focus     (macros/handler-fn
                         (reset! has-focus true))
         :on-blur      (macros/handler-fn
                         (reset! has-focus false))}]
       (if @has-focus
         ^{:key "chat-box-bar-active"}
         [:div.border-bottom.border-color2]
         ^{:key "chat-box-bar-inactive"}
         [:div.border-bottom.border-color1])])))

(defn emoticon-button
  []
  [:i.material-icons.text-color2.circle "insert_emoticon"])

(defn send-button
  []
  [:i.material-icons.text-color2.circle "send"])

(defn bottom-bar
  []
  [:div#bottom-bar.bg-color1.border-top.border-color1
   [:div.flex.items-center.p1
    [:div.m1
     [attachment-button]]
    [:div.flex-auto
     [message-box]]
    [:div.m1
     [emoticon-button]]
    [:div.m1
     [send-button]]]])