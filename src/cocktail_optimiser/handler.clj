(ns cocktail-optimiser.handler
  (:require [compojure.api.sweet :refer [api context GET POST]]
            [cocktail-optimiser.datastore :as ds]))

(def app
  (api
   {:swagger {:ui "/api-docs"
              :spec "/swagger.json"
              :data {}}}
   (context "" []
     (GET "/ping" []
       {:status 200
        :body "pong"})

     (context "/v1/recipes" []
       (GET "/:recipe" req
         {:status 200
          :headers {"Content-Type" "application/json"}
          :body (ds/get-recipe "junk")})))))
