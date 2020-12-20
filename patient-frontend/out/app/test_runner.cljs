(ns app.test-runner
  (:require  [cljs.test :refer-macros [run-tests]]
             [figwheel.main.async-result :as async-result]
             [app.new-integration-test]))

;; (defmethod report [:cljs.test/default :end-run-tests] [test-data]
;;   (if (cljs.test/successful? test-data)
;;     (async-result/send "Tests passed!")
;;     (async-result/send "Tests Failed")))


(defmethod cljs.test/report [:cljs.test/default :end-run-tests] [m]
  (if (cljs.test/successful? m)
    (async-result/send "Tests passed!")
    (println "FAIL")))


(defn -main [& args]
  (run-tests 'app.new-integration-test)
 )



