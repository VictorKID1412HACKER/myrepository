<template><!--系统首页-->
  <div class="home-container">
    <WebHeader></WebHeader>
    <div class="ability">
      <button class="btn btn-color" @click="ToSearchPlan" style="background-color:rgba(149, 30, 34)">
        <span class="glyphicon glyphicon-search"></span>
        &nbsp;历年招生计划查询&nbsp;
        <span class="glyphicon glyphicon-menu-right"></span>
      </button>

      <button class="btn btn-color" @click="ToSearchEnroll" style="background-color:rgba(0, 129, 101)">
        <span class="glyphicon glyphicon-list-alt"></span>
        &nbsp;历年录取情况查询&nbsp;
        <span class="glyphicon glyphicon-menu-right"></span>
      </button>

      <button class="btn btn-color" @click="ToEnroll" style="background-color:rgba(0, 71, 128)">
        <span class="glyphicon glyphicon-education"></span>
        &nbsp;当年录取考生查询&nbsp;
        <span class="glyphicon glyphicon-menu-right"></span>
      </button>

      <button class="btn btn-color" @click="ToChat" style="background-color:rgba(65, 105, 225)">
        <span class="glyphicon glyphicon-comment"></span>
        &nbsp;智能咨询助手&nbsp;
        <span class="glyphicon glyphicon-menu-right"></span>
      </button>

      <button @click="toLogin" class="btn btn-default btn-last">
        管理人员登录
      </button>
    </div>
  </div>
</template>

<script>
import WebHeader from '@/components/WebHeader.vue';
import ChatPage from '@/components/ChatPage.vue'

export default {
  name: "HomePage",
  data() {
    return {};
  },
  mounted() {
  },
  methods: {
    toLogin() {
      this.$router.push({
        path:'/loginpage'
      })
    },
    ToSearchPlan() {
      this.$router.push({
        path: '/searchplan'
      })
    },
    ToSearchEnroll() {
      this.$router.push({
        path: '/searchenroll'
      })
    },
    ToEnroll() {
      this.$router.push({
        path: '/enrollPage'
      })
    },
    ToChat() {
      this.$router.push({
        path: '/chatpage'
      })
    },
    

  },
  components: { WebHeader }
};
</script>

<style  scoped>
.ability {
  display: flex;
  flex-wrap: nowrap;
  align-items: center;
  justify-content: center;
  position: absolute;
  top: 20%;
  bottom: 0;
  left: 0;
  right: 0;
  margin: auto;
  height: 50%;
  width: 90%;
  background-color: rgba(255, 255, 255, 0.853);
}
.btn-color {
  color: aliceblue;
  width: 300px;
  padding: 15px;
  height: 75px;
  margin: 50px;
  font-size: 20px;
  line-height: 20px;
}

.btn_group {
  width: 100%;
  display: flex;
  flex-wrap: nowrap;
  align-items: center;
  justify-content: center;
}

.btn-last {
  position: absolute;
  bottom: 15px;
  right: 15px;
  padding: 10px;
  height: 60px;
  font-size: 20px;
  line-height: 20px;
}

</style>
