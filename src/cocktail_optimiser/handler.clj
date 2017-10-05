(ns cocktail-optimiser.handler
  (:require [compojure.api.sweet :refer [api context GET POST]]))

(def app
  (api
   {:swagger {:ui "/api-docs"
              :spec "/swagger.json"
              :data {}}}
   (context "" []
     (GET "/ping" []
       {:body "pong"
        :status 200}))))
