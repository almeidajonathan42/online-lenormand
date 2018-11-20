(ns online-lenormand.views.home
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [online-lenormand.util.util :refer [s title-font-color card-font-color card-color random-color gradient-background description-font-color]]))


(def hue (random-color))

;; Components ==================================================================

(defn title []
  (let [styles
        {:title {:color (title-font-color hue)
                 :font-family "Charmonman"
                 :font-size "7vw"
                 :font-weight "bolder"}}]

    [:h1#title {:style (:title styles)}
      "Online Lenormand"]))

(defn description []
  (let [styles
        {:text {:color (description-font-color hue)
                :width "70%"
                :margin-top ".08em"
                :margin-bottom "4em"
                :font-size "1.4vw"
                :font-family "Lora"
                :text-align "center"
                :opacity 0}}] ;; Related to the animation

    [:p#description {:style (:text styles)}
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."]))

(defn prompt []
  (let [styles
        {:prompt {:color (title-font-color hue)
                  :font-family "Charmonman"
                  :font-size "3vw"
                  :opacity 0}}] ;; Related to the animation

    [:h1#prompt {:style (:prompt styles)}
      "What do you want to reflect about?"]))

(def input-atom (r/atom ""))

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
         :value @input-atom
         :on-change #(reset! input-atom (-> % .-target .-value))}]]))

(defn button []
  (let [styles
        {:container {:background "transparent"
                     :color (title-font-color hue)
                     :font-size "1.5em"
                     :text-align "center"
                     :border (str "1px solid " (title-font-color hue))
                     :border-radius "100vw"
                     :padding "0.5em 1em"
                     :margin-top "1.2em"
                     :cursor "pointer"
                     :opacity 0}}] ;; Related to the animation

    [:div#go-button {:style (:container styles)
                     :on-click #(js.console.log "Reframe:" @(rf/subscribe [:reflection-input]))}
      [:p "Go!"]]))

(defn home []
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
      [prompt]
      [input]
      [button]]))
