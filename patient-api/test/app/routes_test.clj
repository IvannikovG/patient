(ns app.routes-test
  (:require  [clojure.test :as t]
             [ring.mock.request :as mock]
             [app.router :as router]
             [app.handlers :as handlers]
             [app.db :as db]))


(t/deftest failing-page
  (t/is (= (router/app (mock/request :get
                                     "/random-string"))
           {:status 404
            :body "Requested URI: /random-string <- 404"})))

(t/deftest save-get-patient-page
  (do
    (db/drop-patient-table)
    (db/create-patient-table)
    (handlers/save-patient-page
     {:body {:full_name "Anna Karenina"
             :gender "female"
             :birthdate "1990-11-11"
             :address "Address"
             :insurance "Insurance"}}))
  (t/is (= (:status (handlers/get-patient-page
                     {:params {:id "1"}}))
           200)))

(t/deftest patient-master-index-page
  (do
    (db/drop-patient-table)
    (db/create-patient-table)
    (handlers/master-patient-index-page
     {:body {:full_name "Anna Petrovna"
              :gender "other"
              :birthdate "1990-11-11"
              :address "Address"
             :insurance "Insurance"}})
    (t/is (= (:status (handlers/get-patient-page
                        {:params {:id "1"}}))
              200))
     (handlers/master-patient-index-page
      {:body {:full_name "Anna Petrovna"
              :gender "other"
              :birthdate "1990-11-11"
              :address "Address"
              :insurance "Insurance"}})
     (t/is (= (:status (handlers/get-patient-page
                        {:params {:id "2"}}))
              404))
     (handlers/master-patient-index-page
      {:body {:full_name "Ann Petrovna"
              :gender "othe"
              :birthdate "1990-11-11"
              :address "Addres"
              :insurance "Insurance"}})
     (t/is (= (:status (handlers/get-patient-page
                        {:params {:id "2"}}))
              404))
     )
  )

(t/deftest search-update-delete-patients
  (do
    (db/drop-patient-table)
    (db/create-patient-table)
    (handlers/save-patient-page
       {:body {:full_name "Anna Petrovna"
               :gender "other"
               :birthdate "1990-11-11"
               :address "Address"
               :insurance "Insurance"}})
      (handlers/save-patient-page
       {:body {:full_name "Anna Petrovna"
               :gender "male"
               :birthdate "1990-11-11"
               :address "Address"
               :insurance "Insurance"}})
      (handlers/update-patient-page
       {:body {:address "Updated Address"
               :insurance "Updated Insurance"
               :random "Random"}
        :params {:id "2"}}))
  (let [patients-annas
        (:body (handlers/search-patients-page
                {:query-params {:full_name "Anna Petrovna"}}))
        patient-anna-male
        (:body (handlers/search-patients-page
                {:query-params {:full_name "Anna Petrovna"
                                :gender "male"}}))]
    (t/is (and (= (count patients-annas) 2)
               (= (count patient-anna-male) 1)
               (= (:address (first patient-anna-male)) "Updated Address")
               (= (:insurance (first  patient-anna-male)) "Updated Insurance")))
    (handlers/delete-patient-page {:params {:id "2"}})
    (t/is (= nil (db/get-patient-by-id 2)))
    ))

(t/run-tests)
