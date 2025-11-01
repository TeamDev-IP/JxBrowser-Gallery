import { createRouter, createWebHashHistory } from 'vue-router'
import UserAccount from '@/components/account/UserAccount.vue'
import General from '@/components/general/General.vue'
import Appearance from '@/components/appearance/Appearance.vue'
import Notifications from '@/components/notifications/Notifications.vue'

export const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        { path: '/', component: UserAccount },
        { path: '/prefs/general', component: General },
        { path: '/prefs/appearance', component: Appearance },
        { path: '/prefs/notifications', component: Notifications },
    ]
})
