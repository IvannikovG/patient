(ns app.routes-test
  (:require  [clojure.test :refer :all]
             [ring.mock.request :as mock]
             [app.router :as router]
             [app.handlers :as handlers]
             [cheshire.core :refer [generate-string]]))

(deftest get-patient-page-test
  (is (= (router/app (mock/request :get "/patients/200"))
         {:status 200
          :headers {"content-type" "application/json"}
          :body {:id 200
                 :fullname "Test Patient"
                 :gender "female"
                 :birthdate "10/1/2000"
                 :address "Test Patient"
                 :insurance "Test Patient"
                 :created "202011310"}})))


(deftest find-patient-page-test
  (is (= (router/app
          (mock/request
                  :get
                  "/patients/find?gender=female&fullname=Test%20Patient"))
         {:status 200
          :headers {"content-type" "application-json"}
          :body  [{:id 200
                  :fullname "Test Patient"
                  :gender "female"
                  :birthdate "10/1/2000"
                  :address "Test Patient"
                  :insurance "Test Patient"
                  :created "202011310"}]})))

(deftest save-patient
  (is (= (:status (-> (handlers/save-patient-page (mock/request :post "/patients"))
               (mock/json-body {:fullname "Create-save-test"
                                :gender "female"
                                :birthdate "1000-10-10"
                                :address "Create-save-test"
                                :insurance "Create-save-test"})))
         200)))

(deftest failing-page
  (is (= (router/app (mock/request :get
                                   "/random-string"))
         {:status 404
          :body "Requested URI: /random-string <- 404"})))

