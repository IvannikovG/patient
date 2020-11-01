(ns ^:figwheel-hooks app.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.dom :as dom]
            [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))


(defn stateful-button []
  (let [state (r/atom 0)]
    (fn []
      [:button
       {:on-click #(swap! state inc)}
       (str @state)])))

(defn stateful-button-div [label stateful-button]
  [:div {:style {:background "blue"}}
   [:div label]
   [stateful-button]])

(defn optional-component [name age]
  [:div {:style {:margin-left 10
                 :width "15%"}}
  (when age [:h5 age])
   (when name [:h5 name])]
  )

(defn message [text urgent?]
  [:div {:style {:color (when urgent? "red")}}text])


(def checkbox-state (r/atom false))
(defn checkbox [name checked?]
  [:input {:type :checkbox
           :checked @checkbox-state
           :name name
           :on-click #(swap! checkbox-state not)
           }])



(defn contact-form [first-name last-name email-address message]
  (let [s (r/atom {:first-name first-name
                         :last-name  last-name
                         :email email-address
                         :message message})]
    (fn []
      [:form {:method "POST"
              :on-submit (fn [e]
                           (.preventDefault e)
                           (.log js/console @s)
                           )}
        [:input {:type :text :name :first-name
                 :value (:first-name @s)
                 :on-change (fn [e]
                              (swap! s :first-name (-> e .-target .-value)))}]
        [:input {:type :text :name :last-name
                 :value (:last-name @s)
                 :on-change (fn [e]
                              (swap! s :last-name (-> e .-target .-value)))}]
        [:input {:type :email :name :email
                 :value (:email @s)
                 :on-change (fn [e]
                              (swap! s :email (-> e .-target .-value)))}]
        [:textarea {:name :message
                    :on-change (fn [e]
                                 (swap! s :message (-> e .-target .-value)))}
         (:message @s)]
       [:input {:type :submit
                :value "Submit"
                :on-click #()}]])))

;; HERE OB clICK SEND HTTP

(def state (r/atom {:patients [
                               {:name "Timur" :surname "Imposter"}
                               {:name "asd" :surname "asd"}
                               ]}))

(defn get-all-response []
  (go (let [response (<! (http/get "http://localhost:7500/patients"
                                   {:with-credentials? false}))]
        (:body response))))


(def patient-data (r/atom {}))


(defn make-response-and-update-state [url]
  (go (let [response (<! (http/get url
                                   {:with-credentials? false}))]
      (prn (:status response))
      (prn (:body response))
      (reset! state (:body response))
      (prn @state))))


(defn update-patient-state []
  (make-response-and-update-state "http://localhost:7500/patients/5")
  (.log js/console @patient-data))

(defn patient-component []
  [:div
   [:p (:fullname @state)]
   [:p (:birthdate @state)]])


(defn get-button []
  [:div
   [:button {:on-click #(update-patient-state)
             :value "GET"} "Get"]])


;;REFRAME ARTICLE

(defn basic-container [view-component]
  [:div [:p "Basic component"]
   view-component
   "End of basic component"])


(defn centered-column-container [view-component]
  [:div.ui.grid
   {:style {:background "purple"}}
   [:p "contact-form"]
   [:div.two.column.centered.row
    [:div.column
     {:style {:background "cyan"}}
     view-component]]])

(defn message2 [icon-class message-class header description]
  [:div.ui.icon.message
   {:class message-class}
   [:i.icon
    {:class icon-class}]
   [:div.content
    [:div.heder header]
    [:div description]]])

(def error-message (partial message2 "" "warning"))
(def success-message (partial message2 "" "succ"))

(defn example-embedded-component
  [message header embedded-component]
  (message header embedded-component))

(defn contact-component []
  [:div
   [:div "Something went wrong"]
   [:a {:href "http://localhost:9500"}
    "contact admin"]])

;; REFRAME Article

(dom/render
 [:div
  [:p "sample text"]
  [:p ""]
  [stateful-button-div "Stateful button" (stateful-button)]
  [optional-component]
  [optional-component 1 2]
  [message "Emergency" true]
  [checkbox "Helen" true]
  [get-button]
  [centered-column-container [contact-form "Mister" "propeller" "ASD@web.de" "His vortex"]]
  [error-message "header" "Description"]
  [success-message "Header succ" "Descrp"]
  [example-embedded-component error-message
   "Some header" contact-component]
  [contact-component]
  ]
 (.. js/document (getElementById "app")))


