(ns app.datepicker
  (:require [reagent.core :as r]
            [reagent.dom :as rd]
            [re-frame.core :as rf]
            [cljs-pikaday.reagent :as pikaday]))



(def today (js/Date.))
(defonce birthdate (r/atom today))

(defn date? [x]
  (= (type x) js/Date))

(defn set-date! [date]
  (reset! birthdate date))

(defn get-birthdate! []
  (let [js-date @birthdate]
    (if (date? js-date)
      (.toLocaleDateString js-date "ru" "%d-%b-%Y")
      "unselected")))

(defn dispatch-birthdate []
  (rf/dispatch [:add-birthdate-query-parameter
                (get-birthdate!)]))

(defn datepicker-component []
  [:div
    [:div
      [:label {:for "birthdate"} "Birthdate"]
      [pikaday/date-selector
        {:date-atom birthdate
         :pikaday-attrs {:max-date today}
         :input-attrs {:id "birthdate"}
         :on-click (dispatch-birthdate)}]]
    [:div
     [:p "You selected date: " (get-birthdate!)]
     [:p [:button {:on-click #(do (set-date! today)
                                  (dispatch-birthdate))}
          "Set today"]]
     [:p [:button {:on-click #(do (set-date! nil)
                                  (dispatch-birthdate))}
          "Unset birthdate"]]]])


