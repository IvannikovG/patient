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

(defn app-routes []
  (secretary/set-config! :prefix "#")
  (defroute "/" []
    (rf/dispatch [:change-page :about])
    )
  (defroute "/about" []
    (rf/dispatch [:change-page :about])
    )
  (defroute "/create" []
    (rf/dispatch [:change-page :create])
    )
  (defroute "/patients" []
    (rf/dispatch [:change-page :patients])
    )
  (defroute "/find" []
    (rf/dispatch [:change-page :find])
    )
  (defroute "/update/:id" {:as params}
    (rf/dispatch [:change-page :update])
    (rf/dispatch [:add-id-query-parameter (:id params)])
    (rf/dispatch [:add-all-query-parameters (:id params)])
    )
  (defroute "/master" []
    (rf/dispatch [:change-page :master])
    )
  (hook-browser-navigation!))



(defmulti current-page (fn [] @(rf/subscribe [:page])))
(defmethod current-page :home []
  [pages/about])
(defmethod current-page :about []
  [pages/about])
(defmethod current-page :create []
  [pages/create-patient-page])
(defmethod current-page :patients []
  [pages/all-patients-page])
(defmethod current-page :find []
  [pages/find-patient-page])
(defmethod current-page :update [id]
  [pages/update-patient-page id])
(defmethod current-page :master []
  [pages/master-patient-index-page])
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
