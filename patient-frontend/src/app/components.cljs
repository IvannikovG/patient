(ns app.components
  (:require [re-frame.core :as rf]
            [goog.string :as gstring]
            [app.datepicker :as dp]
            [app.helpers :as h]
            [app.current-query-parameters :as cqp]
))

(defn h1-component [content]
  [:div {:style {:margin "auto"
                 :display "flex"
                 :justify-content "center"
                 :font-size "36px"
                 :font-weight 500}}
   content])

(defn about-component []
  [:div
   [:div.about-container
    [:div
     [:h1 {:style {:margin "8px"}}
      "Patient CRUD demo"]
     [:div.description
      "This app is made using reagent/re-frame on frontend"]
     [:div.description
      "Ring is serving the backend"]
     [:div.description
      "To store data PostgreSQL is used"]
     [:div.by [:a
               {:href "https://github.com/IvannikovG/patient"}
               "View repo on github"]]
     [:div.by "By Georgii Ivannikov"]
     [:div.by "For Health Samurai"]]]])

(defn last-event-component []
  [:div
   [:div @(rf/subscribe [:last-event])]])


(defn input-component [form-input-name string-placeholder
                       dispatch-keyword subscription-keyword
                       max-length]
  (let [gettext (fn [e] (-> e .-target .-value))
        emit    (fn [e] (rf/dispatch [dispatch-keyword
                                      (gettext e)]))]
    [:div.form-input-wrapper
     [:div.form-input form-input-name]
     [:input {:type "text"
              :max-length max-length
              :placeholder string-placeholder
              :value @(rf/subscribe [subscription-keyword])
              :on-change emit}]]))


(defn gender-button [gender]
  [:button {:class (if (= @(rf/subscribe [:gender]) gender)
                     (str "toggled-button")
                     (str "button"))
            :key :female
            :on-click #(rf/dispatch
                        [:select-gender gender])}
   gender])


(defn select-gender-component []
  [:div.form-input-wrapper
   [:div.form-input "Select Gender:"
   [:div.btn-group
    [gender-button "female"]
    [gender-button "male"]
    [gender-button "other"]
    ]
   ]])


(defn load-filtered-patients-button []
  [:button.button {:on-click #(rf/dispatch
                        [:load-patients-with-query
                         (cqp/current-query-parameters)])}
   "Find patients"])

(defn save-button [keyword-dispatcher button-name]
  (let [empty-values (h/find-empty-keywords
                      (cqp/current-query-parameters))
        query-parameters (cqp/current-query-parameters)]
    [:div
     [:button.button {:on-click #(rf/dispatch [keyword-dispatcher
                                               query-parameters
                                               empty-values])}
      button-name]]))

(defn save-patient-button []
  (save-button :create-patient "Save patient"))

(defn save-patient-master-index-button []
  (save-button :master-patient-index "Save patient to master index"))

(defn update-patient-button []
  (let [empty-values (h/find-empty-keywords
                      (cqp/current-query-parameters))
        query-parameters (cqp/current-query-parameters)
        patient-id @(rf/subscribe [:patient-id])]
    [:button.button {:on-click #(rf/dispatch
                                 [:update-patient
                                  empty-values
                                  patient-id
                                  (h/remove-nils-and-empty-strings
                                               query-parameters)])}
     "Update patient"]))

(defn delete-patient-button [patient]
   [:button.delete-button
    {:on-click
     #(do (let [confirmed? (js/confirm "Delete this patient?")]
            (if confirmed?
              (rf/dispatch [:delete-patient-with-id
                            (:id patient)])))
          )}
    "Delete"])

(defn patient-as-row [patient]
  [:thead
  [:tr
   [:td (:id patient)]
   [:td (:full_name patient)]
   [:td (:gender patient)]
   [:td (take 10 (:birthdate patient))]
   [:td (:address patient)]
   [:td (:insurance patient)]
   [:td
    [:div
     [:div {:style {:float "left"
                    :font-size "12px"
                    :margin-left "2px"}}
      [:a
       {:href (str "#/update/" (:id patient))}
       "Update"]]
     [:div {:style {:float "right"
                    :margin-right "4px"}}
      [delete-patient-button patient]]]
    ]]]
  )

(defn patients-table [patients]
  [:table
   [:thead
    [:tr
     [:td.th-header "ID"]
     [:td.th-header "Full name"]
     [:td.th-header "Gender"]
     [:td.th-header "Birthdate"]
     [:td.th-header "Address"]
     [:td.th-header "Insurance number"]
     [:td.th-header "Actions"]
     ]]
   (for [patient patients]
     ^{:key patient} [patient-as-row patient])
   ]
)

(defn sorting-select-component []
  [:div "Sort by "
   [:select {:name "sort-by"
             :on-change #(rf/dispatch [:set-patients-sorter (.. % -target -value)])}
    [:option {:value :id} "ID"]
    [:option {:value :full_name} "Full name"]
    [:option {:value :birthdate} "Birthdate"]
    "Sort by"]])

(defn filtered-patients-list []
  (let [patients-not-found?
        @(rf/subscribe [:filtered-patients-not-found?])
        patients-not-searched? @(rf/subscribe
                                 [:filtered-patients-not-searched?])
        patients @(rf/subscribe [:filtered-patients-list])]
    (cond
      patients-not-searched? nil
      patients-not-found? [h1-component (str "No patients found")]
      :else [:div.patient-table
             [sorting-select-component]
             [:div.patient-table
              [patients-table patients]]]
      )))

(defn all-patients-list []
  (let [_ (rf/dispatch [:load-all-patients])
        patients @(rf/subscribe [:patients-list])
        should-render-patients? @(rf/subscribe [:patients-exist?])]
    (if should-render-patients?
      [:div.patient-table
        [sorting-select-component]
        [:div.patient-table
         [patients-table patients]]]
      [h1-component (str "No patients found")])))

(defn errors-list []
  [:div {:style {:color "red"}}
   @(rf/subscribe [:form-errors])])

(defn query-form [button]
  [:div.form
   [input-component "Full name:" "Enter full name"
    :add-fullname-query-parameter :full_name 100]
   [select-gender-component]
   [input-component "Insurance:" "Enter insurance number"
    :add-insurance-query-parameter :insurance 25]
   [input-component "Address:" "Enter address"
    :add-address-query-parameter :address 200]
   [dp/datepicker-component]
   [:hr]
   [button]
   [:div.last-event
    [last-event-component]
    ]
   ])

(defn navigation []
  [:div
   [:ul
    [:li [:a {:href "#/about"} "About page "]]
    [:li [:a {:href "#/create" } "Create patient "]]
    [:li [:a {:href "#/find"} "Find patients"]]
    [:li [:a {:href "#/patients"} "All patients "]]
    [:li [:a {:href "#/master"} "Master patient index"]]
    ]
   ])
