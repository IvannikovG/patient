(ns app.re-frame-new
  (:require
   [clojure.test :as t]
   [day8.re-frame.test :as rf-test]
   [re-frame.core :as rf]
   [app.events :as e]
   [app.subscriptions :as s]))


(def sample-patients-list
  [{:id 1 :full_name "Georgii Ivannikov"
    :address "Moscow" :gender "male"
    :birthdate "1995-11-11"
    :insurance "1234-1234-1234"}
   {:id 2 :full_name "Aleksandra Nishenko"
    :birthdate "2010-01-20"
    :address "Dudweiler" :gender "male"
    :insurance "1237-5678-1234"}
   {:id 3 :full_name "Honey Xaler"
    :birthdate "1999-10-10"
    :address "San Hose" :gender "male"
    :insurance "9002-1234-1234"}
   {:id 3 :full_name "Honey Xaler"
    :birthdate "1999-10-10"
    :address "San Hose" :gender "male"
    :insurance "9002-1234-1234"}
   ])

(defn test-fixtures []
  (rf/dispatch [:drop-db]))

(t/deftest initialize
  (rf-test/run-test-sync
   (test-fixtures)
   (rf/dispatch [:initialize])
   (let [last-event (rf/subscribe [:last-event])
         errors (rf/subscribe [:form-errors])
         page (rf/subscribe [:page])
         patients-sorter (rf/subscribe [:patients-sorter])]
     (t/is (= @last-event nil))
     (t/is (= @errors nil))
     (t/is (= @page :about))
     (t/is (= @patients-sorter :id)))))


(t/deftest pure-events
  (rf-test/run-test-sync
   (rf/dispatch [:drop-db])
   (rf/dispatch [:change-page :master])
   (rf/dispatch [:set-current-patient-id 1])
   (rf/dispatch [:last-event "Last event"])
   (rf/dispatch [:add-id-query-parameter 2])
   (rf/dispatch [:add-fullname-query-parameter "Full name"])
   (rf/dispatch [:add-birthdate-query-parameter "Birthdate"])
   (rf/dispatch [:add-address-query-parameter "Address"])
   (rf/dispatch [:add-insurance-query-parameter "Insurance"])
   (rf/dispatch [:select-gender "Gender"])
   (rf/dispatch [:put-errors-into-state "Errors"])
   (let [page (rf/subscribe [:page])
         current-patient-id (rf/subscribe [:current-patient-id])
         last-event (rf/subscribe [:last-event])
         id (rf/subscribe [:patient-id])
         full_name (rf/subscribe [:full_name])
         insurance (rf/subscribe [:insurance])
         address (rf/subscribe [:address])
         gender (rf/subscribe [:gender])
         birthdate (rf/subscribe [:birthdate])]
     (t/is (= @page :master))
     (t/is (= @current-patient-id 1))
     (t/is (= @last-event "Last event"))
     (t/is (= @id 2))
     (t/is (= @full_name "Full name"))
     (t/is (= @insurance "Insurance"))
     (t/is (= @address "Address"))
     (t/is (= @gender "Gender"))
     (t/is (= @birthdate "Birthdate"))
     )))

(t/deftest patient-state
  (rf-test/run-test-sync
   (rf/dispatch [:drop-db])
   (rf/dispatch [:add-one-patient-into-state
                 {:id 1 :full_name "Alex"
                  :birthdate "1990-11-11"
                  :insurance "Insurance"
                  :gender "other"
                  :address "Address"}])
   (rf/dispatch [:save-patients-into-state
                 [{:id 2 :full_name "Avdey"
                   :birthdate "1990-11-11"
                   :insurance "Insurance"
                   :gender "other"
                   :address "Address"}
                  {:id 3 :full_name "Andrew"
                   :birthdate "1990-11-11"
                   :insurance "Insurance"
                   :gender "other"
                   :address "Address"}]])
   (rf/dispatch [:save-filtered-patients-into-state
                 [{:id 2 :full_name "Avdey"
                   :birthdate "1990-11-11"
                   :insurance "Insurance"
                   :gender "other"
                   :address "Address"}
                  {:id 3 :full_name "Andrew"
                   :birthdate "1990-11-11"
                   :insurance "Insurance"
                   :gender "other"
                   :address "Address"}]])
   (rf/dispatch [:delete-patient-from-state 1])
   (rf/dispatch [:update-patient-into-state 2 {:full_name "Vith"}])
   (let [patients-list (rf/subscribe [:patients-list])
         filtered-patients-list (rf/subscribe [:filtered-patients-list])
         last-event (rf/subscribe [:last-event])]
     (t/is (= @patients-list
              [{:id 2, :full_name "Avdey",
                :birthdate "1990-11-11",
                :insurance "Insurance",
                :gender "other", :address "Address"}
               {:id 3, :full_name "Andrew",
                :birthdate "1990-11-11",
                :insurance "Insurance",
                :gender "other", :address "Address"}]))
     (t/is (= @filtered-patients-list
              [{:id 2, :full_name "Avdey",
                :birthdate "1990-11-11",
                :insurance "Insurance",
                :gender "other", :address "Address"}
               {:id 3, :full_name "Andrew",
                :birthdate "1990-11-11",
                :insurance "Insurance",
                :gender "other", :address "Address"}])))
   )
  )


(t/deftest requests-test-create-update
 (rf-test/run-test-async
  (let [patients (rf/subscribe [:patients-list])
        filtered-patients (rf/subscribe [:filtered-patients-list])]
     (rf/dispatch [:create-patient
                   (first sample-patients-list) {}])
     (rf-test/wait-for [:create-patient]
                       (rf/dispatch [:create-patient
                                     (second sample-patients-list) {}])
                       (rf-test/wait-for
                        [:create-patient]
                        (rf/dispatch
                         [:update-patient {} 1
                          {:full_name "Gachi Muchi"}])
                        (rf-test/wait-for
                         [:update-patient]
                         (rf/dispatch [:load-patients-list])
                         (rf-test/wait-for
                          [:load-patients-list]
                          (t/is (= (:full_name
                                    (first (filter #(= (:id %) 1)
                                                   @patients))
                                    "Gachi Muchi")))))))))
  )


(t/run-tests)
