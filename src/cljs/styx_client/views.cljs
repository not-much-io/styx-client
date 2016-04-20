(ns styx-client.views
  (:require [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [goog.dom :as gdom]
            [goog.fx.dom :as gfx-dom])
  (:require-macros [styx-client.macros :as macros]))

; Common

(def css-trans-group (reagent.core/adapt-react-class js/React.addons.CSSTransitionGroup))

(defn separator []
  [:div.my1
   [:div.border-bottom.border-color1
    {:style {:width "100%"}}]])

; IDs

(def id_bottom_bar "bottom-bar")
(def id_top_bar "top-bar")

; ==================== Chat Top Bar ====================

(defn- nav-button
  []
  [:div {:on-click #(re-frame/dispatch [:set-view :other])}
   [:i.material-icons.m1.text-color2
    "chevron_left"]])

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

(defn top-bar
  []
  [:div#top-bar.bg-color1.border-bottom.border-color1.shadow3
   [:div.flex.items-center
    [nav-button]
    [display-pic]
    [contact-signature "Kristo Koert" "Away"]]])

; ==================== Chat Feed ====================

;; REVIEW: Is this a good way to do this?
(def id_chat_feed "chat-feed")

(defn- msg-in [k m]
  ^{:key k}
  ;; HACK: Simplify markup
  [:div.ml2.mt1
   [:div.flex.flex-wrap
    [:div.col-12
     [:div.message-in.bg-color2.text-main.p2.rounded.shadow3 m]]
    [:div.col-12
     [:h6.text-secondary.my1 "Sent 9.15"]]]])

(defn- msg-out [k m]
  ^{:key k}
  ;; HACK: Simplify markup
  [:div.mr2.mt1
   [:div.flex.flex-wrap
    [:div.col-12
     [:div.message-out.bg-color3.text-main.p2.rounded.ml-auto.shadow3 m]]
    [:div.col-12
     [:h6.text-secondary.my1.right-align "Sent 9.15"]]]])

(defonce _
         ;; Make views feed take remaining height between top bar and bottom bar.
         ;; HACK: There is probably a better, less CPU intensive way to do this (CSS?).
         (js/setInterval
           (fn []
             (when (gdom/getElement id_bottom_bar)           ; bottom-bar and thus the neccesary comp. are mounted
               (set! (-> id_chat_feed
                         gdom/getElement
                         .-style
                         .-height)
                     (as-> ["app" id_top_bar id_bottom_bar] x
                           (map gdom/getElement x)
                           (map #(.-offsetHeight %) x)
                           (apply - x)
                           (str x "px")))))                 ; available height between top and bottom bar
           16))

(defn- scroll! [el start end time]
  "Plays a animation that will scroll an element from A to B.
  Start and End should be 2 dimensional arrays. [left, top]"
  (.play (gfx-dom/Scroll. el (clj->js start) (clj->js end) time)))

(defn- scrolled-to-bottom? [id]
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

(defn- auto-scroll-button
  [should-scroll]
  ;; FIXME: Fix edge cases with scrolling
  ;;        1) Scrolling up when new message appears
  ;;        2) Auto scroll button not showing after wp dimension change
  ;;        3) Auto scroll button showing when new message added
  (if-not (and @should-scroll
               (scrolled-to-bottom? id_chat_feed))
    [:div.absolute
     {:style {:width "100%"
              :bottom "5em"}}
     [:div.flex
      [:div.col-5]
      [:div.col-2.center
       {:style    {:background-color "black"
                   :opacity          ".5"}
        :on-click (macros/handler-fn
                    (reset! should-scroll true))}
       [:i.material-icons.text-color2.m2 "expand_more"]]
      [:div.col-5]]]
    [:div]))

(defn chat-feed []
  (let [messages           (re-frame/subscribe [:messages])
        should-scroll      (reagent/atom false)]
    (fn []
      ;; TODO: Scroll to bottom on initial load.
      (handle-scroll should-scroll)
      [:div#chat-feed
       {:on-scroll (macros/handler-fn
                     (if (scrolled-to-bottom? id_chat_feed)
                       (reset! should-scroll true)
                       (reset! should-scroll false)))
        :onload    (macros/handler-fn
                     (reset! should-scroll true))}
       [css-trans-group {:transition-name "feed"
                              :transition-enter-timeout 500
                              :transition-leave-timeout 500}
        (for [{:keys [in-or-out? key msg]} (reverse @messages)]
          (if (= :out in-or-out?)
            (msg-out key msg)
            (msg-in key msg)))]
       [auto-scroll-button should-scroll]])))

; ==================== Chat Bottom Bar ====================

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

; ==================== Chat View ====================

(defn chat []
  [:div
   [top-bar]
   [chat-feed]
   [bottom-bar]])

; ==================== Contacts Top Bar ====================

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

; ==================== Contacts List ====================

(defn- display-pic2
  []
  [:img.dp-small.circle.shadow2
   {:src "https://avatars.slack-edge.com/2015-05-30/5147732825_d04b8e601de9c90bed00_192.jpg"}])

(defn- contact-name
  [name]
  [:div
   [:h4.my0
    ;; HACK: Can't make do with basscss?
    {:style {:margin-top "5px"}}
    name]])

(defn- contact-last-seen
  [last-seen]
  [:div
   [:h6.text-secondary.my0
    last-seen]])

(defn- msg-preview
  []
  [:h5.m0.text-secondary
   "- Lorem ipsum dolor sit amet.."])

(defn- contact-item
  []
  [:div.flex
   {:on-click #(re-frame/dispatch [:set-view :chat])}
   [display-pic2]
   [:div.flex.flex-auto
    [:div.flex-auto.ml1
     [contact-name "Kristo"]
     [msg-preview]]
    [:div
     [contact-last-seen "19:29"]]]])

(defn- list-of-contacts
  []
  [:div.px2
   [:h5.text-secondary "Today"]
   (repeat 5
           [:div
            [contact-item]
            [separator]])
   [:h5.text-secondary "Earlier"]
   (repeat 10
           [:div
            [contact-item]
            [separator]])])

; ==================== Contact List View ====================

(defn contact-list
  []
  [:div#contact-list
   [search-bar]
   [list-of-contacts]])

; main panel

(defn main-panel []
  (let [open-view (re-frame/subscribe [:open-view])]
    (fn []
      [:div#wrapping-div.color1.relative
       [css-trans-group {:transition-name "view"
                         :transition-enter-timeout 500
                         :transition-leave-timeout 500}
        (if (= @open-view :chat)
          ^{:key "views-view"}
          [:div.shadow1
           {:min-width "100vw"}
           [chat]]
          ^{:key "contact-list"}
          [:div.shadow1
           [contact-list]])]])))
