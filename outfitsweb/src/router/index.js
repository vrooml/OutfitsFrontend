import Vue from 'vue'
import VueRouter from 'vue-router'
import CommunityIndex from '../views/communityIndex.vue'
import searchPage from '../views/searchPage.vue'
import blogPage from '../views/blogPage.vue'
import blogDetail from '../views/blogDetail.vue'
import interfaceTest from '../views/interfaceTest.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/index'
  },
  {
    path: '/index',
    name: 'communityIndex',
    component: CommunityIndex
  },
  {
    path: '/search',
    name: 'searchPage',
    component: searchPage
  },
  {
    path: '/blog',
    name: 'blogPage',
    component: blogPage
  },
  {
    path: '/blogContent',
    name: 'blogDetail',
    component: blogDetail
  },
  {
    path: '/test',
    name: 'test',
    component: interfaceTest
  }
]

const router = new VueRouter({
  routes
})

export default router
