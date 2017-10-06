;; compojure-api + buddy (token auth)
;; References:
;; https://github.com/metosin/compojure-api/blob/master/examples/simple/src/example/handler.clj
;; https://gist.github.com/Deraen/ef7f65d7ec26f048e2bb
;; https://funcool.github.io/buddy-auth/latest/

(ns exper-api1.handler
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]
            [buddy.auth.backends :as backends]
            [buddy.auth.accessrules :refer [wrap-access-rules]]
            [buddy.auth :refer [authenticated?]]
            [compojure.api.meta :refer [restructure-param]]
            [buddy.auth.middleware :refer [wrap-authentication]]))

(def tokens {:11111111111111 :admin})

(defn my-authfn
  [req token]
  (let [token (keyword token)]
    (get tokens token nil)))

(def backend
  (backends/token
   {:authfn my-authfn}))

(defn access-error [req val]
  {:status 401 :headers {} :body "Unauthorized"})

(defn wrap-rule [handler rule]
  (-> handler
      (wrap-access-rules {:rules [{:pattern #".*"
                                   :handler rule}]
                          :on-error access-error})))

(defmethod restructure-param :auth-rules
  [_ rule acc]
  (update-in acc [:middleware] conj [wrap-rule rule]))

(def api-app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Exper-api1"
                    :description "Compojure Api example"}
             :tags [{:name "api", :description "some apis"}]
             :securityDefinitions {:api_key {:type "apiKey"
                                             :name "Authorization"
                                             :in "header"}}}}}
    (context "/api" []
      :tags ["api"]
      (GET "/plus" []
           :auth-rules authenticated?
           :return {:result Long}
           :query-params [x :- Long, y :- Long]
           :summary "adds two numbers together"           
           (ok {:result (+ x y)})))))

(def app (-> api-app
             (wrap-authentication backend)))
