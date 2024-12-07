<template>
    <!--  查询分班情况-->
    <div v-loading="load">
        <el-row style="margin-top:15px;align-items: center;justify-content: center;">

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


            <label>班级</label>

            <el-select v-model="get.msg.bj" clearable class="m-2" placeholder="请选择班级" @clear="SentSearch">
                <el-option v-for="val in StuClass.class" :key="val.value" :label="val.lable" :value="val.value">
                </el-option>
            </el-select>

            <el-button type="primary" :icon="Search" round @click="SentSearch()" style="margin:15px">查询</el-button>

            <el-button type="primary" :icon="Search" round @click="reset" style="margin:15px">重置</el-button>
        </el-row>


        <el-table :data="student.msg" border stripe height="520"
            :header-cell-style="{ background: '#ECF7FF', color: 'black' }">
            <el-table-column align="center" prop="yx" label="学院" width="180" />
            <el-table-column align="center" prop="zy" label="专业" />
            <el-table-column align="center" prop="bjmc" sortable label="班级" />
            <el-table-column align="center" prop="xssl" label="班级人数" />
            <el-table-column align="center" prop="mnum" label="男生人数" />
            <el-table-column align="center" prop="fnum" label="女生人数" />

        </el-table>

        <button class="btn btn-info btn-style" @click="back">返回</button>
        <button class="btn btn-info btn-style" @click="jumpImport">重新导入分班配置</button>
    </div>
</template>

<script setup>
import { reactive, toRaw, onMounted, ref } from 'vue'
import axios from 'axios'
import { useRoute, useRouter } from 'vue-router'

const student = reactive({
    msg: [{

    }]
})

//获取下拉框的内容
const option = reactive({
    msg: []
})

// 获取专业 
const ZyOption = reactive({
    msg: []
})


//获取下拉框选定数值
const get = reactive({
    msg: {
        dmmc: '',
        bj: '',
        zy: '',
        year: 2022,
    }
})

// 分页情况
const user = reactive({
    total: 0,
    size: 15,
    page: 1
})

// 班级
const StuClass = reactive({
    class: [
        {
            value: '01',
            lable: "1班"
        },
        {
            value: '02',
            lable: "2班"
        },
        {
            value: '03',
            lable: "3班"
        },
        {
            value: '04',
            lable: "4班"
        },
        {
            value: '05',
            lable: "5班"
        },
        {
            value: '06',
            lable: "6班"
        },
        {
            value: '07',
            lable: "7班"
        },
        {
            value: '08',
            lable: "8班"
        },
        {
            value: '09',
            lable: "9班"
        },
        {
            value: '10',
            lable: "10班"
        },
        {
            value: '11',
            lable: "11班"
        },
        {
            value: '12',
            lable: "12班"
        },
        {
            value: '13',
            lable: "13班"
        },
    ]
})

// 搜索后分页判断
const PageFlag = ref(false)

// 是否清空
const clearFlag = ref(false)

const load = ref(false)

const route = useRoute()

const router = useRouter()


onMounted(() => {
    getCollage()
    getTotal()
})


// 获取分班
function getClass() {
    axios.get('http://localhost:9090/fb/bj', {
        params: {
            current: 1, //当前页面
            size: user.total //页面信息条数
        }
    })
        .then(function (res) {
            student.msg = toRaw(res.data.data.records)
            console.log("first", res.data.data.records)
            load.value = false
            // user.total = Math.ceil(res.data.data.total / user.size)
        })
}

// 发送分页页数
function getTotal() {

    load.value = true

    // if (PageFlag.value === false) {
    axios.get('http://localhost:9090/fb/bj', {
        params: {
            current: 1,
            size: user.size
        }
    })
        .then(function (res) {
            user.total = res.data.data.total
            console.log(res.data.data.total)
            getClass()
        })

}

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

// 发送院系获取专业
function sentCollage() {

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

    axios.get('http://localhost:9090/fb/bj', {
        params: {
            current: 1,
            size: user.total,
            zy: get.msg.zy,
            bj: get.msg.bj,
            yx: get.msg.dmmc
        }
    }).then(function (res) {
        console.log(res.data)
        console.log(get.msg.dmmc)
        console.log(get.msg.zy)
        student.msg = toRaw(res.data.data.records)
        user.page = 1
        clearFlag.value = false
    })
}

// 重置查询
function reset() {
    get.msg.dmmc = ''
    get.msg.zy = ''
    get.msg.bj = '',

        axios.get('http://localhost:9090/fb/bj', {
            params: {
                current: 1,
                size: user.total,
            }
        })
            .then(function (res) {
                student.msg = toRaw(res.data.data.records)
                user.page = 1
            })

}


function jumpImport() {
    router.push("/mainpage/uploadclassband")
}

</script>

<style  scoped>
.search-center {
    width: 90%;
    margin: 20px auto;
    text-align: center;
}



.btn-style {
    float: right;
    background-color: #1890FF;
    border: #1890FF;
    margin: 10px;
}

label {
    margin: 5px;
    line-height: 20px;
    font-size: 18px;
}
</style>