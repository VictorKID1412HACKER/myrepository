<template>
  <div>
    <el-icon>
      <Location />
    </el-icon>
    <h4 style="display: inline-block">打印通知书</h4>
  </div>
  <hr style="margin-top: 5px;" />
  <div class="common-layout" v-loading="loading">
    <el-container>
      <el-header>
        <div>
          <el-form :inline="true" :model="form">

            <el-form-item label="计划类别">
              <el-select placeholder="请选择计划类别" clearable v-model="Jhlb.msg.value">

                <el-option v-for="item in jhlb.msg" :key="item.dmmc" :label="item.dmmc" :value="item.dmmc" />

              </el-select>

            </el-form-item>
            <el-form-item label="考生录取专业">
              <el-select placeholder="请选择专业" clearable v-model="Zy.msg.value">
                <el-option v-for="item in zy.msg" :key="item.dmmc" :label="item.dmmc" :value="item.dmmc" />
              </el-select>
            </el-form-item>

            <br/>

            <span>
              <el-form-item label="考生姓名">
                <el-input v-model="StuName" placeholder="请输入考生姓名" />
              </el-form-item>
              <el-form-item style="margin-left: 51px" label="证件号码">
                <el-input placeholder="请输入身份证号后六位" v-model="zjhm" />
              </el-form-item>
            </span>
            <el-form-item style="margin-left: " class="itm">
              <el-button class="btn" type="primary" @click="Search">查询</el-button>
            </el-form-item>
          </el-form>
        </div>
      </el-header>
      <el-main>
        <br><br>

        <el-card class="box-card " style="margin-left: 20% ;display: inline-block;">

          <div style="margin-top: 25px;">

            {{ student.msg.xm }}&nbsp;同学：

            <p style="margin-left: 2em;">
              经2022年全国普通高等学校招生考试，你已被录取到
            </p>
            <p>湖南大学 {{ student.msg.zy }}&nbsp;专业，请持此通知书于8月20日</p>

            <p>全天至8月21日上午来我校报到。</p>
          </div>

          <span style="float:right">校长:段献忠 2022年7月</span>
        </el-card>

        <el-card class="box-card1 " style="margin-left:40px ;display: inline-block;">
          已获取{{ num }}个考生
        </el-card>

      </el-main>
      <el-footer>
        <div class="jiaojiao">
          <div class="l-content">
            <router-link to="/workerpage/workerprint">
              <el-button class="btn" type="primary">返回</el-button>
            </router-link>
          </div>
          <div class="r-content">
            <el-button class="btn" type="primary">打印</el-button>
            <el-button class="btn" type="primary" @click="next">next</el-button>
          </div>
        </div>
      </el-footer>
    </el-container>
  </div>
</template>

<script  setup>
import { Location } from "@element-plus/icons-vue";
import { reactive, onMounted, toRaw, ref, watch } from 'vue';
import axios from 'axios';

const jhlb = reactive({
  msg: {

  }
})

const Jhlb = reactive({
  msg: {
    value: ''
  }
})

const zy = reactive({
  msg: {

  }
})

const Zy = reactive({
  msg: {
    value: ''
  }
})

const StuName = ref('')

const xm = ref('')

const zjhm = ref('')

const loading = ref(false)

const student = reactive({
  msg: [{
    xm: '',
    zy: ''
  }]
})

const sf = reactive({
  msg: [
    {
      lable: '上海',
      value: 'SH'
    },
    {
      lable: '湖南',
      value: 'HN'
    },
    {
      lable: '甘肃',
      value: 'GS'
    },
  ]
})

const SF = ref('')

const temp = reactive({
  msg: []
})

const num = ref('')

let index = 0

onMounted(() => {
  getJhlb()
  getZy()
  // getInfo()
})

function getJhlb() {
  axios.get('/zd/getAllOptByType', {
    params: {
      type: "计划类别"
    }
  }).then(function (res) {
    // console.log(res.data)
    jhlb.msg = toRaw(res.data)
  })
}

function getZy() {
  axios.get('/zd/getAllOptByType', {
    params: {
      type: "专业"
    }
  }).then(function (res) {
    // console.log(res.data)
    zy.msg = toRaw(res.data)
  })
}

// 数据传输处理
function DataProcessing() {

  if (StuName.value.length === 2) {
    xm.value = StuName.value.substr(0, 1) + "*"

  }

  else if (StuName.value.length > 2) {
    xm.value = StuName.value.substr(0, 1) + "*" + StuName.value.substr(2, StuName.value.split('').length)

  }

}

function Search() {

  let newZjhm

  if (zjhm.value !== '') {
    let newZjhm = '**' + zjhm.value.substring(2, zjhm.value.length)
  }

  index = 0

  DataProcessing()

  axios.get('/fb', {
    params: {
      current: 1, //当前页面
      size: 1000,//页面信息条数
      zjhm: newZjhm,
      xm: xm.value,
      jh: Jhlb.msg.value,
      // sf: SF.value,
      zy: Zy.msg.value,
    }
  })
    .then(function (res) {
      console.log(xm.value)

      temp.msg = res.data.data.records

      console.log(temp.msg)

      num.value = res.data.data.total

      if (res.data.data.total === 0) {
        student.msg.xm = ''
        student.msg.zy = ''
        console.log("change")
      }
      else{
        student.msg.xm = temp.msg[0].xm
        student.msg.zy = temp.msg[0].zy
      }

    }
    )
}

function next() {
  index++

  student.msg.xm = temp.msg[index].xm

  student.msg.zy = temp.msg[index].zy

  console.log(index)

  console.log(student.msg.xm)
  console.log(student.msg.zy)
}

//监视证件号不能为空格
watch(() => zjhm.value, () => {
  zjhm.value = zjhm.value.replace(/\s/g, "")

})

watch(() => StuName.value, () => {
  StuName.value = StuName.value.replace(/\s/g, "")
})
</script>


<style scoped>
.jiaojiao {
  padding: 0 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: aliceblue;
}

.btn {
  width: 80px;
  height: 35px;
}

.text {
  font-size: 14px;
}

.item {
  padding: 18px 0;
}

.box-card {
  width: 480px;
  height: 300px;
  align-items: center;
}

p {
  line-height: 2em;
}

.box-card1 {
  width: 150px;
  height: 150px;
  align-items: center;
  line-height: 100px;
}
</style>

