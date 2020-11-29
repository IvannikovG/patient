(ns app.utils
  (:require [clojure.java.jdbc :as j]
            [clojure.walk :as walk]
            [ring.util.codec :refer [form-decode]]
            [clj-time.core :as time]
            [clj-time.coerce :as c]))

(defn now [] (new java.util.Date))

(defn format-date [date]
  (when ((complement nil?) date)
    (let [date-contents (clojure.string/split date #"/")]
      (if (= (count date-contents) 3)
        (clojure.string/join "-" [(last date-contents)
                                  (first date-contents)
                                  (second date-contents)
                                  ])
        date)))
  )

(defn str-to-int [x]
  (Integer/parseInt
   (apply str (filter #(Character/isDigit %) x))))

(defn valid-keys? [form-data]
  (let [needed-keywords [:full_name :gender :birthdate
                         :address :insurance]]
    (if (every? true? (map (fn [el]
                             (contains? form-data el))
                             needed-keywords))
      true
      false)))

(c/to-sql-date "2020-11-29")

(defn valid-patient-parameters
  [query-parameters]
  {:full_name (str (:full_name query-parameters))
   :gender (str (:gender query-parameters))
   :birthdate (c/to-sql-date (format-date (:birthdate query-parameters)))
   :address (str (:address query-parameters))
   :insurance (str (:insurance query-parameters))
   :created_at (c/to-sql-date (now))})

(valid-patient-parameters {:full_name "Karenina", :gender "female",
                           :birthdate "11/29/2020",
                           :address "Address", :insurance "Insurance"})

(defn clean-request-params [query-parameters]
  (let [raw-data {:full_name (str (:full_name query-parameters))
                  :gender (str (:gender query-parameters))
                  :birthdate (c/to-sql-date
                              (format-date (:birthdate query-parameters)))
                  :address (str (:address query-parameters))
                  :insurance (str (:insurance query-parameters))}]
    (apply merge (map (fn [[k v]]
                        (if (or (= (class v) java.sql.Date)
                                ((complement empty?) v))
                           {k v})) raw-data)))
  )


;;;; FOR  DATA GENERATION ;;;;
(def random (java.util.Random.))
(def chariks (map char (concat (range 48 58)
                               (range 66 92)
                               (range 97 123))))
(defn random-char []
  "Untested func. Generates a random character"
  (nth chariks (.nextInt random (count chariks))))
(defn random-string [length]
  "Generates a random string of length n"
  (apply str (take length (repeatedly random-char))))
(defn random-sql-date []
  (c/to-sql-date (time/date-time (rand-nth (range 1940 2020))
                                 (rand-nth (range 1 13))
                                 (rand-nth (range 1 28)))))
(defn sample-patient []
  "Generates a random petient"
  {:full_name (random-string 5)
   :gender (random-string 5)
   :birthdate (random-sql-date)
   :address (random-string 15)
   :insurance (random-string 15)
   :created_at (random-sql-date)
   })

