(ns online-lenormand.views.home
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [online-lenormand.views.reading-reflection :refer [reading-reflection]]
            [online-lenormand.views.reading-prediction :refer [reading-prediction]]
            [online-lenormand.util.util :refer [s title-font-color card-font-color card-color random-color gradient-background description-font-color]]))


(def hue (random-color))

;; Components ==================================================================

(defn title []
  (let [styles
        {:title {:color (title-font-color hue)
                 :font-family "Charmonman"
                 :font-size "6vw"
                 :font-weight "bolder"}}]

    [:h1#title {:style (:title styles)}
      "Online Lenormand"]))

(defn mini-card [number name]
    (let [lowercase-name (clojure.string/lower-case name)
          styles
          {:container {:background (card-color hue)
                       :height "8vw"
                       :width "5vw"
                       :margin ".2vw"
                       :border-radius ".5vw"
                       :display "flex"                   ;;Centering logic
                       :flex-direction "column"          ;;Centering logic
                       :align-items "center"             ;;Centering logic
                       :justify-content "space-between"} ;;Centering logic
           :number-container {:align-self "flex-start"
                              :color (card-font-color hue)
                              :font-family "Lora"
                              :font-size "1vw"
                              :margin ".5vw"}
           :card-name-container {:color (card-font-color hue)
                                 :font-family "Lora"
                                 :font-size "1vw"
                                 :margin "1vw"}
           :image {:width "2.7vw"
                   :filter "invert(100%)"
                   :opacity ".7"}}]

      [:div
        [:div {:style (:container styles)}
            [:div {:style (:number-container styles)}
              [:p number]]
            [:div {:style (:image-container styles)}
              [:img {:src (str "images/" lowercase-name ".png")
                     :style (:image styles)}]]
            [:div {:style (:card-name-container styles)}
              [:p name]]]]))

(defn card-container []
  (let [styles
        {:container {:display "flex"                   ;;Centering logic
                     :flex-direction "row"          ;;Centering logic
                     :align-items "center"             ;;Centering logic
                     :justify-content "center"}}] ;;Centering logic

    [:div {:style (:container styles)}
      [mini-card 18 "Dog"]
      [mini-card 7 "Snake"]]))

(defn description []
  (let [styles
        {:text {:color (description-font-color hue)
                :width "70%"
                :margin-top "1vw"
                :margin-bottom "2vw"
                :font-size "1.4vw"
                :font-family "Lora"
                :text-align "center"
                :opacity 0}}] ;; Related to the animation

    [:div#description {:style (:text styles)}
      [:p "1. Choose the type of reading you want"]
      [:br]
      [:p "2. Think about a question..."]
      [:br]
      [:p "3. Read the cards in pairs."]
      [:br]
      [card-container]
      [:br]
      [:p "The second card becomes a caracteristic of the first"]]))

(defn button [text state]
  (let [styles
        {:container {:background "transparent"
                     :color (title-font-color hue)
                     :font-size "1.5vw"
                     :text-align "center"
                     :border (str "1px solid " (title-font-color hue))
                     :border-radius "100vw"
                     :padding "1vw 2vw"
                     :margin-top "2.5vw"
                     :cursor "pointer"
                     :opacity 0}}] ;; Related to the animation

    [:div#go-button.gobutton {:style (:container styles)
                              :on-click #(rf/dispatch [:set-state state])}
      [:p text]]))

(defn prompt []
  (let [styles
        {:prompt {:color (title-font-color hue)
                  :font-family "Charmonman"
                  :font-size "3vw"
                  :opacity 0}}] ;; Related to the animation

    [:div
      [:h1#prompt {:style (:prompt styles)}
        "What type of reading do you want?"]
      [button "Prediction" "reading-prediction"]
      [button "Reflection" "reading-reflection"]]))

(defn input []
  (let [styles
        {:input {:background "transparent"
                 :color (title-font-color hue)
                 :font-size "2em"
                 :text-align "center"
                 :border "none"
                 :border-bottom (str "1px solid " (description-font-color hue))
                 :transition "border-bottom .5s"
                 :border-radius "5px"
                 :padding ".2em .5em"
                 :margin-top ".3em"
                 :width "50vw"
                 :outline "none"
                 :opacity 0}}] ;; Related to the animation

    [:div
      [:input#promptInput
        {:type "text"
         :spellCheck false
         :auto-focus true
         :style (:input styles)
         :value @(rf/subscribe [:get-question])
         :on-change #(rf/dispatch [:set-question (-> % .-target .-value)])}]]))




(defn writing []
  (let [styles
        {:container {:background (gradient-background hue)
                     :height "100%"
                     :width "100%"

                     :display "flex"              ;;Centering logic
                     :flex-direction "column"     ;;Centering logic
                     :align-items "center"        ;;Centering logic
                     :justify-content "center"}}] ;;Centering logic

    [:div {:style (:container styles)}
      [title]
      [description]
      [prompt]]))

(defn home []
  (cond
    (= @(rf/subscribe [:get-state]) "writing") [writing]
    (= @(rf/subscribe [:get-state]) "reading-reflection") [reading-reflection]
    (= @(rf/subscribe [:get-state]) "reading-prediction") [reading-prediction]))
