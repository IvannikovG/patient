(ns app.handlers
  (:require [clojure.walk :as walk]
            [app.utils :as u]
            [app.db :as db]
            [clj-time.core :as time]
            [clj-time.coerce :as c]
            ))


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
    (cond (u/valid-keys? form-data)
          (do
            (db/save-patient-to-db
             (u/valid-patient-parameters form-data))
            {:status 200
             :headers {"content-type" "application/json"}
             :body {:patient (str "Saved patient with name: "
                                  (:full_name form-data))}})
         :else {:status 400
                :headers {"content-type" "application/json"}
                :body {:error "Patient was not saved due to empty fields"}})
    ))

(defn get-patient-page [request]
  (let [patient-id (:id (:params request))
        patient (db/get-patient-by-id
                 (u/str-to-int patient-id))]
    (if patient
      {:status 200
       :headers {"content-type" "application/json"}
       :body patient}
      {:status 404
       :headers {"content-type" "application/json"}
       :body {:error "Patient was not found"}})
    ))

(defn search-patients-page [request]
  (let [params (:body request)
        cleaned-params (u/clean-request-params params)
        patients (db/get-patients-by-parameters cleaned-params)]
    {:status 200
     :headers {"content-type" "application-json"}
     :body patients
     })
  )

(defn update-patient-page [request]
  (let [form-data (:body request)
        patient-id (u/str-to-int (:id (:params request)))
        cleaned-data (u/clean-request-params form-data)]
    (db/update-patient-with-id patient-id cleaned-data)
    {:status 200
     :headers {}
     :body {:patient (db/get-patient-by-id patient-id)}
     }))


(defn delete-patient-page [request]
  (let [patient-id (u/str-to-int (:id (:params request)))]
    (db/delete-patient-with-id patient-id)
    {:status 200
     :body {:patient
            (format "Patient with id %s was deleted" patient-id)}}))

