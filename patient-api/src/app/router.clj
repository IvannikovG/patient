(ns app.router
  (:require [compojure.core :refer [GET POST DELETE defroutes routes]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer :all]
            [app.handlers :refer :all]
            [ring.logger :as logger]))



(defroutes app
  (GET "/" [] "hello i am he!")
  (GET "/patients" request (patients-page request))
  (POST "/patients" request (save-patient-page request))
  (GET "/patients/:id" [id :as request] (get-patient-page request))
  (POST "/patients/:id/update" [id :as request] (update-patient-page request))
  (DELETE "/patients/:id/delete" [id :as request] (delete-patient-page request))
  page-404)

(defn reloadable-app []
  (wrap-reload #'app))

(defn main [opts]
  (run-jetty (-> (reloadable-app)
                 logger/wrap-with-logger
                 (wrap-json-response {:keywords? true})
                 (wrap-json-body {:keywords? true}))
             {:port 7500 :join? false}))
