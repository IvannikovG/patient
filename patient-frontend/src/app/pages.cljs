(ns app.pages
  (:require [app.components :as components]
            [re-frame.core :as rf]
            ))


(defn about []
  [:div
   [components/navigation]
   [components/about-component]])

(defn create-patient-page []
  [:div
   [components/navigation]
   [components/errors-list]
   [components/h1-component "Create new patient"]
   [components/query-form
    components/save-patient-button]
   ])

(defn all-patients-page []
  [:div
   [components/navigation]
   [components/h1-component "All patients"]
   [components/all-patients-list]
   ])

(defn find-patient-page []
  [:div
   [components/navigation]
   [components/errors-list]
   [components/h1-component "Find patients"]
   [components/query-form
    components/load-filtered-patients-button]
   [components/filtered-patients-list]
   ])

(defn update-patient-page [id]
  [:div
   [components/navigation]
   [components/h1-component "Update patient"]
   [components/errors-list]
   [components/query-form components/update-patient-button]
   ])

(defn master-patient-index-page []
  [:div
   [components/navigation]
   [components/h1-component "Master index patient"]
   [components/errors-list]
   [components/query-form
    components/save-patient-master-index-button]
   ])
