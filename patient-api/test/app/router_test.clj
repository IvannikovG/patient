(ns app.router-test
  (:require [app.router :as r]
            [clojure.test :as t]
            [app.handlers :as h]))


(t/deftest uri-matcher-test
  (t/is (= 1 (r/get-id-from-uri "/patients/1/update")))
  (t/is (= (r/uri-matcher "/patients/") :all))
  (t/is (= (r/uri-matcher "/patients/master") :master))
  (t/is (= (r/uri-matcher "/patients/find") :find))
  (t/is (= (r/uri-matcher "/patients/1") :by-id))
  (t/is (= (r/uri-matcher "/patients/1/update") :update))
  (t/is (= (r/uri-matcher "/patients/1/delete") :delete))
  (t/is (= (r/uri-matcher "/patients/random") :default))
  (t/is (= (r/uri-matcher "/patients/1/1") :default))
  (t/is (= (r/uri-matcher "/patients/1f") :default))
  (t/is (= (r/uri-matcher "utter trash") :default))
  )

(t/deftest correct-handler-test
  (t/is (= (r/correct-handler {:uri "/patients" :request-method :get}) h/patients-page))
  (t/is (= (r/correct-handler {:uri "/patients/master" :request-method :post}) h/master-patient-index-page))
  (t/is (= (r/correct-handler {:uri "/patients/find" :request-method :get}) h/search-patients-page))
  (t/is (= (r/correct-handler {:uri "/patients" :request-method :post}) h/save-patient-page))
  (t/is (= (r/correct-handler {:uri "/patients/1" :request-method :get}) h/get-patient-page))
  (t/is (= (r/correct-handler {:uri "/patients/1/update" :request-method :post}) h/update-patient-page))
  (t/is (= (r/correct-handler {:uri "/patients/1/delete" :request-method :delete}) h/delete-patient-page))
  (t/is (= (r/correct-handler {:uri "/patients/random" :request-method :get}) h/page-404))
  (t/is (= (r/correct-handler {:uri "utter trash" :request-method :get}) h/page-404))
  )

(t/run-tests)
