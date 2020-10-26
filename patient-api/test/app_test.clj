(ns app-test
  (:require [clojure.test :as t :refer :all]
            [app.db :as db]
            [clj-time.core :as time]
            [clj-time.format :as f]))


(def custom-formatter (f/formatter "YYYYMMDD"))


(def sample-patient {:fullname "Viktor Petrovich Suhorukov"
                     :gender "male"
                     :birthdate (f/unparse custom-formatter
                                 (time/date-time 1990 01 01))
                     :address "Moscow, 29th Street"
                     :insurance "1234123412341234"})

(def update-patient-form {:fullname "Alexa McLaren"
                          :gender "female"
                          :address "Moscow, 29th Street"
                          :insurance "123412341"})

(defn db-table-fixture [f]
  (db/create-test-patient-table)
  (f)
  (db/drop-test-patient-table))


(deftest create-patient-test
  (db/create-test-patient sample-patient)
  (let [patient (db/get-test-patient-by-id 1)]
    (is (= (:fullname patient)
           "Viktor Petrovich Suhorukov"))))

(deftest update-patient-test
  (db/update-test-patient 1 update-patient-form)
  (let [patient (db/get-test-patient-by-id 1)]
    (is (= (:fullname patient) "Alexa McLaren"))))

(deftest create-and-update-test
  (testing(create-patient-test)
          (update-patient-test)))


(use-fixtures :once db-table-fixture)

