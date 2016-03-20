(ns styx-client.test-data)

(defn get-random-message []
  (let [text ["Lorem " "ipsum " "dolor " "sit " "amet,
              consectetur " "adipiscing " "elit,
              sed " "do " "eiusmod " "tempor " "incididunt " "ut " "labore "]
        msg (apply str (take (rand-int (count text)) (shuffle text)))
        in-or-out? (fn []
                     (if (even? (rand-int 100))
                       :in
                       :out))]
    (assoc {:in-or-out? (in-or-out?)} :msg msg)))
