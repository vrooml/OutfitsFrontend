<template>
  <div id="blogDetails">
    <div id="blog-detail">

      <div id="blog-detail-context">
        <div id="blog-detail-title">
          <div>今天找到一家超好看的店铺！！！今天找到一家超好看的店铺！！！今天找到一家超好看的店铺！！！今天找到一家超好看的店铺！！！</div>
        </div>
        <div id="blog-detail-second">
          <div id="detail-time">2021年4月7日</div>
          <div id="detail-love">
            <span class="mui-icon mui-icon-chatbubble"></span>
          </div>
        </div>
        <div id="blog-detail-img">
          <img src="https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1685625409,2887309201&fm=26&gp=0.jpg" />
        </div>
        <div id="detail-content">
          {{content}}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'blogDetail.vue',
  data () {
    return {
      content: '从前，在南方一块奇异的土地上，有个工人名叫彼得，他非常勤奋，对他的老板总是百依百顺。但是他的老板是个吝啬的人，从不信任别人，坚决要求随 时知道彼得的工作进度，以防止他偷懒。但是彼得又不想让老板呆在他的办公室里站在背后盯着他，于是就对老板做出承诺：无论何时，只要我的工作取得了一点进 展我都会及时让你知道。彼得通过周期性地使用“带类型的引用”(原文为：“typed reference” 也就是delegate？？)“回调”他的老板来实现他的承诺，如下'
    }
  }
}
</script>

<style scoped lang="less">
  #blogDetails {
    width: 100%;
    min-height: 100%;
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
    padding: 20px 10px;
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
  }
  #detail-content {
    text-align: left;
    text-indent: 2em;
  }
  }
  }
</style>
