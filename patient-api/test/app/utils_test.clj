(ns app.utils-test
  (:require  [clojure.test :as t]
             [app.utils :as u]))


(def test-patient {:full_name "Alex Vith"
                   :gender "female"
                   :birthdate (u/format-date "1990-11-11")
                   :address "Vishnevaya street 8/16. Moscow Russia"
                   :insurance "1111-2222-3333-4444"
                   })
(def test-patient-2 {:full_name "Alex With"
                     :gender "female"
                     :birthdate (u/format-date "1992-11-11")
                     :address "Vishnevaya street 8/16 Moscow Russia"
                     :insurance "1111-2222-3333-4444"
                     })
(def test-patient-3 {:full_name "Alexey Vith"
                     :gender "other"
                     :birthdate (u/format-date "2000-10-10")
                     :address "Lodochnaya Street 11"
                     :insurance "1111-2222-3333-4344"
                     })
(def test-patients [test-patient
                    test-patient-2
                    test-patient-3])

(t/deftest general-utils-test
  (t/is (= (u/str-to-int "123") 123))
  (let [converted (u/valid-patient-parameters
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
    (t/is (= (u/clean-request-params {:full_name ""
                                      :address ""
                                      :insurance "Sample"})
             {:insurance "Sample"}))
    (t/is (= 5 (count (u/random-string 5))))
    (t/is (= (u/format-date "11/29/2020") "2020-11-29"))
    (t/is (true? (u/valid-keys? {:full_name "Sample"
                                 :gender "Sample"
                                 :birthdate "Sample"
                                 :address "Sample"
                                 :insurance "Sample"})))
    (t/is (false? (u/valid-keys? {:full_name "Sample"
                                  :dog "Sample"})))))

(t/deftest string-analyser-test
  (t/is (= (u/analyse-string "ABC") {:A 1 :B 1 :C 1}))
  (t/is (= (u/string-difference
            (u/analyse-string "Alex Vith")
            (u/analyse-string "Alex With"))
           [{:V 1} {:W 1}]))
  (t/is (= (u/total-letter-sum {:a 1 :b 2 :c 3 :d 4}) 10))
  (t/testing "Only one update"
    (t/is (= true (u/has-one-update? "Alex Vith" "Alex With")))
    (t/is (= true (u/has-one-update? "Anna Karenina" "Anni Karenina")))
    (t/is (= false (u/has-one-update? "Ovada Kedavra" "Ogata kevadra")))
    (t/is (= false (u/has-one-update? "Crutsio" "Crutsioo")))
    (t/is (= false (u/has-one-update? "Anna" "nnAa")))
    )
  (t/testing "One insertion or one deletion"
    (t/is (= false (u/has-one-additional-insertion-deletion?
                    "Mudblooded" "Mudblood")))
    (t/is (= true (u/has-one-additional-insertion-deletion?
                   "Sectum Sempra" "Sectum Smpra")))
    (t/is (= true (u/has-one-additional-insertion-deletion?
                   "QQhare" "QQQhare")))
    (t/is (= true (u/has-one-additional-insertion-deletion?
                   "hare" "har")))
    (t/is (= false (u/has-one-additional-insertion-deletion?
                    "Imperial" "Imperiol")))
    )
  (t/is (= true (u/small-difference? ["Ales" "Alex"])))
  (t/is (= true (u/small-difference? ["Aes" "Aeso"])))
  (t/is (= true (u/small-difference? ["Aes" "Ae"])))
  (t/is (= false (u/should-save-patient?
                 (u/patient-difference
                  test-patient
                  test-patient-2))))
  (t/is (= true (u/should-save-patient?
                  (u/patient-difference
                   test-patient
                   test-patient-3))))
  (t/is (= true (first (u/will-not-save-patient? test-patient test-patients))))
  (t/is (= "Too similar patient"
           (second (u/will-not-save-patient? test-patient test-patients))))
  (t/is (= "Insurance id already exists"
           (second (u/will-not-save-patient?
                    {:full_name "Sample name"
                     :gender "female"
                     :birthdate (u/format-date "1990-11-11")
                     :address "Red square. House 1, Moscow."
                     :insurance "1111-2222-3333-4444"
                     }
                    test-patients))))
  (t/is (= false (first (u/will-not-save-patient?
                          {:full_name "Gachi Muchi"
                           :gender "male"
                           :birthdate "1990-11-11"
                           :address "Some address"
                           :insurance "123123123"}
                          test-patients))))
  (t/testing "levenstein distance"
    (t/is (= 0 (u/levenstein-distance "" "")))
    (t/is (= 3 (u/levenstein-distance "" "abc")))
    (t/is (= 3 (u/levenstein-distance "abc" "")))
    (t/is (= 2 (u/levenstein-distance "book" "back")))
    (t/is (= 1 (u/levenstein-distance "hello" "helo")))
    (t/is (= 8 (u/levenstein-distance "good sir" "baal")))
    (t/is (= 5 (u/levenstein-distance "say" "shiver")))
    (t/is (= 13 (u/levenstein-distance "feature" "get-project-features"))))
  )

(t/run-tests)
