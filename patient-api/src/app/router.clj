(ns app.router
  (:require [compojure.core :refer [GET POST DELETE defroutes routes context]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer :all]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [jumblerg.middleware.cors :refer [wrap-cors]]
            [app.handlers :as h]
            [app.utils :as u]
            [clj-http.client :as client]
            [ring.logger :as logger]))


(defn app [db-spec]
  ;; configs here
  (defroutes app
           (GET "/" [] "CRUD PATIENT")
           (context "/patients" request
                    (GET "/" request (h/patients-page request))
                    (POST "/" request (h/save-patient-page request))
                    (POST "/master" request (h/master-patient-index-page request))
                    ;; here db-spec smhow
                    (GET "/find" request (h/search-patients-page request ))
                    (GET "/:id" [id :as request] (h/get-patient-page request))
                    (POST "/:id/update"
                          [id :as request] (h/update-patient-page request))
                    (DELETE "/:id/delete" [id :as request]
                            (h/delete-patient-page request))
                    )
           h/page-404))

(def endpoints
  {:get {:all h/patients-page
         :find h/search-patients-page
         :by-id h/get-patient-page}
   :post {:all h/save-patient-page
          :master h/master-patient-index-page
          :update h/update-patient-page}
   :delete {:delete h/delete-patient-page}
   :default {:default h/page-404}})

(defn uri-matcher [uri]
  "takes uri and returns a second key for endpoints map"
  (let [splitted (filter (fn [el] (not-empty el))
                         (clojure.string/split uri #"/"))]
    (if (not (contains? (set splitted) "patients"))
      :default
      (cond (= (count splitted) 1) :all
            (contains? (set splitted) "master") :master
            (contains? (set splitted) "find") :find
            (contains? (set splitted) "update") :update
            (contains? (set splitted) "delete") :delete
            (and (number? (u/str-to-int (last splitted)))
                 (= (count splitted) 2)) :by-id
            :else :default
            ))))

(defn correct-handler [request]
  (let [{:keys [uri request-method]} request
        handler (get-in endpoints [request-method
                                   (uri-matcher uri)])]
    (if (nil? handler)
      h/page-404
      handler)))

(defn get-id-from-uri [url]
  (let [splitted (clojure.string/split url #"/")]
    (first
     (filter (complement nil?)
             (map u/str-to-int splitted)))))

(defn app-2 [request] ;; will take specs
  (let [handler (correct-handler request)
        patient-id (get-id-from-uri (:uri request))
        request-with-id (merge request {:params {:id patient-id}})]
    (handler request-with-id)))

(defn reloadable-app []
  (wrap-reload app-2))


(defn log-to-file [func]
  (fn [& args]
    (spit "event.log" (str "Args: " args "\n"))
    (apply func args)))

(defn check-func-args [func]
  (fn [& args]
    (println "=============")
    (println "=============")
    (println func)
    (println "=============")
    (println "=============")
    (println "=============")
    (println "=============")
    (println args)
    (println "=============")
    (println "=============")
    (apply func args)
    ))



(defn wrapped-app []
  (-> (reloadable-app)
       logger/wrap-with-logger
       logger/wrap-log-request-params
       wrap-params
       wrap-keyword-params
       check-func-args
       log-to-file
       (wrap-json-response {:keywords? true})
       (wrap-json-body {:keywords? true})))

(defn main [opts]
  (run-jetty (-> (wrapped-app)
                 (wrap-cors #".*"))
             {:port 7500 :join? false}))
