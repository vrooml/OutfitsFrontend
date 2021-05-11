<template>
  <div id="interfaceTest">
    <button type="button" class="btn mui-btn mui-btn-primary" @click="getMyBlogInfo">订阅博客</button>
    <button type="button" class="btn mui-btn mui-btn-primary" @click="getBlogInfo">全部博客</button>
    <button type="button" class="btn mui-btn mui-btn-primary" @click="search">搜索博客</button>
    <button type="button" class="btn mui-btn mui-btn-primary" @click="getBlogDetail">获取博客详细信息</button>
  </div>
</template>

<script>
import { Toast } from 'mint-ui'

export default {
  name: 'interfaceTest.vue',
  data () {
    return {
      tab: true,
      blocks: []
    }
  },
  methods: {
    getMyBlogInfo () {
      console.log('获取订阅用户的博客')
      const that = this
      this.$axios.post('/blog/getSubscription', {
      }, {
        header: {
          'Content-Type': 'application/json' // 如果写成contentType会报错
        }
      }).then(res => {
        console.log(res)
        if (res.data.code === 200) {
          // let temp = {}
          that.blocks = that.blocks.concat(res.data.data)
        }
      }).catch(error => {
        console.log(error)
      }).finally(() => {
      })
    },
    getBlogInfo () {
      console.log('获取所有博客信息')
      const that = this
      this.$axios.post('/blog/getAll', {
      }, {
        header: {
          'Content-Type': 'application/json' // 如果写成contentType会报错
        }
      }).then(res => {
        console.log(res)
        if (res.data.code === 200) {
          // let temp = {}
          that.blocks = that.blocks.concat(res.data.data)
        }
      }).catch(error => {
        console.log(error)
      }).finally(() => {
      })
    },
    search () {
      console.log('搜索博客')
      // const that = this
      this.$axios.post('/blog/search', {
        keyword: '博客'
      }, {
        header: {
          'Content-Type': 'application/json' // 如果写成contentType会报错
        }
      }).then(res => {
        console.log(res)
        if (res.data.code === 200) {
          // let temp = {}
        }
      }).catch(error => {
        console.log(error)
      }).finally(() => {
      })
    },
    getBlogDetail () {
      console.log('获取博客详细信息')
      // const that = this
      this.$axios.post('/blog/getDetail', {
        blogId: 6
      }, {
        header: {
          'Content-Type': 'application/json' // 如果写成contentType会报错
        }
      }).then(res => {
        console.log(res)
        if (res.data.code === 200) {
          // let temp = {}
        } else {
          Toast(res.data.msg)
        }
      }).catch(error => {
        console.log(error)
      }).finally(() => {
      })
    }
  }
}
</script>

<style scoped lang="less">
.btn{
  display: block;
  margin: 20px;
  font-size: 18px;
}
</style>
