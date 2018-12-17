(ns online-lenormand.app
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [online-lenormand.views.home :refer [home]]
            [online-lenormand.views.reading :refer [reading]]
            [online-lenormand.state.reframe :as reframe]))

(defn init []
  (rf/dispatch-sync [:initialize])
  (r/render-component [home] (.getElementById js/document "container")))
