(ns styx-client.contacts.views.contact-list-view
  (:require [re-frame.core :as re-frame]))

(defn search-bar []
  [:div#search-bar.m2
   [:div.flex.items-center
    [:i.material-icons.text-color2.mr2
     {:on-click #(re-frame/dispatch [:set-view :chat])}
     "menu"]
    [:form
     [:input.slim-text-box.color1.text-secondary
      {:defaultValue "Search.."}]]
    [:i.material-icons.text-color2.ml2
     "search"]]])

(defn contact-list
  []
  [:div#contact-list
   [search-bar]
   (comment [:form
             [:div.group
              [:input {:type "text"}]
              [:span.highlight]
              [:span.bar]]])])

