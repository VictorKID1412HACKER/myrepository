import axios from "axios";
import Cookie from "vue-cookies";

// 配置默认的host ex:http://*****
axios.defaults.baseURL = 'http://localhost:9090'

axios.defaults.headers.post["Content-Type"] = "application/x-www-form-urlencoded;charset=UTF-8";

// 添加请求拦截器
axios.interceptors.request.use(function (config){
// 在发送请求之前做些什么,比如添加header设置token验证,openid等,eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxOSIsImV4cCI6MTY3NjU0MjIzMn0.7uddHuMnRGb9hMTAvuPoJt2-xFCM68Z1JDfOhIzYNhc
// 发送请求的相关逻辑
  const token = Cookie.get("token")

  if(!token && (config.url == "http://localhost:9090/zsjh" || config.url == "http://localhost:9090/ksxx/liNianLuQu" || config.url == 'http://localhost:9090/fb')){
    config.headers['token'] ='eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxOSIsImV4cCI6MTY3NjU0MjIzMn0.7uddHuMnRGb9hMTAvuPoJt2-xFCM68Z1JDfOhIzYNhc'
  }
  // 判断token存在再做配置
  if (token) {
    config.headers['token'] = token;  
  }
return config
}, function (error) {
// 请求出错
return Promise.reject(error)
});
// 添加响应拦截器
axios.interceptors.response.use(function (response){
// 处理
return response
}, function (error) {
// 对响出错
return Promise.reject(error)
});