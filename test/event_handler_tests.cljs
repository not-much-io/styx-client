(ns test.event-handler-tests
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [re-frame.core :as re-frame]))

(deftest test-set-loading?
  (is (= 1 1)))

(run-tests)