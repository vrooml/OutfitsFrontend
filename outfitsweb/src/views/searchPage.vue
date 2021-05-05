<template>
  <div id="searchPage" >
    <div id="search-nav">
      <div id="search-nav-back">
        <span @click="toBack()" class="mui-icon mui-icon-arrowleft"></span>
      </div>
      <div id="search-nav-inputBox">
        <input @keyup.enter="search()" v-model="text" id="search-nav-input" placeholder="请输入搜索的标签" />
        <span @click="search()" class="mui-icon mui-icon-search"></span>
      </div>
    </div>
    <div id="search-history" >
      <div class="search-type-title">
        <span class="mui-icon mui-icon-spinner mui-spin"></span>
        <div>搜索历史</div>
      </div>
      <div class="search-type">
        <span class="search-type-item"
              v-for="(item, index) in history"
              :key="index">{{item}}</span>
      </div>
    </div>
    <div id="search-related">
      <div class="search-type-title">
        <span class="mui-icon mui-icon-paperclip"></span>
        <div>相关</div>
      </div>
      <div class="search-type">
        <span class="search-type-item"
              v-for="(item, index) in related"
              :key="index">{{item}}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'searchPage.vue',
  data () {
    return {
      text: '',
      history: ['毛衣', '短袖', '牛仔裤', '工装裤', '毛衣', '短袖', '牛仔裤', '工装裤', '毛衣', '短袖', '牛仔裤', '工装裤'],
      related: ['毛衣', '短袖']
    }
  },
  methods: {
    search () {
      console.log('search')
    },
    toBack () {
      this.$router.back()
    },
    chooseHistory (content) {
      this.text = content
    }
  }
}
</script>

<style scoped lang="less">
  #searchPage {
    width: 100%;
    min-height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    background-color: #faf7f8;
  }
  #search-nav {
    width:100%;
    height: 70px;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    #search-nav-back {
      width: 8%;
    }
    #search-nav-inputBox {
      display: flex;
      flex-direction: row;
      justify-content: space-between;
      align-items: center;
      width: 90%;
      height: 40px;
      background-color: white;
      border: 0.5px solid #9B8F92;
      border-radius: 6px;
      margin: 0 8px;
      #search-nav-input {
        outline: none;
        border: none;
        width: 90%;
        color: #9B8F92;
      }
      *::-webkit-input-placeholder {
        color: #9B8F92;
      }
      *:-moz-placeholder {
        /* FF 4-18 */
        color: #9B8F92;
      }
      *::-moz-placeholder {
        /* FF 19+ */
        color: #9B8F92;
      }
      *:-ms-input-placeholder {
        /* IE 10+ */
        color: #9B8F92;
      }
    }
  }
  #search-history {
    width:90%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    margin: 40px 0 20px 0;
    color: #9B8F92;
  }
  #search-related {
    width: 90%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    margin: 20px 0;
    color: #9B8F92;
  }
  .search-type-title {
    width: 100%;
    display: flex;
    flex-direction: row;
    justify-content: flex-start;
    align-items: center;
    span {
      width:25px;
    }
    >div {
      font-size:20px;
      font-weight: 600;
      margin-left: 10px;
    }
  }
  .search-type {
    width: 100%;
    display: flex;
    flex-wrap: wrap;
    flex-direction: row;
    justify-content: flex-start;
    align-items: flex-start;
    position: relative;
    left: 35px;
    margin: 10px 0;
    color: #AAAAAA;
    >span {
      margin-right: 20px;
    }
  }
</style>
