(ns styx-client.views.chat-feed
  (:require [styx-client.animation :as anim]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [goog.dom :as gdom]
            [goog.fx.dom :as gfx-dom])
  (:require-macros [styx-client.macros :as macros]))

;; REVIEW: Is this a good way to do this?
(def id_chat_feed "chat-feed")

(defn msg-in [k m]
  ^{:key k}
  ;; HACK: Simplify markup
  [:div.msg-in
   [:div.ml2.mt1
    [:div.flex.flex-wrap
     [:div.col-12
      [:div.message-in.bg-color2.text-main.p2.rounded.shadow3 m]]
     [:div.col-12
      [:h6.text-secondary.my1 "Sent 9.15"]]]]])

(defn msg-out [k m]
  ^{:key k}
  ;; HACK: Simplify markup
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
             (when (gdom/getElement "bottom-bar")           ; bottom-bar and thus the neccesary comp. are mounted
               (set! (-> id_chat_feed
                         gdom/getElement
                         .-style
                         .-height)
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

(defn scrolled-to-bottom? [id]
  ;; TODO: add tolerance
  (let [el (gdom/getElement id)]
    (= (.-scrollTop el)
       (- (.-scrollHeight el)
          (.-offsetHeight el)))))

(defn- handle-scroll
  [should-scroll]
  (when @should-scroll
    (as-> id_chat_feed feed
          (gdom/getElement feed)
          (scroll! feed
                   [0 (.-scrollTop feed)]
                   [0 (.-scrollHeight feed)]
                   500))))

(defn auto-scroll-button [scrolled-to-bottom]
    (if-not @scrolled-to-bottom
      [:div.border "Scroll!"]
      [:div]))

(defn chat-feed []
  (let [messages           (re-frame/subscribe [:sub-to [:messages]])
        should-scroll      (reagent/atom false)
        scrolled-to-bottom (reagent/atom false)]
    (fn []
      (handle-scroll should-scroll)
      [:div#chat-feed
       {:on-click  (macros/handler-fn
                     (swap! should-scroll not))
        :on-scroll (macros/handler-fn
                     (if (scrolled-to-bottom? id_chat_feed)
                       (do (reset! should-scroll true)
                           (reset! scrolled-to-bottom true))
                       (do (reset! should-scroll false)
                           (reset! scrolled-to-bottom false))))}
       [anim/css-trans-group {:transition-name "feed"
                              :transition-enter-timeout 5000
                              :transition-leave-timeout 5000}
        (for [{:keys [in-or-out key msg]} (reverse @messages)]
          (if (= :out in-or-out)
            (msg-out key msg)
            (msg-in  key msg)))]
       [auto-scroll-button scrolled-to-bottom]])))
