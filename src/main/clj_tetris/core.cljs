(ns clj-tetris.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]))

(def dom-root (js/document.getElementById "root"))

(def height (r/atom 170))
(def weight (r/atom 60))
(def decimal (r/atom 255))

(defn text-input [value]
  [:input {:type "number"
           :value @value
           :on-change #(reset! value (-> % .-target .-value js/parseInt))}])

(defn bmi [h w]
  (-> w
      (/ h)
      (/ h)))

(defn ui-bmi []
  [:section
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

(defn to-bin [n]
  (.toString n 2))

(defn ui-bin []
  [:section
   [:h1 "10進数を2進数に変換"]
   [:hr]
   [:table
    [:tbody
     [:tr
      [:td "10進数"]
      [:td (text-input decimal)]
      [:td "-> 計算結果"]
      [:td (str (to-bin @decimal))]]]]])

(defn ui []
  [:div
   (ui-bmi)
   (ui-bin)])

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
