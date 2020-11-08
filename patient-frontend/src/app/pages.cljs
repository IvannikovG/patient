(ns app.pages
  (:require [app.components :as components]
            [re-frame.core :as rf]
            ))



(defn home []
  [:div
   [components/navigation]])

(defn about []
  [:div [:h1 "About Patient CRUD"]
   [:a {:href "#/"} "home page"]
   [:div
    [:h3 "By Georgii Ivannikov"]
    [:h3 "For Health Samurai"]]])


(defn create-patient-page []
  [:div
   [:div "Last event: "@(rf/subscribe [:last-event])]
   [components/navigation]
   [:h1 "Create Patient via form"]
   [components/query-form]
   [components/save-patient-button]])

(defn all-patients []
  [:div
   [:div "Last event: "@(rf/subscribe [:last-event])]
   [components/navigation]
   (rf/dispatch [:load-all-patients])
   [components/patient-list
    @(rf/subscribe [:patients-list]) "All found patients"]])

(defn find-patient-page []
  [:div
   [:div "Last event: "@(rf/subscribe [:last-event])]
   [components/navigation]
   [:h1 "Find patients by query"]
   [components/errors-list]
   [components/query-form]
   [components/load-filtered-patients-button]
   [components/patient-list
    @(rf/subscribe [:filtered-patients-list]) "Found this patients"]])

(defn update-patient-page [id]
  [:div
   [components/navigation]
   [:div "Update patient"]
   [components/errors-list]
   [:div "Last event: " @(rf/subscribe [:last-event])]
   [components/query-form]
   [components/update-patient-button]
   ])

(defn update-patient-with-id-page []
  [:div
   [components/navigation]
   [:div "Update patient with id"]
   [components/errors-list]
   [:div "Last event: " @(rf/subscribe [:last-event])]
   [components/id-input]
   [components/query-form]
   [components/update-patient-button]])
