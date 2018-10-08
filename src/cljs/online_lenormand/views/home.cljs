(ns online-lenormand.views.home
  (:require [reagent.core :as r]))

(defn title []
  (let [styles
        {:title {:color "blue"}}]

    [:h1 {:style (:title styles)}
      "Online Lenormand"]))

(defn description []
  (let [styles
        {:text {:color "red"}}]

    [:p {:style (:text styles)}
      "Lalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalalala"]))

(defn prompt []
  (let [styles
        {:prompt {:color "yellow"}}]

    [:h1 {:style (:prompt styles)}
      "What do you wanna know?"]))

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
        {:container {:background-color "grey"
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
