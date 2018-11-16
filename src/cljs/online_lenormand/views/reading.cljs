(ns online-lenormand.views.reading
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))

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
        lightness 80]
    (str "hsl(" hue ", " saturation "%, " lightness "%)")))

(defn gradient-background [hue]
  (let [saturation 70
        lightness 65]
    (str "linear-gradient(to top, hsl(" hue ", " saturation "%, " lightness "%), hsl(" hue ", " saturation "%, " (- lightness 5) "%))")))

;; Components ==================================================================

(defn title []
  (let [styles
        {:title {:color (title-font-color hue)
                 :font-size "4vw"
                 :font-family "Lora"
                 :font-weight "bolder"
                 :margin-bottom "3vw"}}]

    [:h1#title {:style (:title styles)}
      "What should I do to meet my next love?"]))

(defn card [number name]
  (let [styles
        {:container {:background "red"
                     :height "18vw"
                     :width "12vw"
                     :margin "1em"
                     :border-radius "1em"}}]

    [:div {:style (:container styles)}]))

(defn cards []
  (let [styles
        {:container {:display "flex"              ;;Centering logic
                     :flex-direction "row"     ;;Centering logic
                     :align-items "center"        ;;Centering logic
                     :justify-content "center"}}] ;;Centering logic

    [:div {:style (:container styles)}
      [card 1 "Heart"]
      [card 2 "Coffin"]
      [card 3 "Paths"]
      [card 4 "Birds"]
      [card 5 "Scythe"]]))

(defn reading []
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
      [cards]]))
