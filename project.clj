 (defproject exper-api1 "0.1.0-SNAPSHOT"
   :description "FIXME: write description"
   :dependencies [[org.clojure/clojure "1.8.0"]
                  [metosin/compojure-api "2.0.0-alpha7" ;"1.1.11"
                   ]]
   :ring {:handler exper-api1.handler/app}
   :uberjar-name "server.jar"
   :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]
                                   [cheshire "5.5.0"]
                                   [ring/ring-mock "0.3.0"]
                                   [buddy "2.0.0"]]
                    :plugins [[lein-ring "0.12.0"]]}})
