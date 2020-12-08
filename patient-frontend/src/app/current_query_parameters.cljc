(ns app.current-query-parameters
  (:require [re-frame.core :as rf]))



(defn current-query-parameters []
  {:full_name @(rf/subscribe [:full_name])
   :gender @(rf/subscribe [:gender])
   :birthdate @(rf/subscribe [:birthdate])
   :address @(rf/subscribe [:address])
   :insurance @(rf/subscribe [:insurance])
   })
