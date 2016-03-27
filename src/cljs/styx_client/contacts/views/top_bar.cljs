(ns styx-client.contacts.views.top-bar
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent])
  (:require-macros [styx-client.macros :as macros]))

(defn- menu-button
  []
  ;; TODO: Attach actual menu
  [:i.material-icons.text-color2
   {:on-click #(re-frame/dispatch [:set-view :chat])}
   "menu"])

(defn- search-button
  []
  ;; TODO: Make textbox extend out on click and hide on blur
  [:i.material-icons.text-color2
   "search"])

(defn- search-text-field
  []
  ;; TODO: Animate focus and blur
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
