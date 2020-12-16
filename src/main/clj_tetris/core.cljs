(ns clj-tetris.core
  (:require [reagent.core :as r]
            [reagent.dom :as rd]
            [clojure.string :as string]))

(def dom-root (js/document.getElementById "root"))

(def height (r/atom 170))
(def weight (r/atom 60))

(defn component-number-input [value]
  [:input {:type "number"
           :value @value
           :on-change #(reset! value (-> % .-target .-value js/parseInt))}])

(defn component-text-input [value]
  [:input {:type "text"
           :value @value
           :on-change #(reset! value (-> % .-target .-value))}])

(defn bmi [h w]
  (-> w
      (/ h)
      (/ h)))

(defn component-bmi []
  [:section
   [:h1 "BMI計算"]
   [:hr]
   [:table
    [:tbody
     [:tr
      [:td "身長"]
      [:td (component-number-input height)]
      [:td "cm"]]
     [:tr
      [:td "体重"]
      [:td (component-number-input weight)]
      [:td "kg"]]]]
   [:hr]
   [:div "計算結果"]
   [:div (str (bmi (/ @height 100) @weight))]])

(defn component-to-base [n]
  (let [decimal (r/atom 255)]
    (fn []
      [:tr
       [:td "10進数"]
       [:td (component-number-input decimal)]
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

(defn component-convert-with-fn [label f]
  (let [text (r/atom "")]
    (fn []
      [:tr
       [:td "入力"]
       [:td (component-text-input text)]
       [:td "出力: " label]
       [:td (f @text)]])))

(defn component-upper-lower []
  [:section
   [:h1 "大文字小文字変換"]
   [:hr]
   [:table
    [:tbody
     [component-convert-with-fn "大文字へ変換" string/upper-case]
     [component-convert-with-fn "小文字へ変換" string/lower-case]
     ]]])

(defn component-square []
  (let [w (r/atom 3)
        h (r/atom 3)]
    (fn []
      [:section
       [:h1 "表生成"]
        [:hr]
        [:table
         [:tbody
          [:tr
           [:td "横幅"]
           [:td (component-number-input w)]
           [:td "縦幅"]
           [:td (component-number-input h)]]]]
        [:table
         [:tbody
          (doall
            (for [i (range 0 @w)]
              ^{:key i}
              [:tr
               (doall
                 (for [j (range 0 @h)]
                   ^{:key j}
                   [:td "x"]))]))]]])))

(defn ui []
  [:div
   [:table
    [:tbody
     [:tr
      [:td (component-bmi)]
      [:td (component-to-bases)]]
     [:tr
      [:td (component-upper-lower)]
      [:td [component-square]]]]]])

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
