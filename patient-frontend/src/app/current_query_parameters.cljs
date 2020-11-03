(ns app.current-query-parameters
  (:require [re-frame.core :as rf]))



(defn current-query-parameters []
  {:fullname @(rf/subscribe [:fullname])
   :gender @(rf/subscribe [:gender])
   :birthdate @(rf/subscribe [:birthdate])
   :address @(rf/subscribe [:address])
   :insurance @(rf/subscribe [:insurance])
   })
