(ns styx-client.test-data)

(defn get-random-message []
  (let [text ["Lorem " "ipsum " "dolor " "sit " "amet,
              consectetur " "adipiscing " "elit,
              sed " "do " "eiusmod " "tempor " "incididunt " "ut " "labore "]]
    (take (rand-int (count text)) (shuffle text))))
