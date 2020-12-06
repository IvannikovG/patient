(ns app.db-test
  (:require [clojure.test :as t :refer :all]
            [app.db :refer :all]
            [app.utils :as utils]
            [app.specs :as s]))

(t/deftest create-update-delete-patient
  (do
    (drop-patient-table s/db-spec)
    (create-patient-table s/db-spec)
    (save-patient-to-db s/db-spec
                        {:full_name "TEST PATIENT"
                         :gender "TEST GENDER"
                         :birthdate (utils/random-sql-date)
                         :address (utils/random-string 15)
                         :insurance (utils/random-string 15)
                         :created_at (utils/random-sql-date)
                         }))
  (t/is (= (:full_name (get-patient-by-id s/db-spec 1))
           "TEST PATIENT"))
  (t/is (= (:full_name (get-patients-by-parameters s/db-spec
                                                   {:full_name "TEST PATIENT"}))))
  (save-patient-to-db s/db-spec {:full_name "TEST PATIENT"
                       :gender "female"
                       :birthdate (utils/random-sql-date)
                       :address (utils/random-string 15)
                       :insurance (utils/random-string 15)
                       :created_at (utils/random-sql-date)
                       })
  (t/is (= (count (get-patients-by-parameters s/db-spec
                                              {:full_name "TEST PATIENT"
                                               :gender "female"})) 1))
  (update-patient-with-id s/db-spec 1 {:full_name "UPDATED"})
  (t/is (= (:full_name (get-patient-by-id s/db-spec 1))
           "UPDATED"))
  (delete-patient-with-id s/db-spec 1)
  (delete-patient-with-id s/db-spec 2)
  (t/is (nil? (get-patient-by-id s/db-spec 1)))
  )

(t/run-tests)

