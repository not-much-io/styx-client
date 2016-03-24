(defproject styx-client "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.8.34"]
                 [figwheel-sidecar "0.5.0"]
                 [reagent "0.6.0-alpha" :exclusions [cljsjs/react]]
                 [cljsjs/react-with-addons "0.14.7-0"]
                 [re-frame "0.7.0"]
                 [prismatic/schema "1.0.5"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"
                 "scripts"]

  :plugins [[lein-cljsbuild "1.1.1"]]
            ;[lein-figwheel "0.5.0-2"]


  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :figwheel {:css-dirs ["resources/public/css"]}

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src/cljs"]
                        :figwheel {:on-jsload "styx-client.core/mount-root"}
                        :compiler {:main styx-client.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :output-dir "resources/public/js/compiled/out"
                                   :asset-path "js/compiled/out"
                                   :source-map-timestamp true}}

                       {:id "min"
                        :source-paths ["src/cljs"]
                        :compiler {:main styx-client.core
                                   :output-to "resources/public/js/compiled/app.js"
                                   :optimizations :advanced
                                   :closure-defines {goog.DEBUG false}
                                   :pretty-print false}}]})
