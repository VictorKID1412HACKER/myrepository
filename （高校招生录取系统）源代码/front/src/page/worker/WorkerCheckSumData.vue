<!--审核录取汇总数据-->
<template>
  <div style="height:20px;">
    <el-icon><Location /></el-icon>
    <h4 style="display:inline-block;">汇总录取数据</h4>
    <!-- <router-link to="/check_2"></router-link> -->
  </div>
  <hr style="margin-bottom:5px">
   
   <el-row :gutter="20">
    <el-col :span="16" >
      <el-card >
        <el-form :inline="true" :model="form">
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
          <el-button type="primary" @click="Search">汇总</el-button>
        </el-form-item> 
        <el-form-item style="margin-left:45px;">
           <div style="">
            <el-card
            :body-style="{display:'flex',padding: 0}"
            >
            <span style="background:turquoise;width:50px;text-align:center;font-size:x-large;padding-top:4px;"><el-icon><StarFilled/></el-icon></span>
             <div style="width:80px;padding-top:3px;height:25px">
              <p style="font-weight:600;font-size:300;:slategray;margin-left:2px;">总人数:{{ form.num }}</p>
             </div>
            </el-card>
           </div>
        </el-form-item>
        </el-form>
        <el-table :data="data.msg" style="width:100%" height="435">
        <el-table-column fixed prop="zymc" label="专业名称" width="180" />
        <el-table-column prop="jhlb" label="计划类别" width="100" />
        <el-table-column prop="kl" label="科类" width="100" />
        <el-table-column prop="num" label="录取人数" width="100" />
        <el-table-column prop="maxS" label="最高分" width="70" />
        <el-table-column prop="minS" label="最低分" width="70" />
        <el-table-column prop="avgS" label="平均分" width="70" />

       </el-table>
      </el-card>
     
      </el-col>

      <el-col :span="8" >
        <div style="height:150px;width:100%; margin-bottom:100px">
          <el-card>
            <h5 style="font-weight:700;color:slategray;text-align:center">三省录取情况</h5>
            <div id="main" style="width:353px;height:130px;">
            </div>
            <h5 style="font-weight:600;color:slategray;margin-left:30px;"> 录取总人数:&nbsp;{{ form.sum }}</h5>
           </el-card>
        </div>
        <div style="height:200px;width:100%;">
          <el-card >
            <h5 style="font-weight:700;color:slategray;text-align:center">热冷门专业情况</h5>
            <div id="user" style="width:353px;height:206px;">
            </div>
           </el-card>
        </div>
      </el-col>

   </el-row>

</template>

<script setup>
import axios from "axios";
import { reactive, ref,toRaw, onMounted,nextTick} from "vue";
import { ElMessage, ElMessageBox } from "element-plus";
import * as echarts from 'echarts'

const dialogVisible = ref(false);
var flag=ref(true)
const form = reactive({
     sf:'',
     nf:'2022',
     num:'',
     sum:''
});

const option = reactive({
  msg: [{ sfmc: "湖南" }, { sfmc: "上海" }, { sfmc: "甘肃" }],
});

 onMounted(() => {
   getData();
   getZhu();
   zhuZhuang ();
})

const open3 = () => {
  ElMessage({
    message: "请填写必填选项",
    type: "warning",
  });
};

const data = reactive({
  msg: [{}],
});

//饼状图动态数据
const test =reactive({
  data:[
        {
          value:'',
          name:"湖南"
        },
        {
          value:'',
          name:"上海"
        },
        {
          value:'',
          name:"甘肃"
        }]
});


//柱状图动态


//柱状图

let userData =reactive({
  xData:[],
  series:[],
});

//饼状图的
let pieOptions = reactive({
  tooltip:{
    trigger:'item',
    confine:true,
    formatter: "({d}%)"
  },
  color:[
    "#0f78f4",
    "#dd536b",
    "#9462e5",
    "#a6a6a6",
    "#e1bb22",
    "#39c362",
    "#3ed1cf",
  ],
  series:[],
});
//数据
let videoData =reactive({
  series:[],
});

function getData(){
  let sum2=0;
   for(let i=0; i<=3;i++){
    let sum=0;
    if(i<3){
      axios
    .get("http://localhost:9090/ksxx/liNianLuQu", {
      params:{
          year: form.nf,
          current: 1,
          size: 100,
          sf: option.msg[i].sfmc,
      }
    })
    .then(function (res) {
      console.log(res);
      for(let i=0;i<res.data.data.total;i++){
       sum = sum + res.data.data.records[i].num
      }
      test.data[i].value=sum;
      sum2=sum2+test.data[i].value;
      console.log('ababab',sum2)
      form.sum=sum2
      console.log(test.data[i].value)
      doEchart();
    });
    }
   }
    // axios
    // .get("http://localhost:9090/ksxx/liNianLuQu", {
    //   params:{
    //       year: form.nf,
    //       current: 1,
    //       size: 100,
    //       sf: "上海",
    //   }
    // })
    // .then(function (res) {
    //   console.log(res);
    //   test.data[1].value=res.data.data.total
    // });
    // axios
    // .get("http://localhost:9090/ksxx/liNianLuQu", {
    //   params:{
    //       year: form.nf,
    //       current: 1,
    //       size: 100,
    //       sf: "湖南",
    //   }
    // })
    // .then(function (res) {
    //   console.log(res);
    //   test.data[0].value=res.data.data.total
    // });
    // axios
    // .get("http://localhost:9090/ksxx/liNianLuQu", {
    //   params:{
    //       year: form.nf,
    //       current: 1,
    //       size: 100,
    //       sf: "甘肃",
    //   }
    // })
    // .then(function (res) {
    //   console.log(res);
    //   test.data[2].value=res.data.data.total
    // });
}

// function sleep1(numberMillis){    
//         var now = new Date();    
//         var exitTime = now.getTime() + numberMillis;   
//         while (flag.value) { 
//           now = new Date();       
//           if (now.getTime() > exitTime) return;
//         }     
//       }
 
async function doEchart(){

//饼状图
let videoRes = test.data;
  videoData.series = [
    {
      title: {
        show:"true",
        textStyle:{
          color:'#e4393c',
          fontStyle:'normal',
          fontFamily:"san-serif",//主题文字字体，默认微软雅黑
          fontSize:18//主题文字字体大小，默认为18px
        },
        text: '某站点用户访问来源',
        subtext: '纯属虚构',
        left: 'center'
        },
      data:videoRes,
      type:"pie",
      labelLine: {
                    normal: {
                  length: 10,
                  length2: 60
                  }},
      itemStyle: {
                 normal: {
                label: {
                //  show: true,
                 textStyle: {
                //  color: '#5B616A',
                 fontSize: "12"
                 },
                formatter: '{b}\n\n{c}',
                padding: [0, -27],}}
              },
      // label:{
      //     normal:{
      //       position:"outside"
      //     }
      //   },
    },
  ];
  pieOptions.series = videoData.series;
  let Echarts = echarts.init(document.getElementById("main"));
  Echarts.setOption(pieOptions);
  console.log("do")

}

function getZhu(){
  axios
    .get("http://localhost:9090/ksxx/tenLargestAndLeastZy", {
    })
    .then(function (res) {
      console.log('jijiji',res.data.data);
      zhu.data = toRaw(res.data.data);
      zhuZhuang();
    });
}

const zhu =reactive({
  data:[
    {
      count:'',
      zymc:''
    },
  ]
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
    // axisLine:{
    //   lineStyle:{
    //     color:"#17b3a3",
    //   },
    // },
    axisLabel: {
        
    // ***x轴 文字竖排显示***
    interval: 0,
    formatter: function(value) {
      return value.split("").join("\n");
    }

    //******x轴 文字倾斜***************
    // interval:0,
    // rotate:15
  },
  },
  yAxis:[
    {
    type:"value",
    // axisLine:{
    //   lineStyle:{
    //     color:"#17b3a3",
    //   },
    // },
    }
  ],
  color:["#2ec7c9","#b6a2de","#5ab1ef","ffb980","#d87a80","#8d98b3"],

  series:[]
})

//柱状图
function zhuZhuang (){
  let userRes = zhu.data
userData.xData = userRes.map((item) => item.zymc);
userData.series=[
  {
    name:"人数",
    data:userRes.map((item)=> item.count),
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
let userEcharts = echarts.init(document.getElementById("user"));
  userEcharts.setOption(xOptions);
}


function Search() {
  let sum=0;
  if (form.sf === "") {
    open3();
  }
  else{
    axios
    .get("http://localhost:9090/ksxx/liNianLuQu", {
      params:{
          year: form.nf,
          current: 1,
          size: 100,
          sf: form.sf,
      }
    })
    .then(function (res) {
      console.log(res);
      console.log(res.data.data.total);
    
      for(let i=0;i<res.data.data.total;i++){
       sum = sum + res.data.data.records[i].num
      }
      form.num=sum;

      data.msg=toRaw(res.data.data.records);
      
    });
  }
}
</script>

<style scoped>

/* .home{
  padding-left: 100px;
} */
</style>