(ns styx-client.main-panel
  (:require [re-frame.core :as re-frame]
            [styx-client.views.app-bar :refer [top-bar]]
            [styx-client.views.chat-feed :refer [chat-box]]
            [styx-client.views.bottom-bar :refer [message-box]]
            [styx-client.animation :refer [css-trans-group]]))

(defn main-panel []
  (let [messages  (re-frame/subscribe [:messages])
        open-view (re-frame/subscribe [:open-view])]
    (fn []
      [:div#wrapping-div.color1
       [css-trans-group {:transition-name "view"}
        (if (= @open-view :chat)
          ^{:key "chat-view"}
          [:div.shadow5
           [top-bar]
           [chat-box @messages]
           [message-box]]
          ^{:key "contact-list"}
          [:div.coming-soon.shadow5.center
           {:on-click #(re-frame/dispatch [:set-view :chat])}
           [:div
            [:i.material-icons.text-color2
             "warning"]
            [:div.text-main "In the works, click anywhere on his card to go back."]]])]])))
