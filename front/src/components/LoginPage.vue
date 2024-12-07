<template>
  <div id="body">
    <div id="loginDiv">
      <form action="file:///D:/web%E8%84%9A%E6%9C%AC/WEB/login.html" id="form">
        <h1 style="text-align: center; color: rgb(72, 77, 87)">
          高校招生录取系统
        </h1>
        <p style="font-weight: 600;color: rgb(72, 77, 87)">
          账号:
          <input v-model="form.userId" type="text" :rules="rules"
                 :onkeyup="form.userPwd = form.userPwd.replace(/''/g, '')"
                 placeholder="请输入账号"
          />
        </p>

        <p style="font-weight: 600; color: rgb(72, 77, 87)">
          密码:
          <input
              v-model="form.userPwd"
              type="password"
              :rules="rules"
              :onkeyup="(form.userPwd = form.userPwd.replace(/''/g, ''))"
              placeholder="请输入密码"
          />
        </p>

        <div style="text-align: center; margin-top: 30px">
          <input
              type="submit"
              class="button"
              id="loginkey"
              @keyup.enter="login"
              @click="login"
              value="登录"
          />
          <br/>
          <button  style="width: 100px" class="btn" @click="exit">返回</button>

        </div>
      </form>

    </div>
  </div>
</template>

<script >
import Cookie from "vue-cookies";
import axios from "axios";
import store from "@/store";
import {ElMessage} from "element-plus";
export default {
  data() {
    return {
      form: {
        userId: "",
        userPwd: "",
      },
      rules: {
        userId: [{ required: true, trigger: "blur", message: '请输入用户名' }],
        userPwd: [{ required: true, trigger: "blur", message: "请输入密码" }],
      },
    };
  },

  methods: {
    exit() {
      this.$router.push('/')
    },
    login() {
      if (this.form.userId === ""||this.form.userPwd === "") {
        // alert('id不能为空')
        this.$message("账号或密码不能为空");
      }
      else {
        axios
            .post("http://localhost:9090/login", {
              userId: this.form.userId,
              userPwd: this.form.userPwd,
            })
            .then((resp) => {
              console.log(resp);

              // 修改弹框样式


              //根据权限进行跳转
              if (resp.data.code === "200") {
                //获取用户名
                // store.commit("getName", resp.data.data.userName);
                sessionStorage.setItem('userName', resp.data.data.userName)
                console.log(store.state.name);
                // let second = 1
                // var expires = new Date(new Date() * 1 + second * 10)
                // window.sessionStorage.setItem('token', resp.data.data.token)
                // Cookie.set('token', resp.data.data.token,{expires:expires})
                Cookie.set("token", resp.data.data.token);
                if (resp.data.data.userRole === 2) {
                  this.$router.push("/workerpage");
                }
                if (resp.data.data.userRole === 1) {
                  this.$router.push("/mainpage/initialize");
                }
                if (resp.data.data.userRole === 3) {
                  this.$router.push("/systempage/main");
                }
              }
              //密码或账号名报错
              if (resp.data.code === "600") {
                this.$message(resp.data.msg);
                // ElMessage.success("账号或密码错误");
              }
            });
      }
    },


  },
};
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
}

#body {
  height: 100%;
  width: 100%;
  overflow: hidden;
  margin: 0;
  padding: 0;
  /*background: url("../assets/img/loginbackground.jpg") ;*/
  background-size: 100% 100%;
  -moz-background-size: 100% 100%;
}

/*#body {*/
/*  display: flex;*/
/*  align-items: center;*/
/*  justify-content: center;*/
/*  height: 100%;*/
/*}a*/

#loginDiv {
  width: 37%;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 300px;
  background-color: rgba(204, 140, 142, 0.2);
  box-shadow: 7px 7px 17px rgb(146 150 161 / 50%);
  border-radius: 5px;
  margin: 15% 0 30% 30%;
}

/*#name_trip {*/
/*  margin-left: 50px;*/
/*  color: red;*/
/*}*/

p {
  margin-top: 30px;
  margin-left: 50px;
  color: azure;
}

input {
  margin-left: 15px;
  border-radius: 5px;
  border-style: hidden;
  height: 30px;
  width: 140px;
  background-color: rgba(215, 190, 215, 0.5);
  outline: none;
  color: #f0edf3;
  padding-left: 10px;
}

.button {
  border-color: cornsilk;
  background-color: rgba(192, 112, 112, 0.30);
  color: #41424d;
  border-style: hidden;
  border-radius: 5px;
  width: 100px;
  height: 31px;
  font-size: 16px;
  text-align: center;
  margin: 0 0 0 0;
  padding-left: 0px;
}

.btn{
  background-color: rgba(192, 112, 112, 0.30);
  color: #41424d;
  margin-top: 10px;
  height: 31px;
  font-size: 16px;
  text-align: center;
}

.bbb{
  margin-top: 180px;
}
</style>







