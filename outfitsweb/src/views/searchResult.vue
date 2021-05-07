<template>
  <div id="searchResult">
    <div id="index-nav">
      <div id="index-nav-tab">
        <div class="index-nav-tabItem"
             :class="{ index_nav_tabItem_choose: tabType === 1 }"
             @click="setTabType(1)">关注</div>
        <div class="index-nav-tabItem"
             :class="{ index_nav_tabItem_choose: tabType === 2 }"
             @click="setTabType(2)">推荐</div>
      </div>
      <div id="index-nav-search">
        <span class="mui-icon mui-icon-search" @click="toSearch()"></span>
      </div>
    </div>
    <div id="index-content">
    </div>
  </div>
</template>

<script>
export default {
  name: 'searchResult'
}
</script>

<style scoped lang="less">
  #communityIndex {
    width: 100%;
    min-height: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: flex-start;
    background-color: #faf7f8;
  }
  #index-nav{
    width: 100%;
    height: 60px;
    background-color: #87a8be;
    color: white;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    font-size: 18px;
    position: fixed;
    z-index: 2020;
    #index-nav-tab {
      min-width: 50%;
      display: flex;
      flex-direction: row;
      justify-content: flex-start;
      align-items: center;
      .index-nav-tabItem {
        margin:0 20px;
      }
      .index_nav_tabItem_choose {
        font-weight: 600;
      }
    }
    #index-nav-search {
      margin: 0 20px;
    }
  }
  #index-content {
    width:100%;
    background-color: transparent;
    padding-top: 60px;
  }
</style>
