<template>
  <div class="chatbot-container">
    <h1>智能问答助手</h1>
    <div id="coze-chat-container"></div>
    <button class="home-button" @click="goHome">返回首页</button>
  </div>
</template>

<script>
export default {
  name: 'ChatPage',
  methods: {
    goHome() {
      this.$router.push('/')
    }
  },
  mounted() {
    const script = document.createElement('script')
    script.src = 'https://lf-cdn.coze.cn/obj/unpkg/flow-platform/chat-app-sdk/1.0.0-beta.4/libs/cn/index.js'
    script.onload = () => {
      const cozeWebSDK = new window.CozeWebSDK.WebChatClient({
        container: '#coze-chat-container',
        config: {
          botId: '7443828604372516915'
        },
        auth: {
          type: 'oauth',
          clientId: '1131880959856',
          clientSecret: '92796182798835505796120279797203.app.coze'
        },
        ui: {
          chatBot: {
            title: "智能客服",
            uploadable: true,
            position: 'center'
          }
        }
      })
    }
    document.head.appendChild(script)
  },
  beforeUnmount() {
    const script = document.querySelector('script[src*="coze-chat-sdk"]')
    if (script) {
      script.remove()
    }
  }
}
</script>

<style scoped>
.chatbot-container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  height: 100vh;
  background-color: #f5f5f5;
}

#coze-chat-container {
  width: 100%;
  height: calc(100vh - 150px);
  max-width: 800px;
  margin: 20px 0;
  border: 1px solid #eee;
  border-radius: 8px;
  box-shadow: 0 2px 5px rgba(0,0,0,0.1);
  background-color: white;
}

.home-button {
  margin-top: 20px;
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
  transition: background-color 0.3s;
}

.home-button:hover {
  background-color: #45a049;
}

h1 {
  color: #333;
  margin-bottom: 20px;
  font-size: 24px;
}
</style>