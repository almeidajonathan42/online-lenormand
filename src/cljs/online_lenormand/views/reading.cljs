(ns online-lenormand.views.reading
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [online-lenormand.data.cards-meanings :refer [cards-meanings]]))

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

(defn card-color [hue]
  (let [saturation 100
        lightness 80]
    (str "hsl(" hue ", " saturation "%, " lightness "%)")))

(defn card-font-color [hue]
  (let [saturation 100
        lightness 30]
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

(defn meanings [number]
  (let [keyword-number (keyword (str number))
        styles
        {:container {:height "6vw"
                     :width "12vw"
                     :margin "1em"
                     :border-radius "1em"
                     :border "1px solid black"
                     :display "flex"                   ;;Centering logic
                     :flex-direction "column"          ;;Centering logic
                     :align-items "center"             ;;Centering logic
                     :justify-content "center"} ;;Centering logic
         :meaning {:height "2em"
                   :width "100%"
                   :color (card-font-color hue)
                   :display "flex"                   ;;Centering logic
                   :flex-direction "column"          ;;Centering logic
                   :align-items "center"             ;;Centering logic
                   :justify-content "center"}}] ;;Centering logic

    [:div {:style (:container styles)}
      [:div {:style (:meaning styles)}
        [:p (get-in cards-meanings [:en keyword-number :meaning-1])]]
      [:div {:style (:meaning styles)}
        [:p (get-in cards-meanings [:en keyword-number :meaning-2])]]
      [:div {:style (:meaning styles)}
        [:p (get-in cards-meanings [:en keyword-number :meaning-3])]]]))

(defn card [number name]
  (let [styles
        {:container {:background (card-color hue)
                     :height "18vw"
                     :width "12vw"
                     :margin "1em"
                     :border-radius "1em"
                     ; :box-shadow "5px 10px 18px #888888"
                     :display "flex"                   ;;Centering logic
                     :flex-direction "column"          ;;Centering logic
                     :align-items "center"             ;;Centering logic
                     :justify-content "space-between"} ;;Centering logic
         :number-container {:align-self "flex-start"
                            :color (card-font-color hue)
                            :font-family "Lora"
                            :font-size "1.5em"
                            :margin ".5em"}
         :card-name-container {:color (card-font-color hue)
                               :font-family "Lora"
                               :font-size "1.5em"
                               :margin ".5em"}}]

    [:div
      [:div {:style (:container styles)}
        [:div {:style (:number-container styles)}
          [:p number]]
        [:div {:style (:card-name-container styles)}
          [:p name]]]
      [meanings number]]))


(defn draw-cards [number]
  (let [card-numbers (atom (vec (range 1 37)))
        drawn-cards (atom [])]
    (swap! drawn-cards conj (for [x (range number)] (rand-nth @card-numbers)))

    (vec (get @drawn-cards 0))))

(defn cards []
  (let [drawn-cards (draw-cards 5)
        styles
        {:container {:display "flex"              ;;Centering logic
                     :flex-direction "row"        ;;Centering logic
                     :align-items "center"        ;;Centering logic
                     :justify-content "center"}}] ;;Centering logic

    [:div {:style (:container styles)}
      (for [number drawn-cards
            :let [keyword-number (keyword (str number))]]
        [card number (get-in cards-meanings [:en keyword-number :name])])]))

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
