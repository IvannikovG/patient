(ns app.utils
  (:require [clojure.java.jdbc :as j]
            [clojure.walk :as walk]
            [ring.util.codec :refer [form-decode]]
            [clj-time.core :as time]
            [clj-time.coerce :as c]))


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


(defn convert-parameters-to-valid
  [query-parameters]
  {:full_name (str (:full_name query-parameters))
   :gender (str (:gender query-parameters))
   :birthdate (c/to-sql-date (:birthdate query-parameters))
   :address (str (:address query-parameters))
   :insurance (str (:insurance query-parameters))})

;;;; FOR  DATA GENERATION ;;;;
;;;; UNTESTED. But they do not need tests more than ,ef ;;;;
(defn now [] (new java.util.Date))
(def random (java.util.Random.))
(def chariks (map char (concat (range 48 58)
                               (range 66 92)
                               (range 97 123))))
(defn random-char []
  (nth chariks (.nextInt random (count chariks))))
(defn random-string [length]
  (apply str (take length (repeatedly random-char))))
(defn random-sql-date []
  (c/to-sql-date (time/date-time (rand-nth (range 1940 2020))
                                 (rand-nth (range 1 13))
                                 (rand-nth (range 1 28)))))
(defn sample-patient []
  {:full_name (random-string 5)
   :gender (random-string 5)
   :birthdate (random-sql-date)
   :address (random-string 15)
   :insurance (random-string 15)
   :created_at (random-sql-date)
   })

