(ns app.test-runner
  (:require  [cljs.test :refer-macros [run-tests]]
             [figwheel.main.async-result :as async-result]
             [app.integration-test]))


(defmethod cljs.test/report [:cljs.test/default :end-run-tests] [m]
  (if (cljs.test/successful? m)
    (async-result/send "Tests passed!")
    (println "FAIL")))


(defn -main [& args]
  (run-tests 'app.integration-test)
  [:figwheel.main.async-result/wait 25000]
 )



