(ns app.specs-test
  (:require [app.specs :as specs]
            [clojure.spec.alpha :as s]
            [clojure.test :as t]))


(t/deftest specs-test

  (defn now [] (new java.util.Date))

  (t/is (true? (specs/validate-full-name "Assai Assassin")))
  (t/is (false? (specs/validate-full-name "Assai")))
  (t/is (true? (specs/validate-birthdate (now))))
  (t/is (false? (specs/validate-birthdate "Birthdate")))
  (t/is (true? (specs/validate-gender "male")))
  (t/is (true? (specs/validate-gender "female")))
  (t/is (true? (specs/validate-gender "other")))
  (t/is (false? (specs/validate-gender "otter")))
  (t/is (true? (specs/validate-insurance "123-123-123")))
  (t/is (false? (specs/validate-insurance "123-123")))
  (t/is (true? (specs/validate-address "Long address that conforms")))
  (t/is (false? (specs/validate-address "Short address")))
  (t/is (true? (specs/validate-patient
                {:full_name "anna KArenian"
                 :birthdate (now)
                 :insurance "123-123-123-123"
                 :gender "female"
                 :address "Vishnevaya stree 8/15"})))
  (t/is (false? (specs/validate-patient
                {:full_name "anna KArenian"
                 :insurance "123-123-123-123"
                 :gender "female"
                 :address "Vishnevaya stree 8/15"}))))

(t/run-tests)
