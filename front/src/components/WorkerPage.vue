<!--招生工作人员页面-->
<template>
<div class="common-layout">
    <el-container>
      <el-header >
        <MainHeader></MainHeader>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <WorkerNav></WorkerNav>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>

</template>

<script>
import WorkerNav from "@/page/worker/WorkerNav";
import MainHeader from "@/components/MainHeader";
export default {
  name: "WorkerPage",
  components:{
    MainHeader,
    WorkerNav,
  }
}
</script>

<style scoped>

.el-header {
    --el-header-padding: 0 0px;
}
</style>