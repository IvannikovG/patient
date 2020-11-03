(ns app.handlers-for-views
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require
   [cljs-http.client :as http]
   [cljs.core.async :refer [<!]]
   [re-frame.core :as rf]
   [app.helpers :as h]))

 (defn put-element-to-local-storage
   [key-name elements]
   (.setItem js/localStorage key-name elements))

 (defn put-patients-to-local-storage [patients]
   (put-element-to-local-storage "patients" patients))

 (defn put-filtered-patients-to-local-storage [patients]
   (put-element-to-local-storage "filtered-patients" patients))

;; ;; Functions to load patients from api and optionally put them to the local storage

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

 (defn upload-patient [url query-params]
   (go (let [response (<! (http/post url
                                    {:with-credentials false
                                     :query-params query-params}))])))

(defn load-filtered-patients [query-params]
  (go (let [response (<! (http/get "http://localhost:7500/patients/find"
                                   {:with-credentials false
                                    :query-params (h/stringify-map-keywords
                                                   (h/remove-nils-and-empty-strings
                                                    query-params))}))]
        (do (rf/dispatch [:filter-patients-from-backend (:body response)])
            (rf/dispatch [:last-event (str "Returned patients with matching parameters: "
                                           (h/remove-nils-and-empty-strings
                                            @(rf/subscribe [:query-parameters])))])))))

(defn save-patient [query-params]
  (rf/dispatch [:save-patient query-params])
  (let [str-query-params (h/stringify-map-keywords query-params)]
    (if (empty? @(rf/subscribe [:form-errors]))
      (do 
          (upload-patient "http://localhost:7500/patients" str-query-params)
          (rf/dispatch [:last-event (str "Saved patient" str-query-params)]))
      (rf/dispatch [:last-event (str
                                 "Failed to save patient due to errors in: "
                                 @(rf/subscribe [:form-errors]))]))))

