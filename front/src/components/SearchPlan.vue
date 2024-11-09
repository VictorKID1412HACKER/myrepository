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
        <router-link to="/searchplan" style="text-decoration: none">
           <span style="color:aliceblue">历年招生计划</span>
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
      id="table"
      :data="data.msg"
      border
      style="width: 1000px;margin-left:auto;margin-right:auto;"
      :header-cell-style="{ background: '#ECF7FF', color: 'black' }"
    >
      <el-table-column align="center" prop="xy" label="学院" width="180" />
      <el-table-column align="center" prop="zydh" label="专业代号" />
      <el-table-column
        align="center"
        prop="zymc"
        label="专业名称"
        width="180"
      />
      <el-table-column align="center" prop="kl" label="科类" />
      <el-table-column align="center" prop="sfjhrs" label="计划" />
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
  msg: [{}],
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
  } 
  else {
    // console.log(nf.nf);
    showTable();
    axios
      .get("http://localhost:9090/zsjh", {
        params: {
          nf: nf.nf,
          current: 1,
          size: 200,
          sf: sf.sf,
        },
      })
      .then(function (res) {
        data.msg = toRaw(res.data.data.records);
        // other.total = Math.ceil(res.data.data.total / other.size)
        console.log(res);
      });
  }
}
</script>

<style>
</style>