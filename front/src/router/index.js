import { createRouter, createWebHistory } from "vue-router";

import Cookie from "vue-cookies";
import HomePage from '../components/HomePage'
import LoginPage from "@/components/LoginPage"
import MakePlan from '../page/manager/MakePlan'
import MainPage from '../components/MainPage'
import StuBanding from '../page/manager/StuBanding'

import SearchPlan from '../components/SearchPlan'
import SearchEnroll from '../components/SearchEnroll'
import EnrollPage from '../components/EnrollPage'
import XiTong from '../components/XiTong'

import SystemMain from "@/page/SystemManager/SystemMain"
import ManageUser from "@/page/SystemManager/ManageUser"
import AutomaticShift from '../page/manager/AutomaticShift'
import ManualShift from '../page/manager/ManualShift'
import InformationExport from '../page/manager/InformationExport'
import ShiftView from '../page/manager/ShiftView'
import WebAnalysis from '../page/manager/WebAnalysis'
import WorkerPage from "@/components/WorkerPage";
import WorkerCheckStu from "../page/worker/WorkerCheckStu";
import WorkerCheckSumData from "@/page/worker/WorkerCheckSumData";
import WorkerChangeStuMsg from "@/page/worker/WorkerChangeStuMsg";
import WorkerPrint from "@/page/worker/WorkerPrint";
import WorkerPrintPaper from "@/page/worker/WorkerPrintPaper";
import SystemInitialize from "../page/manager/SystemInitialize";
import UploadClassBand from "../page/manager/UploadClassBand";
import ShiftDetail from "../page/manager/ShiftDetail"
import PublishNews from '../page/manager/news/PublishNews.vue'
import NewsList from '../page/manager/news/NewsList.vue'
import NewsCategory from '../page/manager/news/NewsCategory.vue'

const router = new createRouter({
    history: createWebHistory(),

    routes: [
        {
            path: '/',
            component: HomePage,
            name:'homepage',
        }
        ,
        //查询历年招生计划
        {
            path: '/searchplan',
            component: SearchPlan
        }
        ,
        //查询历年录取计划
        {
            path: '/searchenroll',
            component: SearchEnroll
        }
        ,
        //查询考生录取情况
        {
            path: '/enrollpage',
            component: EnrollPage
        },
        //模拟登录页面
        {
            path:'/loginpage',
            name:'login',
            component:LoginPage
        },

        {//主页面
            path: '/mainpage',
            component: MainPage,
            name:'main',
            children: [
                {
                    path:'initialize',
                    component: SystemInitialize
                },
                {//制定分班计划
                    path: 'makeplan',
                    component: MakePlan
                }
                ,
                {//新生分班
                    path: 'stubanding',
                    component: StuBanding,
                }
                ,
                {//自动分班
                    path: 'automaticshift',
                    component: AutomaticShift
                }
                ,
                {
                    //手动分班
                    path: 'manualshift',
                    component: ManualShift
                }
                ,
                //导出新生信息
                {
                    path: 'informationexport',
                    component: InformationExport
                }
                ,
                //查看分班
                {
                    path: 'shiftview',
                    component: ShiftView
                }
                ,
                //查看分班细节
                {
                    path:'shiftdetail',
                    component:ShiftDetail
                }
                ,
                //统计分析
                {
                    path:'webanalysis',
                    component:WebAnalysis
                }
                ,
                //导入分班配置
                {
                    path:'uploadclassband',
                    component:UploadClassBand
                }
                ,
                {
                  path: 'publishnews',
                  name: 'PublishNews',
                  component: PublishNews
                },
                {
                  path: 'newslist',
                  name: 'NewsList',
                  component: NewsList
                },
                {
                  path: 'newscategory',
                  name: 'NewsCategory',
                  component: NewsCategory
                }
            ]

        },
        {
          path:'/workerpage',
          name:'work',
          component:WorkerPage,
          children:[
              {//上载录取考生信息
                  path: 'workercheckstu',
                  component: WorkerCheckStu
              },
              {//审核录取汇总数据
                  path: 'workerchecksumdata',
                  component: WorkerCheckSumData
              },
              {//审核录取汇总数据
                  path: 'workerchangestumsg',
                  component: WorkerChangeStuMsg
              },
              {//打印通知书分配备单号
                  path: 'workerprint',
                  component: WorkerPrint
              },
              {//打印通知书
                  path: 'workerprintpaper',
                  component: WorkerPrintPaper
              },
          ]
        },
        {
            path:'/systempage',
            name:'system',
            component:XiTong,
            children:[
                {
                    path: 'main',
                    component:SystemMain
                },
                {
                    path: 'change',
                    component: ManageUser
                },
            ]
          },
    ]
})

router.beforeEach((to, from, next) => {
    const token = Cookie.get('token')
    if(to.path === '/'){
        next()
    }
    if(to.path === '/searchplan'){
        next()
    }
    if(to.path === '/searchenroll'){
        next()
    }
    if(to.path === '/enrollPage'){
        next()
    }

    if(!token && to.name !=='login'){
        next({name:'login'})
    }
    else{
        next()
    }
})

export default router

