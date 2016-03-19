(ns styx-client.views.bottom-bar)

(defn message-box []
  [:div#bottom-bar.color1.border-top.grey-border
   [:div.flex.flex-wrap
    [:div.col-1
     [:i.material-icons.text-color2.m2.circle "attachment"]]
    [:div.col-9
     [:textarea#chat-box.color1.text-main.m2
      {:defaultValue "Type your message..."
       :rows 1}]]
    [:div.col-1
     [:i.material-icons.text-color2.my2.circle "insert_emoticon"]]
    [:div.col-1
     [:i.material-icons.text-color2.my2.circle "send"]]]])