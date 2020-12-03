(ns app.utils
  (:require [clojure.java.jdbc :as j]
            [clojure.test :as t]
            [clojure.walk :as walk]
            [ring.util.codec :refer [form-decode]]
            [clj-time.core :as time]
            [clj-time.coerce :as c]
            [clojure.data :as d]))

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

(defn valid-patient-parameters
  [query-parameters]
  {:full_name (str (:full_name query-parameters))
   :gender (str (:gender query-parameters))
   :birthdate (c/to-sql-date (format-date (:birthdate query-parameters)))
   :address (str (:address query-parameters))
   :insurance (str (:insurance query-parameters))
   :created_at (c/to-sql-date (now))})


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

(defn convert-patient-entry-to-raw [db-patient]
  {:full_name (:full_name db-patient)
   :gender (:gender db-patient)
   :birthdate (apply str (take 10 (str (:birthdate db-patient))))
   :address (:address db-patient)
   :insurance (:insurance db-patient)})

(defn convert-patients-entries-to-raw [patients]
  (map convert-patient-entry-to-raw patients))

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
(defn random-date-string []
  "Untested. Returns string: YYYY-MM-DD"
  (str (rand-nth (range 1940 2020)) "-"
       (rand-nth (range 1 13)) "-"
       (rand-nth (range 1 28))))

(defn sample-patient []
  "Generates a random petient"
  {:full_name (random-string 5)
   :gender (random-string 5)
   :birthdate (random-date-string)
   :address (random-string 5)
   :insurance (random-string 5)
   })

(defn patient-difference [s1 s2]
  (let [[f-diff s-diff same] (d/diff s1 s2)]
    (cond (= 5 (count same)) nil
          :else (apply merge
                       (filter (complement nil?)
                               (map (fn [c1 c2]
                                      (if (= c1 c2)
                                        nil
                                        (hash-map
                                         (first c1)
                                         [(second c1) (second c2)]))) s1 s2))))))

;; STRING ANALYSER ;;

(defn total-letter-sum
  [analysed-str]
  (reduce (fn [acc [k, v]] (+ acc v)) 0 analysed-str))

(defn analyse-string [string]
  (let [grouped-string
        (group-by identity (clojure.string/split string #""))
        analysed-string
        (map (fn [[k v]] {(keyword k) (count v)}) grouped-string)]
    (apply merge analysed-string)))

(defn string-difference [analysed-1 analysed-2]
  (use 'clojure.data)
  "Return <difference from 1 to 2 | difference from 2 to 1>"
  (let [diffr (clojure.data/diff analysed-1 analysed-2)]
    [(first diffr) (second diffr)]))

(defn has-one-additional-insertion-deletion? [str1 str2]
  (let [analysed-1 (analyse-string str1)
        analysed-2 (analyse-string str2)
        str-diffr (string-difference analysed-1 analysed-2)]
    (if (and
             (or (not (nil? (first str-diffr)))
                 (not (nil? (second str-diffr))))
             (= (Math/abs (- (total-letter-sum analysed-1)
                                  (total-letter-sum analysed-2))) 1))
      true
      false)))

(defn has-one-update? [str1 str2]
  (let [falses (filter false? (map = str1 str2))]
    (if (and (= (count str1)
                (count str2))
             (= (count falses) 1))
      true
      false)))


(defn small-difference? [el]
  (let [[str1 str2] el]
    (or (has-one-additional-insertion-deletion? str1 str2)
        (has-one-update? str1 str2))))

(defn big-difference? [[el1 el2]]
  (if (= el1 el2)
    false
    ((complement small-difference?) [el1 el2])))


(defn should-save-patient? [patient-difference]
  (let [small-difference-count
        (count (filter small-difference? (vals patient-difference)))
        big-difference-count
        (count (filter big-difference? (vals patient-difference)))
        big 4]
    (cond (> big-difference-count 0) true
          (>= small-difference-count big) true
          :else false)))

(def test-patients [ {:full_name "Alex Vith"
                      :gender "male"
                      :birthdate (format-date "1990-11-11")
                      :address "Vishnevaya street 8/16. Moscow Russia"
                      :insurance "1111-2222-3333-4444"
                      }
                    (sample-patient)
                    (sample-patient)
                    ])
(def example-patient {:full_name "Alexi Vith"
                     :gender "emale"
                     :birthdate (format-date "1990-11-11")
                     :address "Vishnevaya street 8/16. Moscow Russia"
                     :insurance "1111-2222-3333-4444"
                      })

(defn will-not-save-patient? [patient existent-patients]
  (if (some false? (map should-save-patient?
                        (map #(patient-difference patient %)
                             existent-patients)))
    true
    false))
