(ns app.tests
  (:require
   [ajax.core :refer [GET POST DELETE] :as ajax]
   [re-frame.core :as rf]
   [clojure.test :as t]
   [clojure.walk :as w]
   [app.subscriptions :as s]
   [app.events :as ev]
   #?(:clj [clojure.test :as t]
      :cljs [cljs.test :as t :include-macros true]
      )
   [clojure.string :as str]))


(comment
  "This is how db looks like"
  (def test-db {:patients-list
                [{:id 1 :full_name "Georgii Ivannikov"
                  :address "Moscow" :gender "male"
                  :birthdate "1995-11-11"
                  :insurance "1234-1234-1234"}
                 {:id 2 :full_name "Aleksandra Nishenko"
                  :birthdate "1999-10-10"
                  :address "Dudweiler" :gender "female"
                  :insurance "1237-1234-1234"}
                 {:id 3 :full_name "Ivana Grishkaeva"
                  :birthdate "1980-01-01"
                  :address "Novisibirsk" :gender "other"
                  :insurance "1234-1234-1237"}]
                :filtered-patients-list
                [{:id 2 :full_name "Aleksandra Nishenko"
                  :address "Dudweiler" :gender "female"
                  :insurance "1237-1234-1234"}]
                :query-parameters {:full_name "Sample name"
                                   :gender "female"
                                   :address "Sample address"
                                   :insurance "Sample insurance"
                                   :birthdate "1990-05-01"
                                   }
                :current-patient-id 1
                :page ""
                :filtered-patients-exists? true
                :form-errors ""
                :last-event ""}))

(comment "Test pure events")

(t/testing

    (def sample-patients-list
      [{:id 1 :full_name "Georgii Ivannikov"
        :address "Moscow" :gender "male"
        :birthdate "1995-11-11"
        :insurance "1234-1234-1234"}
       {:id 2 :full_name "Aleksandra Nishenko"
        :birthdate "1999-10-10"
        :address "Dudweiler" :gender "female"
        :insurance "1237-1234-1234"}])

    (t/is (= (do (rf/dispatch [:set-current-patient-id 1])
               @(rf/subscribe [:current-patient-id]))
             1))

  (t/is (= (do (rf/dispatch [:last-event "Sample last event"])
               @(rf/subscribe [:last-event]))
           "Sample last event"))

  (t/is (= (do (rf/dispatch [:add-id-query-parameter 11])
               @(rf/subscribe [:patient-id]))
           11))

  (t/is (= (do (rf/dispatch [:add-fullname-query-parameter
                             "Full name X"])
               @(rf/subscribe [:full_name]))
           "Full name X"))

  (t/is (= (do (rf/dispatch [:add-birthdate-query-parameter
                             "Birthdate"])
               @(rf/subscribe [:birthdate]))
           "Birthdate"))

  (t/is (= (do (rf/dispatch [:add-insurance-query-parameter
                             "Insurance"])
               @(rf/subscribe [:insurance]))
           "Insurance"))

  (t/is (= (do (rf/dispatch [:add-address-query-parameter
                             "Address"])
               @(rf/subscribe [:address]))
           "Address"))

  (t/is (= (do (rf/dispatch [:select-gender "female"])
               @(rf/subscribe [:gender]))
           "female"))

  (t/is (= @(rf/subscribe [:query-parameters])
           {:patient-id 11
            :full_name "Full name X"
            :address "Address"
            :birthdate "Birthdate"
            :insurance "Insurance"
            :gender "female"}))

  (t/is (= sample-patients-list
           (do (rf/dispatch [:save-patients-into-state
                             sample-patients-list])
               @(rf/subscribe [:patients-list]))))

  (t/is (= sample-patients-list
           (do (rf/dispatch [:save-filtered-patients-into-state
                             sample-patients-list])
               @(rf/subscribe [:filtered-patients-list]))))

  (t/is (= true @(rf/subscribe [:filtered-patients-exist?])))

  (t/is (= true @(rf/subscribe [:patients-exist?])))

  (t/is (= (do (rf/dispatch [:put-errors-into-state "Errors"])
               @(rf/subscribe [:form-errors]))
           "Errors"))
  (t/is (and
         (= false (empty?
                   (do (rf/dispatch [:load-patients-list])
                       @(rf/subscribe [:patients-list]))))
         (= "Loaded all patients"
            @(rf/subscribe [:last-event]))))
  (let [count-without-one-deletion
        (count @(rf/subscribe
                 [:patients-list]))
        count-with-one-deletion
        (do
          (rf/dispatch
           [:delete-patient-from-state 1])
                                  (count
                                   @(rf/subscribe
                                     [:patients-list])))]
    (t/is (= 1 (- count-without-one-deletion
                  count-with-one-deletion))))

  (let [sample-pat (first sample-patients-list)]
    (do
      (rf/dispatch [:save-patient-into-state nil])
      (rf/dispatch [:create-patient sample-pat nil])
      (rf/dispatch [:load-patients-list])
      (println @(rf/subscribe [:patients-list])))
    )
  )

