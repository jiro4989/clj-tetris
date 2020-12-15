(ns clj-tetris.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]))

(def dom-root (js/document.getElementById "root"))

(def height (r/atom 170))
(def weight (r/atom 60))

(defn component-text-input [value]
  [:input {:type "number"
           :value @value
           :on-change #(reset! value (-> % .-target .-value js/parseInt))}])

(defn bmi [h w]
  (-> w
      (/ h)
      (/ h)))

(defn component-bmi []
  [:section
   [:h1 "BMI計算"]
   [:hr]
   [:span "以下に入力してね"]
   [:table
    [:tbody
     [:tr
      [:td "身長"]
      [:td (component-text-input height)]
      [:td "cm"]]
     [:tr
      [:td "体重"]
      [:td (component-text-input weight)]
      [:td "kg"]]]]
   [:hr]
   [:div "計算結果"]
   [:div (str (bmi (/ @height 100) @weight))]])

(defn component-to-base [n]
  (let [decimal (r/atom 255)]
    (fn []
      [:tr
       [:td "10進数"]
       [:td (component-text-input decimal)]
       [:td n "進数"]
       [:td (str (.toString @decimal n))]])))

(defn component-to-bases []
  [:section
   [:h1 "10進数を他進数に変換"]
   [:hr]
   [:table
    [:thead
     [:tr
      [:td]
      [:td "入力"]
      [:td "変換先進数"]
      [:td "出力"]]]
    [:tbody
     [component-to-base 2]
     [component-to-base 8]
     [component-to-base 16]]]])

(defn ui []
  [:div
   (component-bmi)
   (component-to-bases)])

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
