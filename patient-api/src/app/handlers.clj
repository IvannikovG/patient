(ns app.handlers
  (:require [ring.util.codec :refer [form-decode]]
            [clojure.walk :as walk]
            [app.utils :as utils]
            [app.db :as db]))


(defn page-404 [request]
  {:status 404
   :body (str "Requested URI: " (:uri request)
              " <- 404")})

(defn patients-page [request]
  (let [patients (db/get-all-patients)
        response {:status 200
                  :headers {"content-type" "application/json"}
                  :body patients}]
   response))



(defn save-patient-page [request]
  (let [form-data (:body request)]
    (println "form-data" form-data)
    (println request)
    (db/create-patient form-data)
       {:status 200
        :headers {"content-type" "application/json"}
        :body {:patient (str "Saved patient with name: "
                             (:fullname form-data))}}))


(defn get-patient-page [request]
  (let [patient-id (:id (:params request))
        patient (db/get-patient-by-id
                 (utils/strToInt patient-id))]
    (println request)
    {:status 200
     :headers {"content-type" "application/json"}
     :body patient}))

(defn parse-query-string [query-string]
  (walk/keywordize-keys (form-decode query-string)))

(defn search-patients-page [request]
  (let [query-string (parse-query-string (:query-string request))
        patients (db/filter-patients-by query-string)]
    {:status 200
     :headers {"content-type" "application-json"}
     :body patients
     }))


(defn update-patient-page [request]
  (let [form-data (:body request)
        patient-id (utils/strToInt (:id (:params request)))]
    (println "Request" request)
    (println "Form data" form-data)
    (println "Patient id" patient-id "TYPE" (type patient-id))
    (db/update-patient patient-id form-data)
    {:status 200
     :headers {}
     :body {:patient (db/get-patient-by-id patient-id)}
     }))

(defn delete-patient-page [request]
  (let [patient-id (Integer/parseInt (:id (:params request)))]
    (db/delete-patient patient-id)
    {:status 200
     :body {:patient
            (format "Patient with id %s was deleted" patient-id)}}))


