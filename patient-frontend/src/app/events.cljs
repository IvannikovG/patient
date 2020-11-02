(ns app.events
  (:require [re-frame.core :as rf]
            [clojure.edn :as edn]
            [app.helpers :as h]
            [cljs-http.core :as http]
            [clojure.string :as str]))


(defn current-query-parameters []
  {:fullname @(rf/subscribe [:fullname])
   :gender @(rf/subscribe [:gender])
   :birthdate @(rf/subscribe [:birthdate])
   :address @(rf/subscribe [:address])
   :insurance @(rf/subscribe [:insurance])
   })

;; -- Domino 2 - Event Handlers -----------------------------------------------

(rf/reg-event-db
 :initialize
 (fn [_ _]
   (h/load-all-patients)
   {:patients (edn/read-string
               (.getItem js/localStorage "patients"))
    :query-parameters {:fullname nil
                       :gender nil
                       :birthdate nil
                       :address nil
                       :insurance nil}
   :last-event "Start doing something"}))

(rf/reg-event-db
 :save-patient
 (fn [db [_ query-params]]
   ;(println query-params)
   (-> db
       (assoc :errors
              (str/join " "
                        (h/find-empty-keywords query-params))))))

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


(rf/reg-event-db
 :filter-patients
 (fn [db [_ query-params]]
   (-> db
       (assoc :filtered-patients
              (h/filter-by (current-query-parameters)
                           (:patients db))))))

(rf/reg-event-db
 :last-event
 (fn [db [_ event-name]]
   (-> db
       (assoc :last-event event-name))))

