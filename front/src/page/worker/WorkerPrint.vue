<!--打印通知书，分配备单号-->
<template>
  <br>
  <div>
    <el-icon><Location /></el-icon>
    <h4 style="display: inline-block">打印/备单</h4>
  </div>
  <div style="margin: 10% 0 0 20%;">
    <el-form 
    :label-position="labelPosition"
    label-width="100px"
    :model="formLabelAlign"
    style="max-width: 320px"
    >
      <el-form-item label="省份">
        <el-select v-model="form.sf" clearable  placeholder="请选择省份">
      <el-option
        v-for="item in option.msg"
        :key="item.sfmc"
        :value="item.sfmc"
      />
       </el-select>
      </el-form-item>
      <el-form-item label="EMS起始单号">
        <el-input v-model="form.ems" clearable placeholder="请输入起始单号"></el-input>提示: EG893012340CS
      </el-form-item>
      <el-form-item label="分配数量"> 
        <el-input v-model="form.num" clearable ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="generateEms()" style="font-size:medium;">分配备单号</el-button>
      </el-form-item>
    </el-form>
  </div>

  <el-dialog
    v-model="dialogVisible"
    title=""
    width="30%"
  >
  <span>{{ informate }}</span>
  </el-dialog>

</template>

<script setup>
import axios from "axios";
import { reactive, onMounted, toRaw, ref, proxyRefs} from "vue";
import { Location, Plus, Search } from "@element-plus/icons-vue";
import { ElMessage, ElMessageBox } from "element-plus";

const dialogVisible = ref(false);
const flag = ref(false);
const informate = ref("");

const labelPosition = ref('right')

const form = reactive({
     sf:'',
    ems:'',
    num:''
});

const option = reactive({
  msg: [{ sfmc: "湖南" }, { sfmc: "上海" }, { sfmc: "甘肃" }],
});

const handleClose = (done) => {
  ElMessageBox.confirm("你确定关闭吗?", {
    confirmButtonText: "确定",
    cancelButtonText: "取消",
    type: "warning",
  })
    .then(() => {
      done();
    })
    .catch(() => {
      // catch error
    });
};

const open3 = () => {
  ElMessage({
    message: "请填写必填选项",
    type: "warning",
  });
};

const data = reactive({
  msg: [{}],
});


function generateEms() {
  if (form.nf === "" || form.ems === "") {
    open3();
  }
  else{
    flag.value = true
    axios
    .get("http://localhost:9090/ksxx/generateEms", {
      params:{
        startEms:form.ems,
        sf:form.sf,
        num:form.num
      }
    })
    .then(function (res) {
      console.log("EMS", res);
      if (res.data.code === "200") {
        flag.value = false;
        dialogVisible.value = true;
        informate.value = "已分配EMS单号";
      } else {
        flag.value = false;
        dialogVisible.value = true;
        informate.value = "EMS单号分配失败";
      }
    });
  }
  
}
</script> 

<style scoped>

</style>