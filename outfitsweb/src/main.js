import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './lib/mui/css/mui.min.css'
import Mint from 'mint-ui'
import 'mint-ui/lib/style.css'
import './assets/css/global.css'
import axios from 'axios'

import './assets/icon/font/iconfont.css'
import './assets/icon/font/iconfont.js'

import { VueMasonryPlugin } from 'vue-masonry'

Vue.prototype.$axios = axios
axios.defaults.baseURL = 'http://121.5.100.116:8080'
axios.interceptors.request.use(config => {
  config.headers.token = 'eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzIiwiaWF0IjoxNjIwNTUwNzQ1LCJzdWIiOiIxODk2MDE0NzI3MiIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyMDgwOTk0NX0.HjCnvUYa6m7MjRUMpMd_hfiTNwE71oMdAaNnzcr_-Wo'
  return config
})
Vue.use(VueMasonryPlugin)
Vue.use(Mint)
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
