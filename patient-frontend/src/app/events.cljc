(ns app.events
  (:require [re-frame.core :as rf]
            [ajax.core :refer [GET POST DELETE] :as ajax]
            [clojure.walk :as w]
            [app.current-query-parameters :as c]
            [app.helpers :as h]
            [app.config :as conf]
            ))


(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:last-event nil
    :errors nil
    :page :about}))

(rf/reg-event-db
 :change-page
 (fn [db [_ page]]
   (assoc db :page page)))

(rf/reg-event-db
 :set-current-patient-id
 (fn [db [_ id]]
   (-> db
       (assoc :current-patient-id id))))

(rf/reg-event-db
 :last-event
 (fn [db [_ event-name]]
   (-> db
       (assoc :last-event event-name))))

(rf/reg-event-db
 :add-id-query-parameter
 (fn [db [_ id]]
   (-> db
       (assoc-in [:query-parameters :patient-id] id))))

(rf/reg-event-db
 :add-fullname-query-parameter
 (fn [db [_ fullname]]
   (-> db
       (assoc-in [:query-parameters :full_name] fullname))))

(rf/reg-event-db
 :add-birthdate-query-parameter
 (fn [db [_ birthdate]]
   (-> db
       (assoc-in [:query-parameters :birthdate] birthdate))))

(rf/reg-event-db
 :add-insurance-query-parameter
 (fn [db [_ insurance]]
   (-> db
       (assoc-in [:query-parameters :insurance] insurance))))

(rf/reg-event-db
 :add-address-query-parameter
 (fn [db [_ address]]
   (-> db
       (assoc-in [:query-parameters :address] address))))

(rf/reg-event-db
 :select-gender
 (fn [db [_ gender]]
   (-> db
       (assoc-in [:query-parameters :gender] gender))))

(rf/reg-event-db
 :add-all-query-parameters
 (fn [db [_ id]]
   (if-let [patient
            (h/patient-by-id id :patients-list db)]
     (h/assoc-patient-params-to-form-query-params-in-state
      db patient)
     (let [patient
           (h/patient-by-id id :filtered-patients-list db)]
       (h/assoc-patient-params-to-form-query-params-in-state
        db patient))
     )))

(rf/reg-event-db
 :save-patients-into-state
 (fn [db [_ patients]]
   (assoc
    db
    :patients-list patients
    :last-event "Loaded all patients")))

(rf/reg-event-db
 :save-filtered-patients-into-state
 (fn [db [_ patients]]
   (-> db
       (assoc :filtered-patients-list patients
              :last-event "Filtered patients"))))

(rf/reg-event-db
 :put-errors-into-state
 (fn [db [_ errors]]
   (-> db
       (assoc :errors errors))))


(rf/reg-event-db
 :delete-patient-from-state
 (fn [db [_ patient-id]]
   (let [remover (fn [keyword]
                   (remove (fn [p] (= (:id p) patient-id))
                           (keyword db)) )]
     (assoc db :patients-list
            (remover :patients-list)
            :filtered-patients-list
            (remover :filtered-patients-list)))
   ))


(rf/reg-event-db
 :update-patient-into-state
 (fn [db [_ patient-id query-parameters]]
   (assoc db :last-event
          "Patient updated")
   ))

(rf/reg-event-fx
 :load-patients-list
 (fn [db _]
   (let [handler (fn [el]
                   (rf/dispatch [:save-patients-into-state
                                 (w/keywordize-keys el)]))
         _ (rf/dispatch [:last-event "Loaded all patients"])
         error-handler (fn [el] (rf/dispatch
                                 [:put-errors-into-state
                                  el]))
         response (GET (str conf/host ":" conf/port "/patients")
                       {:handler handler
                        :error-handler error-handler
                        :response-format :json})])))

(rf/reg-event-fx
 :load-patients-with-query
 (fn [db [_ query-parameters]]
   (println (h/remove-nils-and-empty-strings query-parameters))
   (let [clean-query-parameters
         (h/remove-nils-and-empty-strings query-parameters)
         handler (fn [el]
                   (rf/dispatch
                    [:save-filtered-patients-into-state
                     (w/keywordize-keys el)]))
         error-handler (fn [el] (rf/dispatch
                                 [:put-errors-into-state
                                  el]))
         response (GET (str conf/host ":" conf/port "/patients/find")
                       {:handler handler
                        :response-format :json
                        :params clean-query-parameters})]
     )))

(rf/reg-event-fx
 :delete-patient-with-id
 (fn [db [_ patient-id]]
   (let [_ (rf/dispatch
            [:last-event (str "Deleting patient"
                              patient-id)])
         handler (fn [el] (rf/dispatch
                           [:delete-patient-from-state
                            patient-id]))
         response (DELETE
                   (str conf/host ":" conf/port "/patients/"
                        patient-id "/delete")
                   {:handler handler
                    :response-format :json})])))

(rf/reg-event-fx
 :create-patient
 (fn [db [_ query-parameters empty-values]]
   (if (empty? empty-values)
     (let [_ (rf/dispatch
              [:last-event (str "Saving patient: "
                                (:full_name query-parameters))])
           handler (fn [_])
           error-handler (fn [el] (rf/dispatch [:last-event el]))
           response (POST (str conf/host ":" conf/port "/patients")
                          {:handler handler
                           :error-handler error-handler
                           :format (ajax/json-request-format)
                           :response-format
                           (ajax/json-response-format
                            {:keywords? true})
                           :params query-parameters})])
     (rf/dispatch [:last-event
                   (str "Save failed. Empty fields: " empty-values)]))))

(rf/reg-event-fx
 :update-patient
 (fn [db [_ empty-query-parameters patient-id query-parameters]]
   (do
     (if (empty? empty-query-parameters)
       (let [_ (rf/dispatch [:last-event "Updating patient"])
             handler (fn [_] _)
             error-handler (fn [el] (rf/dispatch [:last-event el]))
             response (POST
                       (str
                        conf/host ":" conf/port "/patients/"
                        patient-id
                        "/update")
                       {:handler handler
                        :format (ajax/json-request-format)
                        :response-format
                        (ajax/json-response-format
                         {:keywords? true})
                        :params query-parameters})])
       (rf/dispatch [:last-event (str
                                  "Can not update. Empty fields: "
                                  empty-query-parameters)])))))

(rf/reg-event-fx
 :master-patient-index
 (fn [db [_ query-parameters empty-values]]
   (if (empty? empty-values)
     (let [_ (rf/dispatch
              [:last-event (str "Saving patient: "
                                (:full_name query-parameters))])
           handler (fn [_])
           error-handler (fn [el] (rf/dispatch [:last-event (-> el
                                                                :response
                                                                :res)]))
           response (POST (str conf/host ":" conf/port "/patients" "/master")
                          {:handler handler
                           :error-handler error-handler
                           :format (ajax/json-request-format)
                           :response-format
                           (ajax/json-response-format
                            {:keywords? true})
                           :params query-parameters})])
     (rf/dispatch [:last-event
                   (str "Save failed. Empty fields: "
                        empty-values)]))))

