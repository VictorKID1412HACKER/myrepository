<!--招生工作人员导航栏-->
<template>
    <div class="nav-box">
  
      <ul class="nav nav-pills nav-stacked col-md-2" style="color: dimgray">
  
        <li v-for="val in list" :key="val.id">
          <router-link :to="{
                      path: val.path
                  }"
                       v-if="val.id === 1">
  
            <span class="glyphicon glyphicon-home"></span>
            {{ val.name }}
  
          </router-link>
  
          <router-link :to="{
                      path: val.path
                  }" v-else >
  
            <span class="glyphicon glyphicon-user"></span>
            {{ val.name }}
  
          </router-link>
        </li>
      </ul>
  
      <router-view class="center"></router-view>
  
  
    </div>
  </template>
  
  <script>
  export default {
    name: 'SystemNav',
  
    data() {
      return {
        list: [
          {
            id: 1,
            name: '系统管理员首页',
            path: '/systempage/main'
          },
          {
            id: 2,
            name: '管理用户及权限',
            path: '/systempage/change'
          }
        ]
      };
    },
  
    mounted() {
  
    },
  
    methods: {
  
    },
  };
  </script>
  
  <style  scoped>
  .nav>li>a:focus {
    background-color: #f6e5e5;
  
    color: #933f3f
  }
  .nav>li>a{
    color: dimgray
  }
  .center {
    position: absolute;
    color: dimgray;
    top: 0;
    bottom: 0;
    left: 15%;
    right: 0;
    margin: auto;
    width: 80%;
    height: 80%;
  }
  </style>