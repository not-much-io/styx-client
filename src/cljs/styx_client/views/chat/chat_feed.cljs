(ns styx-client.views.chat-feed
  (:require [styx-client.animation :as anim]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [goog.dom :as gdom]
            [goog.fx.dom :as gfx-dom]
            [styx-client.utils :as utils]))

(defn msg-in [m]
  [:div.msg-in
   [:div.ml2.mt1
    [:div.flex.flex-wrap
     [:div.col-12
      [:div.message-in.bg-color2.text-main.p2.rounded.shadow3 m]]
     [:div.col-12
      [:h6.text-secondary.my1 "Sent 9.15"]]]]])

(defn msg-out [m]
  [:div.msg-out
   [:div.mr2.mt1
    [:div.flex.flex-wrap
     [:div.col-12
      [:div.message-out.bg-color3.text-main.p2.rounded.ml-auto.shadow3 m]]
     [:div.col-12
      [:h6.text-secondary.my1.right-align "Sent 9.15"]]]]])

(defonce _
         ;; Make chat feed take remaining height between top bar and bottom bar.
         ;; HACK: There is probably a better, less CPU intensive way to do this (CSS?).
         (js/setInterval
           (fn []
             (when (gdom/getElement "wrapping-div")         ; wrapping-div and thus its contents are mounted to DOM
               (set! (-> "chat-feed"
                         (gdom/getElement)
                         (.-style)
                         (.-height))
                     (as-> ["app" "top-bar" "bottom-bar"] x
                           (map gdom/getElement x)
                           (map #(.-offsetHeight %) x)
                           (apply - x)
                           (str x "px")))))                 ; available height between top and bottom bar
           16))

(defn scroll! [el start end time]
  "Plays a animation that will scroll an element from A to B.
  Start and End should be 2 dimensional arrays. [left, top]"
  (.play (gfx-dom/Scroll. el (clj->js start) (clj->js end) time)))

(defn chat-feed []
  (let [messages      (re-frame/subscribe [:sub-to [:messages]])
        should-scroll (reagent/atom false)]
    (fn []
      (when @should-scroll
        (as-> "chat-feed" feed
              (gdom/getElement feed)
              (scroll! feed
                       [0 (.-scrollTop feed)]
                       [0 (.-scrollHeight feed)]
                       300)))
      [:div#chat-feed
       ;; FIXME: handler-fn returns an Object not a fn as it should. (?)
       #_ {:on-click (utils/handler-fn
                       (reset! should-scroll (not @should-scroll)))}
       [anim/css-trans-group {:transition-name "feed"}
        (for [msg (reverse @messages)]
          (if (= :out (:in-or-out? msg))
            (msg-out (:msg msg))
            (msg-in (:msg msg))))]])))