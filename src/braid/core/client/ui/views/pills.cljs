(ns braid.core.client.ui.views.pills
  (:require
    [re-frame.core :refer [dispatch subscribe]]
    [braid.lib.color :as color]))

(defn tag-pill-view
  [tag-id]
  (let [tag (subscribe [:tag tag-id])
        user-subscribed-to-tag? (subscribe [:user-subscribed-to-tag? tag-id])
        color (color/->color tag-id)]
    [:span.pill {:class (if @user-subscribed-to-tag? "on" "off")
                 :tab-index -1
                 :style {:background-color color
                         :color color
                         :border-color color}}
     [:div.name "#" (@tag :name)]]))

(defn user-pill-view
  [user-id]
  (let [user (or @(subscribe [:user user-id])
                 {:nickname "DELETED"
                  :status :offline})
        color (color/->color user-id)]
    [:span.pill {:class (str (case (user :status) :online "on" "off"))
                 :tab-index -1
                 :style {:background-color color
                         :color color
                         :border-color color}}
     [:span.name (str "@" (user :nickname))]]))
