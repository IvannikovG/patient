(ns app.views
  (:require [reagent.dom :as dom]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [cljs-http.client :as http]
            [app.datepicker :as dp]
            [app.events :as events]
            [app.subscriptions :as subscriptions]
            [goog.string :as gstring]
            [app.helpers :as h]
            [app.current-query-parameters :as cqp]
            [app.handlers-for-views :as handlers]))

(enable-console-print!)


;; ;; -- Domino 5 - View Functions ----------------------------------------------

 (defn fullname-input []
   (let [gettext (fn [e] (-> e .-target .-value))
         emit    (fn [e] (rf/dispatch [:add-fullname-query-parameter
                                       (gettext e)]))]
     [:div "Full name: "
      [:input {:type "text"
               :placeholder "Enter full name"
               :value @(rf/subscribe [:fullname])
               :on-change emit}]]))


 (defn insurance-input []
   (let [gettext (fn [e] (-> e .-target .-value))
         emit    (fn [e] (rf/dispatch [:add-insurance-query-parameter
                                       (gettext e)]))]
     [:div "Insurance number: "
      [:input {:type "text"
               :placeholder "Enter insurance number"
               :value @(rf/subscribe [:insurance])
               :on-change emit}]]))

 (defn address-input []
   (let [gettext (fn [e] (-> e .-target .-value))
         emit    (fn [e] (rf/dispatch [:add-address-query-parameter
                                       (gettext e)]))]
     [:div "Address: "
      [:input {:type "text"
               :placeholder "Enter address number"
               :value @(rf/subscribe [:address])
               :on-change emit}]]))


 (defn select-gender-component []
   [:div.btn-group {:field :single-select :id :unique-position}
    [:button.btn.btn-default {:key :female
                              :on-click #(rf/dispatch
                                          [:select-gender "female"])} "female"]
    [:button.btn.btn-default {:key :male
                              :on-click #(rf/dispatch
                                          [:select-gender "male"])} "male"]
    [:button.btn.btn-default {:key :other
                              :on-click #(rf/dispatch
                                          [:select-gender "other"])} "other"]])



(defn load-filtered-button []
  [:button {:on-click #(handlers/load-filtered-patients (cqp/current-query-parameters))}
   "Load users by parameters from the form"])


(defn create-patient-button []
   (let [empty-values (h/find-empty-keywords (cqp/current-query-parameters))]
      [:button {:on-click #(handlers/save-patient (cqp/current-query-parameters))}
       "Create user from parameters you provided"]))



(defn patient-component [patient]
  [:div.patient
   [:div "Full name: " (:fullname patient)]
   [:div "Gender: " (:gender patient)]
   [:div "Birthdate: " (:birthdate patient)]
   [:div "Address: " (:address patient)]
   [:div "Insurance-number: " (:insurance patient)]])

(defn patient-list [patients component-show-name]
  [:div (if (nil? patients) nil component-show-name)
   (gstring/unescapeEntities "&nbsp;")
   [:div
    (for [patient patients]
      ^{:key patient} [:div [patient-component patient]
                       (gstring/unescapeEntities "&nbsp;")])]])


(defn errors-list []
  [:div {:style {:color "red"}} @(rf/subscribe [:form-errors])])

(defn query-form []
  [:div
   [fullname-input]
   [select-gender-component]
   [insurance-input]
   [address-input]
   [dp/datepicker-component]
   ])

(defn form-with-user-input []
  [:div
   [errors-list]
   [:div "Fullname: " @(rf/subscribe [:fullname])]
   [:div "Insurance: " (str @(rf/subscribe [:insurance]))]
   [:div "Birthdate: " (str @(rf/subscribe [:birthdate]))] 
   [:div "Address: " (str @(rf/subscribe [:address]))]
   [:div "Gender: " (str @(rf/subscribe [:gender]))]
   ])


(defn test-component []
  [:div
   [:h3 "Asd"]])

(defn ui
  []
  [:div
   [:h1 "Patients CRUD"]
   [:div "Last event: "@(rf/subscribe [:last-event])]
   [query-form]
   [load-filtered-button]
   [form-with-user-input]
   [create-patient-button]
   [patient-list @(rf/subscribe [:filtered-patients]) "Found patients"]
  ])



;; -- Entry Point -------------------------------------------------------------

(defn render
  []
  (reagent.dom/render [ui]
                      (js/document.getElementById "app")))

(defn run
  []
 (rf/dispatch-sync [:initialize]) ;; put a value into application state
  (render)
)

(run)
