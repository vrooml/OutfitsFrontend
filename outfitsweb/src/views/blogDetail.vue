<template>
  <div id="blogDetail">
    <vue-scroll>
      <div id="blog-detail">

        <div id="blog-detail-context">
          <div id="blog-detail-title">
            <div>{{data.title}}</div>
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
            {{data.article}}
          </div>
        </div>
      </div>
    </vue-scroll>

  </div>
</template>

<script>
import { Toast } from 'mint-ui'
import axios from 'axios'
export default {
  name: 'blogDetail.vue',
  data () {
    return {
      blogId: '',
      data: {
        article: '晗杰最美 从前，在南方一块奇异的土地上，有个工人名叫彼得，他非常勤奋，对他的老板总是百依百顺。但是他的老',
        blogId: 6,
        favorite: 0,
        picture: 'https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1685625409,2887309201&fm=26&gp=0.jpg',
        time: '2021-05-09 18:22:06',
        title: '博客2',
        userId: 2,
        user_nickname: 'sdagsgsgsgsd金',
        user_pic: 'https://dss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2062164223,3783917881&fm=26&gp=0.jpg',
        user_state: 2
      }
    }
  },
  methods: {
    getInfo () {
      // console.log(this.$route.params.blogId)
    },
    getBlogDetail () {
      console.log('token')
      // const token = window.android.getToken()
      axios.interceptors.request.use(config => {
        // config.headers.token = token
        config.headers.token = 'eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI2IiwiaWF0IjoxNjIwODM4MjExLCJzdWIiOiIxMzAyMzgzNjU4NyIsImlzcyI6InJ1aWppbiIsImV4cCI6MTYyMTA5NzQxMX0.n4guRYmW5gmbi5LwrWAqlM9khpdnY08WdUaFIW6GgJk'
        return config
      })
      console.log('blogId')
      const aBlogId = this.$route.query.blogId
      this.blogId = aBlogId
      console.log('获取博客详细信息')
      const that = this
      this.$axios.post('/blog/getDetail', {
        blogId: this.blogId
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
        Toast(error)
      }).finally(() => {
      })
    },
    follow () {
      console.log('关注')
      if (this.data.user_state === 1) {
        Toast('不能关注自己哦')
      } else if (this.data.user_state === 2 || this.data.user_state === 3) {
        console.log('关注用户')
        this.$axios.post('/user/subscribe', {
          userId: this.data.userId
        }, {
          header: {
            'Content-Type': 'application/json' // 如果写成contentType会报错
          }
        }).then(res => {
          console.log(res)
          if (res.data.code === 200) {
            // let temp = {}
            if (res.data.msg === '关注成功') {
              this.data.user_state = 2
            } else if (res.data.msg === '取消关注成功') {
              this.data.user_state = 3
            }
          } else {
            Toast(res.data.msg)
          }
        }).catch(error => {
          console.log(error)
          Toast(error)
        }).finally(() => {
        })
      }
    },
    collect () {
      console.log('收藏')
      if (this.data.favorite === 0 || this.data.favorite === 1) {
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
        })
      }
    },
    getBlogId () {
      const id = this.$route.query.blogId
      console.log(id)
    }
  },
  mounted () {
    this.getBlogDetail()
  }
}
</script>

<style scoped lang="less">
  #blogDetail {
    width: 100%;
    height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
  }
  #blog-detail {
    width:100%;
    min-height: 100%;
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
