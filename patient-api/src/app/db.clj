(ns app.db
  (:require [clojure.java.jdbc :as j]
            [clj-time.core :as time]
            [clj-time.format :as f]
            [app.utils :as utils]
            ))


;; CONSTANTS ;;

(def database-type (System/getenv "DATABASE_TYPE"))
(def database-name (System/getenv "DATABASE_NAME"))
(def database-user (System/getenv "DATABASE_USER"))
(def database-password (System/getenv "DATABASE_PASSWORD"))

(def custom-formatter (f/formatter "YYYYMMDD"))

(def db-spec {:dbtype database-type
              :dbname database-name
              :user database-user
              :password database-password})

(def patient-table-description
   [[:id "SERIAL"]
    [:fullname "varchar(70) NOT NULL"]
    [:gender "varchar(15) NOT NULL"]
    [:birthdate "varchar(70) NOT NULL"]
    [:address "varchar(200)"]
    [:insurance "varchar(20)"]
    [:created "varchar(100)"]])

(def patient-table-ddl
  (j/create-table-ddl :patient
                      patient-table-description))

(def test-patient-table-ddl
  (j/create-table-ddl :testpatient
                      patient-table-description))


;; FUNCTIONS for TABLES ;;

(defn create-table [ddl]
  (j/db-do-commands db-spec [ddl]))

(defn create-patient-table []
  (create-table patient-table-ddl))


(defn create-test-patient-table []
  (create-table test-patient-table-ddl))


(defn drop-table [table-name]
  "Table name as keyword"
  (j/db-do-commands db-spec
   (j/drop-table-ddl table-name)))

(defn drop-test-patient-table []
  (drop-table :testpatient))

;; SELECT functions

(defn get-all-patients []
  (j/query db-spec "SELECT * FROM patient"))

(defn get-pat-by-id [id table-name]
  (j/get-by-id db-spec "patient" id))

(defn get-patient-by-id [id]
  (get-pat-by-id id "patient"))

(defn get-test-patient-by-id [id]
  (get-pat-by-id id "testpatient"))


;; INSERT/UPDATE functions

(defn create-pat [form table-name]
  (let [{:keys [fullname
                gender
                birthdate
                address
                insurance]} form]
    (j/insert-multi! db-spec table-name
                    [{:fullname fullname
                      :gender gender
                      :birthdate birthdate
                      :address address
                      :insurance insurance
                      :created (f/unparse custom-formatter
                                          (time/now))}])))

(defn create-patient [form]
  (create-pat form :patient))

(defn create-test-patient [form]
  (create-pat form :testpatient))

(defn update-pat [form id table-name]
  (j/update! db-spec table-name
             form ["id=?" id]))

(defn update-patient [id form]
  (update-pat form id :patient))

(defn update-test-patient [id form]
  (update-pat form id :testpatient))


;; DELETE functions

(defn delete-pat [id table-name]
  (j/delete! db-spec table-name ["id=?" id]))

(defn delete-patient [id]
  (delete-pat id :patient))

(defn delete-test-patient [id]
  (delete-pat id :testpatient))


;; FILTERING

(defn filter-by [search-config table-name]
  "By {:gender female}"
  (j/find-by-keys db-spec table-name search-config))

(defn filter-patients-by [search-config]
  (if (empty? search-config)
    (vec (get-all-patients))
    (vec (filter-by search-config :patient))))


(defn create-n-patients-in [n table-name]
  (j/insert-multi! db-spec table-name (utils/generate-n-patients n)))
