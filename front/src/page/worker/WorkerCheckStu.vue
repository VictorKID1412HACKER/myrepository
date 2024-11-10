<!--上载考生信息-->
<template>
  <div>
    <div>
    <el-icon><Location /></el-icon>
    <h4 style="display:inline-block;">上传录取考生信息</h4>
    <!-- <router-link to="/check_2"></router-link> -->
    </div>
  <hr>


    <div class="file-load">
      <label>省份</label>
      <el-select v-model="Data.sf" clearable placeholder="选择省份" @change="getsflbke">
        <el-option  v-for="item in province "
                    :key="item.id"
                    :value="item.sfname"
        >
        </el-option>
      </el-select>

      <label>计划类别</label>
      <el-select v-model="Data.jhlb" clearable placeholder="选择类别" @change="getsflbke">
        <el-option  v-for="item in plankinds.msg "
                    :key="item.dm"
                    :value="item.dmmc"
        >
        </el-option>
      </el-select>

      <label>科类</label>
      <el-select v-model="Data.kl" clearable placeholder="选择科类" @change="getsflbke">
        <el-option  v-for="item in kl.msg "
                    :key="item.dm"
                    :value="item.dmmc"
        >
        </el-option>
      </el-select>

    </div>

    <el-upload
        style="margin-left:15%; margin-top: 20px; margin-bottom: 40px;"
        action=""
        ref="upload"
        :on-remove="handleRemove"
        :show-file-list="true"
        :limit=3
        multiple
        :auto-upload="false"
        v-model:file-list="fileList"
        :http-request="uploading"
    >
<!--      <el-button class="btn-style">上传数据</el-button>-->
      <template #tip>
        <div class="el-upload__tip">
          提示：请按照该省份的计划类别分类上传——只需上传t_tdd表、t_bmk表，（若该省无bmk表则无需上传）
        </div>
      </template>
    </el-upload>
    <el-button @click="UploadSubmit" class="btn-style"
               style="margin-left:15%; margin-bottom: 40px;"
    >上传数据</el-button>

<!--    照片上传-->
    <div style="margin-left:15%">
      <label>省份</label>
      <el-select v-model="uploadZP.sf" clearable placeholder="选择省份" @change="getsflbke">
        <el-option  v-for="item in province "
                    :key="item.id"
                    :value="item.sfname"
        >
        </el-option>
      </el-select>

      <label>照片类型</label>
    <el-select v-model="Data.zpName" clearable placeholder="选择类型" >
        <el-option  v-for="item in picture.msg "
                    :key="item.name"
                    :value="item.name"
        >
        </el-option>
      </el-select>
    </div>

    <el-upload
        style="margin-left:15%; margin-top: 20px; margin-bottom: 40px;"
        v-model:file-list="fileList2"
        ref="upload"
        action=""
        :auto-upload="false"
        :show-file-list="false"
        accept="image/png,image/gif,image/jpg,image/jpeg"
        :http-request="uploading"
        multiple
        :on-remove="handleRemove"
        :limit="500"
    >
<!--      <el-button class="btn-style">上传照片</el-button>-->

      <template #tip>
        <div class="el-upload__tip">
          提示：请完成该省份所有计划类别的考生数据上传后再上传照片

        </div>
      </template>
    </el-upload>
    <el-button @click="UploadPicture" class="btn-style"
               style="margin-left:15%; margin-bottom: 40px;"
    >上传照片</el-button>

  </div>
  <el-dialog
    v-model="dialogVisible"
    title="提示"
    width="30%"
   center
  >
    <span style="margin-left:40%">上载成功</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button style="margin-right:12px" type="danger" @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="onekey">
          一键审核
        </el-button>
      </span>
    </template>

  </el-dialog>

  <el-dialog
    v-model="innerVisible"
    title="提示"
    width="30%"
    append-to-body
    :before-close="handleClose"
  >
    <span style="margin-left:20%">该部分考生数据入库成功</span>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="show">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>

  <el-dialog
    v-model="dialogVisible2"
    title="核对考生待审核信息"
    width="70%"
    hight="60%"
  >
  <el-table :data="tableData.msg" stripe height="500" style="width: 100%">
    <el-table-column fixed prop="xm" label="姓名" width="60" />
    <el-table-column prop="ksh" label="考生号" width="90" />
    <el-table-column prop="zkzh" label="准考证号" width="100" />
    <el-table-column prop="xbdm" label="性别代码" width="80" />
    <el-table-column prop="csrq" label="出生日期" width="80" />
    <el-table-column prop="zzmmdm" label="政治面貌代码" width="120" />
    <el-table-column prop="mzdm" label="民族代码" width="80" />
    <el-table-column prop="sfdm" label="省份代码" width="80" />
    <el-table-column prop="kslxdm" label="考生类型代码" width="150" />
    <el-table-column prop="kslbdm" label="考生类别代码" width="150" />
    <el-table-column prop="bylbdm" label="毕业类别代码" width="150" />
    <el-table-column prop="byxxdm" label="毕业学校代码" width="120" />
    <el-table-column prop="byxxmc" label="毕业学校名称" width="120" />
    <el-table-column prop="ysjzdm" label="应试卷种代码" width="50" />
    <el-table-column prop="wyyzdm" label="外语语种代码" width="80" />
    <el-table-column prop="kstc" label="考生特征" width="80" />
    <el-table-column prop="bmdwdm" label="报名单位代码" width="80" />
    <el-table-column prop="dqdm" label="地区代码" width="180" />
    <el-table-column prop="zjlxdm" label="证件类型代码" width="40" />
    <el-table-column prop="zjhm" label="证件号码" width="150" />
    <el-table-column prop="xjh" label="学籍号" width="60" />
    <el-table-column prop="wyks" label="外语口试" width="50" />
    <el-table-column prop="wytl" label="外语听力" width="50" />
    <el-table-column prop="txdz" label="通讯地址" width="150" />
    <el-table-column prop="yzbm" label="邮政编码" width="80" />
    <el-table-column prop="lxsj" label="联系手机" width="120" />
    <el-table-column prop="lxdh" label="联系电话" width="120" />
    <el-table-column prop="sjr" label="收件人" width="100" />
    <el-table-column prop="kstc" label="考生特长" width="50" />
    <el-table-column prop="ksjlhcf" label="考生奖励和处分" width="250" />
    <el-table-column prop="sxpdkhyj" label="升学评定考核意见" width="250" />
    <el-table-column prop="hjlbdm" label="户籍类别代码" width="50" />
    <el-table-column prop="czlbdm" label="残障类别代码" width="50" />
    <el-table-column prop="xkkm" label="选考科目" width="40" />
    <el-table-column prop="tddwdm" label="投档单位代码" width="50" />
    <el-table-column prop="pcdm" label="批次代码" width="50" />
    <el-table-column prop="kldm" label="科类代码" width="70" />
    <el-table-column prop="jhxzdm" label="计划性质代码" width="50" />
    <el-table-column prop="jhlbdm" label="计划类别代码" width="70" />
    <el-table-column prop="tdlxdm" label="投档类型代码" width="50" />
    <el-table-column prop="cj" label="成绩" width="50" />
    <el-table-column prop="tzcj" label="特征成绩" width="55" />
    <el-table-column prop="tdcj" label="投档成绩" width="55" />
    <el-table-column prop="zgf" label="照顾分" width="50" />
    <el-table-column prop="lqzydm" label="录取专业代码" width="150" />
    <el-table-column prop="lqsj" label="录取时间" width="180" />
    <el-table-column prop="lqlxdm" label="录取类型代码" width="50" />
    <el-table-column prop="tdyydm" label="退档原因代码" width="50" />
    <el-table-column prop="zyhg" label="专业合格" width="80" />
    <el-table-column prop="tyhg" label="体验合格" width="80" />
    <el-table-column prop="xkkmjg" label="选考科目及格" width="70" />
    <el-table-column prop="lqxh" label="录取序号" width="50" />
    <el-table-column prop="tddbh" label="投档单编号" width="60" />
  </el-table>
  <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="dialogVisible2=false">
          确认
        </el-button>
      </span>
    </template>
  </el-dialog>

</template>

<script setup>

import axios from "axios";
import { ElMessage } from "element-plus";
import {onMounted, reactive, ref, toRaw} from 'vue'

const dialogVisible = ref(false);
const dialogVisible2 =ref(false)
const innerVisible =ref(false)

onMounted (() =>{
  getFormData();
})

const picture =reactive({
  msg:[
    {name:'考生照片'},
    {name:'档案照片1'},
    {name:'档案照片2'},
    {name:'档案照片3'}
  ]
})

let pictureNum = 0;

const Data = reactive({
  sf:'',
  jhlb:'',
  kl:'',
  zpName:''
})

const uploadZP = reactive({
  sf:'',
  jhlb:'',
  kl:'',
  zpName:''
})

const tableData =reactive({
  msg:[{}]
})
const province = [
        {
          id: '1',
          sfname: '上海',
        },
        {
          id: '2',
          sfname: '湖南',
        },
        {
          id: '3',
          sfname: '甘肃',
        },
      ]

const plankinds = reactive({
    msg:[{}]
})

const kl = reactive({
     msg:[{}]
})

const fileList2 = ref([]);

const fileList = ref([]);

function UploadSubmit(){
  
  if(Data.sf===''||Data.jhlb===''||Data.kl===''|| fileList.value==''){
    ElMessage({
    message: '请选择必要选项.',
    type: 'warning',
  })
  }
  else{
    console.log('文件',fileList.value)

const anang = toRaw(fileList.value);

var file_form = new FormData();

for(let i=0;i<anang.length;i++){
  file_form.append("files",anang[i].raw)
}

  file_form.append("sf",Data.sf)
  console.log('集合',file_form);
  axios({
    headers:{'Content-Type': 'multipart/form-data'},
    url: 'http://localhost:9090/ksxx/upload',
    method: "post",
    data: file_form
  }).then((res) => {
        console.log(res);
       if(res.data.code == 200){
        dialogVisible.value = true
       }else if(res.data.code == 401){
         ElMessage.error(res.data.msg)
       }
       else if(res.data.code == 501){
         ElMessage.error(res.data.msg)
       }
       else{
        ElMessage.error('入库失败')
       }
      })
  }
  
}

function getFormData(){
  axios.get('http://localhost:9090/zd/getAllOptByType', {
    params: {
      type: '计划类别'
    }
  }).then(function (res) {
    console.log(res.data)
    plankinds.msg = toRaw(res.data)
  })
  axios.get('http://localhost:9090/zd/getAllOptByType', {
    params: {
      type: '科类'
    }
  }).then(function (res) {
    console.log(res.data)
    kl.msg = toRaw(res.data)
    console.log('科类',kl.msg)
  })
}

function onekey(){
  dialogVisible.value = false
  axios.get('http://localhost:9090/ksxx/audit', {
    params: {
      sf:Data.sf,
      jhlb:Data.jhlb,
      kl:Data.kl
    }
  }).then(function (res) {
    console.log(res.data)
    if(res.data.code == 200){
      innerVisible.value= true
    }else if(res.data.code == 501){
      ElMessage.success(res.data.msg)
    } else{
      ElMessage.success(res.data.msg)
    }
  })
}

function show(){
  innerVisible.value = false
  dialogVisible2.value = true
  axios.get('http://localhost:9090/ksxx/checkKsxx', {
    params: {
      sf:Data.sf,
      jhlb:Data.jhlb,
      kl:Data.kl
    }
  }).then(function (res) {
    console.log(res.data)
    tableData.msg = toRaw(res.data.data)
    pictureNum=res.data.data.length
    console.log(pictureNum)
  })
}

function UploadPicture(){
  const anang = toRaw(fileList2.value);

  axios.get('/ksxx/getSfKsxxAmount',{
    params:{
      sf:'uploadZP.sf'
    }
  }).then(function(res) {
    pictureNum = res.data
  })

  console.log('入库考生数量',pictureNum)

var file_form2 = new FormData();


for(let i=0;i<anang.length;i++){
  file_form2.append("files",anang[i].raw)
  console.log('照片',anang[i].raw);
}


  file_form2.append("zplx",Data.zpName)
  console.log('集合',file_form2);
  axios({
    url: 'http://localhost:9090/ksxx/uploadPhoto',
    method: "post",
    data: file_form2
  }).then((res) => {
        console.log(res);
       if(res.data.code == 200){
        ElMessage.success('照片上传成功')
       }
       else{
        ElMessage.error('照片上传失败')
       }
      })
  }


function handleRemove(file, fileList) {
  console.log(file, fileList)
}

</script>

<style  scoped>
h2{
  margin-left: 600px;
}
.search-box {
  font-size: 16px;
  margin: 100px 0 0 0;
  height: 10%;
}

label {
  margin: 10px;
  font-size: 18px;
  height: 20px;
}

.input-style {
  display: inline;
  width: 15%;

  font-size: 16px;
}

.btn-style {
  background-color: rgb(149, 30, 34);
  color: white;
  height: 60%;
  margin-left: 20px;
  margin-right: 20px;
}
.file-load{
  margin-left:15%;
  margin-top: 50px;
}
.upload-demo{
  margin-left:15%;
  margin-top: 20px;
  margin-bottom: 40px;
}

.el-table{
  align-items: center;
}
</style>