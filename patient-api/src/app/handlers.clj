(ns app.handlers
  (:require [clojure.walk :as walk]
            [app.utils :as u]
            [app.db :as db]
            [clj-time.core :as time]
            [clj-time.coerce :as c]
            [ring.util.request :refer [body-string]]
            ))


(defn page-404 [request db-spec]
  {:status 404
   :body (str "Requested URI: " (:uri request)
              " <- 404")})

(defn patients-page [request db-spec]
  (let [patients (db/get-all-patients db-spec)
        response {:status 200
                  :headers {"content-type" "application/json"}
                  :body patients}]
   response))

(defn save-patient-page [request db-spec]
  (let [form-data (:body request)]
    (cond (u/valid-keys? form-data)
          (do
            (db/save-patient-to-db db-spec
             (u/valid-patient-parameters form-data))
            {:status 200
             :headers {"content-type" "application/json"}
             :body {:patient (str "Saved patient with name: "
                                  (:full_name form-data))}})
         :else {:status 400
                :headers {"content-type" "application/json"}
                :body {:error
                       "Patient was not saved due to empty fields"}})
    )
  )


(defn get-patient-page [request db-spec]
  (let [patient-id (:id (:params request))
        patient (db/get-patient-by-id db-spec (u/str-to-int patient-id))]
    (if patient
      {:status 200
       :headers {"content-type" "application/json"}
       :body patient}
      {:status 404
       :headers {"content-type" "application/json"}
       :body {:error "Patient was not found"}})
    ))

(defn search-patients-page [request db-spec]
  (let [params (:query-string request)
        cleaned-params (u/clean-request-params
                        (clojure.walk/keywordize-keys
                         params))
        patients (db/get-patients-by-parameters db-spec cleaned-params)]
    (println cleaned-params)
    (println "++++++++++++++++++++++++++++++++++++++++++++++++++++++++")
    (println params)
    {:status 200
     :headers {"content-type" "application-json"}
     :body patients
     })
  )

(defn update-patient-page [request db-spec]
  (let [form-data (:body request)
        patient-id (u/str-to-int (:id (:params request)))
        cleaned-data (u/clean-request-params form-data)]
    (db/update-patient-with-id db-spec patient-id cleaned-data)
    {:status 200
     :headers {"content-type" "application-json"}
     :body {:patient (db/get-patient-by-id db-spec patient-id)}
     }))


(defn delete-patient-page [request db-spec]
  (let [patient-id (u/str-to-int (:id (:params request)))]
    (db/delete-patient-with-id db-spec patient-id)
    {:status 200
     :headers {"content-type" "application-json"}
     :body {:patient
            (format "Patient with id %s was deleted" patient-id)}}))


(defn master-patient-index-page [request db-spec]
  (let [patient-data (:body request)
        existent-patients (u/convert-patients-entries-to-raw
                  (db/get-all-patients db-spec))
        [will-not-save-patient reason] (u/will-not-save-patient?
                                        patient-data existent-patients)]
    (if will-not-save-patient
      {:status 400
       :headers {"content-type" "application-json"}
       :body {:res (str "Patient not saved. Provided data: "
                        patient-data
                        "Reason: " reason)}}
       (do
         (db/save-patient-to-db db-spec
          (u/valid-patient-parameters patient-data))
         {:status 200
          :headers {"content-type" "application/json"}
          :body {:patient (str "Saved patient with name: "
                               (:full_name patient-data))}})
      )))

