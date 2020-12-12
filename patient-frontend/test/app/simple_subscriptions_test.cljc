(ns app.simple-subscriptions-test
  (:require
   [re-frame.core :as rf]
   [clojure.test :as t]
   [app.events :as e]
   [app.subscriptions :as s]
   ))


(t/deftest subscriptions-test
  (rf/clear-subscription-cache!)
  (rf/dispatch [:drop-db])

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
  (comment "Test initialize")

  (t/is (= (do
             (rf/dispatch [:initialize])
             (Thread/sleep 50)
             @(rf/subscribe [:log-database]))
           {:last-event nil
            :errors nil
            :page :about
            :patients-sorter :id}))
  (comment "Test change page")

  (t/is (= (do
             (rf/dispatch [:change-page :about])
             (Thread/sleep 50)
             @(rf/subscribe [:page]))
           :about))

    (t/is (= (do (rf/dispatch [:set-current-patient-id 1])
                 (Thread/sleep 100)
                 @(rf/subscribe [:current-patient-id]))
             1))

    (t/is (= (do (rf/dispatch [:last-event "Sample last event"])
                 (Thread/sleep 100)
                 @(rf/subscribe [:last-event]))
             "Sample last event"))

    (t/is (= (do (rf/dispatch [:add-id-query-parameter 11])
                 (Thread/sleep 100)
                 @(rf/subscribe [:patient-id]))
             11))

    (t/is (= (do (rf/dispatch [:add-fullname-query-parameter
                               "Full name X"])
                 (Thread/sleep 100)
                 @(rf/subscribe [:full_name]))
             "Full name X"))

    (t/is (= (do (rf/dispatch [:add-birthdate-query-parameter
                               "Birthdate"])
                 (Thread/sleep 100)
                 @(rf/subscribe [:birthdate]))
             "Birthdate"))

    (t/is (= (do (rf/dispatch [:add-insurance-query-parameter
                               "Insurance"])
                 (Thread/sleep 100)
                 @(rf/subscribe [:insurance]))
             "Insurance"))
    (t/is (= (do (rf/dispatch [:add-address-query-parameter
                               "Address"])
                 (Thread/sleep 100)
                 @(rf/subscribe [:address]))
             "Address"))

    (t/is (= (do (rf/dispatch [:select-gender "female"])
                 (Thread/sleep 100)
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
               (rf/dispatch [:drop-db])
               (Thread/sleep 100)
               (rf/dispatch [:save-patients-into-state
                             sample-patients-list])
               (Thread/sleep 100)
               @(rf/subscribe [:patients-list]))))

  (t/is (= sample-patients-list
           (do (rf/dispatch [:save-filtered-patients-into-state
                             sample-patients-list])
               (Thread/sleep 100)
              @(rf/subscribe [:filtered-patients-list]))))

  (t/is (and (false? @(rf/subscribe [:filtered-patients-not-searched?]))
             (false? @(rf/subscribe [:filtered-patients-not-found?]))))

  (t/is (= true @(rf/subscribe [:patients-exist?])))

  (t/is (= (do (rf/dispatch [:put-errors-into-state "Errors"])
               (Thread/sleep 100)
               @(rf/subscribe [:form-errors]))
           "Errors"))
  ;; LOADERS ;;
(comment "Create patient")
  (let [patient-test-count 3
        _ (rf/dispatch [:drop-db])
        _ (Thread/sleep 205)
        _ (do (rf/dispatch [:create-patient (first sample-patients-list) {}])
              (rf/dispatch [:create-patient (second sample-patients-list) {}])
              (rf/dispatch [:create-patient (last sample-patients-list) {}]))
        _ (Thread/sleep 500)
        ;; this assumes db is empty this tests to pass
        _ (rf/dispatch [:load-patients-list])
        _ (Thread/sleep 100)]
    (t/is (and (= (count (set @(rf/subscribe [:patients-list])))
                  patient-test-count))))

  (comment "Update patient")
  (let [_ (rf/dispatch [:drop-db])
        _ (Thread/sleep 100)
        _ (rf/dispatch [:load-patients-list])
        _ (do (rf/dispatch [:add-birthdate-query-parameter
                            "1997-07-07"])
              (rf/dispatch [:add-fullname-query-parameter
                            "Frontend test full name"])
              (rf/dispatch [:add-address-query-parameter
                            "Frontend Address"])
              (rf/dispatch [:add-insurance-query-parameter
                            "Frontend Insurance"])
              (rf/dispatch [:select-gender
                            "other"]))
        _ (Thread/sleep 100)
        update-query-parameters @(rf/subscribe [:query-parameters])
        _ (Thread/sleep 150)
        _ (rf/dispatch [:update-patient {}
                        (:id (first @(rf/subscribe [:patients-list])))
                        update-query-parameters])
        _ (Thread/sleep 150)
        _ (rf/dispatch [:drop-db])
        _ (Thread/sleep 150)
        ]
    (let [_ (rf/dispatch [:set-patients-sorter :id])
          _ (Thread/sleep 100)
          _ (rf/dispatch [:load-patients-with-query
                          {:full_name "Frontend test full name"
                           :gender "other"}])
          _ (Thread/sleep 300)
          test-patient (first @(rf/subscribe [:filtered-patients-list]))]
      (t/is (= (:full_name test-patient) "Frontend test full name"))
      (t/is (= (:gender test-patient) "other"))
      (t/is (= ((complement nil?) (:birthdate test-patient))))
      (t/is (= (:address test-patient) "Frontend Address"))
      (t/is(= (:insurance test-patient) "Frontend Insurance"))
      ))

  (comment "Delete patient")
  (let [_ (rf/dispatch [:load-patients-list])
        _ (Thread/sleep 100)
        patients-list @(rf/subscribe [:patients-list])
        patient-id-1 (:id (first patients-list))
        patient-id-2 (:id (second patients-list))
        patient-id-3 (:id (last patients-list))
        _ (do (rf/dispatch [:delete-patient-with-id patient-id-1])
              (rf/dispatch [:delete-patient-with-id patient-id-2])
              (rf/dispatch [:delete-patient-with-id patient-id-3]))
        _ (Thread/sleep 1000)
        _ (rf/dispatch [:load-patients-list])
        _ (Thread/sleep 150)]
    (println patients-list)
    (println patient-id-1 patient-id-2 patient-id-3)
    (t/is (= 0 (count @(rf/subscribe [:patients-list]))))
    )
  )

(t/run-tests)
