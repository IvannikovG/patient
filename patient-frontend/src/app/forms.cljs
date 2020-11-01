(ns app.forms
  (:require [reagent.core :as r]
            [reagent.dom :as rd]
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
      (.toLocaleDateString js-date "en" "%d-%b-%Y")
      "unselected")))

(defn home-page []
  [:div [:h2 "Select a birthdate: "]
    [:div
      [:label "Birthdate"]
      [pikaday/date-selector
        {:date-atom birthdate 
         :pikaday-attrs {:max-date today}
         :input-attrs {:id "birthdate"}}]]
    [:div
     [:p "You selected date: " (get-birthdate!)]
     [:p [:button {:on-click #(set-date! today)} "Start today"]]
     [:p [:button {:on-click #(do (set-date! nil))} "Unset birthdate"]]]])

(defn test-birthdate []
  [:div
   [:div (str @birthdate)]])

;; -------------------------
;; Initialize app
(defn mount-root []
  (rd/render [:div [home-page]
                   [test-birthdate]]
             (.getElementById js/document "app")))

(defn init! []
  (mount-root))
