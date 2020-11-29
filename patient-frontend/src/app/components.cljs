(ns app.components
  (:require [re-frame.core :as rf]
            [goog.string :as gstring]
            [app.datepicker :as dp]
            [app.helpers :as h]
            [app.current-query-parameters :as cqp]
))

(defn last-event-component []
  [:div
   [:div  @(rf/subscribe [:last-event])]])

(defn id-input []
  (let [gettext (fn [e] (-> e .-target .-value))
        emit    (fn [e] (rf/dispatch [:add-id-query-parameter
                                      (gettext e)]))]
    [:div.form-input-wrapper
     [:div.form-input "Id: "]
     [:input.form-input {:type "text"
              :placeholder "Enter patient id"
              :value @(rf/subscribe [:patient-id])
              :on-change emit}]]))

 (defn fullname-input []
   (let [gettext (fn [e] (-> e .-target .-value))
         emit    (fn [e] (rf/dispatch [:add-fullname-query-parameter
                                       (gettext e)]))]
     [:div.form-input-wrapper
      [:div.form-input "Full name: "]
      [:input {:type "text"
               :max-length 70
               :placeholder "Enter full name"
               :value @(rf/subscribe [:full_name])
               :on-change emit}]]))

 (defn insurance-input []
   (let [gettext (fn [e] (-> e .-target .-value))
         emit    (fn [e] (rf/dispatch [:add-insurance-query-parameter
                                       (gettext e)]))]
     [:div.form-input-wrapper
      [:div.form-input "Insurance number: "]
      [:input {:type "text"
               :max-length 20
               :placeholder "Enter insurance number"
               :value @(rf/subscribe [:insurance])
               :on-change emit}]]))

 (defn address-input []
   (let [gettext (fn [e] (-> e .-target .-value))
         emit    (fn [e] (rf/dispatch [:add-address-query-parameter
                                       (gettext e)]))]
     [:div.form-input-wrapper
      [:div.form-input "Address: "]
      [:input {:type "text"
               :max-length 200
               :placeholder "Enter address number"
               :value @(rf/subscribe [:address])
               :on-change emit}]]))

(defn selected-gender []
  [:div.selected-gender
   @(rf/subscribe [:gender])])

(defn select-gender-component []
  [:div.form-input-wrapper
   [:div.form-input "Select Gender:"
   [:div.btn-group
    [:button {:class (if (= @(rf/subscribe [:gender]) "female")
                       (str "toggled-button" )
                       (str "button"))
                     :key :female
                     :on-click #(rf/dispatch
                                 [:select-gender "female"])}
     "female"]
    [:button {:class (if (= @(rf/subscribe [:gender]) "male")
                              (str "toggled-button" )
                              (str "button"))
                     :key :male
                     :on-click #(rf/dispatch
                                          [:select-gender "male"])}
     "male"]
    [:button {:class (if (= @(rf/subscribe [:gender]) "other")
                       (str "toggled-button" )
                       (str "button"))
                     :key :other
                     :on-click #(rf/dispatch
                                          [:select-gender "other"])}
     "other"]]
   ]])

(defn load-all-patients-button []
  [:button.button {:on-click #(rf/dispatch
                        [:load-all-patients])}
   "Load patients"])


(defn load-filtered-patients-button []
  [:button.button {:on-click #(rf/dispatch
                        [:load-patients-with-query
                         (cqp/current-query-parameters)])}
   "Load patients by parameters from the form"])


(defn save-patient-button []
  (let [empty-values (h/find-empty-keywords
                      (cqp/current-query-parameters))
        query-parameters (cqp/current-query-parameters)]
    [:div
     [:button.button {:on-click #(rf/dispatch [:create-patient
                                        query-parameters
                                        empty-values])}
     "Save patient"]]))

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
     "Update patient with current query parameters"]))


(defn delete-patient-button [patient]
   [:button.delete-button
    {:on-click
     #(do (js/alert "Deleted")
          (rf/dispatch [:delete-patient-with-id
                    (:id patient)]))}
    "Delete"])


(defn patient-as-row [patient]
  [:thead
  [:tr
   [:td (:id patient)]
   [:td (:full_name patient)]
   [:td (:gender patient)]
   [:td (take 10(:birthdate patient))]
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

(defn patient-list [patients component-show-name]
  [:div.patient (if (nil? patients)
                  nil
                  component-show-name)
   (gstring/unescapeEntities "&nbsp;")
   (if (nil? patients)
     nil
     [patients-table patients]
     )
   ])


(defn errors-list []
  [:div {:style {:color "red"}}
   @(rf/subscribe [:form-errors])])

(defn query-form [button]
  [:div.form
   [fullname-input]
   [select-gender-component]
   [insurance-input]
   [address-input]
   [dp/datepicker-component]
   [:hr]
   [button]
   [:div.last-event
    [last-event-component]]
   ])


(defn navigation []
  [:div
   [:ul
    [:li [:a {:href "#/about"} "About page "]]
    [:li [:a {:href "#/create" } "Create patient "]]
    [:li [:a {:href "#/find"} "Find patients"]]
    [:li [:a {:href "#/patients"} "All patients "]]
    ]
   ])



