(ns styx-client.views.chat-feed
  (:require [styx-client.animation :as anim]
            [re-frame.core :as re-frame]
            [goog.dom :as gdom]))

(defn msg-in [m]
  ^{:key (rand-int 999)}
  [:div.ml2.mt1
   [:div.flex.flex-wrap
    [:div.col-12
     [:div.message-in.bg-color2.text-main.p2.rounded.shadow3 m]]
    [:div.col-12
     [:h6.text-secondary.my1 "Sent 9.15"]]]])

(defn msg-out [m]
  ^{:key (rand-int 999)}
  [:div.mr2.mt1
   [:div.flex.flex-wrap
    [:div.col-12
     [:div.message-out.bg-color3.text-main.p2.rounded.ml-auto.shadow3 m]]
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

(defn start-scroll-interval
  []
  (let [interval-id (js/setInterval
                      #(anim/scroll-down-by "chat-feed" 5)
                      16)]
    (re-frame/dispatch [:set-auto-scroll-id interval-id])))

(defn stop-scroll-interval
  [auto-scroll-id]
  (js/clearInterval auto-scroll-id)
  (re-frame/dispatch [:set-auto-scroll-id nil]))

(defn- scroll-state-handler
  [auto-scroll-on auto-scroll-id]
  (if auto-scroll-on
    (when-not auto-scroll-id
      ; Auto scroll on, but no interval running -> start interval
      (start-scroll-interval))
    (when auto-scroll-id
      ; Auto scroll off, but interval running -> stop interval
      (stop-scroll-interval auto-scroll-id))))

(defn chat-feed [messages]
  (let [auto-scroll-on  (re-frame/subscribe [:auto-scroll-on])
        auto-scroll-id  (re-frame/subscribe [:auto-scroll-id])]
    (scroll-state-handler @auto-scroll-on @auto-scroll-id)
    [:div#chat-feed
     {:on-click #(re-frame/dispatch
                  [:set-auto-scroll (not @auto-scroll-on)])}
     [anim/css-trans-group {:transition-name "feed"}
      (for [msg messages]
        (do
          (println "3 >>>" msg)
          (if (= :out (:in-or-out? msg))
            (msg-out (:msg msg))
            (msg-in (:msg msg)))))]]))
