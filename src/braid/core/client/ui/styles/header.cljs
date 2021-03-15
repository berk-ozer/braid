(ns braid.core.client.ui.styles.header
  (:require
   [braid.core.client.ui.styles.mixins :as mixins]
   [braid.core.client.ui.styles.vars :as vars]
   [garden.arithmetic :as m]
   [garden.units :refer [em px rem]]))

(def header-height vars/top-bar-height)

(defn bar []
  [:&
   {:background "black"
    :color "white"
    :height header-height
    :border-radius vars/border-radius
    :overflow "hidden"}
   (mixins/box-shadow)])

(defn header-text [size]
  {:text-transform "uppercase"
   :letter-spacing "0.1em"
   :font-weight "bold"
   :line-height size
   :-webkit-font-smoothing "antialiased"
   :padding [[0 (m/* vars/pad 0.75)]]})

(defn menu []
  [:>.options
    (mixins/context-menu)
    {:position "absolute"
     :top 0
     :right 0
     :margin-top header-height
     :z-index 110
     :display "none"}

    [:>.content

     [:>a
      {:display "block"
       :color "black"
       :text-align "right"
       :text-decoration "none"
       :line-height "1.85em"
       :white-space "nowrap"
       :cursor "pointer"}

      [:&:hover
       {:color "#666"}]

      [:&::after
       {:margin-left "0.5em"}]

      [:>.icon
       {:font-family "fontawesome"
        :display "inline-block"
        :width "1.5em"}]

      ; ADMIN

      [:&.group-bots::after
       (mixins/fontawesome \uf12e)]

      [:&.settings::after
       (mixins/fontawesome \uf013)]]]])

(defn header-button [size]
  [:&
   {:color "white"
    :height size
    :line-height size
    :width size
    :-webkit-font-smoothing "antialiased"
    :text-align "center"
    :text-decoration "none"}

   [:>.icon
    {:font-family "fontawesome"}]

   [:&:hover
    :&.active
    {:background "rgba(0,0,0,0.25)"}]

   [:&.open-sidebar::after
    (mixins/fontawesome \uf0e8)]])

(defn group-header [size]
  [:>.group-header

   [:>.bar
    (bar)
    {:display "inline-block"
     :vertical-align "top"}

    [:>.group-name
     :>.buttons>a
     {:color "white"
      :display "inline-block"
      :vertical-align "top"
      :height size
      :line-height size
      :-webkit-font-smoothing "antialiased"}]

    [:>.group-name
     (header-text size)
     {:min-width (em 5)}]

    [:>.buttons
     {:display "inline-block"
      :vertical-align "top"}

     [:>a
      (header-button size)]]

    [:>.search-bar
     {:display "inline-block"
      :position "relative"}

     [:>input
      {:border 0
       :padding-left vars/pad
       :min-width "15em"
       :width "25vw"
       :height size
       :outline "none"}]

     [:>.action
      [:&::after
       {:top 0
        :right (m/* vars/pad 0.75)
        :height size
        :line-height size
        :position "absolute"
        :cursor "pointer"}]

      [:&.search::after
       {:color "#ccc"
        :pointer-events "none"}
       (mixins/fontawesome \uf002)]

      [:&.clear::after
       (mixins/fontawesome \uf057)]]]]

   [:>.loading-indicator
    {:display "inline-block"
     :vertical-align "middle"}

    [:&::before
     {:height size
      :line-height size
      :margin-left (em 0.5)
      :font-size (em 1.5)}]

    [:&.error
     [:&::before
      (mixins/fontawesome\uf071)]]

    [:&.loading
     [:&::before
      (mixins/fontawesome \uf110)
      mixins/spin]]]])

(defn admin-header [size]
  [:>.admin-header
   {:position "relative"}

   [:>.admin-icon
    (header-text size)
    {:padding [[0 (em 1)]]
     :color "#CCC"}

    [:&::before
     (mixins/fontawesome \uf0e3)]]

   (menu)

   [".admin-icon:hover + .options"
    ".options:hover"
    {:display "inline-block"}]])

(defn header-text-button [size]
  [:&
   (header-text size)
   mixins/standard-font
   {:background "transparent"
    :cursor "pointer"
    :border "none"
    :color "white"}
  [:&:hover
   :&.active
   {:background "rgba(0,0,0,0.25)"}] ])

(defn user-header [size]
  [:>.user-header
   {:position "relative"}

   [".bar:hover + .options"
    ".options:hover"
    {:display "inline-block"}]

   [:>.bar
    (bar)
    {:margin-left vars/pad}

    [:button.join
     (header-text-button size)]

    [:>.user-info
     {:display "inline-block"
      :vertical-align "top"}

     [:&:hover
      :&.active
      {:background "rgba(0,0,0,0.25)"}]

     [:>.name
      (header-text size)
      {:color "white"
       :display "inline-block"
       :text-decoration "none"
       :vertical-align "top"}]

     [:>.avatar
      {:height header-height
       :width header-height
       :background "white"}]]

    [:>.more
     {:display "inline-block"
      :line-height header-height
      :vertical-align "top"
      :height header-height
      :width header-height
      :text-align "center"}

     [:&::after
      {:-webkit-font-smoothing "antialiased"}
      (mixins/fontawesome \uf078)]]]

   (menu)])

(defn header [pad]
  [:>.header
   {:position "absolute"
    :top vars/pad
    :left vars/sidebar-width
    :margin-left vars/pad
    :right vars/pad
    :display "flex"
    :justify-content "space-between"
    :z-index 102}

   (group-header header-height)

   [:>.spacer
    {:flex-grow 2}]

   (user-header header-height)
   (admin-header header-height)])
