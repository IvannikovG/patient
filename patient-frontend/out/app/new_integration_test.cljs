(ns app.new-integration-test
  (:require
   [cljs.test :refer-macros [deftest is]]
   [day8.re-frame.test :as rf-test]
   [re-frame.core :as rf]
   app.events
   app.subscriptions))



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


(deftest initialize
  (rf-test/run-test-sync
   (rf/dispatch [:initialize])
   (let [last-event (rf/subscribe [:last-event])
         errors (rf/subscribe [:form-errors])
         page (rf/subscribe [:page])
         patients-sorter (rf/subscribe [:patients-sorter])]
     (is (= @last-event nil))
     (is (= @errors nil))
     (is (= @page :about))
     (is (= @patients-sorter :id)))))


(deftest pure-events
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
     (is (= @page :master))
     (is (= @current-patient-id 1))
     (is (= @last-event "Last event"))
     (is (= @id 2))
     (is (= @full_name "Full name"))
     (is (= @insurance "Insurance"))
     (is (= @address "Address"))
     (is (= @gender "Gender"))
     (is (= @birthdate "Birthdate"))
     )))


(deftest patient-state
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
     (is (= @patients-list
              [{:id 2, :full_name "Avdey",
                :birthdate "1990-11-11",
                :insurance "Insurance",
                :gender "other", :address "Address"}
               {:id 3, :full_name "Andrew",
                :birthdate "1990-11-11",
                :insurance "Insurance",
                :gender "other", :address "Address"}]))
     (is (= @filtered-patients-list
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


(deftest requests-test-create-update-find
  (rf-test/run-test-async
   (let [patients (rf/subscribe [:patients-list])]
     (rf/dispatch [:create-patient
                   (first sample-patients-list) {}])
     (rf-test/wait-for [:create-patient]
                       (rf/dispatch [:create-patient
                                     (second sample-patients-list)
                                     {}])
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
                          (is (= (:full_name
                                  (first (filter #(= (:id %) 1)
                                                 @patients))
                                  "Gachi Muchi")))))))))
  )


(deftest requests-test-delete
  (rf/dispatch-sync [:drop-db])
  (rf-test/run-test-async
   (let [patients (rf/subscribe [:patients-list])]
     (rf/dispatch [:delete-patient-with-id 1])
     (rf-test/wait-for
      [:delete-patient-with-id]
      (rf/dispatch [:load-all-patients])
      (rf-test/wait-for
       [:load-all-patients]
       (is (= nil (first (filter #(= (:id %) 1) @patients))))))))
  )
