(ns clj-tetris.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]))

(def dom-root (js/document.getElementById "root"))

(def height (r/atom 170))
(def weight (r/atom 60))

(defn text-input [value]
  [:input {:type "number"
           :value @value
           :on-change #(reset! value (-> % .-target .-value))}])

(defn bmi [h w]
  (-> w
      (/ h)
      (/ h)))

(defn ui []
  [:div
   [:h1 "BMI計算"]
   [:hr]
   [:span "以下に入力してね"]
   [:table
    [:tbody
     [:tr
      [:td "身長"]
      [:td (text-input height)]
      [:td "cm"]]
     [:tr
      [:td "体重"]
      [:td (text-input weight)]
      [:td "kg"]]]]
   [:hr]
   [:div "計算結果"]
   [:div (str (bmi (/ @height 100) @weight))]])

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
