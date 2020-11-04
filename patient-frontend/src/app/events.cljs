(ns app.events
  (:require [re-frame.core :as rf]
            [clojure.edn :as edn]
            [app.helpers :as h]
            [day8.re-frame.http-fx]
            [ajax.core :as ajax]
            [clojure.string :as str]
            [app.current-query-parameters :as cqp]
            ))


;; INIT ;;



(rf/reg-event-db
 :initialize
 (fn [_ _]
   {:last-event (str "Initialised App")
    :errors nil}))


;; STABLE PURE EVENTS ;;

;; Another one
(rf/reg-event-db
 :last-event
 (fn [db [_ event-name]]
   (-> db
       (assoc :last-event event-name))))


(rf/reg-event-db
 :add-fullname-query-parameter
 (fn [db [_ fullname]]
   (-> db
       (assoc-in [:query-parameters :fullname] fullname))))


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
 :select-gender
 (fn [db [_ gender]]
   (-> db
       (assoc-in [:query-parameters :gender] gender))))


(rf/reg-event-db
 :add-address-query-parameter
 (fn [db [_ address]]
   (-> db
       (assoc-in [:query-parameters :address] address))))


;; PATIENTS LOADERS STABLE ;;

(rf/reg-event-db
 :save-patients-into-state
 (fn [db [_ patients]]
   ; (println patients)
   (assoc db :patients-list patients :last-event "Loaded all patients")))

(rf/reg-event-db
 :put-errors-into-state
 (fn [db [_ errors]]
   (-> db
       (assoc :errors errors))))

(rf/reg-event-fx
 :load-all-patients
 (fn [{:keys [db]} _]
   (println "DB" db )
   {:http-xhrio {:method :get
                :uri "http://localhost:7500/patients"
                :response-format (ajax/json-response-format {:keywords? true})
                :on-success [:save-patients-into-state]
                :on-failure [:put-errors-into-state]}}))



(rf/reg-event-db
 :save-filtered-patients-into-state
 (fn [db [_ patients]]
   (println db)
   (-> db
       (assoc :patients-list patients :last-event "Filtered patients"))))


(rf/reg-event-fx
 :load-patients-with-query
 (fn [{:keys [db]} [_ query-parameters]]
   (println db)
   {:db (assoc db :last-event (str "Loaded patients with following query parameters: "
                                   query-parameters ))
    :http-xhrio {:method :get
                 :uri "http://localhost:7500/patients/find"
                 :response-format (ajax/json-response-format {:keywords? true})
                 :body (h/remove-nils-and-empty-strings query-parameters)
                 :on-success [:save-filtered-patients-into-state]
                 :on-failure [:put-errors-into-state]}}))

;;; PATIENT SAVERS AND DELETERS ;;;

;; Legacy
(rf/reg-event-db
 :save-patient-legacy
 (fn [db [_ query-params]]
   (println query-params)
   (-> db
       (assoc :errors
              (str/join " "
                        (h/find-empty-keywords query-params))))))



;; Deleters
(rf/reg-event-db
 :delete-patient-from-state
 (fn [db [_ patient-id]]
   (println patient-id)
   (assoc db :patients-list (remove (fn [p] (= (:id p) patient-id))
                                     (:patients-list db)))))


(rf/reg-event-fx
 :delete-patient-with-id
 (fn [{:keys [db]} [_ patient-id]]
   {:db (assoc db :last-event (str "Deleting patient with id: " patient-id)
          :patients-list (remove (fn [p] (= (:id p) patient-id))
                                 (:patients-list db)))
    :http-xhrio {:method :delete
                 :uri (str "http://localhost:7500/patients/" patient-id "/delete")
                 :format (ajax/json-request-format)
                 :response-format (ajax/json-response-format {:keywords? true})
                 :on-success [:delete-patient-from-state patient-id]
                 :on-failure [:put-errors-into-state]}}))

;; Savers

(rf/reg-event-fx
 :create-patient
 (fn [{:keys [db]} [_ query-parameters empty-query-parameters]]
   ; (println "QP" query-parameters "EQP" empty-query-parameters)
   (if (empty? empty-query-parameters)
     (do (println "Parameters OK" query-parameters)
         {:db (assoc db :last-event (str "Creating patient"))
          :http-xhrio {:method :post
                       :uri "http://localhost:7500/patients"
                       :format (ajax/json-request-format)
                       :params query-parameters
                       :response-format (ajax/json-response-format {:keywords? true})
                       :on-success [:save-patient query-parameters]
                       :on-failure [:put-errors-into-state]}})
     {:db (assoc db :last-event (str "Failed to save patient due to empty fields: "
                                     empty-query-parameters))})))

(rf/reg-event-db
 ;; DO not know what to do here
 :save-patient
 (fn [db [query-parameters]]
   (println query-parameters)))


;; UPDATERS

;; TODO ;;
