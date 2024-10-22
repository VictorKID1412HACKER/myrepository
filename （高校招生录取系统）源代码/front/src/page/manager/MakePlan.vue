<template>
  <div v-loading="">

    <!-- 搜索框 -->

    <label>计划类别</label>

    <el-select v-model="get.dmmc" class="m-2" clearable placeholder="请选择计划类别" @clear="toSearch">
      <el-option v-for="item in option.msg" :key="item.dmmc" :label="item.dmmc" :value="item.dmmc" />
    </el-select>

    <label>院系</label>

    <el-select v-model="get.yx" clearable class="m-2" placeholder="请选择院系" @clear="toSearch(clearFlag = true)">
      <el-option v-for="item in option1.msg" :key="item.dmmc" :label="item.dmmc" :value="item.dmmc"
        @click="sentCollage" />
    </el-select>

    <el-button type="primary" :icon="Search" round @click="toSearch()" style="margin:15px 0 15px 10px">搜索</el-button>
    <el-button type="primary" :icon="Search" round @click="reset" style="margin:15px 0 15px 10px">重置</el-button>

    <!-- 表格 -->
    <el-table :data="user.msg" border height="450" stripe :header-cell-style="{ background: '#ECF7FF', color: 'black' }">
      <el-table-column align="center" prop="xy" label="学院" width="180" />
      <el-table-column align="center" prop="zydh" label="代码" />
      <el-table-column align="center" prop="zymc" label="专业名称" width="180" />
      <el-table-column align="center" prop="kl" label="科类" />
      <el-table-column align="center" prop="zyjhrs" label="招生总人数" />


      <el-table-column align="center" prop="SH" label="上海" >
        <template v-slot="scope">
          <el-input v-model="scope.row.sfdmAndsfjhrs['31']"   type="text" @change="abmitSH(scope.row)" onkeyup="value=value.replace(/[^\d\.]/g,'')"></el-input>
        </template>
      </el-table-column>

      <el-table-column align="center" prop="HN" label="湖南">
        <template v-slot="scope">
          <el-input v-model="scope.row.sfdmAndsfjhrs['43']" type="text" @change="abmitHN(scope.row)" onkeyup="value=value.replace(/[^\d\.]/g,'')"></el-input>
        </template>
      </el-table-column>

      <el-table-column align="center" prop="GS" label="甘肃">
        <template v-slot="scope">
          <el-input v-model="scope.row.sfdmAndsfjhrs['62']" type="text" @change="abmitGS(scope.row)" onkeyup="value=value.replace(/[^\d\.]/g,'')"></el-input>
        </template>
      </el-table-column>


    </el-table>

  </div>
</template>



<script  setup>
import axios from 'axios';

import { reactive, onMounted, toRaw, ref } from 'vue';

import { ElMessage } from 'element-plus'

const user = reactive({
  msg: [{
  }]
})

const other = reactive({
  size: 1000,
  current: 1,
  total: 1000,
  page: 1
})

const get = reactive({
  dmmc: '',
  dmmc1: '',
  yx: ''
})
const option = reactive({
  msg: []
})

const option1 = reactive({
  msg: []
})

// 搜索后分页判断
// const PageFlag = ref(false)

const load = ref(false)

const clearFlag = ref(false)

const ZyOption = reactive({
  msg: ''
})

onMounted(() => {
  getInfo()
  // getTotal()
  getLB()
  getCollage()
})

//获得初始数据 SH:64 GS:56 HN:56
function getInfo() {
  axios.get('/zsjh/searchWithoutPagnition', {
    params: {
      size: 1000,
      // sf: '甘肃'
      // yx:'信息科学与工程学院'
      nf: 2022
    }
  })
    .then(function (res) {
      console.log("use")
      user.msg = toRaw(res.data.data)
      console.log(res.data)
      console.log("user" + user.msg)
      load.value = false
      // other.total = Math.ceil(response.data.data.total / other.size)
    }, error => {
      console.log('错误', error.message)
    })
}

function toSearch() {
  if (clearFlag.value === true) {
    get.dmmc1 = ''
  }
  axios.get('/zsjh/searchWithoutPagnition', {
    params: {
      yx: get.yx,
      // zy:get.dmmc1,
      jh: get.dmmc,
      nf: 2022
    }
  })
    .then(function (res) {
      console.log(get.dmmc)
      user.msg = toRaw(res.data.data);
      console.log(res.data);
      console.log("data" + user.msg);
      clearFlag.value = false;
    })
}

//获取全部计划类别
function getLB() {
  axios.get('http://localhost:9090/zd/getAllOptByType', {
    params: {
      type: "计划类别"
    }
  })
    .then(function (res) {
      {
        console.log(toRaw(res.data))
        option.msg = toRaw(res.data)
      }
    })
}

// 获取院系
function getCollage() {
  axios.get('http://localhost:9090/zd/getAllOptByType', {
    params: {
      type: '院系'
    }
  })
    .then(function (res) {
      // console.log(res.data)
      option1.msg = res.data
    })
}

// 重置查询
function reset() {
  get.dmmc = ''
  // get.dmmc1 = ''
  get.yx = '',

    axios.get('http://localhost:9090/zsjh', {
      params: {
        current: 1,
        size: other.size,
        // sf:'上海',
        jh: get.dmmc,
        yx: get.yx,
      }
    })
      .then(function (res) {
        user.msg = toRaw(res.data.data.records)
        // other.total = Math.ceil(res.data.data.total /other.size)
        other.page = 1
      })

}

function abmitSH(row) {

  const text = ref({
    year: 2022,
    zydh: row.zydh,
    kldm: row.kldm,
    zyjhrs: row.zyjhrs,
    sfdm: 31,
    sfjhrs: row.sfdmAndsfjhrs['31'],
    jhlbdm: row.jhlbdm
  })

  axios({
    method: 'post',
    url: '/zsjh/makePlan',
    data: text.value
  }).then(function (res) {
    // console.log(res.data)
    // console.log(text.value)
    if (res.data.code == 400) {
      // console.log(123)
      ElMessage({
        // showClose: true,
        message: ' 修改失败，请先选择具体计划类别',
        type: 'error',
      })
    }
    else {
      ElMessage({
        // showClose: true,
        message: ' 修改成功',
        type: 'success',
      })
    }
  })
}

function abmitHN(row) {

  const text = ref({
    year: 2022,
    zydh: row.zydh,
    kldm: row.kldm,
    zyjhrs: row.zyjhrs,
    sfdm: 31,
    sfjhrs: row.sfdmAndsfjhrs['43'],
    jhlbdm: row.jhlbdm
  })

  axios({
    method: 'post',
    url: '/zsjh/makePlan',
    data: text.value
  }).then(function (res) {
    // console.log(res)
    // console.log(text.value)
    // console.log(res.data.code)
    if (res.data.code == 400) {
      // console.log(123)
      ElMessage({
        // showClose: true,
        message: ' 修改失败，请先选择具体计划类别',
        type: 'error',
      })
    }
    else{
      ElMessage({
        // showClose: true,
        message: ' 修改成功',
        type: 'success',
      })
    }
  })
}

function abmitGS(row) {

  const text = ref({
    year: 2022,
    zydh: row.zydh,
    kldm: row.kldm,
    zyjhrs: row.zyjhrs,
    sfdm: 31,
    sfjhrs: row.sfdmAndsfjhrs['62'],
    jhlbdm: row.jhlbdm
  })

  axios({
    method: 'post',
    url: '/zsjh/makePlan',
    data: text.value
  }).then(function (res) {
    if (res.data.code == 400) {
      ElMessage({
        message: ' 修改失败，请先选择具体计划类别',
        type: 'error',
      })
    }
    else {
      ElMessage({
        message: ' 修改成功',
        type: 'success',
      })
    }
  })
}

function use(row){
  console.log(row.sfdmAndsfjhrs['31'])
}


//  


</script>

<style  scoped>
label {
  margin: 5px;
  line-height: 20px;
  font-size: 18px;
}

.m-2 {
  width: 20%;
}
</style>