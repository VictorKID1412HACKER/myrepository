<template>
  <div class="publish-news">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>发布新闻</span>
        </div>
      </template>
      
      <el-form :model="newsForm" :rules="rules" ref="newsFormRef" label-width="100px">
        <el-form-item label="新闻标题" prop="title">
          <el-input v-model="newsForm.title" placeholder="请输入新闻标题"></el-input>
        </el-form-item>
        
        <el-form-item label="新闻分类" prop="category">
          <el-select v-model="newsForm.category" placeholder="请选择新闻分类">
            <el-option
              v-for="item in categories"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        
        <el-form-item label="新闻内容" prop="content">
          <el-input
            v-model="newsForm.content"
            type="textarea"
            :rows="10"
            placeholder="请输入新闻内容">
          </el-input>
        </el-form-item>
        
        <el-form-item>
          <el-button type="primary" @click="submitForm">发布新闻</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

export default {
  name: 'PublishNews',
  setup() {
    const newsFormRef = ref(null)
    
    const newsForm = reactive({
      title: '',
      category: '',
      content: ''
    })
    
    const categories = [
      { value: '1', label: '招生新闻' },
      { value: '2', label: '通知公告' },
      { value: '3', label: '政策解读' }
    ]
    
    const rules = {
      title: [
        { required: true, message: '请输入新闻标题', trigger: 'blur' },
        { min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur' }
      ],
      category: [
        { required: true, message: '请选择新闻分类', trigger: 'change' }
      ],
      content: [
        { required: true, message: '请输入新闻内容', trigger: 'blur' }
      ]
    }
    
    const submitForm = async () => {
      if (!newsFormRef.value) return
      
      await newsFormRef.value.validate((valid) => {
        if (valid) {
          // TODO: 调用后端API发布新闻
          ElMessage.success('新闻发布成功！')
        }
      })
    }
    
    const resetForm = () => {
      if (!newsFormRef.value) return
      newsFormRef.value.resetFields()
    }
    
    return {
      newsForm,
      newsFormRef,
      categories,
      rules,
      submitForm,
      resetForm
    }
  }
}
</script>

<style scoped>
.publish-news {
  padding: 20px;
}
.box-card {
  width: 100%;
  margin: 0 auto;
}
.card-header {
  font-weight: bold;
}
</style> 