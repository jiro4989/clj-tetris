(ns clj-tetris.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]))

(def dom-root (js/document.getElementById "root"))

(defn ui []
  [:div "hello world"])

;; lifecycle hook ;;
(defn ^:dev/before-load stop []
  (js/console.log "stop"))

;; lifecycle hook ;;
(defn ^:dev/after-load start []
  (js/console.log "start")
  (rd/render [ui] dom-root))

(defn init []
  (println "init")
  (start))
