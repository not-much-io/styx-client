(ns styx-client.views.chat-feed
  (:require [styx-client.animation :as anim]
            [re-frame.core :as re-frame]
            [goog.dom :as gdom]))

(defn from-msg [i m]
  ^{:key i}
  [:div.ml2.mt1
   [:div.flex.flex-wrap
    [:div.col-12
     [:div.message1.color2.text-main.p2.rounded.shadow3 m]]
    [:div.col-12
     [:h6.text-secondary.my1 "Sent 9.15"]]]])

(defn to-msg [i m]
  ^{:key i}
  [:div.mr2.mt1
   [:div.flex.flex-wrap
    [:div.col-12
     [:div.message2.color3.text-main.p2.rounded.ml-auto.shadow3 m]]
    [:div.col-12
     [:h6.text-secondary.my1.right-align "Sent 9.15"]]]])

(defonce fit-feed
         ; Make chat feed take remaining height between top bar and bottom bar
         (js/setInterval
           #(let [ids ["chat-feed" "top-bar" "bottom-bar" "app"]
                  els (map gdom/getElement ids)]
             (when-not (some nil? els)
               ; All elements are mounted to DOM
               (apply anim/fit-vertical ids)))
           16))

(defn- scroll-state-handler
  [auto-scroll-on auto-scroll-id]
  (let [start-interval (fn []
                         (let [interval-id (js/setInterval
                                             #(anim/scroll-down-by "chat-feed" 5)
                                             16)]
                           (re-frame/dispatch [:set-auto-scroll-id interval-id])))
        stop-interval  (fn []
                         (js/clearInterval auto-scroll-id)
                         (re-frame/dispatch [:set-auto-scroll-id nil]))]
    (if auto-scroll-on
      (when-not auto-scroll-id
        ; Auto scroll on, but no interval running -> start interval
        (start-interval))
      (when auto-scroll-id
        ; Auto scroll off, but interval running -> stop interval
        (stop-interval)))))

(defn chat-box [messages]
  (let [auto-scroll-on  (re-frame/subscribe [:auto-scroll-on])
        auto-scroll-id  (re-frame/subscribe [:auto-scroll-id])]
    (scroll-state-handler @auto-scroll-on @auto-scroll-id)
    [:div#chat-feed {:on-click #(re-frame/dispatch
                                 [:set-auto-scroll (not @auto-scroll-on)])}
     [anim/css-trans-group {:transition-name "feed"}
      (map-indexed
        (fn [idx msg]
          (if (even? idx)
            (from-msg idx msg)
            (to-msg idx msg)))
        (reverse messages))]]))
