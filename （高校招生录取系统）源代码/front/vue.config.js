
module.exports = {
  devServer: {
    port: 8080, //前端端口--随意，通过Nginx反向代理后都一样
    //host: 'localhost', //主机地址
    open: true,
    proxy: {
      '/api': {
        target: 'http://localhost:9090', //接口域名
        changeOrigin: true,       //是否跨域
        ws: true,            //是否代理 websockets
        secure: true,          //是否https接口
        pathRewrite: {         //路径重置
          '^/api': ''
        },
        ['/api/']:'',
      }
    }
  }
};