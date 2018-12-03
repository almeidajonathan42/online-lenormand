(ns online-lenormand.views.reading-prediction
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
                     :height "8vw"
                     :margin "1em"
                     :cursor "pointer"
                     :display "flex"                   ;;Centering logic
                     :flex-direction "column"          ;;Centering logic
                     :align-items "center"             ;;Centering logic
                     :justify-content "flex-begin"}        ;;Centering logic
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
         :meaning {:height "2.2vw"
                   :width "100%"
                   :border-radius "1em"
                   :background (card-color hue)
                   :color (card-font-color hue)
                   :margin ".2em"
                   :font-size "1vw"

                   :display "flex"                   ;;Centering logic
                   :flex-direction "column"          ;;Centering logic
                   :align-items "center"             ;;Centering logic
                   :justify-content "center"}}] ;;Centering logic

    (if @is-open
      [:div {:style (:container styles)}
        [:div.meaning {:style (:meaning styles)
                       :on-click #(select-meaning selected-meaning 1 is-open)}
          [:p (get-in cards-meanings [:en keyword-number :meaning-1])]]
        [:div.meaning {:style (:meaning styles)
                       :on-click #(select-meaning selected-meaning 2 is-open)}
          [:p (get-in cards-meanings [:en keyword-number :meaning-2])]]
        [:div.meaning {:style (:meaning styles)
                       :on-click #(select-meaning selected-meaning 3 is-open)}
          [:p (get-in cards-meanings [:en keyword-number :meaning-3])]]]

      [:div {:style (:container styles)}
        (if (= 1 @selected-meaning)
          [:div.meaning {:style (:meaning styles)
                         :on-click #(select-meaning selected-meaning 1 is-open)}
            [:p (get-in cards-meanings [:en keyword-number :meaning-1])]])
        (if (= 2 @selected-meaning)
          [:div.meaning {:style (:meaning styles)
                         :on-click #(select-meaning selected-meaning 2 is-open)}
            [:p (get-in cards-meanings [:en keyword-number :meaning-2])]])
        (if (= 3 @selected-meaning)
          [:div.meaning {:style (:meaning styles)
                         :on-click #(select-meaning selected-meaning 3 is-open)}
            [:p (get-in cards-meanings [:en keyword-number :meaning-3])]])])))

(defn card [number name clicked]
  (let [is-open (r/atom true)
        selected-meaning (r/atom nil)
        lowercase-name (clojure.string/lower-case name)
        styles
        {:container {:background (card-color hue)
                     :height "18vw"
                     :width "12vw"
                     :margin "1em"
                     :border-radius "1em"
                     :cursor "pointer"
                     :display "flex"                   ;;Centering logic
                     :flex-direction "column"          ;;Centering logic
                     :align-items "center"             ;;Centering logic
                     :justify-content "space-between"} ;;Centering logic
         :flip-container {:background (card-color hue)
                          :height "18vw"
                          :width "12vw"
                          :margin "1em"
                          :border-radius "1em"
                          :cursor "pointer"
                          :display "flex"                   ;;Centering logic
                          :flex-direction "column"          ;;Centering logic
                          :align-items "center"             ;;Centering logic
                          :justify-content "center"} ;;Centering logic
         :number-container {:align-self "flex-start"
                            :color (card-font-color hue)
                            :font-family "Lora"
                            :font-size "1.5vw"
                            :margin "1vw"}
         :card-name-container {:color (card-font-color hue)
                               :font-family "Lora"
                               :font-size "1.5vw"
                               :margin "1vw"}
         :image {:width "9vw"
                 :filter "invert(100%)"
                 :opacity ".7"}
         :interrogation {:font-size "10vw"
                         :font-family "Lora"
                         :color (card-font-color hue)}}]

    [:div {:on-click #(reset! clicked true)}

      (if @clicked
        [:div
          [:div {:style (:container styles)}
              [:div {:style (:number-container styles)}
                [:p number]]
              [:div {:style (:image-container styles)}
                [:img {:src (str "images/" lowercase-name ".png")
                       :style (:image styles)}]]
              [:div {:style (:card-name-container styles)}
                [:p name]]]
          [meanings number is-open selected-meaning]]
      ;; else
        [:div {:style (:flip-container styles)}
          [:p {:style (:interrogation styles)}
            "?"]])]))

(defn remove-element [coll element]
  (vec (remove #(= element %) coll)))

(defn include-element [coll element]
  (conj coll element))

(defn draw-cards [number]
  (let [card-numbers (atom (vec (range 1 37)))
        drawn-cards (atom [])]
    (doseq [i (range number)
            :let [random (rand-nth @card-numbers)]]
      (swap! card-numbers remove-element random)
      (swap! drawn-cards include-element random))

    @drawn-cards))

(defn separator []
  [:div {:style {:width "22vw"
                 :height "1vw"}}])

(defn separator2 []
 [:div {:style {:width "18vw"
                :height "1vw"}}])

(defn cards []
  (let [drawn-cards (draw-cards 6)
        styles
        {:container {:display "flex"              ;;Centering logic
                     :flex-direction "column"        ;;Centering logic
                     :align-items "flex-start"    ;;Centering logic
                     :justify-content "center"    ;;Centering logic
                     :opacity 0} ;; Related to the animation
         :label-container {:width "100%"
                           :height "3vw"
                           :min-height "3vw"
                           :max-height "3vw"
                           :color (description-font-color hue)
                           :margin ".08vw"
                           :font-size "3vw"
                           :font-family "Lora"
                           :display "flex"              ;;Centering logic
                           :flex-direction "row"        ;;Centering logic
                           :align-items "center"    ;;Centering logic
                           :justify-content "center"}    ;;Centering logic
         :inner-container {:height "30vw"
                           :display "flex"              ;;Centering logic
                           :flex-direction "row"        ;;Centering logic
                           :align-items "flex-start"    ;;Centering logic
                           :justify-content "center"}}]    ;;Centering logic

    [:div#card-container {:style (:container styles)}
      [:div {:style (:label-container styles)}
        [:p "PAST"]
        [separator]
        [:p "PRESENT"]
        [separator2]
        [:p "FUTURE"]]
      [:div {:style (:inner-container styles)}
        (for [number drawn-cards
              :let [keyword-number (keyword (str number))
                    clicked (r/atom false)]]
          [card number (get-in cards-meanings [:en keyword-number :name]) clicked])]]))

(defn button []
  (let [styles
        {:container {:background "transparent"
                     :color (title-font-color hue)
                     :font-size "1.5vw"
                     :text-align "center"
                     :border (str "1px solid " (title-font-color hue))
                     :border-radius "100vw"
                     :padding "1vw 2vw"
                     :margin-top "1.5vw"
                     :cursor "pointer"}}]

    [:div.gobutton {:style (:container styles)
                    :on-click #(rf/dispatch [:set-state "writing"])}
      [:p "Go back"]]))

(defn reading-prediction []
  (let [styles
        {:container {:background (gradient-background hue)
                     :height "100%"
                     :width "100%"

                     :display "flex"              ;;Centering logic
                     :flex-direction "column"     ;;Centering logic
                     :align-items "center"        ;;Centering logic
                     :justify-content "center"}}] ;;Centering logic

    [:div {:style (:container styles)}
      [cards]
      [button]]))
