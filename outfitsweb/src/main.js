import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './lib/mui/css/mui.min.css'
import Mint from 'mint-ui'
import 'mint-ui/lib/style.css'
import './assets/css/global.css'
import touch from 'vue-directive-touch'

import { VueMasonryPlugin } from 'vue-masonry'

Vue.use(VueMasonryPlugin)
Vue.use(Mint)
Vue.use(touch)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
