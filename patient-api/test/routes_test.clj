(ns routes-test
  (:require  [clojure.test :refer :all]
             [ring.mock.request :as mock]
             [app.router :as router ]))

(deftest get-patient-page-test
  (is (= (router/app (mock/request :get "/patients/5"))
         {:status 200
          :headers {"content-type" "application/json"}
          :body {:id 5
                 :fullname "Angel Angel Smith"
                 :gender "other"
                 :birthdate "2019-11-9"
                 :address "j2wj9YhBCx"
                 :insurance "Qzn3ewcxpc"
                 :created "202010300"}})))


(deftest find-patient-page-test
  (is (= (router/app
          (mock/request :get
                        "/patients/find?gender=female"))
         {:status 200
          :headers {"content-type" "application/json"}
          :body {:id 2
                 :fullname "Alexis Texas"
                 :gender "female"
                 :birthdate "1990-11-11"
                 :address "Moscow, 29th Street"
                 :insurance "1234123412341234"
                 :created "202010300"}})))


(deftest failing-page
  (is (= (router/app (mock/request :get
                                   "/random-string"))
         {:status 404
          :body "Requested URI: /random-string <- 404"})))
