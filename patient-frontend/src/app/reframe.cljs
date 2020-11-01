(ns ^:figwheel-hooks app.reframe
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [reagent.dom :as dom]
            [reagent.core :as r]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            [re-frame.core :as rf]
            [re-frame.subs :as subs]
            [re-frame.events :as events]
            [clojure.string :as str]
            [cljs-http.client :as http]
            [clojure.edn :as edn]
            [goog.string :as gstring]
            [app.datepicker :as dp]
            [clojure.walk :as walk]))

(enable-console-print!)

;; Functions to put to local storage
(defn put-element-to-local-storage
  [key-name elements]
  (.setItem js/localStorage key-name elements))

(defn put-patients-to-local-storage [patients]
  (put-element-to-local-storage "patients" patients))

(defn put-filtered-patients-to-local-storage [patients]
  (put-element-to-local-storage "filtered-patients" patients))

;; Functions to load patients from api and put them to the local storage

(defn load-patients [url query-params put-function]
  (go (let [response (<! (http/get url
                                   {:with-credentials false
                                    :query-params query-params}))]
        (put-function (:body response)))))


(defn load-all-patients []
  (load-patients "http://localhost:7500/patients" {}
                 put-patients-to-local-storage))

(defn load-patients-with-query [query-params]
  (load-patients "http://localhost:7500/patients/find" query-params
                 put-filtered-patients-to-local-storage))

;; HELPERS
(defn stringify-map-keywords
  [map]
  (walk/stringify-keys map))


(defn remove-nils-and-empty [record]
  (reduce (fn [m [k v]] (if (or (nil? v)
                                (empty? v)
                                (= "unselected" v)) m (assoc m k v))) {} record))


(defn lower-cased-values [record]
  (reduce (fn [m [k v]] (if (string? v)
                          (assoc m k (str/lower-case v))
                          (assoc m k v))) {} record))


(defn map-subset? [a-map b-map]
  (every? (fn [[k _ :as entry]] (= entry (find b-map k))) a-map))

(defn filter-by [config collection]
  (let [conf (remove-nils-and-empty config)]
    (filter (fn [el] (map-subset? (lower-cased-values conf)
                                  (lower-cased-values el))) collection)))


(defn current-query-parameters []
  {:fullname (str @(rf/subscribe [:fullname]))
   :insurance @(rf/subscribe [:insurance])
   :address @(rf/subscribe [:address])
   :birthdate @(rf/subscribe [:birthdate])
   :gender (str @(rf/subscribe [:gender]))
   })
;; -- Domino 2 - Event Handlers -----------------------------------------------

(rf/reg-event-db
 :initialize
 (fn [_ _]
   (load-all-patients)
   {:patients (edn/read-string
               (.getItem js/localStorage "patients"))
    :query-parameters {:fullname nil
                       :gender nil
                       :birthdate nil
                       :address nil
                       :insurance nil}}))


(rf/reg-event-db
 :add-fullname-query-parameter
 (fn [db [_ fullname]]
   (-> db
       (assoc-in [:query-parameters :fullname] fullname))))


(rf/reg-event-db
 :add-birthdate-query-parameter
 (fn [db [_ birthdate]]
   (-> db
       (assoc-in [:query-parameters :birthdate] birthdate))))


(rf/reg-event-db
 :add-insurance-query-parameter
 (fn [db [_ insurance]]
   (-> db
       (assoc-in [:query-parameters :insurance] insurance))))


(rf/reg-event-db
 :select-gender
 (fn [db [_ gender]]
   (-> db
       (assoc-in [:query-parameters :gender] gender))))


(rf/reg-event-db
 :add-address-query-parameter
 (fn [db [_ address]]
   (-> db
       (assoc-in [:query-parameters :address] address))))


(rf/reg-event-db
 :filter-patients
 (fn [db [_ query-params]]
   ;(load-patients-with-query query-params)
   (-> db
       (assoc :filtered-patients
              (filter-by (current-query-parameters) (:patients db))))))


;; -- Domino 4 - Query  -------------------------------------------------------

(rf/reg-sub
 :patients
 (fn [db _]
   (:patients db)))


(rf/reg-sub
 :fullname
 (fn [db _]
   (get-in db [:query-parameters :fullname])))


(rf/reg-sub
 :insurance
 (fn [db _]
   (get-in db [:query-parameters :insurance])))


(rf/reg-sub
 :address
 (fn [db _]
   (get-in db [:query-parameters :address])))


(rf/reg-sub
 :gender
 (fn [db _]
   (get-in db [:query-parameters :gender])))

(rf/reg-sub
 :birthdate
 (fn [db _]
   (get-in db [:query-parameters :birthdate])))


(rf/reg-sub
 :filtered-patients
 (fn [db _]
   (:filtered-patients db)))

;; -- Domino 5 - View Functions ----------------------------------------------

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
  [:button {:on-click #(rf/dispatch [:filter-patients
                                     (current-query-parameters)
                                     ])}])

(defn patient-component [patient]
  [:div.patient
   [:div "Full name: " (:fullname patient)]
   [:div "Gender: " (:gender patient)]
   [:div "Birthdate: " (:birthdate patient)]
   [:div "Address: " (:address patient)]
   [:div "Insurance-number: " (:insurance patient)]])

(defn patient-list [patients component-show-name]
  [:div component-show-name
   (gstring/unescapeEntities "&nbsp;")
   [:div
    (for [patient patients]
      ^{:key patient} [:div [patient-component patient]
                       (gstring/unescapeEntities "&nbsp;")])]])


(defn query-form []
  [:div
   [fullname-input]
   [select-gender-component]
   [insurance-input]
   [address-input]
   [dp/datepicker-component]
   ])

(defn element-with-query-parameters []
  [:div
   [:div :Fullname (str @(rf/subscribe [:fullname]))]
   [:div :Insurance (str @(rf/subscribe [:insurance]))]
   [:div :Birthdate (str @(rf/subscribe [:birthdate]))] 
   [:div :Address (str @(rf/subscribe [:address]))]
   [:div :Gender (str @(rf/subscribe [:gender]))]
   ])


(defn ui
  []
  [:div
   [:h1 "Patients CRUD"]
   [query-form]
   [load-filtered-button]
   [element-with-query-parameters]
   [patient-list @(rf/subscribe [:filtered-patients]) "Found"]
  ])



;; -- Entry Point -------------------------------------------------------------

(defn render
  []
  (reagent.dom/render [ui]
                      (js/document.getElementById "app")))

(defn ^:dev/after-load clear-cache-and-render!
  []
  (rf/clear-subscription-cache!)
  (render))

(defn run
  []
  (rf/dispatch-sync [:initialize]) ;; put a value into application state
  (render)
)
