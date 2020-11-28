(ns app.utils-test
  (:require  [clojure.test :as t]
             [app.utils :as u]))

(t/deftest utils-test
  (t/is (= (u/str-to-int "123") 123))
  (let [converted (u/convert-parameters-to-valid
                   {:full_name "TEST PATIENT"
                    :gender "TEST GENDER"
                    :birthdate "1990-11-11"
                    :address "Adress"
                    :insurance "Insurance"
                    })]
    (t/is (and (= (class (:full_name converted)) java.lang.String)
               (= (class (:gender converted)) java.lang.String)
               (= (class (:birthdate converted)) java.sql.Date)
               (= (class (:address converted)) java.lang.String)
               (= (class (:insurance converted)) java.lang.String)))
    (t/is (true? (u/valid-keys? {:full_name "Sample"
                                 :gender "Sample"
                                 :birthdate "Sample"
                                 :address "Sample"
                                 :insurance "Sample"})))
    (t/is (false? (u/valid-keys? {:full_name "Sample"
                                  :dog "Sample"})))))
(t/run-tests)
