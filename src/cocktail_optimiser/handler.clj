(ns cocktail-optimiser.handler
  (:require [compojure.api.sweet :as sweet]))

(sweet/defapi app
  (sweet/GET "/ping" []
             {:body "pong"
              :status 200}))
