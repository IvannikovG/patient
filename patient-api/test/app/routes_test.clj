(ns app.routes-test
  (:require  [clojure.test :as t]
             [ring.mock.request :as mock]
             [app.router :as router]
             [app.handlers :as handlers]
             [app.db :as db]
             [app.specs :as s]))

(def db-spec s/db-spec-testing)

(t/deftest failing-page
  (t/is (= ((router/app db-spec)
            (mock/request :get
                          "/random-string"))
           {:status 404
            :body "Requested URI: /random-string <- 404"})))

(t/deftest save-get-patient-page
  (do
    (db/drop-patient-table db-spec)
    (db/create-patient-table db-spec)
    (handlers/save-patient-page
     {:body {:full_name "Anna Karenina"
             :gender "female"
             :birthdate "1990-11-11"
             :address "Address"
             :insurance "Insurance"}}
     db-spec))
  (t/is (= (:status (handlers/get-patient-page
                     {:params {:id "1"}}
                     db-spec))
           200))
  (do (db/drop-patient-table db-spec)
      (db/create-patient-table db-spec)))

(t/deftest patient-master-index-page
  (do
    (db/drop-patient-table db-spec)
    (db/create-patient-table db-spec)
    (handlers/master-patient-index-page
     {:body {:full_name "Anna Petrovna"
              :gender "other"
              :birthdate "1990-11-11"
              :address "Address"
             :insurance "Insurance"}}
     db-spec)
    (t/is (= (:status (handlers/get-patient-page
                       {:params {:id "1"}}
                       db-spec))
              200))
     (handlers/master-patient-index-page
      {:body {:full_name "Anna Petrovna"
              :gender "other"
              :birthdate "1990-11-11"
              :address "Address"
              :insurance "Insurance"}}
      db-spec)
     (t/is (= (:status (handlers/get-patient-page
                        {:params {:id "2"}}
                        db-spec))
              404))
     (handlers/master-patient-index-page
      {:body {:full_name "Ann Petrovna"
              :gender "othe"
              :birthdate "1990-11-11"
              :address "Addres"
              :insurance "Insurance"}}
      db-spec)
     (t/is (= (:status (handlers/get-patient-page
                        {:params {:id "2"}}
                        db-spec))
              404))
     (handlers/master-patient-index-page
      {:body
       {:full_name "Some completely different patient same id"
        :gender "other"
        :birthdate "2000-12-10"
        :address "Some address"
        :insurance "Insurance"}}
      db-spec)
     (t/is (= (:status (handlers/get-patient-page
                        {:params {:id "2"}}
                        db-spec))
              404))
     )
  (do (db/drop-patient-table db-spec)
      (db/create-patient-table db-spec))
  )

(t/deftest search-update-delete-patients
  (do
    (db/drop-patient-table db-spec)
    (db/create-patient-table db-spec)
    (handlers/save-patient-page
       {:body {:full_name "Anna Petrovna"
               :gender "other"
               :birthdate "1990-11-11"
               :address "Address"
               :insurance "Insurance"}}
       db-spec)
      (handlers/save-patient-page
       {:body {:full_name "Anna Petrovna"
               :gender "male"
               :birthdate "1990-11-11"
               :address "Address"
               :insurance "Insurance"}}
       db-spec)
      (handlers/update-patient-page
       {:body {:address "Updated Address"
               :insurance "Updated Insurance"
               :random "Random"}
        :params {:id "2"}}
       db-spec))
  (let [patients-annas
        (:body (handlers/search-patients-page
                {:query-string
                 {:full_name "Anna Petrovna"}}
                db-spec))
        patient-anna-male
        (:body (handlers/search-patients-page
                {:query-string
                 {:full_name "Anna Petrovna"
                  :gender "male"}}
                db-spec))]
    (t/is (and (= (count patients-annas) 2)
               (= (count patient-anna-male) 1)
               (= (:address
                   (first patient-anna-male))
                  "Updated Address")
               (= (:insurance
                   (first patient-anna-male))
                  "Updated Insurance")))
    (handlers/delete-patient-page
     {:params {:id "2"}} db-spec)
    (t/is (= nil (db/get-patient-by-id db-spec 2)))
    )
  (do (db/drop-patient-table db-spec)
      (db/create-patient-table db-spec)))

#_(t/run-tests)
