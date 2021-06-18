<template>
  <div id="blogPage">
    <div id="myLoding" v-show="loading === 1">
      <div class="pswp__preloader__icn">
        <div class="pswp__preloader__cut">
          <div class="pswp__preloader__donut"></div>
        </div>
      </div>
      <div id="loadText">加 载 中</div>
    </div>
    <div id="myLoding2" v-show="loading2 === 1">
      <div class="pswp__preloader__icn">
        <div class="pswp__preloader__cut">
          <div class="pswp__preloader__donut"></div>
        </div>
      </div>
      <!--      <div id="loadText2">加 载 中</div>-->
    </div>
    <vue-scroll v-show="loading === 0">
      <div id="blog-detail" :style="screenHeight1">

        <div id="blog-detail-context">
          <div id="blog-detail-title">
            <div>{{data.title.replace(/^\"|\"$/g,'')}}</div>
          </div>
          <div id="blog-detail-second">
            <div id="detail-time">{{data.time}}</div>
            <div id="detail-love">
              <svg v-show="data.favorite === 1" class="icon" aria-hidden="true" @click="collect()">
                <use xlink:href="#icon-aixin"></use>
              </svg>
              <svg v-show="data.favorite === 0" class="icon" aria-hidden="true" @click="collect()">
                <use xlink:href="#icon-icon-test"></use>
              </svg>
            </div>
          </div>
          <div id="blog-detail-img">
            <img :src="data.picture" />
          </div>
          <div id="detail-content">
            {{data.article.replace(/^\"|\"$/g,'')}}
          </div>
        </div>
      </div>
    </vue-scroll>

  </div>
</template>

<script>
import { Toast } from 'mint-ui'
import Vue from 'vue'
import axios from 'axios'
export default {
  name: 'blogPage.vue',
  data () {
    return {
      data: {
        article: '晗杰最美 从前，在南方一块奇异的土地上，有个工人名叫彼得，他非常勤奋，对他的老板总是百依百顺。但是他的老从前，在南方一块奇异的土地上，有个工人名叫彼得，他非常勤奋，对他的老板总是百依百顺。但是他的老从前，在南方一块奇异的土地上，有个工人名叫彼得，他非常勤奋，对他的老板总是百依百顺。但是他的老从前，在南方一块奇异的土地上，有个工人名叫彼得，他非常勤奋，对他的老板总是百依百顺。但是他的老从前，在南方一块奇异的土地上，有个工人名叫彼得，他非常勤奋，对他的老板总是百依百顺。但是他的老从前，在南方一块奇异的土地上，有个工人名叫彼得，他非常勤奋，对他的老板总是百依百顺。但是他的老从前，在南方一块奇异的土地上，有个工人名叫彼得，他非常勤奋，对他的老板总是百依百顺。但是他的老从前，在南方一块奇异的土地上，有个工人名叫彼得，他非常勤奋，对他的老板总是百依百顺。但是他的老',
        blogId: 6,
        favorite: 0,
        picture: 'https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1674770964,3952856125&fm=11&gp=0.jpg',
        time: '2021-05-09 18:22:06',
        title: '博客,这里是博客的名字，可以长这样哦',
        userId: 2,
        user_nickname: '最帅的那个用户',
        user_pic: 'https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1441836571,2166773131&fm=26&gp=0.jpg',
        user_state: 2
      },
      loading: 1,
      loading2: 0
    }
  },
  computed: {
    screenHeight1: () => {
      // const ele = document.getElementById('communityIndex')
      // console.log('communityIndex的高度' + ele.clientHeight)
      const num = parseInt(document.body.clientHeight)
      const str = num + 'px'
      console.log('height="' + str)
      const obj = {
        height: str
      }
      return obj
    }
  },
  methods: {
    getToken () {
      console.log('token')
      Vue.prototype.$axios = axios
      // axios.interceptors.request.use(config => {
      //   config.headers.token = 'eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2IiwiaWF0IjoxNjIzODE4NDA4LCJzdWIiOiIxMzAyMzgzNjU4NyIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyNDA3NzYwOH0.XfLDqPo9_A6uNURwTxNRPzFBBBgoreQgu5Gexc4DleA'
      //   return config
      // })
      const myToken = window.android.getToken(' ')
      console.log(myToken)
      axios.interceptors.request.use(config => {
        config.headers.token = myToken
        return config
      })
    },
    getBlogDetail () {
      this.getToken()
      console.log('获取博客详细信息')
      const that = this
      this.loading = 1
      this.$axios.post('/blog/getDetail', {
        blogId: this.$route.query.blogId
      }, {
        header: {
          'Content-Type': 'application/json' // 如果写成contentType会报错
        }
      }).then(res => {
        console.log(res)
        if (res.data.code === 200) {
          // let temp = {}
          that.data = res.data.data
        } else {
          Toast(res.data.msg)
        }
      }).catch(error => {
        console.log(error)
      }).finally(() => {
        this.loading = 0
      })
    },
    collect () {
      console.log('收藏')
      if (this.data.favorite === 0 || this.data.favorite === 1) {
        this.loading2 = 1
        this.$axios.post('/blog/collect', {
          blogId: this.data.blogId
        }, {
          header: {
            'Content-Type': 'application/json' // 如果写成contentType会报错
          }
        }).then(res => {
          console.log(res)
          if (res.data.code === 200) {
            // let temp = {}
            if (res.data.msg === '收藏成功') {
              this.data.favorite = 1
            } else if (res.data.msg === '删除成功') {
              this.data.favorite = 0
            }
          } else {
            Toast(res.data.msg)
          }
        }).catch(error => {
          console.log(error)
          Toast(error)
        }).finally(() => {
          this.loading2 = 0
        })
      }
    }
  },
  mounted () {
    this.getBlogDetail()
  }
}
</script>

<style scoped lang="less">
  #blogPage {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    background-color: white;
    #myLoding {
      width: 100%;
      height: 100%;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      background-color: #faf7f8;
      .pswp__preloader__icn {
        opacity:0.75;
        width: 60px;
        height: 60px;
        -webkit-animation: clockwise 500ms linear infinite;
        animation: clockwise 500ms linear infinite;
      }
      /* The idea of animating inner circle is based on Polymer loading indicator by Keanu Lee https://blog.keanulee.com/2014/10/20/the-tale-of-three-spinners.html */
      .pswp__preloader__cut {
        position: relative;
        width: 30px;
        height: 60px;
        overflow: hidden;
        position: absolute;
        top: 0;
        left: 0;
      }
      .pswp__preloader__donut {
        box-sizing: border-box;
        width: 60px;
        height: 60px;
        border: 5px solid #4dcfcf;
        border-radius: 50%;
        border-left-color: transparent;
        border-bottom-color: transparent;
        position: absolute;
        top: 0;
        left: 0;
        background: none;
        margin:0;
        -webkit-animation: donut-rotate 1000ms cubic-bezier(.4,0,.22,1) infinite;
        animation: donut-rotate 1000ms cubic-bezier(.4,0,.22,1) infinite;
      }
      @-webkit-keyframes clockwise {
        0% { -webkit-transform: rotate(0deg) }
        100% { -webkit-transform: rotate(360deg) }
      }
      @keyframes clockwise {
        0% { transform: rotate(0deg) }
        100% { transform: rotate(360deg) }
      }
      @-webkit-keyframes donut-rotate {
        0% { -webkit-transform: rotate(0) }
        50% { -webkit-transform: rotate(-140deg) }
        100% { -webkit-transform: rotate(0) }
      }
      @keyframes donut-rotate {
        0% { transform: rotate(0) }
        50% { transform: rotate(-140deg) }
        100% { transform: rotate(0) }
      }
      #loadText {
        margin: 20px 0;
        font-size: 18px;
        font-color: #87a8be;
      }
    }
    #myLoding2 {
      width: 100%;
      height: 100%;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      background-color: transparent;
      position: absolute;
      z-index: 20;
      .pswp__preloader__icn {
        opacity:0.75;
        width: 60px;
        height: 60px;
        -webkit-animation: clockwise 500ms linear infinite;
        animation: clockwise 500ms linear infinite;
      }
      /* The idea of animating inner circle is based on Polymer loading indicator by Keanu Lee https://blog.keanulee.com/2014/10/20/the-tale-of-three-spinners.html */
      .pswp__preloader__cut {
        position: relative;
        width: 30px;
        height: 60px;
        overflow: hidden;
        position: absolute;
        top: 0;
        left: 0;
      }
      .pswp__preloader__donut {
        box-sizing: border-box;
        width: 60px;
        height: 60px;
        border: 5px solid #000000;
        border-radius: 50%;
        border-left-color: transparent;
        border-bottom-color: transparent;
        position: absolute;
        top: 0;
        left: 0;
        background: none;
        margin:0;
        -webkit-animation: donut-rotate 1000ms cubic-bezier(.4,0,.22,1) infinite;
        animation: donut-rotate 1000ms cubic-bezier(.4,0,.22,1) infinite;
      }
      @-webkit-keyframes clockwise {
        0% { -webkit-transform: rotate(0deg) }
        100% { -webkit-transform: rotate(360deg) }
      }
      @keyframes clockwise {
        0% { transform: rotate(0deg) }
        100% { transform: rotate(360deg) }
      }
      @-webkit-keyframes donut-rotate {
        0% { -webkit-transform: rotate(0) }
        50% { -webkit-transform: rotate(-140deg) }
        100% { -webkit-transform: rotate(0) }
      }
      @keyframes donut-rotate {
        0% { transform: rotate(0) }
        50% { transform: rotate(-140deg) }
        100% { transform: rotate(0) }
      }
      /*#loadText2 {*/
      /*  margin: 20px 0;*/
      /*  font-size: 18px;*/
      /*  font-color: #87a8be;*/
      /*}*/
    }
  }
  #blog-nav {
    width:100%;
    height:60px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    background-color: #87a8be;
    color: white;
    position: relative;
    z-index: 2020;
    box-shadow: 1px 0px 10px rgba(3, 0, 24, 0.11);
    #blog-nav-left {
      width: 80%;
      display: flex;
      flex-direction: row;
      justify-content: flex-start;
      align-items: center;
      font-size: 18px;
      #blog-nav-back {
        margin: 10px;
      }
      #blog-nav-info {
        width: 80%;
        display: flex;
        flex-direction: row;
        justify-content: flex-start;
        align-items: center;
        font-weight: 600;
        #blog-nav-avatar {
          width:40px;
          height: 40px;
          border-radius: 50%;
          margin-right: 10px;
          img {
            width:40px;
            height: 40px;
            border-radius: 50%;
          }
        }
        #blog-nav-name {
          max-width: 80%;
          overflow: hidden;/*超出部分隐藏*/
          white-space: nowrap;/*不换行*/
          text-overflow:ellipsis;/*超出部分文字以...显示*/
        }
      }
    }
    #blog-nav-right {
      #blog-nav-btn {
        font-size: 14px;
        padding: 3px 10px;
        margin-right: 10px;
        border: 1px solid white;
        border-radius: 4px;
      }
      #blog-nav-btnInfo {
        font-size: 14px;
        padding: 3px 10px;
        margin-right: 10px;
        border: 1px solid white;
        border-radius: 4px;
      }
    }
  }
  .__vuescroll {
    height: calc(100% - 60px) !important;
  }
  #blog-detail {
    width:100%;
    overflow: visible;
    /*padding-top: 60px;*/
    background-color: white;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    #blog-detail-img {
      width:100%;
      img {
        width:100%;
        object-fit: cover;
      }
    }
    #blog-detail-context {
      width:100%;
      display: flex;
      flex-direction: column;
      justify-content: flex-start;
      align-items: center;
      padding: 10px 10px 20px 10px;
      >div {
        margin: 8px 0;
      }
      #blog-detail-title {
        width: 100%;
        text-align: left;
        font-size: 22px;
        font-weight: 600;
        line-height: 26px;
      }
      #blog-detail-second {
        width:100%;
        display: flex;
        flex-direction: row;
        justify-content: space-between;
        align-items: center;
        font-size: 16px;
        font-weight: 400;
        #detail-love {
          width: 30px;
          height:30px;
          >svg {
            width: 30px;
            height:30px;
          }
        }
      }
      #detail-content {
        text-align: left;
        text-indent: 2em;
        width: 100%;
      }
    }
  }
</style>
