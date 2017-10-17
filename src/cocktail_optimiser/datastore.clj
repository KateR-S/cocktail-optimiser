(ns cocktail-optimiser.datastore
  (:require [clj-http.client :as client]
            [cheshire.core :as json]
            [clojure.string :as s]
            [clojure.walk :as w]))

(defn add-recipe
  [recipe])

(defn- only-exact-name
  [recipe passed-name]
  (let [name-from-recipe (s/upper-case (:strDrink recipe))]
    (if
        (= name-from-recipe (s/upper-case passed-name))
      recipe
      )))

(defn- check-recipe-exists
  [recipe-name]

  (let [url (str "http://www.thecocktaildb.com/api/json/v1/1/search.php?s=" recipe-name)
        resp (client/get url)
        drink-list (:drinks (json/parse-string (:body resp) true))]

    (into {} (map #(only-exact-name % recipe-name) drink-list))))

(defn- identify-category
  [k v category]
  (and (re-find #category (name k)) (not (s/blank? v))))

(defn- find-ingredient
  [res [k v]]
  (if (identify-category k v "Ingredient")
    (conj res v)
    res
    ))

(defn- get-ingredient-list
  [recipe]
  (reduce find-ingredient [] recipe))

(defn get-recipe
  [recipe-name]
  (some-> recipe-name
          (check-recipe-exists)
          (get-ingredient-list)))

