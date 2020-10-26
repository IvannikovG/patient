(ns app.utils
  (:require [app.db :as db]
            [clojure.java.jdbc :as j]))


(def random (java.util.Random.))

(def characters
  (map char (concat (range 48 58) (range 66 92) (range 97 123))))

(defn random-char []
  (nth characters (.nextInt random (count characters))))

(defn random-string [length]
  (apply str (take length (repeatedly random-char))))


(def names ["Alice Daby Johansen" "Bob Tyge Fiber" "James Yu Xin"
            "Taiwenn UI PO" "Olwenne VB GH" "Rusty Van Hoven"
            "Serafim Alekseevich Sorovskiy" "Angel Angel Smith"
            "Veronica Eleonora Vernitz"])

(defn random-name []
  (rand-nth names))

(defn random-gender []
  (rand-nth ["female" "male" "other"]))

(defn random-birth-date []
  (let [year (rand-nth (range 1910 2021))
        month (rand-nth (range 1 13))
        day (rand-nth (range 1 32))]
    (str year "-" month "-" day)))

(defn random-address [length]
  (random-string length))

(defn random-insurance [length]
  (random-string length))


(defn generate-random-patient-edn []
  {:fullname (random-name)
   :gender (random-gender)
   :birthdate (random-birth-date)
   :address (random-address 10)
   :insurance (random-insurance 10)})

(defn generate-n-patients [n]
  (take n (repeatedly generate-random-patient-edn)))

(defn create-n-patients-in [n table-name]
  (j/insert-multi! db/db-spec table-name (generate-n-patients n)))
