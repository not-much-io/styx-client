(ns styx-client.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [styx-client.handlers]
              [styx-client.subs]
              [styx-client.views :as views]
              [styx-client.config :as config]))

(when config/debug?
  (println "dev mode"))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app"))
  (re-frame/dispatch [:set-loading? false]))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (mount-root))
