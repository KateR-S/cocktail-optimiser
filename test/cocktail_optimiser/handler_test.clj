(ns cocktail-optimiser.handler-test
  (:require [cocktail-optimiser.handler :as handler]
            [clojure.test :refer :all]
            [cheshire.core :as json]))

(defn coerce-body
  [{:keys [headers] :as resp}]
  (println (get headers "Content-Type"))
  (if (= (get headers "Content-Type") "application/json; charset=utf-8")
    (update resp :body (comp #(json/parse-string % true) slurp))
    resp))

(deftest ping-pong
  (let [{:keys [status body]} (handler/app {:uri "/ping"
                                            :request-method :get})]
    (is (= 200 status))
    (is (= "pong" body))))

(deftest recipe-returns
  (let [{:keys [status body]} (coerce-body (handler/app {:uri "/v1/recipes/appletini"
                                                         :request-method :get}))]
    (is (= 200 status))
    (is (= {:ingredients {:vodka 3
                          :calvados 1
                          :contreau 1}
            :garnish {:apple-slice 1}
            :method ["chill glass" "mix in a shaker" "pour into glass" "garnish and serve"]}
           body))))
