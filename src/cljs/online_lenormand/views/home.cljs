(ns online-lenormand.views.home
  (:require [reagent.core :as r]))

;; Color util ==================================================================

(defn random-color []
  (let [random-number (rand-int 360)]
    (if (and (> random-number 20) (< random-number 160))
      (+ random-number 100)
      random-number)))

(def hue (random-color))

(defn title-font-color [hue]
  (let [saturation 100
        lightness 95]
    (str "hsl(" hue ", " saturation "%, " lightness "%)")))

(defn description-font-color [hue]
  (let [saturation 100
        lightness 90]
    (str "hsl(" hue ", " saturation "%, " lightness "%)")))

(defn gradient-background [hue]
  (let [saturation 80
        lightness 75]
    (str "linear-gradient(to top, hsl(" hue ", " saturation "%, " lightness "%), hsl(" hue ", " saturation "%, " (- lightness 5) "%))")))

;; Components ==================================================================

(defn title []
  (let [styles
        {:title {:color (title-font-color hue)
                 :font-family "Charmonman"
                 :font-size "7vw"
                 :font-weight "bolder"}}]

    [:h1 {:style (:title styles)}
      "Online Lenormand"]))

(defn description []
  (let [styles
        {:text {:color (description-font-color hue)
                :width "70%"
                :margin-top ".08em"
                :margin-bottom "2em"
                :font-size "1.4vw"
                :font-family "Lora"
                :text-align "center"}}]

    [:p {:style (:text styles)}
      "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."]))

(defn prompt []
  (let [styles
        {:prompt {:color (title-font-color hue)
                  :font-family "Charmonman"
                  :font-size "3vw"}}]

    [:h1 {:style (:prompt styles)}
      "What do you want to reflect about?"]))

(def input-atom (r/atom ""))

(defn input []
  (let [styles
        {:container {:background "green"}
         :input {:color "blue"}}]

    [:div {:style (:container styles)}
      [:input
        {:type "text"
         :spellCheck false
         :style (:input styles)
         :value @input-atom
         :on-change #(reset! input-atom (-> % .-target .-value))}]]))

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
      [input]]))
