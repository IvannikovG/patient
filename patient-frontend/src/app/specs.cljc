(ns app.specs
  (:require [clojure.spec.alpha :as s]))

(s/def ::full_name (s/and string? #(>= (count %) 10)))
(s/def ::birthdate inst?)
(s/def ::insurance (s/and string? #(>= (count %) 10)))
(s/def ::gender (comp #{"male" "female" "other"}))
(s/def ::address (s/and string? #(>= (count %) 15)))

(s/def :unq/patient
  (s/keys :req-un [::full_name
                   ::address ::birthdate
                   ::gender ::insurance]))


(defn validate-full-name [full_name]
  (s/valid? ::full_name full_name))
(defn validate-birthdate [birthdate]
  (s/valid? ::birthdate birthdate))
(defn validate-gender [gender]
  (s/valid? ::gender gender))
(defn validate-insurance [insurance]
  (s/valid? ::insurance insurance))
(defn validate-address [address]
  (s/valid? ::address address))
(defn validate-patient [patient]
  (s/valid? :unq/patient patient))

