<template>
  <div class="news-category">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>新闻分类管理</span>
          <el-button type="primary" @click="dialogVisible = true">添加分类</el-button>
        </div>
      </template>
      
      <el-table :data="categories" style="width: 100%">
        <el-table-column prop="name" label="分类名称" width="180"></el-table-column>
        <el-table-column prop="description" label="描述"></el-table-column>
        <el-table-column prop="count" label="新闻数量" width="100"></el-table-column>
        <el-table-column label="操作" width="200">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button 
              size="small" 
              type="danger" 
              @click="handleDelete(scope.row)"
              :disabled="scope.row.count > 0">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- 添加/编辑分类对话框 -->
    <el-dialog
      v-model="dialogVisible"
      :title="isEdit ? '编辑分类' : '添加分类'"
      width="500px"
    >
      <el-form :model="categoryForm" :rules="rules" ref="categoryFormRef" label-width="100px">
        <el-form-item label="分类名称" prop="name">
          <el-input v-model="categoryForm.name"></el-input>
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input type="textarea" v-model="categoryForm.description"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="submitForm">确定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { ref, reactive } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'

export default {
  name: 'NewsCategory',
  setup() {
    const dialogVisible = ref(false)
    const isEdit = ref(false)
    const categoryFormRef = ref(null)
    
    const categoryForm = reactive({
      name: '',
      description: ''
    })
    
    // 模拟数据
    const categories = ref([
      {
        id: 1,
        name: '招生新闻',
        description: '关于学校招生的新闻',
        count: 10
      },
      {
        id: 2,
        name: '通知公告',
        description: '学校通知公告',
        count: 5
      }
    ])
    
    const rules = {
      name: [
        { required: true, message: '请输入分类名称', trigger: 'blur' },
        { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
      ],
      description: [
        { required: true, message: '请输入分类描述', trigger: 'blur' }
      ]
    }
    
    const handleEdit = (row) => {
      isEdit.value = true
      categoryForm.name = row.name
      categoryForm.description = row.description
      dialogVisible.value = true
    }
    
    const handleDelete = (row) => {
      ElMessageBox.confirm(
        '确定要删除这个分类吗？',
        '警告',
        {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        }
      ).then(() => {
        // TODO: 调用删除API
        ElMessage.success('删除成功')
      })
    }
    
    const submitForm = async () => {
      if (!categoryFormRef.value) return
      
      await categoryFormRef.value.validate((valid) => {
        if (valid) {
          // TODO: 调用添加/编辑API
          ElMessage.success(isEdit.value ? '编辑成功' : '添加成功')
          dialogVisible.value = false
        }
      })
    }
    
    return {
      dialogVisible,
      isEdit,
      categoryForm,
      categoryFormRef,
      categories,
      rules,
      handleEdit,
      handleDelete,
      submitForm
    }
  }
}
</script>

<style scoped>
.news-category {
  padding: 20px;
}
.box-card {
  width: 100%;
}
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}
</style> 