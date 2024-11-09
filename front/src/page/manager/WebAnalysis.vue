<!--审核录取汇总数据-->
<template>
    <div>
    <div style="height:20px;">
      <el-icon><Location /></el-icon>
      <h4 style="display:inline-block;">统计分析报表</h4>
    </div>
    <hr style="margin-bottom:5px">
     
     <el-row :gutter="10">
        <el-col :span="24" >
          <div style="height:500px;width:100%; margin-bottom:80px">
            <el-card>
              <h5 style="font-weight:700;color:slategray;text-align:center">院系录取情况</h5>
              <div id="enroll" style="width:100;height:500px;">
              </div>
             </el-card>
          </div>
          <div style="height:500px;width:100%;">
            <el-card >
              <h5 style="font-weight:700;color:slategray;text-align:center">各省专业录取情况</h5>
              <el-form inline="true">
              <el-form-item label="省份" >
              <el-select v-model="form.sf" clearable  placeholder="请选择省份">
              <el-option
               v-for="item in option.msg"
               :key="item.sfmc"
                :value="item.sfmc"
               />
             </el-select>
              </el-form-item>
              <el-form-item>
              <el-button type="primary" @click="Enroll">获取</el-button>
             </el-form-item> 
            </el-form>
              <div id="sf" style="width:100%;height:400px;">
              </div>
             </el-card>
          </div>
        </el-col>
  
     </el-row>
    </div>
  </template>

<script setup>
import axios from "axios";
import { onMounted, reactive, toRaw } from "vue";
import * as echarts from 'echarts'
onMounted(()=>{
  Admit();
})

const form = reactive({
     sf:''
});

const option = reactive({
  msg: [{ sfmc: "湖南" }, { sfmc: "上海" }, { sfmc: "甘肃" }],
});

const zhu =reactive({
  data:[
    {
     院系名称:'',
      总录取人数:'',
      第一志愿录取人数:'',
      第一志愿录取率:''
    },
  ]
});

const zhu2 =reactive({
  data:[
    {
     专业名称:'',
      录取人数:'',
    },
  ]
});

const zhu21 =reactive({
  data:[
    {
     专业名称:'',
      录取人数:'',
    },
  ]
});


let userData =reactive({
  xData:[],
  series:[],
});

let xOptions =reactive({
  textStyle:{
    color:"#333",
  },
  grid:{
    // left:"20%",
  },
  tooltip:{
    // trigger:"axis",
  },
  xAxis:{
    type:"category",
    data:[],

    axisLabel: {
        
    // ***x轴 文字竖排显示***
    interval: 0,
    formatter: function(value) {
      return value.split("").join("\n");
    }

  },
  },
  yAxis:[
    {
    type:"value",

    }
  ],
  color:["#2ec7c9","#b6a2de","#5ab1ef","ffb980","#d87a80","#8d98b3"],

  series:[]
})

function zhuZhuang (){
  let userRes = zhu.data
userData.xData = userRes.map((item) => item.院系名称);
userData.series=[
  {
    name:"总录取人数",
    data:userRes.map((item)=> item.总录取人数),
    type:"bar",
    barWidth:'30%',
    label:{
          show: true,
          position:'right'
          },
  }
];
xOptions.xAxis.data = userData.xData;
xOptions.series = userData.series;
let userEcharts = echarts.init(document.getElementById("enroll"));
  userEcharts.setOption(xOptions);
}

function zhuZhuang2 (){
  let userRes = zhu21.data
userData.xData = userRes.map((item) => item.专业名称);
userData.series=[
  {
    name:"录取人数",
    data:userRes.map((item)=> item.录取人数),
    type:"bar",
    barWidth:'30%',
    label:{
          show: true,
          position:'right'
          },
  }
];
xOptions.xAxis.data = userData.xData;
xOptions.series = userData.series;
let userEcharts = echarts.init(document.getElementById("sf"));
  userEcharts.setOption(xOptions);
}

function Enroll(){
    axios
    .get("http://localhost:9090/tjfx/firstEnrollRateBySf", {
        params:{
          sf:form.sf
      }
    })
    .then(function (res) {
        console.log('省份',res.data.data);
        zhu2.data = toRaw(res.data.data);
        if(form.sf == '上海'){
           zhu21.data = zhu2.data.slice(0,13)
           zhuZhuang2();
        }
        else if(form.sf == '湖南'){
           zhu21.data = zhu2.data.slice(14,64)
           zhuZhuang2();
        }
        else{
            zhu21.data = zhu2.data.slice(65,103)
           zhuZhuang2();
        }
    });
}

function Admit(){
    axios
    .get("http://localhost:9090/tjfx/firstAdmitRateByYx", {
    })
    .then(function (res) {
      console.log('院系',res.data.data);
      zhu.data = toRaw(res.data.data);
      zhuZhuang();
    });
}

function enrollRate(){
    axios
    .get("http://localhost:9090/tjfx/enrollRateBySf", {
    })
    .then(function (res) {
      console.log("第三个",res);
    });
}

</script>

<style  scoped>

</style>