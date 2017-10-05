(ns cocktail-optimiser.handler-test
  (:require [cocktail-optimiser.handler :as handler]
            [clojure.test :refer :all]))

(deftest ping-pong
  (let [{:keys [status body]} (handler/app {:uri "/ping"
                                            :request-method :get})]
    (is (= 200 status))
    (is (= "pong" body))))
