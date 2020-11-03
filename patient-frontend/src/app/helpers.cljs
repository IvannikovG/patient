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


(defn map-subset? [a-map b-map]
  (every? (fn [[k _ :as entry]] (= entry (find b-map k))) a-map))

(defn filter-by [config collection]
  (let [conf (remove-nils-and-empty-strings config)]
    (filter (fn [el] (map-subset? (lower-cased-values conf)
                                  (lower-cased-values el))) collection)))



(defn find-empty-keywords [record]
  (map first (filter (fn [el] (empty-value?
                    (second el))) record)))


