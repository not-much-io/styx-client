(ns styx-client.views.bottom-bar)

(defn attachment-button
  []
  [:i.material-icons.text-color2.mx1.circle "attachment"])

(defn message-box
  []
  [:textarea.slim-text-box.bg-color1.mx1
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
   [:div.flex.items-center.py1
    [:div.col-1
     [attachment-button]]
    [:div.col-9
     [message-box]]
    [:div.col-1
     [emoticon-button]]
    [:div.col-1
     [send-button]]]])