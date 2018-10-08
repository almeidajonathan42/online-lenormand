(ns online-lenormand.app
  (:require [reagent.core :as r]
            [online-lenormand.views.home :refer [home]]))

(defn init []
  (r/render-component [home] (.getElementById js/document "container")))
