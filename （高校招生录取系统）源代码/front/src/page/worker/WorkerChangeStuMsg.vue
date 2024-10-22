<!--查询修改录取考生信息-->
<template >
  <div>
    <el-icon>
      <Location />
    </el-icon>
    <h4 style="display:inline-block;">查询修改录取考生信息</h4>
    <!-- <router-link to="/check_2"></router-link> -->
  </div>
  <hr>


  <div class="common-layout" v-loading="load" style="margin-top:15px">
    <el-container>
      <el-header>
        <el-form :inline="true" :model="form">

          <el-form-item label="省份">
            <el-select v-model="searchType.msg.sf" class="m-2" clearable placeholder="请选择省份" @clear="SentSearch">
              <el-option v-for="item in sf.name" :key="item" :label="item" :value="item"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="计划类别">
            <el-select v-model="searchType.msg.lb" class="m-2" clearable placeholder="请选择类别" @clear="SentSearch">
              <el-option v-for="item in option.msg.lb" :key="item.dmmc" :label="item.dmmc"
                :value="item.dmmc"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="院系">
            <el-select v-model="searchType.msg.yx" class="m-2" clearable placeholder="请选择院系" @clear="SentSearch">
              <el-option v-for="item in option.msg.yx" :key="item.dmmc" :label="item.dmmc" :value="item.dmmc"
                @click="sentCollage(clearFlag = true)"></el-option>
            </el-select>
          </el-form-item>


          <el-form-item label="专业">
            <el-select v-model="searchType.msg.zy" class="m-2" clearable placeholder="请选择专业" @clear="SentSearch">
              <el-option v-for="item in option.msg.zy" :key="item.dmmc" :label="item.dmmc"
                :value="item.dmmc"></el-option>
            </el-select>
          </el-form-item>

          <br />

          <el-form-item label="证件号">
            <el-input v-model="searchType.msg.zjh" clearable maxlength="6" placeholder="请输入证件号后六位" class="w-50 m-2"
              @clear="SentSearch" />
          </el-form-item>


          <el-form-item label="姓名">

            <el-input v-model="searchType.msg.xm" clearable placeholder="请输入姓名" class="w-50 m-2" @clear="SentSearch" />


          </el-form-item>

          <div style="float:right ;margin-top: 5px;">
            <el-button type="primary" @click="SentSearch">查询</el-button>
            <el-button type="primary" @click="Reset">重置</el-button>
          </div>

          <!-- <el-form-item style="float:right">

          </el-form-item> -->
        </el-form>
      </el-header>
      <el-main style="margin-top:40px">


        <el-table :data="student.msg" stripe border height="400"
          :header-cell-style="{ background: '#ECF7FF', color: 'black' }" style="width: 100%" :key="reflash">
          <el-table-column prop="xm" label="姓名" align="center">
            <template #default="scope">
              {{ scope.row.xm }}
            </template>
          </el-table-column>
          <el-table-column prop="xb" label="性别" align="center" />
          <el-table-column prop="ksh" label="考生号" align="center" />
          <el-table-column prop="jh" label="招生计划类别" align="center" />
          <el-table-column prop="zy" label="录取专业" align="center" />
          <el-table-column prop="kl" label="科类" align="center" />
          <el-table-column label="操作" align="center">
            <template #default="scope">
              <el-button link type="primary" size="small" @click="view(scope.row)">Details</el-button>
              <el-button link type="primary" size="small" @click="Edit(scope.row)">Edit</el-button>
            </template>
          </el-table-column>
        </el-table>

      </el-main>
    </el-container>


    <!-- 身份证提示弹框 -->
    <el-dialog v-model="check" title="Tips" width="30%">
      <span>{{ msg }}</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button type="primary" @click="check = false">确认</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 查看框 -->
    <el-dialog v-model="dialogFormVisible" title="考生信息" width="80%">

      <div class="half">
        <span v-for="val in dialog" :key="val.ksh" style="display:inline-block ;width:25% ; padding: 15px;">
          {{ val }}
        </span>
      </div>


      <template #footer>
        <span>
          <el-button type="primary" @click="dialogFormVisible = false">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 编辑框 -->
    <el-dialog v-model="editFormDialog" title="编辑信息">


      <table class="table ">
        <tr>
          <td style="font-weight:700">姓名</td>
          <td>{{ editMSG.msg[0].xm }}</td>
        </tr>

        <tr>
          <td style="font-weight:700">考生号</td>
          <td>{{ editMSG.msg[0].ksh }}</td>
        </tr>

        <tr>
          <td style="font-weight:700">计划</td>
          <td>

            <el-select v-model="sentEditMSG.msg.jh" :placeholder="editMSG.msg[0].jh">
              <el-option v-for="val in editOption.msg.jh" :key="val.dm" :lable="val.dmmc" :value="val.dmmc"></el-option>
            </el-select>
          </td>
        </tr>

        <tr>
          <td style="font-weight:700">科类</td>
          <td>

            <el-select v-model="sentEditMSG.msg.kl" :placeholder="editMSG.msg[0].kl">
              <el-option v-for="val in editOption.msg.kl" :key="val.dm" :lable="val.dmmc" :value="val.dmmc"></el-option>
            </el-select>

          </td>
        </tr>

        <tr>
          <td style="font-weight:700">专业</td>
          <td>
            <el-select v-model="sentEditMSG.msg.zy" :placeholder="editMSG.msg[0].zy">
              <el-option v-for="val in editOption.msg.zy" :key="val.dm" :lable="val.dmmc" :value="val.dmmc"></el-option>
            </el-select>
          </td>
        </tr>
      </table>



      <template #footer>
        <span>
          <el-button @click="editFormDialog = false">取消</el-button>
          <el-button type="primary" @click="(sentCheck = true)">确定</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- 修改信息确认 -->
    <el-dialog v-model="sentCheck" title="Tip">
      <span>是否修改考生信息</span>
      <template #footer>
        <span>
          <el-button @click="sentCheck = false">否</el-button>
          <el-button type="primary" @click="sent">是</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog v-model="result" title="Tip">
      <span>{{ informate }}</span>
      <template #footer>
        <span>
          <el-button @click="(result = false, editFormDialog = false)">确认</el-button>
        </span>
      </template>
    </el-dialog>

  </div>
</template>


<script setup>
import { Location } from "@element-plus/icons-vue"
import axios from "axios";
import { reactive, ref, onMounted, watch, computed, toRaw } from 'vue'


//考生信息
const student = reactive({
  msg: ''
})

//清楚标记，用来处理院系改变时的专业清空
const clearFlag = ref(false)

//全部条数
const total = ref('')

//加载判断
const load = ref(false)

//搜索类别
const searchType = reactive({
  msg: {
    lb: '',
    yx: '',
    sf: '',
    zjh: '',
    xm: '',
    zy: ''
  }
})

//下拉框数据
const option = reactive({
  msg: {
    lb: '',
    yx: '',
    sf: '',
    zy: ''
  }
})

//提示信息
const msg = ref('')

//判断身份证弹框显示
const check = ref(false)

//判断查看框显示
const dialogFormVisible = ref(false)

//查看编辑框的显示
const editFormDialog = ref(false)

//省份 
const sf = reactive({
  name: {
    HN: '湖南',
    SH: '上海',
    GS: '甘肃'
  }

})

//详细考生信息
const viewMSG = reactive({
  msg: [
    {

    }
  ]
})

//可编辑的信息
const editMSG = reactive({
  msg: [{
    jh: '',
    zy: '',
    kl: ''
  }]
})

//发送编辑信息
const sentEditMSG = reactive({
  msg: {
    jh: '',
    zy: '',
    kl: ''
  }
})


//获取查看考生的考生号
const viewCheck = ref(1)

//获取编辑考生的考生号
const editCheck = ref(1)


const editOption = reactive({
  msg: {
    jh: '',
    kl: '',
    zy: '',
  }
})

const sentCheck = ref(false)

const result = ref(false)

const informate = ref('')

const tempMSG = reactive({
  msg: {}
})

const MSG = reactive({
  msg: {}
})

const reflash = ref(false)

onMounted(() => {
  getTotal()
  getLb()
  getYx()
})

//获取考生信息
function getStudent() {
  axios.get('/fb', {
    params: {
      current: 1,
      size: 1000
    }
  })
    .then(function (res) {
      student.msg = res.data.data.records
      load.value = false
      // console.log(res.data.data.records)
      // console.log(res.data.data.total)
    })
}

//获取全部信息
function getTotal() {
  axios.get('/fb', {
    params: {
      current: 1,
      size: 1
    }
  })
    .then(function (res) {
      load.value = true
      total.value = res.data.data.total
      getStudent()
    })
}

// 获取类别
function getLb() {
  axios.get('/zd/getAllOptByType', {
    params: {
      type: '计划类别'
    }
  }).then(function (res) {
    console.log(res.data)
    option.msg.lb = res.data
  })
}

// 获取院系
function getYx() {
  axios.get('/zd/getAllOptByType', {
    params: {
      type: '院系'
    }
  }).then(function (res) {
    console.log(res.data)
    option.msg.yx = res.data
  })
}


//发送学院获取专业
function sentCollage() {

  searchType.msg.zy = ''

  axios.get('/zd/getZyByYx', {
    params: {
      yx: searchType.msg.yx,
      year: 2022
    }
  }).then(function (res) {
    option.msg.zy = res.data
  })
}

function DataProcessing() {

  if (searchType.msg.xm.length === 2) {
    searchType.msg.xm = searchType.msg.xm.substr(0, 1) + "*"
  }

  else if (searchType.msg.xm.length > 2) {
    searchType.msg.xm = searchType.msg.xm.substr(0, 1) + "*" + searchType.msg.xm.substr(2, searchType.msg.xm.split('').length)
  }
}

//发送查询
function SentSearch() {

  DataProcessing()


  if (clearFlag.value === true) {
    searchType.msg.zy = ''
  }

  if (searchType.msg.zjh !== '') {
    if (searchType.msg.zjh.length !== 6) {
      msg.value = '请输入六位身份证号'
      check.value = true
      return
    }
  }

  let newZjhm

  //对发送给后端得数据进行处理
  if (searchType.msg.zjh !== '') {
    newZjhm = '**' + searchType.msg.zjh.substring(2, searchType.msg.zjh.length)
  }


  load.value = true


  //发送请求
  axios.get('/fb', {
    params: {
      current: 1,
      size: 1000,
      zjhm: newZjhm,
      xm: searchType.msg.xm,
      jh: searchType.msg.lb,
      yx: searchType.msg.yx,
      zy: searchType.msg.zy,
      sf: searchType.msg.sf
    }
  })
    .then(function (res) {

      student.msg = res.data.data.records
      clearFlag.value = false
      load.value = false
      console.log(res.data)
    })
}

//重置查询
function Reset() {
  searchType.msg.lb = ''
  searchType.msg.yx = ''
  searchType.msg.zjh = ''
  searchType.msg.xm = ''
  searchType.msg.zy = ''

  SentSearch()
}

//查看框的显示
function view(row) {
  dialogFormVisible.value = true
  viewCheck.value = row.ksh
  getView()
  console.log(row)
  viewMSG.msg = row
}

//获取需要查看的人
function getView() {
  axios.get('/ksxx/getKsxxByksh', {
    params: {
      current: 1,
      size: 1,
      ksh: viewCheck.value
    }
  }).then(function (res) {
    // console.log("MSG"+res)
    MSG.msg = res.data.data
    console.log(MSG.msg)

  })
}


//编辑框的查看
function Edit(row) {
  editFormDialog.value = true
  editCheck.value = row.ksh
  tempMSG.msg = row
  getChange()
  getCollage()
  getKL()
  getJH()
}

//获取需要修改的人
function getChange() {
  axios.get('/fb', {
    params: {
      current: 1,
      size: 1,
      ksh: editCheck.value
    }
  }).then(function (res) {
    editMSG.msg = toRaw(res.data.data.records)
    console.log(editMSG.msg)
  })
}

//获取专业
function getCollage() {
  axios.get('/zd/getAllOptByType', {
    params: {
      type: '专业'
    }
  })
    .then(function (res) {
      // console.log(res.data)
      editOption.msg.zy = res.data
    })
}

//获取科类
function getKL() {
  axios.get('/zd/getAllOptByType', {
    params: {
      type: '科类'
    }
  })
    .then(function (res) {
      editOption.msg.kl = res.data
      // console.log(editOption.msg.kl)
    })
}

//获取计划
function getJH() {
  axios.get('/zd/getAllOptByType', {
    params: {
      type: '计划类别'
    }
  })
    .then(function (res) {
      editOption.msg.jh = res.data
      // console.log(editOption.msg.kl)
    })
}


//发送修改信息
function sent() {

  load.value = true

  sentCheck.value = false

  const body = ref({
    ksh: editCheck.value,
    jh: sentEditMSG.msg.jh,
    kl: sentEditMSG.msg.kl,
    zy: sentEditMSG.msg.zy
  })

  axios({
    method: 'post',
    url: '/ksxx/updateStuInfo',
    data: body.value
  }).then(function (res) {
    if (res.data.code === '200') {
      informate.value = '修改成功'
      reflash.value = !reflash.value
    }

    else {
      informate.value = '修改失败'
    }
    load.value = false
    result.value = true
    console.log(editCheck.value)
    console.log(sentEditMSG.msg.jh)
    console.log(sentEditMSG.msg.kl)
    console.log(sentEditMSG.msg.zy)
  })
}


//限制证件号输入格式
watch(() => searchType.msg.zjh, () => {
  searchType.msg.zjh = searchType.msg.zjh.replace(/([^0-9A-z])/g, "")
})

// 限制姓名输入形式
watch(() => searchType.msg.xm, () => {
  searchType.msg.xm = searchType.msg.xm.replace(/([^0-9A-z\u4e00-\u9fa5])/g, "")
})


//考生详细信息
let dialog = computed(() => {
  return {
    xy: "学院:" + viewMSG.msg.xy,
    ksh: '考生号:' + viewMSG.msg.ksh,
    zy: '专业:' + viewMSG.msg.zy,
    bj: '班级:' + viewMSG.msg.bj,
    xm: '姓名:' + viewMSG.msg.xm,
    xb: '性别:' + viewMSG.msg.xb,
    xh: '学号:' + viewMSG.msg.xh,
    kslbdm: '考生类别代码:' + viewMSG.msg.kslbdm,
    jh: "计划:" + viewMSG.msg.jh,
    kl: '科类:' + viewMSG.msg.kl,
    zjhm: '证件号码:' + viewMSG.msg.zjhm,
    lxfs: '联系方式:' + viewMSG.msg.lxdh,
    txdz: '通讯地址:' + viewMSG.msg.txdz,
    // chrq: '出生日期:' + viewMSG.msg.chrq,
    sf: '省份:' + viewMSG.msg.sf,
    cjx: '成绩项:' + viewMSG.msg.cj,
    bylbdm: '毕业类别代码' + MSG.msg.bylbdm,
    byxxdm: '毕业学校代码' + MSG.msg.byxxdm,
    c: '毕业学校名称' + MSG.msg.byxxmc,
    d: '出生日期：' + MSG.msg.csrq,
    e: '残障类别代码：' + MSG.msg.czlbdm,
    f: '地区代码：' + MSG.msg.dqdm,
    g: '户籍代码：' + MSG.msg.hjdm,
    h: '户籍类别代码：' + MSG.msg.hjlbdm,
    i: '计划类别代码：' + MSG.msg.jhlbdm,
    j: '科类代码：' + MSG.msg.kldm,
    k: '考生奖励和处分：' + MSG.msg.ksjlhcf,
    l: '考生类别代码：' + MSG.msg.kslbdm,
    m: '考生类型代码：' + MSG.msg.kslxdm,
    n: '考生特长：' + MSG.msg.kstc,
    o: '考生特征：' + MSG.msg.kstz,
    p: '录取类型代码：' + MSG.msg.lqlxdm,
    q: '录取时间：' + MSG.msg.lqsj,
    r: '录取序号：' + MSG.msg.lqxh,
    s: '录取专业代码：' + MSG.msg.lqzydm,
    x: '联系电话：' + MSG.msg.lxdh,
    y: '联系手机：' + MSG.msg.lxsj,
    z: '民族代码：' + MSG.msg.mzdm,
    k1: '省份代码：' + MSG.msg.sfdm,
    l1: '收件人：' + MSG.msg.sjr,
    m1: '升学评定考核意见：' + MSG.msg.sxpdkhyj,
    n1: '投档成绩：' + MSG.msg.tdcj,
    o1: '投档单编号：' + MSG.msg.ktddbh,
    p1: '投档类型代码：' + MSG.msg.tdlxdm,
    q1: '退档原因代码：' + MSG.msg.tdyydm,
    r1: '通讯地址：' + MSG.msg.txdz,
    s1: '特征成绩：' + MSG.msg.tzcj,
    t1: '外语考试：' + MSG.msg.wyks,
    u1: '外语听力：' + MSG.msg.wytl,
    v1: '外语语种代码：' + MSG.msg.wyyzdm,
    x1: '学号：' + MSG.msg.xh,
    y1: '学籍号：' + MSG.msg.xjh,
    z1: '选考科目：' + MSG.msg.xkkm,
    l2: '应试卷种代码：' + MSG.msg.ysjzdm,
    m2: '邮政编码：' + MSG.msg.yzbm,
    n2: '照顾分：' + MSG.msg.zgf,
    o2: '证件号码：' + MSG.msg.zjhm,
    p2: '证件类型代码：' + MSG.msg.zjlxdm,
    q2: '准考证号：' + MSG.msg.zkzh,
    r2: '政治面貌代码：' + MSG.msg.zzmmdm,
  }
})



</script>

<style scoped>
.half {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  text-align: left;
}

td {
  border: 1px solid #ccc;
  padding: 15px;
  text-align: center;
}
</style>