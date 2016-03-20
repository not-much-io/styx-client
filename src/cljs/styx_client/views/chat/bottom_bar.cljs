(ns styx-client.views.bottom-bar)

(defn attachment-button
  []
  [:i.material-icons.text-color2.m2.circle "attachment"])

(defn message-box
  []
  [:textarea.slim-text-box.bg-color1.text-main.m2
   {:defaultValue "Type your message..."
    :rows         1}])

(defn emoticon-button
  []
  [:i.material-icons.text-color2.my2.circle "insert_emoticon"])

(defn send-button
  []
  [:i.material-icons.text-color2.my2.circle "send"])

(defn bottom-bar
  []
  [:div#bottom-bar.bg-color1.border-top.border-color1
   [:div.flex.flex-wrap
    [:div.col-1
     [attachment-button]]
    [:div.col-9
     [message-box]]
    [:div.col-1
     [emoticon-button]]
    [:div.col-1
     [send-button]]]])