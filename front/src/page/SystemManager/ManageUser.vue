<!-- eslint-disable no-undef -->
<!--打印通知书，分配备单号-->
<template>
  <br />
  <div>
    <el-icon><Location /></el-icon>
    <h4 style="display: inline-block">管理用户及权限</h4>
  </div>
  <div
    class="common-layout"
    style="margin-left: 20%; margin-top: 30px; width: 70%;"
  >
    <el-container>
      <el-header>
        <el-form :inline="true" style="width: 100%">
          <el-form-item label="账号">
            <el-input v-model="SearchId.userId" />
          </el-form-item>
          <el-form-item style="margin-left: 25px">
            <el-button type="primary">
              <el-icon style="vertical-align: middle">
                <Search />
              </el-icon>
              <span style="vertical-align: middle" @click="IdSearch()">
                搜索</span
              >
            </el-button>
          </el-form-item>
          <el-form-item style="margin-left: 25px">
            <el-button type="primary" @click="dialogFormVisible = true">
              <el-icon style="vertical-align: middle">
                <Plus />
              </el-icon>
              <span style="vertical-align: middle"> 增加</span>
            </el-button>
          </el-form-item>
          <el-form-item style="margin-left: 25px;">
            <el-button type="primary" @click="getData()" >
              <el-icon style="vertical-align: middle">
                <Refresh />
              </el-icon>
              <span style="vertical-align: middle"> 刷新</span>
            </el-button>
          </el-form-item>
        </el-form>
      </el-header>
      <el-main>
        <el-table :data="data.msg" style="width: 100%">
          <el-table-column prop="id" label="ID"></el-table-column>
          <el-table-column prop="userName" label="姓名"></el-table-column>
          <el-table-column prop="userId" label="账号"></el-table-column>
          <el-table-column prop="userPwd" label="密码"></el-table-column>
          <el-table-column prop="userRole" label="角色"></el-table-column>
          <el-table-column label="操作" width="200">
            <template #default="scope">
              <el-button type="primary" @click="handleData(scope.row)"
                >
                <el-icon style="vertical-align: middle">
                <Edit />
              </el-icon>
              <span style="vertical-align: middle"> 编辑</span>
              </el-button
              >
              <el-button type="danger" @click="deleteData(scope.row)"
                >
                <el-icon style="vertical-align: middle">
                <Delete />
              </el-icon>
              <span style="vertical-align: middle"> 删除</span>
               </el-button
              >
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>
  </div>

  <el-dialog
    v-model="dialogFormVisible"
    title="新增用户"
    :before-close="handleClose"
  >
    <el-form :model="form" :inline="true" ref="userForm">
      <el-form-item
        label="用户名"
        prop="userName"
        :rules="[
          {
            required: true,
            message: '用户名是必填项',
          },
        ]"
      >
        <el-input
          v-model="form.userName"
          autocomplete="off"
          placeholder="请输入用户名"
        />
      </el-form-item>
      <el-form-item
        label="账号"
        prop="userId"
        :rules="[
          {
            required: true,
            message: '账号是必填项',
            trigger: 'blur',
          },
        ]"
      >
        <el-input
          v-model="form.userId"
          autocomplete="off"
          placeholder="请输入账号"
        />
      </el-form-item>
      <el-form-item
        label="密码"
        style="margin-left: 14px"
        prop="userPwd"
        :rules="[
          {
            required: true,
            message: '密码是必填项',
            trigger: 'blur',
          },
        ]"
      >
        <el-input
          v-model="form.userPwd"
          autocomplete="off"
          placeholder="请输入密码"
        />
      </el-form-item>
      <el-form-item
        label="角色"
        prop="userRole"
        :rules="[
          {
            required: true,
            message: '角色是必填项',
            trigger: 'blur',
          },
          { type: 'number', message: '角色必须是数字' },
          { pattern: /^(1|[1-4]\d?|4)$/,message: '范围在1-4', trigger: 'blur'}
        ]"
      >
        <el-input
          v-model.number="form.userRole"
          autocomplete="off"
          placeholder="请输入角色"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="handleCancel()">取消</el-button>
        <el-button type="primary" @click="Add()"> 确认 </el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog
    v-model="dialogFormVisible2"
    title="编辑用户"
    :before-close="handleClose"
  >
    <el-form :model="form2" :inline="true" ref="userForm2"
      >&nbsp;&nbsp;&nbsp;
      <el-form-item label="ID">
        <el-input v-model="form2.id" disabled autocomplete="off" />
      </el-form-item>
      <el-form-item
        label="用户名"
        prop="userName"
        :rules="[
          {
            required: true,
            message: '用户名是必填项',
            trigger: 'blur',
          },
        ]"
      >
        <el-input
          v-model="form2.userName"
          autocomplete="off"
          placeholder="请输入用户名"
        />
      </el-form-item>
      <el-form-item
        label="账号"
        prop="userId"
        :rules="[
          {
            required: true,
            message: '账号是必填项',
            trigger: 'blur',
          },
        ]"
      >
        <el-input
          v-model="form2.userId"
          autocomplete="off"
          placeholder="请输入账号"
        />
      </el-form-item>
      <el-form-item
        label="密码"
        prop="userPwd"
        style="margin-left: 15px"
        :rules="[
          {
            required: true,
            message: '密码是必填项',
            trigger: 'blur',
          },
        ]"
      >
        <el-input
          v-model="form2.userPwd"
          autocomplete="off"
          placeholder="请输入密码"
        />
      </el-form-item>
      <el-form-item
        label="角色"
        prop="userRole"
        :rules="[
          {
            required: true,
            message: '角色是必填项',
            trigger: 'blur',
          },
        ]"
      >
        <el-input
          v-model="form2.userRole"
          autocomplete="off"
          placeholder="请输入角色"
        />
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogCancel()">取消</el-button>
        <el-button type="primary" @click="editData()"> 确认 </el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import axios from "axios";
import { reactive, onMounted, toRaw, ref, proxyRefs } from "vue";
import { Location, Plus, Search, Refresh,Delete,Edit} from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";

import { getCurrentInstance } from "vue";
const { proxy } = getCurrentInstance();

const dialogFormVisible = ref(false);

const dialogFormVisible2 = ref(false);
// const formLabelWidth = '140px'
const SearchId = reactive({
  userId: "",
});

const form = reactive({
  userId: "",
  userName: "",
  userPwd: "",
  userRole: "",
});

const form2 = reactive({
  userId: "",
  userName: "",
  userPwd: "",
  userRole: "",
  id: "",
});

const handleClose = (done) => {
  ElMessageBox.confirm("你确定关闭吗?", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      done();
      proxy.$refs.userForm.resetFields();
    })
    .catch(() => {
      // catch error
    });
};

const data = reactive({
  msg: [{}],
});

onMounted(() => {
  getData();
});

//获取原始页面的数据
function getData() {
  if (SearchId.userId !== "") {
    SearchId.userId = null;
  }
  // flag.value = true;
  axios.get("http://localhost:9090/getUserList", {}).then(function (res) {
    data.msg = toRaw(res.data.data);
  });
}

function deleteData(row) {
  ElMessageBox.confirm("你确定删除吗?", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      axios
        .get("http://localhost:9090/deleteUser", {
          params: {
            id: row.id,
          },
        })
        .then(function (res) {
          getData();
        });

      ElMessage.success({
        message: "删除成功!",
      });
    })
    .catch(() => {
      // catch error
      ElMessage.info({
        message: "已取消删除",
      });
    });
}

function Add() {
  if (
    form.userId == "" ||
    form.userName == "" ||
    form.userPwd == "" ||
    form.userRole == ""
  ) {
    ElMessage.warning({
      message: "请输入必填选项",
    });
  } 
  else if(form.userRole > 4 ||form.userRole<=0){
    ElMessage.warning({
      message: "角色必须输入1-4之间的数"
    });
   }
  else {
    axios
      .post("http://localhost:9090/addUser", {
        userId: form.userId,
        userName: form.userName,
        userPwd: form.userPwd,
        userRole: form.userRole,
      })
      .then(function (res) {
        console.log(res)
        if(res.data.code === "200"){
          ElMessage.success({
            message:"添加成功"
          }),
          dialogFormVisible.value = false;
          form.userId='',form.userName='',form.userPwd='',form.userRole='',
          proxy.$refs.userForm.resetFields();        
          getData();
        }
        if(res.data.code == "500"){
          ElMessage.success({
            message:res.data.msg
          })
        }
      });
  }

}

function handleData(row) {
  dialogFormVisible2.value = true;
  Object.assign(form2, row);
}

function editData(){
  if (
    form2.userId == "" ||
    form2.userName == "" ||
    form2.userPwd == "" ||
    form2.userRole == ""
  ) {
    ElMessage.warning({
      message: "请输入必填选项",
    });
  }
  else{
    axios
        .post("http://localhost:9090/updateUser", {
          id: form2.id,
          userId: form2.userId,
          userName: form2.userName,
          userPwd: form2.userPwd,
          userRole: form2.userRole,
        })
        .then(function (res) {
          getData();
        });
      dialogFormVisible2.value = false;
      ElMessage.success({
        message: "修改成功",
      }); 
      proxy.$refs.userForm2.resetFields();
  }

}

const handleCancel = () => {
  dialogFormVisible.value = false;
  proxy.$refs.userForm.resetFields();
};

function dialogCancel()
{
  dialogFormVisible2.value = false
  proxy.$refs.userForm2.resetFields();
}
function IdSearch() {
  var user = [];
  if (SearchId.userId == "") {
    if (data.msg.length == 1) {
      getData();
    } else {
      ElMessage.warning({
        message: "请输入账号",
      });
    }
  } else {
    axios
      .get("http://localhost:9090/getUser", {
        params: {
          userId: SearchId.userId,
        },
      })
      .then(function (res) {
        if (res.data.code == 200) {
          user.push(res.data.data);
          (data.msg = user);
        } else {
          ElMessage.warning({
            message: "查询失败，请输入正确信息",
          });
        }
      });
  }
}
</script> 
  
  <style scoped>
</style>