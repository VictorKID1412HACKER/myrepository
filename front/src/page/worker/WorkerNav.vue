<!--招生工作人员导航栏-->
<template>
    <el-aside width="200px">
      <el-menu class="el-menu-vertical-demo 
      ">
        <el-menu-item :index="item.id"
        v-for="item in noChildren()"
        :key="item.path"
        @click="clickMenu(item)"
        >
        <template #title>   
          <component class="icons" :is="item.icon"></component>
          <span>{{item.name}}</span>         
        </template>
        
        </el-menu-item>
        <el-sub-menu 
        :index="item.name"
        v-for = "item in hasChildren()"
        :key="item.path"
        >
        <template #title>   
          <component class="icons" :is="item.icon"></component>
          <span>{{item.name}}</span>         
        </template>
        <el-menu-item-group>
            <el-menu-item 
            :index="subItem.path"
            v-for="(subItem,subIndex) in item.children"
            :key="subIndex"
            @click="clickMenu(subItem)"
            >
            <component class="icons" :is="subItem.icon"></component>
          <span>{{subItem.name}}</span> 
            </el-menu-item>
        </el-menu-item-group>
        </el-sub-menu>
      </el-menu>
    </el-aside>
</template>

<script>
import { useRouter } from 'vue-router';
export default {
  setup(){
    const list = [
{
id: 1,
name: '首页',
path: '/workerpage',
icon:"House"
},
{
id: 2,
name: '上传录取考生信息',
icon:"Expand",
path: '/workerpage/workercheckstu'
},
{
id: 3,
name: '审核汇总录取数据',
icon:"Operation",
path: '/workerpage/workerchecksumdata'
},

{
id: 4,
name: '查询修改录取考生信息',
icon:"Filter",
 path: '/workerpage/workerchangestumsg'
},

{
  name:"通知书",
  icon: "Location",
  id:9,
  path:'/tong',
  children:[
  {
   id: 5,
   name: '备单',
   icon:"Pointer",
   path: '/workerpage/workerprint'
   },
  {
    id:6,
    name:'打印',
    icon:"Printer",
    path:'/workerpage/workerprintpaper'
  }
  ]
},
{
 id: 7,
name: '查询历年录取情况',
icon:"Search",
path: '/searchenroll'
},
 {
id: 8,
 name: '查询历年招生情况',
 icon:"Search",
path: '/searchplan'
  },
];
const router = useRouter();

const clickMenu = (item) =>{
  router.push({
    path:item.path
  });
}
      const noChildren = () =>{
        return list.filter((item) => !item.children);
       };

       const hasChildren = () =>{
        return list.filter((item) => item.children);
       };

       return {
        noChildren,
        hasChildren,
        clickMenu
       }
  }
}
</script>

<style scoped>
.icons{
  width: 16px;
  height: 16px;
  margin-right: 2px;
}
</style>