(ns online-lenormand.state.reframe
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))

(rf/reg-event-db
  :initialize
  (fn [_ _]
    {:state "writing"
     :question ""}))

(rf/reg-sub
  :get-state
  (fn [db _]
    (:state db)))

(rf/reg-event-db
  :set-state
  (fn [db [_ val]]
    (assoc db :state val)))

(rf/reg-sub
  :get-question
  (fn [db _]
    (:question db)))

(rf/reg-event-db
  :set-question
  (fn [db [_ val]]
    (assoc db :question val)))
