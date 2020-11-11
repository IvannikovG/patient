(ns app.pages
  (:require [app.components :as components]
            [re-frame.core :as rf]
            ))


(defn about []
  [:div
   [components/navigation]
   [:div.form
   [:h1 "About Patient CRUD"]
   [:div
    [:div
     "This app is made using reagent/re-frame on frontend"]
    [:div
     "Ring/compojure are serving the backend"]
    [:div
     "To store data PostgreSQL is used, jdbc to connect to it"]
    [:div [:a
           {:href "https://github.com/IvannikovG/patient"}
           "View repo on github"]]
    [:h3 "By Georgii Ivannikov"]
    [:h3 "For Health Samurai"]]]])


(defn create-patient-page []
  [:div
   [components/navigation]
   [components/errors-list]
   [:h1 "Create Patient via form"]
   [components/query-form
    components/save-patient-button]
   ])

(defn all-patients []
  [:div
   [components/navigation]
   [:div {:style {:margin "auto"
                  :text-align "center"
                  :font-size "36px"
                  :font-weight 500}}
    "All patients"]
   (rf/dispatch [:load-all-patients])
   [components/patient-list
    @(rf/subscribe [:patients-list])]
   ])

(defn find-patient-page []
  [:div
   [components/navigation]
   [components/errors-list]
   [:h1 "Find patients by query"]
   [components/errors-list]
   [components/query-form
    components/load-filtered-patients-button]
   [components/patient-list
    @(rf/subscribe [:filtered-patients-list])]
   ])

(defn update-patient-page [id]
  [:div
   [components/navigation]
   [:h1 "Update patient"]
   [components/errors-list]
   [components/query-form components/update-patient-button]
   ])

(defn update-patient-with-id-page []
  [:div
   [components/navigation]
   [:div {:style {:font-weight "bold"}}
    "Update patient with id"]
   [components/errors-list]
   [:div "Last event: " @(rf/subscribe [:last-event])]
   [components/id-input]
   [components/query-form components/update-patient-button]
   ])
