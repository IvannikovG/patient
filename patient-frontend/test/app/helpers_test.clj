(ns app.helpers-test
  (:require  [clojure.test :as t]
             [app.helpers :as h]))


(t/deftest helper-tests
  (t/testing "Helpers"
    (t/testing "Stringify map keywords"
      (t/is (= (h/stringify-map-keywords {:a "abc" :b 1})
               {"a" "abc" "b" 1})))
    (t/testing "Find nils empty string and unselected"
      (t/is (= true (h/empty-value? "")))
      (t/is (= true (h/empty-value? nil)))
      (t/is (= true (h/empty-value? "unselected")))
      (t/is (= false (h/empty-value? 1)))
      (t/is (= false (h/empty-value? "Anna"))))
    (t/testing "Remove nils, empty strings, unselected"
      (t/is (= {:a "test"}
               (h/remove-nils-and-empty-strings
                {:a "test" :b nil :c "" :d "unselected"}))))
    (t/testing "Return lower cased values in a map"
      (t/is (= {:a "anna" :b "maria"}
               (h/lower-cased-values {:a "AnNa" :b "MaRIa"}))))
    (t/testing "Return patient with id from database"
      (t/is (= {:id 1 :content "Sample 1"}
               (h/patient-by-id
                1 :patients
                {:patients [{:id 1 :content "Sample 1"}
                            {:id 2 :content "Sample 2"}]}))))
    (t/testing "Assoc query parameters function"
      (t/is (= (h/assoc-patient-params-to-form-query-params-in-state
                {} {:insurance "Insurance"
                    :full_name "Full name"
                    :address "Address"
                    :birthdate "Birthdate"
                    :gender "Female"})
               {:query-parameters {:insurance "Insurance"
                                   :full_name "Full name"
                                   :address "Address"
                                   :birthdate "Birthdate"
                                   :gender "Female"} }
               )))
    (t/testing "Sorting patients"
      (def patients [{:id 1 :full_name "Georgii Ivannikov"
                      :address "Moscow" :gender "male"
                      :birthdate "1995-11-11"
                      :insurance "1234-1234-1234"}
                     {:id 2 :full_name "Aleksandra Nishenko"
                      :birthdate "1999-10-10"
                      :address "Dudweiler" :gender "female"
                      :insurance "1237-1234-1234"}
                     {:id 3 :full_name "Ivana Grishkaeva"
                      :birthdate "1980-01-01"
                      :address "Novisibirsk" :gender "other"
                      :insurance "1234-1234-1237"}])
      (t/is (and (= (h/sort-patients-by patients :random) patients)
                 (= (h/sort-patients-by patients :id) patients)
                 (= (h/sort-patients-by patients :full_name)
                    [{:id 2 :full_name "Aleksandra Nishenko"
                      :birthdate "1999-10-10"
                      :address "Dudweiler" :gender "female"
                      :insurance "1237-1234-1234"}
                     {:id 1 :full_name "Georgii Ivannikov"
                      :address "Moscow" :gender "male"
                      :birthdate "1995-11-11"
                      :insurance "1234-1234-1234"}
                     {:id 3 :full_name "Ivana Grishkaeva"
                      :birthdate "1980-01-01"
                      :address "Novisibirsk" :gender "other"
                      :insurance "1234-1234-1237"}])))
      )))

(t/run-tests)
