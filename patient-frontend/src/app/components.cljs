(ns app.components
  (:require [re-frame.core :as rf]
            [goog.string :as gstring]
            [app.datepicker :as dp]
            [app.helpers :as h]
            [app.current-query-parameters :as cqp]

))

(defn id-input []
  (let [gettext (fn [e] (-> e .-target .-value))
        emit    (fn [e] (rf/dispatch [:add-id-query-parameter
                                      (gettext e)]))]
    [:div "Id: "
     [:input {:type "text"
              :placeholder "Enter patient id"
              :value @(rf/subscribe [:patient-id])
              :on-change emit}]]))

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
  [:div
   [:div.btn-group {:field :single-select :id :unique-position}
    [:button.btn.btn-default {:key :female
                              :on-click #(rf/dispatch
                                          [:select-gender "female"])} "female"]
    [:button.btn.btn-default {:key :male
                              :on-click #(rf/dispatch
                                          [:select-gender "male"])} "male"]
    [:button.btn.btn-default {:key :other
                              :on-click #(rf/dispatch
                                          [:select-gender "other"])} "other"]]
   [:div (str @(rf/subscribe [:gender]))]])

(defn load-all-patients-button []
  [:button {:on-click #(rf/dispatch
                        [:load-all-patients])}
   "Load patients"])


(defn load-filtered-patients-button []
  [:button {:on-click #(rf/dispatch
                        [:load-patients-with-query
                         (cqp/current-query-parameters)])}
   "Load patients by parameters from the form"])


(defn save-patient-button []
  (let [empty-values (h/find-empty-keywords
                      (cqp/current-query-parameters))
        query-parameters (cqp/current-query-parameters)]
    [:button {:on-click #(rf/dispatch [:create-patient
                                       query-parameters
                                       empty-values])}
     "Save patient"]))

(defn update-patient-button []
  (let [query-parameters (cqp/current-query-parameters)
        patient-id @(rf/subscribe [:patient-id])]
    [:button {:on-click #(rf/dispatch [:update-patient
                                       patient-id
                                       (h/remove-nils-and-empty-strings
                                        query-parameters)])}
     "Update patient with current query parameters"]))

(defn patient-component [patient]
  [:div.patient
   [:div "Id: " (:id patient)]
   [:div "Full name: " (:fullname patient)]
   [:div "Gender: " (:gender patient)]
   [:div "Birthdate: " (:birthdate patient)]
   [:div "Address: " (:address patient)]
   [:div "Insurance-number: " (:insurance patient)]
   [:a {:href (str "#/update/" (:id patient))} "Update"]
   [:button {:on-click #(rf/dispatch [:delete-patient-with-id
                                      (:id patient)])} "Delete"]])

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
   [:div "Id: " @(rf/subscribe [:patient-id])]
   [:div "Fullname: " @(rf/subscribe [:fullname])]
   [:div "Insurance: " (str @(rf/subscribe [:insurance]))]
   [:div "Birthdate: " (str @(rf/subscribe [:birthdate]))]
   [:div "Address: " (str @(rf/subscribe [:address]))]
   [:div "Gender: " (str @(rf/subscribe [:gender]))]
   ])

(defn navigation []
  [:div [:h1 "Navigation"]
   [:a {:href "#/about"} "About page "]
   [:a {:href "#/create"} "Create patient "]
   [:a {:href "#/patients"} "All patients "]
   [:a {:href "#/find"} "Find "]
   [:a {:href "#/update-patient"} "Update "]
   [:a {:href "#/debug"} "Debug"]
   ]
  )


(defn ui
  []
  [:div
   [navigation]
   [:h1 "Patients CRUD"]
   [errors-list]
   [:div "Last event: "@(rf/subscribe [:last-event])]
   [id-input]
   [query-form]
   [form-with-user-input]
   [update-patient-button]
   [save-patient-button]
   [:div "Debugging loading patients"]
   [load-all-patients-button]
   [load-filtered-patients-button]
   [patient-list @(rf/subscribe [:patients-list]) "Queried Patients"]
   ])

