<!--查询历年招生计划-->
<template>
  <div>
    <WebHeader></WebHeader>
  </div>
  <div style="background-color:brown; width: 100%; height: 40px; display: flex;
			align-items: center;padding-left: 6%; font-size:medium; font-weight: 600; color: aliceblue;">
      <span style="color: aliceblue;">
        当前位置: <router-link to="" @click="back" style="text-decoration: none">
          <span style="color:aliceblue">首页</span>
        </router-link>  >> 
        <router-link to="/searchenroll" style="text-decoration: none">
           <span style="color:aliceblue">历年录取情况</span>
        </router-link>
      </span>
   </div>
  <div>
    <label style="margin: 2% 2% 0 5%">年份</label>

    <el-select v-model="nf.nf" clearable placeholder="请选择年份">
      <el-option :value="2022"></el-option>
      <el-option :value="2021"></el-option>
      <el-option :value="2020"></el-option>
    </el-select>

    <label style="margin: 2% 2% 0 5%">省份</label>

    <el-select v-model="sf.sf" clearable placeholder="请选择省份">
      <el-option
        v-for="item in option.msg"
        :key="item.sfmc"
        :value="item.sfmc"
      />
    </el-select>

    <!-- <label style="margin: 2% 2% 0 5%">计划类别</label>
  
      <el-select v-model="lb.lbmc" id="lbmc" class="m-2" placeholder="请选择计划类别">
        <el-option v-for="item in optionlb.lblist" :key="item.lbid"  :value="item.lbmc" />
      </el-select> -->

    <el-button
      type="primary"
      :icon="Search"
      round
      @click="toSearch()"
      style="margin: 15px"
      >查询</el-button
    >
    <el-button
      type="primary"
      @click="back()"
      style="margin: 15px; border-radius: 16px"
      >返回</el-button
    >
    <el-table
      :style="styleObject"
      :data="data.msg"
      style="width: 1000px;margin-left:auto;margin-right:auto;"
    >
      <el-table-column label="录取分数线" align="center">
        <el-table-column align="center" prop="kl" label="科类" />
        <el-table-column align="center" prop="wk" label="文科" />
        <el-table-column align="center" prop="lk" label="理科" />
        <el-table-column align="center" prop="zh" label="综合" />
      </el-table-column>
    </el-table>
    <el-table
      :style="styleObject"
      :data="zyData.msg"
      style="width: 1000px; margin-left:auto;margin-right:auto;"
    >
      <el-table-column label="各专业录取分数线" align="center">
        <el-table-column align="center" prop="zymc" label="专业名称" width="180" />
        <el-table-column align="center" prop="jhlb" label="计划类别" width="150"/>
        <el-table-column align="center" prop="kl" label="科类名称" />
        <el-table-column
          align="center"
          prop="num"
          label="录取人数"
          width=""
        />
        <el-table-column align="center" prop="maxS" label="最高分" width="150" />
        <el-table-column align="center" prop="minS" label="最低分" width="150" />
        <el-table-column align="center" prop="avgS" label="平均分" width="150" />
      </el-table-column>
    </el-table>
  </div>
</template>
  
  <script setup>
import WebHeader from "@/components/WebHeader";
import { ElMessage } from "element-plus";
import { onMounted, reactive, toRaw } from "vue";
import axios from "axios";

const nf = reactive({
  nf: "",
});

const sf = reactive({
  sf: "",
});

const data = reactive({
  msg: [
    { kl: "一本线", wk: "", lk: "", zh: "" },
    { kl: "录取线", wk: "", lk: "", zh: "" },
  ],
});

const zyData = reactive({
  msg: [],
});

const styleObject = reactive({
  display: "",
});

const open3 = () => {
  ElMessage({
    message: "请选择年份和省份",
    type: "warning",
  });
};

onMounted(() => {
  initialize();
});

function showTable() {
  styleObject.display = "block";
}

const option = reactive({
  msg: [{ sfmc: "湖南" }, { sfmc: "上海" }, { sfmc: "甘肃" }],
});

function initialize() {
  styleObject.display = "none";
}

//搜索
function toSearch() {
  if (nf.nf === "" || sf.sf === "") {
    open3();
  } else {
    showTable();
    axios
      .get("http://localhost:9090/ksxx/liNianLuQu",{
        params: {
          year: nf.nf,
          current: 1,
          size: 100,
          sf: sf.sf,
        },
      })
      .then(function (res) {
        if(sf.sf=='湖南'){
           data.msg[0].wk=499,
           data.msg[0].lk=475,
           data.msg[1].wk=598,
           data.msg[1].lk=607
           data.msg[0].zh='',
           data.msg[1].zh=''
        }
        else if(sf.sf=='上海'){
          data.msg[0].wk='',
           data.msg[0].lk='',
           data.msg[1].wk='',
           data.msg[1].lk='',
           data.msg[0].zh=503,
           data.msg[1].zh=549
        }
        else {
          data.msg[0].wk=485,
           data.msg[0].lk=442,
           data.msg[1].wk=514,
           data.msg[1].lk=513
           data.msg[0].zh='',
           data.msg[1].zh=''
        }
        zyData.msg = toRaw(res.data.data.records)
        console.log(res)
      });
  }
}
</script>
  
  <style>
</style>