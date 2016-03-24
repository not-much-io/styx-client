(ns test.utils-tests
  (:require [cljs.test :refer-macros [deftest is testing run-tests]]
            [styx-client.utils :refer [handler-fn]]))

(deftest test-handler-fn
  (let [test-atom (atom false)]
    (is (= nil  ((handler-fn #(swap! test-atom not)))))
    (is (= nil  ((handler-fn #(swap! test-atom not)))))))
