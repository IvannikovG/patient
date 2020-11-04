(ns app.subscriptions
  (:require [re-frame.core :as rf]))

;; -- Domino 4 - Query  -------------------------------------------------------

(rf/reg-sub
 :patients-list
 (fn [db _]
   (:patients-list db)))

(rf/reg-sub
 :query-parameters
 (fn [db _]
   (:query-parameters db)))

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
