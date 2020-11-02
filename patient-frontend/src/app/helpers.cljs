(ns app.helpers
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [clojure.string :as str]
            [re-frame.core :as rf]
            [clojure.walk :as walk]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
))

;; HELPERS
(defn stringify-map-keywords
  [map]
  (walk/stringify-keys map))

(defn empty-value? [val]
  (cond
    (number? val) false
    (empty? val) true
    (nil? val) true
    (= "unselected" val) true
    :else false))


(defn remove-nils-and-empty-strings [record]
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
  (let [conf (remove-nils-and-empty-strings config)]
    (filter (fn [el] (map-subset? (lower-cased-values conf)
                                  (lower-cased-values el))) collection)))



(defn find-empty-keywords [record]
  (map first (filter (fn [el] (empty-value?
                    (second el))) record)))


 (defn put-element-to-local-storage
   [key-name elements]
   (.setItem js/localStorage key-name elements))

 (defn put-patients-to-local-storage [patients]
   (put-element-to-local-storage "patients" patients))

 (defn put-filtered-patients-to-local-storage [patients]
   (put-element-to-local-storage "filtered-patients" patients))

;; ;; Functions to load patients from api and put them to the local storage

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




(defn save-patient [query-params]
  (rf/dispatch [:save-patient query-params])
  (let [str-query-params (stringify-map-keywords query-params)]
    (if (empty? @(rf/subscribe [:form-errors]))
      (do (println @(rf/subscribe [:form-errors]))
          (upload-patient "http://localhost:7500/patients" str-query-params)
          (rf/dispatch [:last-event (str "Saved patient" str-query-params)]))
      (rf/dispatch [:last-event (str
                                 "Failed to save patient due to errors in: "
                                 @(rf/subscribe [:form-errors]))]))))

