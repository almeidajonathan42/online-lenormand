(ns online-lenormand.state.routes
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [secretary.core :as secretary :refer-macros [defroute]]))

(defroute "/users/:id" {:as params}
  (js/console.log (str "User: " (:id params))))
