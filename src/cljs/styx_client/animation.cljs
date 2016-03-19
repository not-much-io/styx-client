(ns styx-client.animation
  (:require [goog.dom :as gdom]))

(def css-trans-group (reagent.core/adapt-react-class js/React.addons.CSSTransitionGroup))

(defn fit-vertical
  "Fits a div between a top and bottom div inside the same parent div."
  [to-fit-id top-id bottom-id parent-id]
  (let [heights (map #(.-offsetHeight (gdom/getElement %))
                     [parent-id top-id bottom-id])
        fit-height (apply - heights)]
    (set! (-> to-fit-id
              (gdom/getElement)
              (.-style)
              (.-height))
          (str fit-height "px"))))

(defn scroll-down-by
  "Scroll element with id down by x pixels."
  [id x]
  (let [el (gdom/getElement id)]
    (set! (.-scrollTop el)
          (+ (.-scrollTop el) x))))