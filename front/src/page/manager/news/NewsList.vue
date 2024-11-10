<template>
  <div class="news-list">
    <el-card class="box-card">
      <template #header>
        <div class="card-header">
          <span>新闻列表</span>
          <el-button type="primary" @click="$router.push('/mainpage/publishnews')">发布新闻</el-button>
        </div>
      </template>
      
      <el-table :data="newsList" style="width: 100%">
        <el-table-column prop="title" label="标题" width="280"></el-table-column>
        <el-table-column prop="category" label="分类" width="120"></el-table-column>
        <el-table-column prop="createTime" label="发布时间" width="180"></el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.status === '已发布' ? 'success' : 'info'">
              {{ scope.row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template #default="scope">
            <el-button size="small" @click="handleEdit(scope.row)">编辑</el-button>
            <el-button size="small" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      
      <div class="pagination">
        <el-pagination
          v-model:current-page="currentPage"
          v-model:page-size="pageSize"
          :page-sizes="[10, 20, 30, 50]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
  </div>
</template>

<script>
import { ref } from 'vue'
import { ElMessageBox, ElMessage } from 'element-plus'

export default {
  name: 'NewsList',
  setup() {
    const currentPage = ref(1)
    const pageSize = ref(10)
    const total = ref(100)
    
    // 模拟数据
    const newsList = ref([
      {
        id: 1,
        title: '2024年招生计划公布',
        category: '招生新闻',
        createTime: '2024-03-20 10:00:00',
        status: '已发布'
      },
      // 更多数据...
    ])
    
    const handleEdit = (row) => {
      console.log('编辑', row)
    }
    
    const handleDelete = (row) => {
      ElMessageBox.confirm(
        '确定要删除这条新闻吗？',
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
    
    const handleSizeChange = (val) => {
      pageSize.value = val
      // TODO: 重新加载数据
    }
    
    const handleCurrentChange = (val) => {
      currentPage.value = val
      // TODO: 重新加载数据
    }
    
    return {
      currentPage,
      pageSize,
      total,
      newsList,
      handleEdit,
      handleDelete,
      handleSizeChange,
      handleCurrentChange
    }
  }
}
</script>

<style scoped>
.news-list {
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
.pagination {
  margin-top: 20px;
  text-align: right;
}
</style> 