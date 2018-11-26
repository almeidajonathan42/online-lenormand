(ns online-lenormand.views.reading
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [online-lenormand.data.cards-meanings :refer [cards-meanings]]
            [clojure.string :refer [join]]
            [online-lenormand.util.util :refer [s title-font-color card-font-color card-color random-color gradient-background description-font-color]]))


(def hue (random-color))

;; Components ==================================================================

(defn title []
  (let [styles
        {:title {:color (title-font-color hue)
                 :font-size "4vw"
                 :font-family "Lora"
                 :font-weight "bolder"
                 :margin-bottom "3vw"}}]

    [:h1#title {:style (:title styles)}
      @(rf/subscribe [:get-question])]))

(defn select-meaning [selected-meaning new-meaning is-open]
  (if @is-open
    (do
      (reset! selected-meaning new-meaning)
      (reset! is-open false))
    (do
      (reset! is-open true))))


(defn meanings [number is-open selected-meaning]
  (let [keyword-number (keyword (str number))
        styles
        {:container {:width "12vw"
                     :margin "1em"
                     :border-radius "1em"
                     :border (if @is-open (s "1px solid" (card-font-color hue))
                                          "1px solid transparent")
                     :background (if (not @is-open) (description-font-color hue))
                     :cursor "pointer"
                     :display "flex"                   ;;Centering logic
                     :flex-direction "column"          ;;Centering logic
                     :align-items "center"             ;;Centering logic
                     :justify-content "center"}        ;;Centering logic
         :container-closed {:width "12vw"
                            :margin "1em"
                            :border-radius "1em"
                            :border "none"
                            :background (description-font-color hue)
                            :color (gradient-background hue)
                            :cursor "pointer"
                            :display "flex"                   ;;Centering logic
                            :flex-direction "column"          ;;Centering logic
                            :align-items "center"             ;;Centering logic
                            :justify-content "center"} ;;Centering logic
         :meaning {:height "2.2em"
                   :width "100%"
                   :color (if @is-open (card-font-color hue)
                                       (gradient-background hue))
                   :display "flex"                   ;;Centering logic
                   :flex-direction "column"          ;;Centering logic
                   :align-items "center"             ;;Centering logic
                   :justify-content "center"}}] ;;Centering logic

    (if @is-open
      [:div {:style (:container styles)}
        [:div {:style (:meaning styles)
               :on-click #(select-meaning selected-meaning 1 is-open)}
          [:p (get-in cards-meanings [:en keyword-number :meaning-1])]]
        [:div {:style (:meaning styles)
               :on-click #(select-meaning selected-meaning 2 is-open)}
          [:p (get-in cards-meanings [:en keyword-number :meaning-2])]]
        [:div {:style (:meaning styles)
               :on-click #(select-meaning selected-meaning 3 is-open)}
          [:p (get-in cards-meanings [:en keyword-number :meaning-3])]]]

      [:div {:style (:container styles)}
        (if (= 1 @selected-meaning)
          [:div {:style (:meaning styles)
                 :on-click #(select-meaning selected-meaning 1 is-open)}
            [:p (get-in cards-meanings [:en keyword-number :meaning-1])]])
        (if (= 2 @selected-meaning)
          [:div {:style (:meaning styles)
                 :on-click #(select-meaning selected-meaning 2 is-open)}
            [:p (get-in cards-meanings [:en keyword-number :meaning-2])]])
        (if (= 3 @selected-meaning)
          [:div {:style (:meaning styles)
                 :on-click #(select-meaning selected-meaning 3 is-open)}
            [:p (get-in cards-meanings [:en keyword-number :meaning-3])]])])))

(defn card [number name]
  (let [is-open (r/atom true)
        selected-meaning (r/atom nil)
        lowercase-name (clojure.string/lower-case name)
        styles
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
                               :margin ".5em"}
         :image {:width "9vw"
                 :filter "invert(100%)"
                 :opacity ".5"}}]

    [:div
      [:div {:style (:container styles)}
        [:div {:style (:number-container styles)}
          [:p number]]
        [:div {:style (:image-container styles)}
          [:img {:src (str "images/" lowercase-name ".png")
                 :style (:image styles)}]]
        [:div {:style (:card-name-container styles)}
          [:p name]]]
      [meanings number is-open selected-meaning]]))


(defn remove-element [coll element]
  (vec (remove #(= element %) coll)))

(defn include-element [coll element]
  (conj coll element))

(defn draw-cards [number]
  (let [card-numbers (atom (vec (range 1 37)))
        drawn-cards (atom [])]
    (doseq [i (range 5)
            :let [random (rand-nth @card-numbers)]]
      (swap! card-numbers remove-element random)
      (swap! drawn-cards include-element random))

    @drawn-cards))

(defn cards []
  (let [drawn-cards (draw-cards 5)
        styles
        {:container {:display "flex"              ;;Centering logic
                     :flex-direction "row"        ;;Centering logic
                     :align-items "flex-start"        ;;Centering logic
                     :justify-content "center"}}] ;;Centering logic

    [:div {:style (:container styles)}
      (for [number drawn-cards
            :let [keyword-number (keyword (str number))]]
        [card number (get-in cards-meanings [:en keyword-number :name])])]))

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
                     :cursor "pointer"}}]

    [:div {:style (:container styles)
           :on-click #(rf/dispatch [:set-state "writing"])}
      [:p "Go back"]]))

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
      [cards]
      [button]]))
