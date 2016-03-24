(ns styx-client.main-panel
  (:require [re-frame.core :as re-frame]
            [styx-client.views.chat-view :refer [chat]]
            [styx-client.views.contact-list-view :refer [contact-list]]
            [styx-client.animation :refer [css-trans-group]]))

(defn main-panel []
  (let [open-view (re-frame/subscribe [:sub-to [:open-view]])]
    (fn []
      [:div#wrapping-div.color1
       [css-trans-group {:transition-name "view"
                         :transition-enter-timeout 5000
                         :transition-leave-timeout 5000}
        (if (= @open-view :chat)
          ^{:key "chat-view"}
          [:div
           [chat]]
          ^{:key "contact-list"}
          [:div
           [contact-list]])]])))
