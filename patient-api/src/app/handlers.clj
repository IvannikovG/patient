(ns app.handlers
  (:require [ring.util.response :refer :all]
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
  (let [form-data (:body request)
        query-string-data (:params request)]
    (println form-data query-string-data)
    (cond
      (empty? query-string-data) (db/create-patient form-data)
      (not (= (type form-data)
              clojure.lang.PersistentArrayMap))
           (db/create-patient (walk/keywordize-keys query-string-data))
      :else (println "Some unexpected bug happened"))
       {:status 200
        :headers {"content-type" "application/json"}
        :body {:patient (str "Saved patient with name: "
                             (:fullname form-data)
                             (:fullname (walk/keywordize-keys query-string-data)))}}))



(defn get-patient-page [request]
  (let [patient-id (:id (:params request))
        patient (db/get-patient-by-id
                 (utils/strToInt patient-id))]
    (println request)
    {:status 200
     :headers {"content-type" "application/json"}
     :body patient}))



(defn update-patient-page [request]
  (let [form-data (:body request)
        query-string-data (:params request)
        patient-id (utils/strToInt (:id (:params request)))]
    (println "Request" request)
    (println "Form data" form-data)
    (println "Patient id" patient-id "TYPE" (type patient-id))
    (db/update-patient patient-id form-data)
    {:status 200
     :headers {}
     :body (db/get-patient-by-id patient-id)
     }))

(defn delete-patient-page [request]
  (let [patient-id (Integer/parseInt (:id (:params request)))]
    (db/delete-patient patient-id)
    {:status 200
     :body {:patient
            (format "Patient with id %s was deleted" patient-id)}}))


(defn search-patients-page [request]
  (println request)
 (let [search-params (:params request)
       patients (vec (db/filter-patients-by search-params))]
   (println search-params)
   {:status 200
    :headers {"content-type" "application/json"}
    :body patients}))

