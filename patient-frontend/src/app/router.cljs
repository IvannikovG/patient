(ns app.router
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
            [app.pages :as pages]
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
  (defroute "/update-patient" []
    (swap! current-page-state assoc :page :update-patient))
  (defroute "/update/:id" {:as params}
    (swap! current-page-state assoc :page :update)
    (rf/dispatch [:add-id-query-parameter (:id params)])
    )
  (defroute "/debug" []
    (swap! current-page-state assoc :page :debug)
  )
  (hook-browser-navigation!))



(defmulti current-page #(@current-page-state :page))
(defmethod current-page :home []
  ;(println @(rf/subscribe [:log-database]))
  [pages/home])
(defmethod current-page :about []
  [pages/about])
(defmethod current-page :create []
  [pages/create-patient-page])
(defmethod current-page :patients []
  [pages/all-patients])
(defmethod current-page :find []
  [pages/find-patient-page])
(defmethod current-page :update-patient []
  [pages/update-patient-with-id-page])
(defmethod current-page :update [id]
  [pages/update-patient-page id])
(defmethod current-page :debug []
  [components/ui])
(defmethod current-page :default []
  [:div ])

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
