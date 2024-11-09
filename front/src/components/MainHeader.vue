<!--招生负责人和工作人员顶栏-->
<template>
    <div class="head">
      <span style="margin: 5px 0 0 0">
        <img src="../assets/img/HNU.png" alt="error" style="margin-left:10px">
        <label style="font-size:15px">高校招生系统</label>
        <div class="right">
          <span class="glyphicon glyphicon-user"></span>
          欢迎您，{{ name }}
          <span class="glyphicon glyphicon-share-alt"></span>
          <button class="btn btn-style" type="button" @click="exit">退出登录</button>
        </div>
      </span>
    </div>
</template>

<script>
import Cookie from 'vue-cookies'
export default {
    name: 'MainHeader',

    data() {
        return {
            name: sessionStorage.getItem('userName')
        };
    },

    mounted() {
    },

    methods: {
        exit() { 
            this.$router.push('/LoginPage')
            Cookie.remove('token')
        }
    },
};
</script>

<style  scoped>
* {
    padding: 0;
    margin: 0;
}

.head {
    height: 55px;
    background-color: rgba(149, 30, 34);
    line-height: 50px;
    color: white;
}

img {
    height: 45px;
    width: 45px;
    border-radius: 45px;
    margin-right: 10px;
}

.right {
    float: right;
    margin-right: 50px;
}

.right span {
    margin-right: 10px;
    margin-left: 15px;
}

.btn-style {
    width: auto;
    background-color: rgba(149, 30, 34);
}
</style>