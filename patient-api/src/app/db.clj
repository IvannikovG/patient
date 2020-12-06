(ns app.db
  (:require [clojure.java.jdbc :as j]
            [clj-time.core :as time]
            [clj-time.format :as f]
            [clj-time.coerce :as c]
            [app.utils :as utils]
            [app.specs :as s]
            ))


;; CONSTANTS ;;


;; (def database-type (System/getenv "DATABASE_TYPE"))
;; (def database-name (System/getenv "DATABASE_NAME"))
;; (def database-user (System/getenv "DATABASE_USER"))
;; (def database-password
;;   (System/getenv "DATABASE_PASSWORD"))

;; (def db-spec {:dbtype database-type
;;               :dbname database-name
;;               :user database-user
;;               :password database-password})

(def custom-formatter (f/formatter "YYYYMMDD"))

(def patient-table-description
  [[:id "SERIAL"]
   [:full_name "varchar(70) NOT NULL"]
   [:gender "varchar(15) NOT NULL"]
   [:birthdate "TIMESTAMPTZ"]
   [:address "varchar(70) NOT NULL"]
   [:insurance "varchar(200) NOT NULL"]
   [:created_at "TIMESTAMPTZ"]])



;; FUNCTIONS for TABLES ;;

(def patient-table-ddl
  (j/create-table-ddl :patient
                      patient-table-description
                      {:conditional? true}))

(defn create-table [db-spec ddl]
  (j/db-do-commands db-spec [ddl]))

(defn create-patient-table [db-spec]
  (create-table db-spec patient-table-ddl))

(create-patient-table s/db-spec)

(defn drop-patient-table [db-spec]
  (let [drop-table-ddl (j/drop-table-ddl
                        :patient
                        {:conditional? true})]
    (j/execute! db-spec drop-table-ddl)))

;; SELECT functions

(defn get-all-patients [db-spec]
  (let [patients (j/query
                  db-spec
                  ["SELECT * FROM patient"])]
    patients))

(defn get-patient-by-id [db-spec id]
  (let [patient (j/get-by-id
                 db-spec
                 :patient
                 id)]
    patient))

(defn get-patients-by-parameters [db-spec parameters]
  (if (empty? parameters)
    (get-all-patients)
    (j/find-by-keys
     db-spec
     :patient
     parameters)
    ))

;; INSERT/UPDATE functions

(defn save-patient-to-db [db-spec patient]
  (j/insert! db-spec
             :patient
             patient))

(defn update-patient-with-id [db-spec id parameters]
  (j/update! db-spec
             :patient
             parameters
             ["id=?" id]))


;; DELETE functions

(defn delete-patient-with-id [db-spec id]
  (j/delete! db-spec
             :patient
             ["id = ?" id]))


;; Patient generator
(defn save-n-patients-to-db [db-spec n]
  (take n (repeatedly #(save-patient-to-db db-spec (utils/sample-patient)))))

