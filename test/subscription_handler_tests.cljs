(ns tests.subscription-handler-tests
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            [styx-client.subs :as subs]))

(deftest sub-to-test
  (let [test-db (reagent/atom {:a 0
                               :b 1
                               :ne {:st {:ed 2}}})]
    (testing "With single key."
      (is (= (subs/sub-to-handler test-db [:a]) 0))
      (is (= (subs/sub-to-handler test-db [:b]) 1)))
    (testing "Nested keys."
      (is (= (subs/sub-to-handler test-db [:ne :st :ed]) 2)))))

(run-tests)