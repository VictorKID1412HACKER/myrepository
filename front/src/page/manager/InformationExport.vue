<template>
    <div v-loading="load">

        <label>院系</label>

        <el-select v-model="get.msg.dmmc" clearable class="m-2" placeholder="请选择院系"
            @clear="SentSearch(clearFlag = true)">
            <el-option v-for="item in option.msg" :key="item.dmmc" :label="item.dmmc" :value="item.dmmc"
                @click="sentCollage" />
        </el-select>

        <label>专业</label>


        <el-select v-model="get.msg.zy" clearable class="m-2" placeholder="请选择专业" @clear="SentSearch">
            <el-option v-for="item in ZyOption.msg" :key="item.dmmc" :label="item.dmmc" :value="item.dmmc" />
        </el-select>

        <label>类别</label>

        <el-select v-model="get.msg.dmmc1" clearable class="m-2" placeholder="请选择类别" @clear="SentSearch">
            <el-option v-for="item in option1.msg" :key="item.dmmc" :label="item.dmmc" :value="item.dmmc" />
        </el-select>


        <el-button type="primary" :icon="Search" round @click="SentSearch()" style="margin:15px">查询</el-button>

        <el-button type="primary" :icon="Search" round @click="reset" style="margin:15px">重置</el-button>

        <el-table :data="student.msg" border height="520" stripe
            :header-cell-style="{ background: '#ECF7FF', color: 'black' }">
            <el-table-column align="center" prop="xy" label="学院" />
            <el-table-column align="center" prop="zy" label="专业" />
            <el-table-column align="center" prop="bj" sortable label="班级" />
            <el-table-column align="center" prop="xm" label="姓名" />
            <el-table-column align="center" prop="xb" label="性别" />
            <el-table-column align="center" prop="lxdh" label="联系方式" />
            <el-table-column align="center" prop="txdz" label="家庭住址 " />
            <el-table-column align="center" prop="" label="出生年月" />
        </el-table>

        <el-button type="primary" class="btn-style" @click="back">返回</el-button>

        <el-button type="primary" class="btn-style" @click="mcExpore">新生名册导出</el-button>
        <el-button type="primary" class="btn-style" @click="zpExpore">新生照片导出</el-button>
        <el-button type="primary" class="btn-style" @click="dnExpore">新生档案导出</el-button>
    </div>
</template>

<script setup>
import { reactive, toRaw, onMounted, ref } from 'vue'
import axios from 'axios'

//学生信息
const student = reactive({
    msg: [{

    }]
})

//页面信息
const user = reactive({
    total: 0,
    size: 10,
    page: 1
})

//获取下拉框的内容
const option = reactive({
    msg: []
})

const option1 = reactive({
    msg: []
})

//获取下拉框选定数值
const get = reactive({
    msg: {
        dmmc: '',
        zy: '',
        year: 2022,
        dmmc1: ''
    }
})


// 获取专业 
const ZyOption = reactive({
    msg: []
})

const clearFlag = ref(false)

const load = ref(false)


onMounted(() => {
    // getStudent()
    getCollage()
    getLB()
    getTotal()
})

// 获取院系
function getCollage() {
    axios.get('http://localhost:9090/zd/getAllOptByType', {
        params: {
            type: '院系'
        }
    })
        .then(function (res) {
            // console.log(res.data)
            option.msg = res.data

        })
}

//获取学生信息
function getStudent() {
    axios.get('http://localhost:9090/fb', {
        params: {
            current: 1, //当前页面
            size: user.total //页面信息条数
        }
    })
        .then(function (res) {
            student.msg = toRaw(res.data.data.records)
            // console.log("first", res.data.data.records)
            // user.total = Math.ceil(res.data.data.total / user.size)
            user.total = res.data.data.total
            load.value = false
        })
}

// 获取总页数
function getTotal() {

    load.value = true

    // if (PageFlag.value === false) {
    axios.get('http://localhost:9090/fb', {
        params: {
            current: 1, //当前页面
            size: user.size, //页面信息条数
        }
    }).then(function (res) {
        // student.msg = toRaw(res.data.data.records)
        user.total = res.data.data.total
        getStudent()
    })

}

// 发送院系获取专业
function sentCollage() {

    get.msg.zy = ''

    axios.get('http://localhost:9090/zd/getZyByYx', {
        params: {
            yx: get.msg.dmmc,
            year: get.msg.year
        }
    }).then(function (res) {
        ZyOption.msg = res.data
    })
}

// 发送搜索请求
function SentSearch() {

    if (clearFlag.value === true) {
        get.msg.zy = ''
    }

    axios.get('http://localhost:9090/fb', {
        params: {
            current: 1,
            size: user.total,
            zy: get.msg.zy,
            jh: get.msg.dmmc1,
            yx: get.msg.dmmc
        }
    }).then(function (res) {
        console.log(res.data)
        console.log(get.msg.dmmc)
        console.log(get.msg.zy)
        student.msg = toRaw(res.data.data.records)
        // user.total = Math.ceil(res.data.data.total / user.size)
        user.page = 1
        // PageFlag.value = true
        clearFlag.value = false
    })
}

//获取全部类型
function getLB() {
    axios.get('http://localhost:9090/zd/getAllOptByType', {
        params: {
            type: "计划类别"
        }
    })
        .then(function (res) {
            {
                console.log(toRaw(res.data))
                option1.msg = toRaw(res.data)
            }
        })
}

// 重置查询
function reset() {
    get.msg.dmmc = ''
    get.msg.dmmc1 = ''
    get.msg.zy = ''

    axios.get('http://localhost:9090/fb', {
        params: {
            current: 1,
            size: user.total,
        }
    })
        .then(function (res) {
            // console.log(xm.value)
            student.msg = toRaw(res.data.data.records)
            // user.total = Math.ceil(res.data.data.total / user.size)
            // user.page = 1
        })

}

function dnExpore() {
    console.log('档案调用')

    let data = axios.get('ksxx/dazpZipDownload', {
        responseType: 'blob'
    }).then(function(res){
        console.log( "data:"+data)
        console.log(res.data)
        const url = window.URL.createObjectURL(new Blob([res.data]))
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.setAttribute('download', '新生档案.zip')
        document.body.appendChild(link)
        link.click();
        document.body.removeChild(link)
        
    })    

}

function mcExpore() {
    console.log('名册调用')

    let data = axios.get('ksxx/ksxxZipDownload', {
        responseType: 'blob'
    }).then(function (res) {
        console.log("data:" + data)
        console.log(res.data)
        const url = window.URL.createObjectURL(new Blob([res.data]))
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.setAttribute('download', '新生名册.zip')
        document.body.appendChild(link)
        link.click();
        document.body.removeChild(link)

    })
}

function zpExpore() {
    console.log('照片调用')
    let data = axios.get('ksxx/kszpZipDownload', {
        responseType: 'blob'
    }).then(function (res) {
        console.log("data:" + data)
        console.log(res.data)
        const url = window.URL.createObjectURL(new Blob([res.data]))
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = url
        link.setAttribute('download', '新生照片.zip')
        document.body.appendChild(link)
        link.click();
        document.body.removeChild(link)

    })
}

</script>

<style  scoped>
.search-center {
    width: 90%;
    margin: 20px auto;
    text-align: center;
}

h3 {
    background-color: #ECF7FF;
    border: 1px solid #ccc;
    border-bottom: 0;
    margin: 0;
    padding: 15px;
    text-align: center;
}

th {
    border: 1px solid #ccc;
    background-color: rgba(0, 150, 255, .36);
    text-align: center;
    padding: 10px;
}

td {
    text-align: center;
}

.btn-style {
    float: right;
    margin: 10px;
}

label {
    margin: 5px;
    line-height: 20px;
    font-size: 18px;
}
</style>