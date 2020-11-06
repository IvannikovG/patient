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


(deftest update-patient-handler-test
  (let [request {:body {:gender "male"}
                 :params {:id "200"}}]
    (is (= (handlers/update-patient-page request)
           {:status 200
            :headers {}
            :body {:patient
                   {:id 200
                   :fullname "Test Patient"
                   :gender "male"
                   :birthdate "10/1/2000"
                   :address "Test Patient"
                   :insurance "Test Patient"
                   :created "202011310"}}}))))


(deftest update-patient-handler-test-2
  (let [request {:body {:gender "female"}
                 :params {:id "200"}}]
    (is (= (handlers/update-patient-page request)
           {:status 200
            :headers {}
            :body {:patient
                   {:id 200
                    :fullname "Test Patient"
                    :gender "female"
                    :birthdate "10/1/2000"
                    :address "Test Patient"
                    :insurance "Test Patient"
                    :created "202011310"}}}))))


(deftest save-patient-handler-test
  (let [request {:body {:fullname "Angel"
                        :gender "female"
                        :birthdate "1010/10/10"
                        :address "Address"
                        :insurance "Angel"}}]
    (is (= (handlers/save-patient-page request)
           {:status 200
            :headers {"content-type" "application/json"}
            :body {:patient "Saved patient with name: Angel"}}))))

;; (deftest delete-patient-page
;;   (let [request {:params {:id ""}}]))

(deftest failing-page
  (is (= (router/app (mock/request :get
                                   "/random-string"))
         {:status 404
          :body "Requested URI: /random-string <- 404"})))

