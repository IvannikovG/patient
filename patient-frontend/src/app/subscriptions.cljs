(ns app.subscriptions
  (:require [re-frame.core :as rf]))


(rf/reg-sub
 :log-database
 (fn [db _]
   (println db)
   db))

(rf/reg-sub
 :page
 (fn [db _]
   (:page db)))


(rf/reg-sub
 :patients-list
 (fn [db _]
   (:patients-list db)))

(rf/reg-sub
 :filtered-patients-list
 (fn [db _]
   (:filtered-patients-list db)))


(rf/reg-sub
 :query-parameters
 (fn [db _]
   (:query-parameters db)))

(rf/reg-sub
 :patient-id
 (fn [db _]
   (get-in db [:query-parameters :patient-id])))

(rf/reg-sub
 :fullname
 (fn [db _]
   (get-in db [:query-parameters :fullname])))


(rf/reg-sub
 :insurance
 (fn [db _]
   (get-in db [:query-parameters :insurance])))


(rf/reg-sub
 :address
 (fn [db _]
   (get-in db [:query-parameters :address])))


(rf/reg-sub
 :gender
 (fn [db _]
   (get-in db [:query-parameters :gender])))

(rf/reg-sub
 :birthdate
 (fn [db _]
   (get-in db [:query-parameters :birthdate])))


(rf/reg-sub
 :filtered-patients
 (fn [db _]
   (:filtered-patients db)))

(rf/reg-sub
 :form-errors
 (fn [db _]
   (:errors db)))

(rf/reg-sub
 :last-event
 (fn [db _]
   (:last-event db)))
