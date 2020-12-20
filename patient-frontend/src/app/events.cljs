(ns app.events
  (:require [re-frame.core :as rf]
            [clojure.walk :as w]
            [app.current-query-parameters :as c]
            [app.helpers :as h]
            [app.config :as conf]
            [ajax.core :as ajax]
            [day8.re-frame.http-fx]
            ))

(rf/reg-event-db
 :drop-db
 (fn [db _]
   {}))

(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:last-event nil
    :errors nil
    :page :about
    :patients-sorter :id}))

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
 :add-one-patient-into-state
 (fn [db [_ patient]]
   (comment "Is the patient coming?? Spec?")
   (assoc db :patients-list (conj (:patients-list db) patient))))


(rf/reg-event-db
 :save-patients-into-state
 (fn [db [_ patients]]
   (assoc
    db
    :patients-list (w/keywordize-keys patients))))

(rf/reg-event-db
 :save-filtered-patients-into-state
 (fn [db [_ patients]]
   (-> db
       (assoc :filtered-patients-list (w/keywordize-keys patients)
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

(rf/reg-event-db
 :set-patients-sorter
 (fn [db [_ sorter]]
   (assoc db :patients-sorter sorter)))

(rf/reg-event-fx
 :load-all-patients
 (fn [{:keys [db]} _]
   {:db (assoc db :last-event "Loading all patients"
               )
    :http-xhrio {:method :get
                 :uri "http://localhost:7500/patients"
                 :response-format (ajax/json-response-format
                                   {:keywords true})
                 :on-success [:save-patients-into-state]
                 :on-failure nil}}))


(rf/reg-event-fx
 :create-patient
 (fn [{:keys [db]} [_ parameters empty-parameters]]
   (if ((complement empty?) empty-parameters)
     {:dispatch [:last-event (str "Please provide all needed inputs: "
                                  empty-parameters)]}
     {:db (assoc db :last-event (str "Saving patient: " parameters))
      :dispatch [:add-one-patient-into-state parameters]
      :http-xhrio {:method :post
                   :params parameters
                   :format          (ajax/json-request-format)
                   :uri "http://localhost:7500/patients"
                   :response-format (ajax/json-response-format
                                     {:keywords true})
                   :on-success [:save-patients-into-state]
                   :on-failure nil}})
   ))

(rf/reg-event-fx
 :load-patients-with-query
 (fn [{:keys [db]} [_ query-parameters]]
   (let [query-parameters-to-use (w/keywordize-keys
                                  (h/remove-nils-and-empty-strings
                                   (h/stringify-map-keywords query-parameters)))]
     {:db (assoc db
                 :last-event
                 (str "Loading filtered patients"
                      query-parameters-to-use)
                 :filtered-patients-list nil)
      :http-xhrio {:method :get
                   :params query-parameters-to-use
                   :format (ajax/json-request-format)
                   :uri "http://localhost:7500/patients/find"
                   :response-format (ajax/json-response-format
                                     {:keywords true})
                   :on-success [:save-filtered-patients-into-state]
                   :on-failure nil}}
     )))


(rf/reg-event-fx
 :update-patient
 (fn [{:keys [db]} [_ empty-parameters patient-id parameters]]
   (if ((complement empty?) empty-parameters)
     {:dispatch [:last-event (str "Some inputs were deleted. Can not update. "
                                  empty-parameters "<- empty")]}
     {:db (assoc db :last-event
                 (str "Updating patient patient with parameters: " parameters))
      :http-xhrio {:method :post
                   :params parameters
                   :format          (ajax/json-request-format)
                   :uri (str "http://localhost:7500/patients" "/" patient-id "/update")
                   :response-format (ajax/json-response-format
                                     {:keywords true})
                   :on-success [:update-patient-into-state parameters]
                   :on-failure nil}})
   ))


(rf/reg-event-fx
 :delete-patient-with-id
 (fn [{:keys [db]} [_ patient-id]]
   {:db (assoc db :last-event
               (str "Deleting patient with id: " patient-id))
    :http-xhrio {:method :delete
                 :uri (str "http://localhost:7500/patients" "/" patient-id "/delete")
                 :format (ajax/json-request-format)
                 :response-format (ajax/json-response-format
                                   {:keywords true})
                 :on-success [:delete-patient-from-state patient-id]
                 :on-failure [:last-event (str "Failed to delete patient"
                                               " with id: "
                                               patient-id)]}})
   )


(rf/reg-event-fx
 :master-patient-index
 (fn [{:keys [db]} [_ parameters empty-parameters]]
   (if ((complement empty?) empty-parameters)
     {:dispatch [:last-event (str "Please provide all needed inputs: "
                                  empty-parameters)]}
     {:db (assoc db :last-event (str "Saving patient: " parameters))
      :dispatch [:add-one-patient-into-state parameters]
      :http-xhrio {:method :post
                   :params parameters
                   :format          (ajax/json-request-format)
                   :uri "http://localhost:7500/patients/master"
                   :response-format (ajax/json-response-format
                                     {:keywords true})
                   :on-success [:save-patients-into-state]
                   :on-failure [:last-event (str "Master patient validation failed."
                                                 "Patient you are trying to save "
                                                 "is likely to exist."
                                                 parameters)]}})
   ))
