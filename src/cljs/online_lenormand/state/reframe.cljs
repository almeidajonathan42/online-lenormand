(ns online-lenormand.state.reframe
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))

(rf/reg-event-db              ;; sets up initial application state
  :initialize
  (fn [_ _]                   ;; the two parameters are not important here, so use _
    {:reflection-input "lalalala"}))

(rf/reg-sub
  :reflection-input
  (fn [db _]     ;; db is current app state. 2nd unused param is query vector
    (:reflection-input db))) ;; return a query computation over the application state
