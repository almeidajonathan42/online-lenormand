(ns online-lenormand.util.util
  (:require [reagent.core :as r]
            [clojure.string :refer [join]]))

;; Util ========================================================================

(defn convert [value]
  (cond
    (keyword? value) (name value)
    (number? value) (str value "px")
    :else value))

(defn s [& args]
  (->>
    args
    (map convert)
    (join " ")))

;; Color util ==================================================================

(defn random-color []
  (let [random-number (rand-int 360)]
    (if (and (> random-number 20) (< random-number 160))
      (+ random-number 100)
      random-number)))

(defn title-font-color [hue]
  (let [saturation 100
        lightness 95]
    (str "hsl(" hue ", " saturation "%, " lightness "%)")))

(defn description-font-color [hue]
  (let [saturation 100
        lightness 80]
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
