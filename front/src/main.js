import { createApp } from 'vue'
import App from './App.vue'
import ViewUIPlus from 'view-ui-plus'
import router from './router'
import plugins from './plugins'
import ElementPlus from 'element-plus'
import store from './store'
import 'element-plus/dist/index.css'
import 'view-ui-plus/dist/styles/viewuiplus.css'

import qs from 'qs'
import './axios/index.js'

import '@/assets/css/global.css'

import * as ElementPlusIconsVue from '@element-plus/icons-vue'


const app = createApp(App)

for (const [key,component] of Object.entries(ElementPlusIconsVue)){
    app.component(key,component)
}

app.use(store)
app.use(plugins)
app.use(router)
app.use(ElementPlus)
app.use(ViewUIPlus)
app.use(qs)
app.mount('#app')
