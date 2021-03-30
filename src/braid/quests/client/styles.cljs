(ns braid.quests.client.styles
  (:require
   [braid.core.client.ui.styles.header :refer [header-text header-height]]
   [braid.core.client.ui.styles.mixins :as mixins]
   [braid.core.client.ui.styles.vars :as vars]
   [garden.arithmetic :as m]
   [garden.units :refer [em px rem]]))

(def quest-icon-size (rem 2.5))

(def quests-header
  [:.quests-header
   {:position "relative"}

   [".quests-icon:hover + .quests-menu"
    ".quests-menu:hover"
    {:display "inline-block"}]

   [:.quests-icon
    (header-text header-height)
    {:padding [[0 "1em"]]
     :color "#CCC"}

    [:&::before
     (mixins/fontawesome \uf091)]]

   [:.quests-menu
    (mixins/context-menu)
    {:position "absolute"
     :top header-height
     :right 0
     :z-index 150
     :display "none"}

    [:.content

     [:.congrats
      {:min-width (em 22)}

      [:&::before
       (mixins/fontawesome \uf091)
       {:font-size (em 4.5)
        :float "left"
        :margin-right (rem 0.75)}]

      [:h1
       {:font-size (em 1.2)
        :margin 0}

       [:p
        {:margin 0}]]]

     [:.quests

      [:.quest
       {:margin-bottom (em 2)}

       [:&:last-child
        {:margin-bottom 0}]

       [:.main
        {:display "flex"
         :justify-content "space-between"}

        [:&::before
         {:content "attr(data-icon)"
          :display "block"
          :font-size quest-icon-size
          :color "#666"
          :margin [[0 vars/pad 0 (m// vars/pad 2)]]
          :align-self "center"}
         (mixins/fontawesome nil)]

        [:.info
         {:margin-right (em 1)}

         [:h1
          {:font-size (em 1.2)
           :margin 0
           :display "inline-block"}

          [:.count
           {:color "#999"}]]

         [:.progress
          {:display "inline-block"
           :float "right"}

          [:.icon
           {:display "inline-block"
            :font-size (em 1.2)
            :margin-right (em 0.5)
            :vertical-align "bottom"}

           [:&.incomplete::before
            (mixins/fontawesome \uf10c)]

           [:&.complete::before
            (mixins/fontawesome \uf058)]]]

         [:p
          {:margin 0
           :width (em 22)}]]

        [:.actions
         {:align-self "center"
          :white-space "nowrap"}
         [:a
          (mixins/outline-button {:text-color "#aaa"
                                  :border-color "#ccc"
                                  :hover-text-color "#999"
                                  :hover-border-color "aaa"})
          {:margin-left (em 0.5)}

          [:&.video
           {:width (em 5.5)}]

          [:&.video::after
           {:margin [[0 (em 0.25)]]}]

          [:&.video.show::after
           (mixins/fontawesome \uf144)]

          [:&.video.hide::after
           (mixins/fontawesome \uf28d)]]]]

       ["> .video"
        {:margin [[(em 1) (em -1) 0]]}
        [:img
         {:width "100%"}]]]]]]])
