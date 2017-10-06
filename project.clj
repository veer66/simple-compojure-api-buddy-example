 (defproject exper-api1 "simple-compojure-api-buddy-example"
   :description "Simple Compojure-api with Buddy Example"
   :dependencies [[org.clojure/clojure "1.8.0"]
                  [metosin/compojure-api "2.0.0-alpha7"]]
   :ring {:handler simple-compojure-api-buddy-example.handler/app}
   :uberjar-name "server.jar"
   :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]
                                   [cheshire "5.5.0"]
                                   [ring/ring-mock "0.3.0"]
                                   [buddy "2.0.0"]]
                    :plugins [[lein-ring "0.12.0"]]}})
