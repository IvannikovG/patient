(ns app.router
  (:require [compojure.core :refer [GET POST DELETE defroutes routes context]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer :all]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [jumblerg.middleware.cors :refer [wrap-cors]]
            [app.handlers :as h]
            [ring.logger :as logger]))


(defroutes app
  (GET "/" [] "CRUD PATIENT")
  (context "/patients" request
           (GET "/" request (h/patients-page request))
           (POST "/" request (h/save-patient-page request))
           (POST "/master" request (h/master-patient-index-page request))
           (GET "/find" request (h/search-patients-page request))
           (GET "/:id" [id :as request] (h/get-patient-page request))
           (POST "/:id/update"
                  [id :as request] (h/update-patient-page request))
           (DELETE "/:id/delete" [id :as request]
                   (h/delete-patient-page request))
           )
  h/page-404)

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
