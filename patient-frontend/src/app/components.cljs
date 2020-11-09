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
    [:div 
     [:div.form-input "Id: "]
     [:input.form-input {:type "text"
              :placeholder "Enter patient id"
              :value @(rf/subscribe [:patient-id])
              :on-change emit}]]))

 (defn fullname-input []
   (let [gettext (fn [e] (-> e .-target .-value))
         emit    (fn [e] (rf/dispatch [:add-fullname-query-parameter
                                       (gettext e)]))]
     [:div
      [:div.form-input "Full name: "]
      [:input {:type "text"
               :max-length 70
               :placeholder "Enter full name"
               :value @(rf/subscribe [:fullname])
               :on-change emit}]]))


 (defn insurance-input []
   (let [gettext (fn [e] (-> e .-target .-value))
         emit    (fn [e] (rf/dispatch [:add-insurance-query-parameter
                                       (gettext e)]))]
     [:div
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
     [:div
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
  [:div {:style {:font-weight "bold"}}"Select Gender"
  [:div
   [:div.btn-group
    [:button.button {:key :female
                              :on-click #(rf/dispatch
                                          [:select-gender "female"])}
     "female"]
    [:button.button {:key :male
                              :on-click #(rf/dispatch
                                          [:select-gender "male"])}
     "male"]
    [:button.button {:key :other
                              :on-click #(rf/dispatch
                                          [:select-gender "other"])}
     "other"]]
   [selected-gender]]])

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
  (let [query-parameters (cqp/current-query-parameters)
        patient-id @(rf/subscribe [:patient-id])]
    [:button.button {:on-click #(rf/dispatch [:update-patient
                                       patient-id
                                       (h/remove-nils-and-empty-strings
                                        query-parameters)])}
     "Update patient with current query parameters"]))

(defn patient-component [patient]
  [:div
   [:span " Id: "
    (:id patient) ]
   [:span " Full name: "
    (:fullname patient)]
   [:span " Gender: "
    (:gender patient)]
   [:span " Birthdate: "
    (:birthdate patient)" "]
   [:span " Address: "
    (:address patient) " "]
   [:span " Insurance-number: "
    (:insurance patient) " "]
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
   ])


(defn navigation []
  [:div
   [:ul
    [:li [:a {:href "#/about"} "About page "]]
    [:li [:a {:href "#/create" } "Create patient "]]
    [:li [:a {:href "#/patients"} "All patients "]]
    [:li [:a {:href "#/find"} "Find patients"]]
    [:li [:a {:href "#/update-patient"} "Manual patient update "]]
    ]
   ])

(defn last-event-component []
  [:div
   [:div.last-event @(rf/subscribe [:last-event])]])


