(ns styx-client.utils)

(defn handler-fn
  [f]
  ;; FIXME:  https://github.com/Day8/re-frame/wiki/Beware-Returning-False
  ;;         But I am trying to avoid usign a macro. Getting "not a function" in ratom..
  (fn [event]
    (let [res (f)]
      (if (= (type res) js/Boolean)
        nil
        res))))

(defonce lastId (atom -1))

(defn get-random-key
  []
  ;; HACK: This is just for temporary use in a testing environment.
  (swap! lastId inc))

(defn get-random-message []
  ;; HACK: Thrown together randomly
  (let [text ["Lorem " "ipsum " "dolor " "sit " "amet,
              consectetur " "adipiscing " "elit,
              sed " "do " "eiusmod " "tempor " "incididunt " "ut " "labore "]
        msg (apply str
                   (take
                     (inc
                       (rand-int
                         (dec (count text))))
                     (shuffle text)))
        in-or-out? (fn []
                     (if (even? (rand-int 100))
                       :in
                       :out))]
    (assoc {:in-or-out? (in-or-out?)
            :key        (get-random-key)} :msg msg)))