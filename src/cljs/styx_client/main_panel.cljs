(ns styx-client.main-panel
  (:require [re-frame.core :as re-frame]
            [styx-client.chat.views.chat-view :refer [chat]]
            [styx-client.contacts.views.contact-list-view :refer [contact-list]]
            [styx-client.animation :refer [css-trans-group]]))

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
