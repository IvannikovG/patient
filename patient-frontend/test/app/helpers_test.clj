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
      (t/is (= {:a "test"} (h/remove-nils-and-empty-strings
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
               ))
      )))
