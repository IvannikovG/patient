(ns app.db-test
  (:require [clojure.test :as t :refer :all]
            [app.db :refer :all]
            [app.utils :as utils]))

(t/deftest create-update-delete-patient
  (do
    (drop-patient-table)
    (create-patient-table)
    (save-patient-to-db {:full_name "TEST PATIENT"
                         :gender "TEST GENDER"
                         :birthdate (utils/random-sql-date)
                         :address (utils/random-string 15)
                         :insurance (utils/random-string 15)
                         :created_at (utils/random-sql-date)
                         }))
  (t/is (= (:full_name (get-patient-by-id 1))
           "TEST PATIENT"))
  (t/is (= (:full_name (get-patients-by-parameters {:full_name "TEST PATIENT"}))))
  (save-patient-to-db {:full_name "TEST PATIENT"
                       :gender "female"
                       :birthdate (utils/random-sql-date)
                       :address (utils/random-string 15)
                       :insurance (utils/random-string 15)
                       :created_at (utils/random-sql-date)
                       })
  (t/is (= (count (get-patients-by-parameters {:full_name "TEST PATIENT"
                                                      :gender "female"})) 1))
  (update-patient-with-id 1 {:full_name "UPDATED"})
  (t/is (= (:full_name (get-patient-by-id 1))
           "UPDATED"))
  (delete-patient-with-id 1)
  (delete-patient-with-id 2)
  (t/is (nil? (get-patient-by-id 1)))
  )

(t/run-tests)

