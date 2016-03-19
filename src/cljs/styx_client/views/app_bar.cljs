(ns styx-client.views.app-bar
  (:require [re-frame.core :as re-frame]))

(defn top-bar []
  [:div#top-bar.color1.border-bottom.grey-border.shadow4
   [:div.flex.items-center.py1
    [:div {:on-click #(do
                       (re-frame/dispatch [:set-view :other])
                       (re-frame/dispatch [:set-auto-scroll false]))}
     [:i.material-icons.mx1.mt1.text-color2 "chevron_left"]]
    [:div.ml1
     [:img.dp.circle.shadow2 {:src "https://avatars.slack-edge.com/2015-05-30/5147732825_d04b8e601de9c90bed00_192.jpg"}]]
    [:div.ml1
     [:h3.text-main.my0 "Kristo Koert"]
     [:h5.text-grey.m0 "online"]]]])