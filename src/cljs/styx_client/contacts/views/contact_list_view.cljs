(ns styx-client.contacts.views.contact-list-view
  (:require [styx-client.contacts.views.top-bar :refer [search-bar]]
            [styx-client.contacts.views.contact-list :refer [list-of-contacts]]))

(defn contact-list
  []
  [:div#contact-list
   [search-bar]
   [list-of-contacts]])

