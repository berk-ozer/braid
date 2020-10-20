(ns braid.chat.core
  "The majority of the core 'chat' functionality of Braid"
  (:require
    [braid.core.api :as core]
    [braid.base.api :as base]
    #?@(:cljs
         [[re-frame.core :refer [dispatch]]
          [braid.core.client.ui.views.autocomplete]
          [braid.core.client.store]
          [braid.popovers.api :as popovers]
          [braid.core.client.ui.styles.hover-menu]
          [braid.core.client.ui.styles.hover-cards]
          [braid.core.client.ui.styles.thread]
          [braid.core.client.ui.views.pages.global-settings :refer [global-settings-page-view]]
          [braid.core.client.invites.views.invite-page :refer [invite-page-view]]
          [braid.core.client.ui.views.pages.changelog :refer [changelog-view]]
          [braid.core.client.ui.views.pages.recent :refer [recent-page-view]]
          [braid.core.client.ui.views.pages.tags :refer [tags-page-view]]
          [braid.core.client.ui.views.pages.me :refer [me-page-view]]
          [braid.core.client.group-admin.views.group-settings-page :refer [group-settings-page-view]]])))

(defn init! []
  #?(:clj
     (do
       ;; TODO some of these might better belong elsewhere
       (doseq [k [:api-domain
                  :asana-client-id
                  :asana-client-secret
                  :aws-access-key
                  :aws-domain
                  :aws-region
                  :aws-secret-key
                  :db-url
                  :embedly-key
                  :environment
                  :github-client-id
                  :github-client-secret
                  :hmac-secret
                  :mailgun-domain
                  :mailgun-password
                  :site-url]]
         (base/register-config-var! k)))

     :cljs
     (do

       (core/register-autocomplete-engine!
         braid.core.client.ui.views.autocomplete/user-autocomplete-engine)

       (core/register-autocomplete-engine!
         braid.core.client.ui.views.autocomplete/tag-autocomplete-engine)

       (core/register-state! braid.core.client.store/initial-state
                             braid.core.client.store/AppState)

       (popovers/register-popover-styles!
         braid.core.client.ui.styles.hover-cards/>user-card)

       (popovers/register-popover-styles!
         braid.core.client.ui.styles.hover-cards/>tag-card)

       (popovers/register-popover-styles!
         (braid.core.client.ui.styles.hover-menu/>hover-menu))

       (popovers/register-popover-styles!
         braid.core.client.ui.styles.thread/add-tag-popover-styles)

       (base/register-system-page!
         {:key :global-settings
          :view global-settings-page-view})

       (core/register-group-page!
         {:key :recent
          :view recent-page-view
          :on-load (fn [page]
                     (dispatch [:set-page-loading true])
                     (dispatch [:load-recent-threads
                                {:group-id (page :group-id)
                                 :on-complete (fn [_]
                                                (dispatch [:set-page-loading false]))
                                 :on-error (fn [e]
                                             (dispatch [:set-page-loading false])
                                             (dispatch [:set-page-error true]))}]))})

       (core/register-group-page!
         {:key :settings
          :view group-settings-page-view})

       (core/register-group-page!
         {:key :tags
          :view tags-page-view})

       (core/register-group-page!
         {:key :me
          :view me-page-view})

       (core/register-group-page!
         {:key :invite
          :view invite-page-view})

       (base/register-system-page!
         {:key :changelog
          :view changelog-view}))))

