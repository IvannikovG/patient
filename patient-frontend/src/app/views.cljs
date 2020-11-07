(ns app.views
  (:require [reagent.dom :as dom]
            [secretary.core :as secretary :refer-macros [defroute ]]
            [reagent.core :as r]
            [reagent.dom :as rdom]
            [re-frame.core :as rf]
            [goog.string :as gstring]
            [goog.events :as e]
            [goog.history.EventType :as EventType]
            [app.datepicker :as dp]
            [app.events :as events]
            [app.subscriptions :as subscriptions]
            [app.components :as components]
            [reagent.session :as session]
            )
  (:import goog.history.Html5History)
  )


(enable-console-print!)


(defn hook-browser-navigation! []
  (doto (Html5History.)
    (e/listen
     EventType/NAVIGATE
     (fn [event]
       (secretary/dispatch! (.-token event))
       ))
    (.setEnabled true)))


(def current-page-state (r/atom {}))
;; (def app-state (r/atom {}))


;; (defn app-routes []
;;   (secretary/set-config! :prefix "#")

;;   (defroute "/" []
;;     (swap! app-state assoc :page :home))

;;   (defroute "/about" []
;;     (swap! app-state assoc :page :about))

;;   (hook-browser-navigation!))


(defn app-routes []
  (secretary/set-config! :prefix "#")
  (defroute "/" []
    (swap! current-page-state assoc :page :home)
    )
  (defroute "/about" []
    (swap! current-page-state assoc :page :about)
    )
  (defroute "/create" []
    (swap! current-page-state assoc :page :create)
    )
  (defroute "/patients" []
    (swap! current-page-state assoc :page :patients)
    )
  (defroute "/find" []
    (swap! current-page-state assoc :page :find)
    )
  (defroute "/debug" []
    (swap! current-page-state assoc :page :debug)
  )
  (hook-browser-navigation!))


;; (defn home []
;;   [:div [:h1 "Home Page"]
;;    [:a {:href "#/about"} "about page"]])

;; (defn about []
;;   [:div [:h1 "About Page"]
;;    [:a {:href "#/"} "home page"]])


(defn home []
  [:div
   [components/navigation]])

(defn about []
  [:div [:h1 "About Patient CRUD"]
   [:a {:href "#/"} "home page"]
   [:div
    [:h3 "By Georgii Ivannikov"]
    [:h3 "For Health Samurai"]]])


(defn create-patient-page []
  [:div
   [:div "Last event: "@(rf/subscribe [:last-event])]
   [components/navigation]
   [:h1 "Create Patient via form"]
   [components/query-form]
   [components/save-patient-button]])

(defn all-patients []
  [:div
   [:div "Last event: "@(rf/subscribe [:last-event])]
   [components/navigation]
   (rf/dispatch [:load-all-patients])
   [components/patient-list
    @(rf/subscribe [:patients-list]) "All found patients"]])

(defn find-patient-page []
  [:div
   [:div "Last event: "@(rf/subscribe [:last-event])]
   [components/navigation]
   [:h1 "Find patients by query"]
   [components/errors-list]
   [components/query-form]
   [components/load-filtered-patients-button]
   [components/patient-list
    @(rf/subscribe [:filtered-patients-list]) "Found this patients"]])

(defmulti current-page #(@current-page-state :page))
(defmethod current-page :home []
  ;(println @(rf/subscribe [:log-database]))
  [home])
(defmethod current-page :about []
  [about])
(defmethod current-page :create []
  [create-patient-page])
(defmethod current-page :patients []
  [all-patients])
(defmethod current-page :find []
  [find-patient-page])
(defmethod current-page :debug []
  [components/ui])
(defmethod current-page :default []
  [:div ])

;; (defmulti current-page #(@app-state :page))
;; (defmethod current-page :home []
;;   [home])
;; (defmethod current-page :about []
;;   [about])
;; (defmethod current-page :default []
;;   [:div ])

(def application
  (js/document.getElementById "app"))

(defn render
  [page]
  (rdom/render [page] application))

(defn ^:export main
  []
  (app-routes)
  (rf/dispatch-sync [:initialize])
  (render
   current-page)
  )

(main)
