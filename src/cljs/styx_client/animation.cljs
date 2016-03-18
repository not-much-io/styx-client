(ns styx-client.animation
  (:require [goog.dom :as gdom]))

(defn- get-height
  [id]
  (.-offsetHeight (gdom/getElement id)))

(defn fit-vertical
  [to-fit-id top-id bottom-id parent-id]
  (let [heights     (map get-height [parent-id top-id bottom-id])
        fit-height  (apply - heights)]
    (set! (-> to-fit-id
              (gdom/getElement)
              (.-style)
              (.-height))
          (str fit-height "px"))))

(defn scroll-down-by-1px
  [scrollable-dom-el]
  (let [scrl-top    (.-scrollTop scrollable-dom-el)]
    (set! (.-scrollTop scrollable-dom-el)
          (inc scrl-top))))
