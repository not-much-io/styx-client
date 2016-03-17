(ns styx-client.db)

(def default-db
  {:messages (shuffle
               (flatten
                 (repeat 10 ["Lorem impsum dolor sit amper consecreteur adipiscing"
                             "lore impsum dolor"])))})
