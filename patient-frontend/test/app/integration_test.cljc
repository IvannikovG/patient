(ns app.integration-test
  (:require
   [re-frame.core :as rf]
   [clojure.test :as t]
   [app.events :as e]
   [app.subscriptions :as s]
   ))

(t/deftest subscriptions-test
  (rf/clear-subscription-cache!)
  (rf/dispatch-sync [:drop-db])

  (def sample-patients-list
    [{:id 1 :full_name "Georgii Ivannikov"
      :address "Moscow" :gender "male"
      :birthdate "1995-11-11"
      :insurance "1234-1234-1234"}
     {:id 2 :full_name "Aleksandra Nishenko"
      :birthdate "2010-01-20"
      :address "Dudweiler" :gender "other"
      :insurance "1237-5678-1234"}
     {:id 3 :full_name "Honey Xaler"
      :birthdate "1999-10-10"
      :address "San Hose" :gender "female"
      :insurance "9002-1234-1234"}
     {:id 3 :full_name "Honey Xaler"
      :birthdate "1999-10-10"
      :address "San Hose" :gender "female"
      :insurance "9002-1234-1234"}
     ])

  (t/is (= (do
             (rf/dispatch-sync [:initialize])
             @(rf/subscribe [:log-database]))
           {:last-event nil
            :errors nil
            :page :about
            :patients-sorter :id}))

  (t/is (= (do
             (rf/dispatch-sync [:change-page :about])
             @(rf/subscribe [:page]))
           :about))

  (t/is (= (do (rf/dispatch-sync
                [:set-current-patient-id 1])
                 @(rf/subscribe [:current-patient-id]))
             1))

  (t/is (= (do (rf/dispatch-sync
                [:last-event "Sample last event"])
                 @(rf/subscribe [:last-event]))
             "Sample last event"))

  (t/is (= (do (rf/dispatch-sync
                [:add-id-query-parameter 11])
                 @(rf/subscribe [:patient-id]))
             11))

  (t/is (= (do (rf/dispatch-sync
                [:add-fullname-query-parameter
                               "Full name X"])
                 @(rf/subscribe [:full_name]))
             "Full name X"))

  (t/is (= (do (rf/dispatch-sync
                [:add-birthdate-query-parameter
                               "Birthdate"])
                 @(rf/subscribe [:birthdate]))
             "Birthdate"))

  (t/is (= (do (rf/dispatch-sync
                [:add-insurance-query-parameter
                               "Insurance"])
                 @(rf/subscribe [:insurance]))
             "Insurance"))
  (t/is (= (do (rf/dispatch-sync
                [:add-address-query-parameter
                               "Address"])
                 @(rf/subscribe [:address]))
             "Address"))

  (t/is (= (do (rf/dispatch-sync
                [:select-gender "female"])
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
             (do
               (rf/dispatch-sync [:drop-db])
               (rf/dispatch-sync
                [:save-patients-into-state
                             sample-patients-list])
               @(rf/subscribe [:patients-list]))))

  (t/is (= sample-patients-list
           (do (rf/dispatch-sync
                [:save-filtered-patients-into-state
                             sample-patients-list])
              @(rf/subscribe [:filtered-patients-list]))))

  (t/is (and (false?
              @(rf/subscribe
                [:filtered-patients-not-searched?]))
             (false?
              @(rf/subscribe
                [:filtered-patients-not-found?]))))

  (t/is (= true @(rf/subscribe [:patients-exist?])))

  (t/is (= (do (rf/dispatch-sync
                [:put-errors-into-state "Errors"])
               @(rf/subscribe [:form-errors]))
           "Errors"))

  ;; LOADERS ;;
(comment "Create patient")
  (let [patient-test-count 3
        _ (do
            (rf/dispatch [:drop-db])
            (rf/dispatch
             [:create-patient
              (first sample-patients-list) {}])
            (rf/dispatch
             [:create-patient
              (second sample-patients-list) {}])
            (rf/dispatch
             [:create-patient
              (last sample-patients-list) {}])
            (Thread/sleep 1000)
            (rf/dispatch-sync [:load-patients-list])
            (Thread/sleep 500))
        ]
        ;; this assumes db is empty this tests to pass
    (t/is (= (count
              (set @(rf/subscribe [:patients-list])))
                  patient-test-count)))

  (comment "Update patient")
  (let [_ (rf/dispatch [:drop-db])
        _ (rf/dispatch [:load-patients-list])
        _ (do (rf/dispatch-sync
               [:add-birthdate-query-parameter
                "1997-07-07"])
              (rf/dispatch-sync
               [:add-fullname-query-parameter
                "Frontend test full name"])
              (rf/dispatch-sync
               [:add-address-query-parameter
                "Frontend Address"])
              (rf/dispatch-sync
               [:add-insurance-query-parameter
                "Frontend Insurance"])
              (rf/dispatch-sync
               [:select-gender "other"]))
        update-query-parameters @(rf/subscribe
                                  [:query-parameters])
        _ (rf/dispatch [:update-patient {}
                             (:id (first @(rf/subscribe [:patients-list])))
                        update-query-parameters])
        _ (Thread/sleep 500)
        _ (rf/dispatch-sync [:drop-db])
        _ (Thread/sleep 500)
        ]
    (let [_ (rf/dispatch-sync [:set-patients-sorter :id])
          _ (rf/dispatch [:load-patients-with-query
                          {:full_name "Frontend test full name"
                           :gender "other"}])
          _ (Thread/sleep 500)
          test-patient (first @(rf/subscribe [:filtered-patients-list]))]
      (t/is (= (:full_name test-patient) "Frontend test full name"))
      (t/is (= (:gender test-patient) "other"))
      (t/is (= ((complement nil?) (:birthdate test-patient))))
      (t/is (= (:address test-patient) "Frontend Address"))
      (t/is(= (:insurance test-patient) "Frontend Insurance"))
      ))

  (comment "Delete patient")
  (let [_ (rf/dispatch [:load-patients-list])
        _ (Thread/sleep 500)
        patients-list @(rf/subscribe [:patients-list])
        patient-id-1 (:id (first patients-list))
        patient-id-2 (:id (second patients-list))
        patient-id-3 (:id (last patients-list))
        _ (do (rf/dispatch [:delete-patient-with-id patient-id-1])
              (rf/dispatch [:delete-patient-with-id patient-id-2])
              (rf/dispatch [:delete-patient-with-id patient-id-3])
              (Thread/sleep 1500))
        _ (rf/dispatch [:load-patients-list])
        _ (Thread/sleep 500)]
    (println patients-list)
    (println patient-id-1 patient-id-2 patient-id-3)
    (t/is (= 0 (count @(rf/subscribe [:patients-list]))))
    )
  )

