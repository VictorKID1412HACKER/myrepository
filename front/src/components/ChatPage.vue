<template>
    <div class="chatbot-container">
      <h1>智能问答助手</h1>
      <div id="position_demo"></div>
      <button class="home-button" @click="goHome">返回首页</button>
    </div>
  </template>
  
  <script>
  // 在文件顶部声明 CozeWebSDK 为全局变量
  /* global CozeWebSDK */
  
  export default {
    name: 'ChatBot',
    data() {
      return {
        question: '',
        chatRecord: [{ sender: "AI:", message: "您好，主人！有什么需要向我提问的吗？"}],
        cozeWebSDK: null, // 用于存储 SDK 实例
      }
    },
    mounted() {
      // 动态加载 SDK
      const script = document.createElement('script');
      script.src = "https://lf-cdn.coze.cn/obj/unpkg/flow-platform/chat-app-sdk/1.0.0-beta.4/libs/cn/index.js"; // 加载 CozeWebSDK
      script.onload = () => {
        // SDK 加载后初始化
        this.cozeWebSDK = new CozeWebSDK.WebChatClient({
          config: {
            botId: '7432576937199370291', // 替换为你的 botId
          },
          auth: {
            type: 'token',
            token: 'pat_nu43DwZx7jGaZ3iQM10b8ZU9Wjp2hauVdYbjXLHTWKESOMuV41vQdpfuvJNymr4Q', // 替换为你的 token
            onRefreshToken: () => {
              return 'pat_nu43DwZx7jGaZ3iQM10b8ZU9Wjp2hauVdYbjXLHTWKESOMuV41vQdpfuvJNymr4Q'; // 处理 token 刷新逻辑
            },
          },
        });
  
        // 默认显示聊天窗口
        this.cozeWebSDK.showChatBot();
      };
      document.head.appendChild(script); // 把 script 标签加到 head 中
    },
    methods: {
      goHome() {
        this.$router.push({ name: 'homepage' }); // Navigates to the home page
      }
    }
  };
  </script>
  
  <style scoped>
  /* 自定义样式 */
  .chatbot-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    padding: 20px;
    background-color: #f5f5f5;
    border-radius: 8px;
    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
  }
  
  #position_demo {
    position: absolute;
    right: 10px;
    bottom: 60px; /* 给按钮留出空间 */
  }
  
  .home-button {
    margin-top: 20px;
    padding: 10px 20px;
    background-color: #4CAF50; /* 绿色背景 */
    color: white;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
  }
  
  .home-button:hover {
    background-color: #45a049; /* 鼠标悬停效果 */
  }
  </style>