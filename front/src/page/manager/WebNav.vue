<!--招生负责人导航栏-->
<template>
    
    <div class="nav-box" >
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
        <router-view class="center"></router-view>


    </div>
</template>

<script>
import { useRouter } from 'vue-router';
export default {
  setup(){
    const list = [
{
id: 1,
name: '首页',
path: '/mainpage/initialize',
icon:"House"
},
{
id: 2,
name: '制定招生计划',
icon:"EditPen",
path: '/mainpage/makeplan'
},
{
id: 3,
name: '统计分析',
icon:"Filter",
 path: '/mainpage/webanalysis'
},

{
  name:"新生编班",
  icon: "Location",
  id:4,
  path:'/tong',
  children:[
  {
   id: 5,
   name: '导入分班配置',
   icon:"Plus",
   path: '/mainpage/uploadclassband'
   },
  {
    id:6,
    name:'手动分班',
    icon:"Pointer",
    path:'/mainpage/manualshift'
  },
  {
    id:6,
    name:'自动分班',
    icon:"Operation",
    path:'/mainpage/automaticshift'
  },
  {
    id:6,
    name:'导出新生信息',
    icon:"Expand",
    path:'/mainpage/informationexport'
  },
  {
    id:6,
    name:'查看分班情况',
    icon:"DCaret",
    path:'/mainpage/shiftview'
  },
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

<style  scoped>

.icons{
  width: 16px;
  height: 16px;
  margin-right: 2px;
}
.center {
    position: absolute;
    top: 0;
    bottom: 0;
    left: 15%;
    right: 0;
    margin: auto;
    width: 80%;
    height: 80%;

}
</style>