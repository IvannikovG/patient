(ns app.views
  (:require [reagent.dom :as dom]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [goog.string :as gstring]

            [app.datepicker :as dp]
            [app.events :as events]
            [app.subscriptions :as subscriptions]
            [app.helpers :as h]
            [app.current-query-parameters :as cqp]
            ))

(enable-console-print!)



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

(defn load-all-patients-button []
  [:button {:on-click #(rf/dispatch
                        [:load-all-patients])}
   "Load patients"])


(defn load-filtered-button []
  [:button {:on-click #(rf/dispatch
                        [:load-patients-with-query
                         (cqp/current-query-parameters)])}
   "Load patients by parameters from the form"])


(defn save-patient-button []
  (let [empty-values (h/find-empty-keywords (cqp/current-query-parameters))
        query-parameters (cqp/current-query-parameters)]
    [:button {:on-click #(rf/dispatch [:create-patient query-parameters empty-values])}
     "SAAVE PATIETN"]))

(defn patient-component [patient]
  [:div.patient
   [:div "Id: " (:id patient)]
   [:div "Full name: " (:fullname patient)]
   [:div "Gender: " (:gender patient)]
   [:div "Birthdate: " (:birthdate patient)]
   [:div "Address: " (:address patient)]
   [:div "Insurance-number: " (:insurance patient)]
   [:button {:on-click #(rf/dispatch [:delete-patient-with-id (:id patient)])} "Delete"]])

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
   [:div "Fullname: " @(rf/subscribe [:fullname])]
   [:div "Insurance: " (str @(rf/subscribe [:insurance]))]
   [:div "Birthdate: " (str @(rf/subscribe [:birthdate]))] 
   [:div "Address: " (str @(rf/subscribe [:address]))]
   [:div "Gender: " (str @(rf/subscribe [:gender]))]
   ])


(defn ui
  []
  [:div
   [:h1 "Patients CRUD"]
   [errors-list]
   [:div "Last event: "@(rf/subscribe [:last-event])]
   [query-form]
   [form-with-user-input]
   [save-patient-button]
   [:div "Debugging loading patients"]
   [load-all-patients-button]
   [load-filtered-button]
   [patient-list @(rf/subscribe [:patients-list]) "Queried Patients"]
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
