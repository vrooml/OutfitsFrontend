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
// axios.interceptors.request.use(config => {
//   config.headers.token = 'eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyIiwiaWF0IjoxNjIwNzM1NjAyLCJzdWIiOiIxNTI2MDAxMTM4NSIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyMDk5NDgwMn0.SM7ERdR_qw3gSHjwtoYuM9XO2Zjd7IHymHTAHusRYFw'
//   return config
// })
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
