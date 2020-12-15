(ns app.specs)

(def db-spec {:dbtype "postgresql"
              :dbname "test_patient"
              :host "db"
              :port 5432
              :user "georgii"
              :password "blank"})

(def db-spec-testing {:dbtype "postgresql"
                      :dbname "test_patient"
                      :host "localhost"
                      :port 5432
                      :user "georgii"
                      :password "blank"})
(comment
  #_(def db-spec
      "postgresql://georgii:blank@db:5432/test_patient"))
