(ns app.handlers
  (:require [ring.util.response :refer :all]
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
    ;; validate data before save to db
    (db/create-patient form-data)
       {:status 200
        :headers {}
        :body (str "Successfully saved"
                   (:fullname form-data))}))



(defn get-patient-page [request]
  (let [patient-id (:id (:params request))
        patient (db/get-patient-by-id patient-id)]
    {:status 200
     :headers {"content-type" "application/json"}
     :body patient}))




(defn update-patient-page [request]
  (let [form-data (:body request)
        patient-id (Integer/parseInt (:id (:params request)))]
    (db/update-patient patient-id form-data)
    {:status 200
     :headers {}
     :body (str (db/get-patient-by-id patient-id))}))

(defn delete-patient-page [request]
  (let [patient-id (Integer/parseInt (:id (:params request)))]
    (db/delete-patient patient-id)
    {:status 200
     :body (format "Patient with id %s was deleted" patient-id)}))
