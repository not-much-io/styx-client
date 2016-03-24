(ns styx-client.test-data
  (:require [styx-client.utils :as utils]))

(defn get-random-message []
  ;; HACK: Thrown together randomly
  (let [text ["Lorem " "ipsum " "dolor " "sit " "amet,
              consectetur " "adipiscing " "elit,
              sed " "do " "eiusmod " "tempor " "incididunt " "ut " "labore "]
        msg (apply str (take (rand-int (count text)) (shuffle text)))
        in-or-out? (fn []
                     (if (even? (rand-int 100))
                       :in
                       :out))]
    (assoc {:in-or-out? (in-or-out?)
            :key        (utils/get-random-key)} :msg msg)))
