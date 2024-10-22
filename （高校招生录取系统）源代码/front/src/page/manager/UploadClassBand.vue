<template>

  <div>

  <div style="margin: 5% 0 0 2%;" >
    <input type="file" ref="file" id="file" @change="updata" style="display: inline-block;">
  </div>


  <span v-if="detail" v-loading="tableLoading || flag">
    <el-table :data="student.msg" border height="470" stripe style="margin-top:12px"
      :header-cell-style="{ background: '#ECF7FF', color: 'black' }">
      <el-table-column align="center" prop="yx" label="学院" />
      <el-table-column align="center" prop="zy" label="专业" />
      <!-- <el-table-column   align="center" prop="kshList" label="考生号" width="180" /> -->
      <el-table-column prop="xssl" align="center" label="班级人数">
      </el-table-column>
      <el-table-column align="center" prop="bjmc" label="班级名称" />
    </el-table>
    <!-- 可复用 -->
    <div style="float: right;margin-top:10px">
      <button class="btn btn-info btn-style" @click="setAuto">确认分班</button>
      <button class="btn btn-info btn-style"  style="margin-left:12px" @click="back">返回</button>
    </div>

    <el-dialog v-model="dialogVisible" title="提示" width="30%">
      <span>{{ informate }}</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="jumpView">是</el-button>
          <el-button type="primary" @click="jumpDetail">否</el-button>
        </span>
      </template>
    </el-dialog>
  </span>

  <el-dialog v-model="fileSUC" title="提示" width="30%">
    <span>入库成功！</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="fileSUC = false" >确定</el-button>
      </span>
    </template>
  </el-dialog>

</div>

</template>

<script setup>
import axios from "axios";
import { reactive, onMounted, toRaw, ref } from "vue";
import { useRouter } from "vue-router";

const detail = ref(false)

const flag = ref(false)

const fileSUC = ref(false)

const dialogVisible = ref(false);

const shiftCheck = ref(false);

const tableLoading = ref(false)



const router = useRouter();

const informate = ref("");

const student = reactive({
  msg: [
    {
      yx: "",
      xy: "",
      bjmc: "",
      kshList: [],
    },
  ],
});

let updata = function (event) {
  flag.value = true
  let file = event.target.files[0];//拿到上传的file
  let param = new FormData();//创建form对象
  param.append("files", file);

  axios({
    headers: { headers: { 'Content-Type': 'multipart/form-data' } },
    url: 'http://localhost:9090/fb/upload/',
    method: "post",
    data: param,
  }).then((res) => {
    console.log(res)
    if (res.status == 200) {
      console.log(res)
      flag.value = false
      detail.value = true
      getAuto()
      fileSUC.value = true
      tableLoading.value = true
    }
  }
  ).catch(function (error) {
    console.log(error)
  })
}



const kshAlist = reactive({
  ksh: {}
})



let arr = reactive([]);


// 获取原始页面的数据
function getAuto() {

  axios.get("http://localhost:9090/fb/automaticShift", {}).then(function (res) {
    console.log("ziDong", res);
    if (res.data.code === "200") {
      student.msg = toRaw(res.data.data);
      flag.value = false;
      tableLoading.value = false
    }
  });
}

//自动分班
function setAuto() {
  flag.value = true;
  axios
    .get("http://localhost:9090/fb/automaticShift/confirm", {})
    .then(function (res) {
      // console.log("jalap",res)
      if (res.data.code === "200") {
        console.log(res)
        flag.value = false;
        dialogVisible.value = true;
        shiftCheck.value = true;
        informate.value = "分班成功,是否跳转到查看分班页面";
      } else {
        flag.value = false;
        dialogVisible.value = true;
        informate.value = "分班失败";
      }
    });
}


function jumpView() {
  dialogVisible.value = false
  if (shiftCheck.value === true) {
    router.push('/mainpage/shiftview');
  }

}

function jumpDetail() {
  dialogVisible.value = false
  if (shiftCheck.value === true) {
    router.push('/mainpage/shiftdetail');
  }

}

</script>

<style scoped>

</style>
