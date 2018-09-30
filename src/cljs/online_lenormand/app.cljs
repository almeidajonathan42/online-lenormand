(ns online-lenormand.app
  (:require [reagent.core :as reagent :refer [render-component]]
            [online-lenormand.main-page.core :refer [main-page]]))

(defn init []
  (let [c (.. js/document (createElement "DIV"))]
    (aset c "innerHTML" "<p>i'm created</p>")
    (.. js/document (getElementById "container") (appendChild c))))
