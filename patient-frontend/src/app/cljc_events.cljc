(ns app.cljc-events)
(comment
  (ns app.cljc-events
    #?@
    (:clj
     [(:require [clj-http.client :as client]
                [clojure.data.json :as json])]
     :cljs
     [(:require [re-frame.core :as rf]
                [clojure.walk :as w])])
    )


  (rf/reg-event-db
   :save-patients-into-state
   (fn [db [_ patients]]
     (println (w/keywordize-keys patients))
     (assoc
      db
      :patients-list (w/keywordize-keys patients)
      :last-event "Loaded all patients")))



                                        ;#?(:clj
  (rf/reg-event-fx
   :load-all-p
   (fn [cofx [_ {:keys [uri]}]]
     (println "Trying to load patients")
     (println (:db cofx))
     {:db (assoc (:db cofx) :patients-list nil)
      :dispatch [:save-patients-into-state
                 #?(:clj (w/keywordize-keys
                          (json/read-str
                           (:body (client/get uri)))))]}))
                                        ;)


  (rf/dispatch [:load-all-p {:uri
                             "http://localhost:7500/patients"}])

  (rf/reg-event-fx
   :load-all-patients
   (fn [cofx [_ {:keys [uri]}]]
     {:db (assoc (:db cofx) :patients-list nil)
      :dispatch [:save-patients-into-state
                 (w/keywordize-keys
                  (json/read-str
                   (:body (client/get uri))))]}))


  (rf/reg-event-fx
   :load-filtered-patients
   (fn [cofx [_ {:keys [uri query-params]}]]
     {:db (assoc (:db cofx) :filtered-patients-list nil)
      :dispatch [:save-filtered-patients-into-state
                 (w/keywordize-keys
                  (json/read-str
                   (:body (client/get 
                           uri
                           {:query-params query-params}))))
                 query-params]}))




  @(rf/dispatch [:load-all-patients-from-backend])

  (rf/reg-event-fx
   :load-filtered-pats
   (fn [cofx [_ {:keys [uri]}]]
     {:dispatch [:load-patients-from-backend uri]}))

  (rf/reg-fx
   :send-patient-data
   (fn [{:keys [params uri]}]
     (ss/try+
      (client/post uri {:body (json/write-str params)
                        :content-type :json})
      (catch [:status 400] {:keys [request-time body]}
        (println request-time body)
        ))
     ))


  (rf/reg-event-fx
   :save-patient
   (fn [cofx [_ {:keys [uri params empty-params]}]]
     (if (empty? empty-params)
       {:db (assoc (:db cofx)
                   :last-event
                   (str "Saving patient with name: "
                        (:full_name params)))
        :send-patient-data {:params params
                            :uri uri}}
       {:db (assoc (:id cofx) :last-event
                   (str "Failed to save patient. Please fill empty fields: "
                        empty-params))})))



  (rf/reg-fx
   :send-update-patient-data
   (fn [{:keys [params uri]}]
     (ss/try+
      (client/post uri {:body (json/write-str params)
                        :content-type :json})
      (catch [:status 400] {:keys [request-time body]}
        (println " 400 " request-time body)))
     ))

  (rf/reg-event-fx
   :update-patient
   (fn [cofx [_ {:keys [empty-params patient-id params]}]]
     (if (empty? empty-params)
       {:db (assoc (:db cofx)
                   :last-event
                   (str "Updating patient: " params))
        :send-update-patient-data
        {:params params
         :uri (str "http://localhost:7500/" "patients/"
                   patient-id
                   "/update")}
        :dispatch [:update-patient-into-state {:patient-id patient-id
                                               :parameters params}]}
       {:db (assoc (:db cofx)
                   :last-event
                   (str "Failing to update patient. You have left some fields empty"))})))


  (rf/reg-fx
   :send-delete-patient-data
   (fn [{:keys [uri]}]
     (ss/try+
      (client/delete uri)
      (catch [:status 400] {:keys [request-time body]}
        (println " 400 " request-time body)))))


  (rf/reg-event-fx
   :delete-patient
   (fn [cofx [_ {:keys [patient-id]}]]
     {:db (assoc (:db cofx)
                 :last-event
                 (str "Delting patient with id: " patient-id))
      :send-delete-patient-data
      {:uri (str "http://localhost:7500/" "patients/"
                   patient-id
                   "/delete")}
      :dispatch [:delete-patient-from-state {:patient-id patient-id}]}
     ))


  (rf/reg-event-fx
   :send-patient-data-to-master-index
   (fn [cofx [_ {:keys [params uri]}]]
     (ss/try+
      (client/post uri {:body (json/write-str params)
                        :content-type :json})
      (catch [:status 400] {:keys [request-time body]}
        (println request-time body)
        {:db (assoc (:db cofx)
                    :last-event
                    (str "Failed master-patient-index-validation for: "
                         params))})
        ))
     )


  (rf/reg-event-fx
   :master-patient-index
   (fn [cofx [_ {:keys [uri params empty-params]}]]
     (if (empty? empty-params)
       {:db (assoc (:db cofx)
                   :last-event
                   (str "Saving patient with name: "
                        (:full_name params)))
        :dispatch [:send-patient-data-to-master-index {:params params
                                                       :uri uri}]}
       {:db (assoc (:id cofx) :last-event
                   (str "Failed to save patient. Please fill empty fields: "
                        empty-params))})))

  )
