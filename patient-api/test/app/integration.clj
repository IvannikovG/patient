(ns app.integration
  (:require  [clojure.test :as t]
             [app.newest-tests :as nt]
             [re-frame.core :as rf]))

(comment
  (rf/reg-fx
   :send-request
   (fn [_]
     (app/handlers ({:full_name "Alex"
                     :birthdate "1990-11-11"
                     :gender "female"
                     :insurance "Some insurance"
                     :address "Addresse"}))))

  (rf/dispatch [:send-request "http://localhost:7500/patients"]))
