(ns styx-client.views.chat-feed
  (:require [styx-client.animation :as anim]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [goog.dom :as gdom]))

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

(defonce fit-feed
         ; Make chat feed take remaining height between top bar and bottom bar
         (js/setInterval
           (fn []
             (let [mounted (gdom/getElement "bottom-bar")]
               (when mounted
                 ; Elements are mounted to DOM, bottom-bar mounted lastly
                 (let [heights    (map #(.-offsetHeight (gdom/getElement %))
                                       ["app" "top-bar" "bottom-bar"])
                       fit-height (apply - heights)]
                   (set! (-> "chat-feed"
                             (gdom/getElement)
                             (.-style)
                             (.-height))
                         (str fit-height "px"))))))
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


(defn chat-feed []
  (let [messages      (re-frame/subscribe [:sub-to [:messages]])
        should-scroll (reagent/atom true)]
    (fn []
      [:div#chat-feed {:on-click  (fn []
                                    (reset! should-scroll
                                            (not @should-scroll)))}
       [anim/css-trans-group {:transition-name "feed"}
        (for [msg (reverse @messages)]
          (if (= :out (:in-or-out? msg))
            (msg-out (:msg msg))
            (msg-in (:msg msg))))]])))