(ns styx-client.chat.views.bottom-bar)

(def id_bottom_bar "bottom-bar")

(defn attachment-button
  []
  [:i.material-icons.text-color2.circle "attachment"])

(defn message-box
  []
  [:textarea.slim-text-box.bg-color1
   {:defaultValue "Type your message..."
    :rows         1}])

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