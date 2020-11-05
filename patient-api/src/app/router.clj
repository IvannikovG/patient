(ns app.router
  (:require [compojure.core :refer [GET POST DELETE defroutes routes context]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer :all]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [jumblerg.middleware.cors :refer [wrap-cors]]
            [app.handlers :refer :all]
            [ring.logger :as logger]))



(defroutes appa
  (GET "/" [] "CRUD PATIENT")
  (GET "/patients" request (patients-page request))
  (POST "/patients" request (save-patient-page request))
  (GET "/patients/find" request (search-patients-page request))
  (GET "/patients/:id" [id :as request] (get-patient-page request))
  (POST "/patients/:id/update" [id :as request] (update-patient-page request))
  (DELETE "/patients/:id/delete" [id :as request] (delete-patient-page request))
  page-404)

(defroutes app
  (GET "/" [] "CRUD PATIENT")
  (context "/patients" request
           (GET "/" request (patients-page request))
           (POST "/" request (save-patient-page request))
           (GET "/find" request (search-patients-page request))
           (GET "/:id" [id :as request] (get-patient-page request))
           (POST "/:id/update"
                 [id :as request] (update-patient-page request))
           (DELETE "/:id/delete" [id :as request]
                   (delete-patient-page request))
           )
  page-404)

(defn reloadable-app []
  (wrap-reload #'app))

(defn wrapped-app []
  (-> (reloadable-app)
       logger/wrap-with-logger
       logger/wrap-log-request-params
       wrap-params
       wrap-keyword-params
       (wrap-json-response {:keywords? true})
       (wrap-json-body {:keywords? true})))

(defn main [opts]
  (run-jetty (-> (wrapped-app)
                 (wrap-cors #".*"))
             {:port 7500 :join? false}))
