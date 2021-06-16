import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './lib/mui/css/mui.min.css'
import Mint from 'mint-ui'
import 'mint-ui/lib/style.css'
import './assets/css/global.css'
import axios from 'axios'
import vuescroll from 'vuescroll'

import './assets/icon/font/iconfont.css'
import './assets/icon/font/iconfont.js'

import { VueMasonryPlugin } from 'vue-masonry'

Vue.prototype.$axios = axios
axios.defaults.baseURL = 'http://121.5.100.116:8080'
axios.interceptors.request.use(config => {
  config.headers.token = 'eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2IiwiaWF0IjoxNjIzODE4NDA4LCJzdWIiOiIxMzAyMzgzNjU4NyIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyNDA3NzYwOH0.XfLDqPo9_A6uNURwTxNRPzFBBBgoreQgu5Gexc4DleA'
  return config
})
Vue.use(VueMasonryPlugin)
Vue.use(Mint)
Vue.config.productionTip = false
Vue.use(vuescroll)
Vue.prototype.$vuescrollConfig = {
  bar: {
    background: '#cecece',
    minSize: 0.2,
    opacity: 0.8,
    specifyBorderRadius: '10px',
    onlyShowBarOnScroll: false
  }
}

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
