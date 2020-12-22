(ns app.integration-test
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


(t/deftest requests-test-create-update-find
  (doseq []
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
                            (t/is (= (:full_name
                                      (first (filter #(= (:id %) 1)
                                                     @patients))
                                      "Gachi Muchi")))))))))))

(t/deftest requests-test-delete
  (rf/dispatch-sync [:drop-db])
  (rf-test/run-test-async
   (let [patients (rf/subscribe [:patients-list])]
     (rf/dispatch [:delete-patient-with-id 1])
     (rf-test/wait-for
      [:delete-patient-with-id]
      (rf/dispatch [:load-all-patients])
      (rf-test/wait-for
       [:load-all-patients]
       (t/is (= nil (first (filter #(= (:id %) 1) @patients))))))))
  )

(t/run-tests)
