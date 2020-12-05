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

(defn all-patients []
  [:div
   [components/navigation]
   [components/h1-component "All patients"]
   (rf/dispatch [:load-all-patients])
   [components/patient-list
    @(rf/subscribe [:patients-list])]
   ])

(defn find-patient-page []
  [:div
   [components/navigation]
   [components/errors-list]
   [components/h1-component "Find patients"]
   [components/query-form
    components/load-filtered-patients-button]
   [components/patient-list
    @(rf/subscribe [:filtered-patients-list])]
   ])

(defn update-patient-page [id]
  [:div
   [components/navigation]
   [components/h1-component "Update patient"]
   [components/errors-list]
   [components/query-form components/update-patient-button]
   ])

