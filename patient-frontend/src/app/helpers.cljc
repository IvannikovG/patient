(ns app.helpers
(:require [clojure.string :as str]
          [clojure.walk :as walk]
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


(defn find-empty-keywords [record]
  (map first (filter (fn [el] (empty-value?
                               (second el))) record)))

(defn patient-by-id
  [id keyword db]
  (first (filter (fn [p] (= (:id p)
                            (int id)))
                 (keyword db))))


(defn assoc-patient-params-to-form-query-params-in-state
  [db patient]
  (-> db
      (assoc-in [:query-parameters :full_name]
                (:full_name patient))
      (assoc-in [:query-parameters :gender]
                (:gender patient))
      (assoc-in [:query-parameters :insurance]
                (:insurance patient))
      (assoc-in [:query-parameters :address]
                (:address patient))
      (assoc-in [:query-parameters :birthdate]
                (:birthdate patient))
      ))
