<template>
    <div v-loading=loading>

        <!-- 表格 -->
        <el-table :data="student.msg" border height="450" stripe
            :header-cell-style="{ background: '#ECF7FF', color: 'black' }">
            <el-table-column label="新生名单" align="center">

                <el-table-column align="center" prop="xm" label="姓名" />
                <el-table-column align="center" prop="xb" label="性别" />
                <el-table-column align="center" prop="ksh" label="考生号" width="180" />
                <el-table-column align="center" prop="xy" label="学院" />

                <el-table-column align="center" prop="zy" label="专业" />

                <el-table-column align="center" prop="StuBj" label="班级">
                    <!-- 获取单行考生信息  -->
                    <template v-slot="scope">

                        <el-select v-model="scope.row.StuBj" class="m-2" :placeholder="scope.row.bj"
                            @focus="getBjList(scope.row.zy) ; getDetail(scope.row.ksh)" @change="check = true">

                            <el-option v-for="item in bj.msg" :key="item" :label="item" :value="item"
                                @click="getBJ(item) ; check = true" />
                        </el-select>
                    </template>
                </el-table-column>
            </el-table-column>
        </el-table>


        <!-- 弹框 -->
        <el-dialog v-model="check" title="Tips" width="30%">
            <span>是否要进行分班</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button type="default" @click="check = false">否</el-button>
                    <el-button type="primary" @click="sentClass">是</el-button>
                </span>
            </template>
        </el-dialog>


        <!-- 分班结果显示 -->
        <el-dialog v-model="dialogVisible" title="Tips" width="30%">
            <span>{{ informate }}</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button type="primary" @click="dialogVisible = false">确定</el-button>
                </span>
            </template>
        </el-dialog>

        <el-button type="primary" round style="float:right ;margin:25px" @click="multiple = true">批量分班</el-button>

        <!-- 批量分班部分 -->

        <el-dialog v-model="multiple" title="请选择需要批量分班的类别院系和专业" width="80%">
            <!-- 搜索框 -->

            <el-row style="align-items: center;">

                <label>类别</label>

                <el-select v-model="get.msg.dmmc" class="m-2" clearable placeholder="请选择类别">
                    <el-option v-for="item in option.msg" :key="item.dmmc" :label="item.dmmc" :value="item.dmmc" />
                </el-select>

                <label>院系</label>


                <el-select v-model="get.msg.yx" clearable class="m-2" placeholder="请选择院系">
                    <el-option v-for="item in option1.msg" :key="item.dmmc" :label="item.dmmc" :value="item.dmmc"
                        @click="sentCollage" />
                </el-select>

                <label>专业</label>

                <el-select v-model="get.msg.zy" clearable class="m-2" placeholder="请选择专业" @change="getBjList(get.msg.zy)">
                    <el-option v-for="item in ZyOption.msg" :key="item.dmmc" :label="item.dmmc" :value="item.dmmc" />
                </el-select>

                <el-button type="primary" :icon="Search" round @click="SentMultipleSearch"
                    style="margin-left: 20px; ">确定</el-button>

                <el-button type="primary" :icon="Search" round @click="reset" style="margin:15px">重置</el-button>

                <!-- 批量分班信息 -->

                <el-table v-if="show" :data="multipleStudent.msg" border height="450" stripe ref="multipleTable"
                    :header-cell-style="{ background: '#ECF7FF', color: 'black' }">
                    <el-table-column label="新生名单" align="center">

                        <el-table-column align="center" prop="xm" label="姓名" />
                        <el-table-column align="center" prop="xb" label="性别" />
                        <el-table-column align="center" prop="ksh" label="考生号" width="180" />
                        <el-table-column align="center" prop="xy" label="学院" />
                        <el-table-column align="center" prop="zy" label="专业" />

                    </el-table-column>
                </el-table>

                
                <div v-if="show" style="margin-top:20px">
                    <el-select placeholder="请选择班级" v-model="mutipleClass" clearable>
                        <el-option v-for="item in bj.msg" :key="item" :lable="item" :value="item"
                            @click="item = mutipleClass" />
                    </el-select>

                    <el-button style="margin-left:20px" type="primary" @click="getSelection">确认分班</el-button>
                </div>
            </el-row>
        </el-dialog>

</div>
</template>

<script  setup>
import axios from 'axios';
import { reactive, onMounted, toRaw, ref, watch } from 'vue'

//获取下拉框选定数值、考生号、姓名
const get = reactive({
    msg: {
        ksh: '',
        xm: '',
        dmmc: '',
        yx: '',
        zy: '',
        year: 2022
    }
})

//获取下拉框的内容
const option = reactive({
    msg: []
})

//获取下拉框的内容
const option1 = reactive({
    msg: []
})

//获取考生信息
const student = reactive({
    msg: [{
        xm: '',
        xb: '',
        ksh: '',
        xy: '',
        zy: '',
        StuBj: ''
    }]
})

//批量分班的考生信息
const multipleStudent = reactive({
    msg: [{
        xm: '',
        xb: '',
        ksh: '',
        xy: '',
        zy: '',
        StuBj: ''
    }]
})
// 获取专业 
const ZyOption = reactive({
    msg: []
})

//分班班级
const bj = reactive({
    msg: []
})

//当学院清空时清空专业
const clearFlag = ref(false)

//控制提示框的显示
const dialogVisible = ref(false)

// 控制加载页面的显示
const loading = ref(false)

//提示框内容
const informate = ref('')

// 控制页面情况
const user = reactive({
    total: 1000,
    size: 1000,
    Page: 1
})

// 是否发送的是查询后的分页
const searchFlag = ref(false)

// 记录最新的查询名字
const xm = ref('')

// 记录考生号
const ksh = reactive({
    msg: []
})

//记录批量处理的考生号
const multipleKsh = reactive({
    msg: []
})

//记录班级
const BJ = ref('')

// 确认分班提示
const check = ref(false)


//页面数
const total = ref(1)

//批量分班表格的id
const multipleTable = ref('mutipleTable')

//批量分班页面显示
const multiple = ref(false)

//批量分班表格显示
const show = ref(false)

//批量处理
const mutipleClass = ref('')

//获得页面初始数据
onMounted(() => {
    getTotal()
    getLB()
    getCollage()
})

//获取全部计划类别
function getLB() {
    axios.get('http://localhost:9090/zd/getAllOptByType', {
        params: {
            type: "计划类别"
        }
    })
        .then(function (res) {
            {
                // console.log(toRaw(res.data))
                option.msg = toRaw(res.data)
            }
        })
}


//获取原始的数据
function getShift() {
    axios.get('http://localhost:9090/fb', {
        params: {
            current: 1, //当前页面
            size: total.value //页面信息条数
        }
    })
        .then(function (res) {
            student.msg = toRaw(res.data.data.records)
            loading.value = false
        })
}

//获取全部条数
function getTotal() {
    axios.get('/fb', {
        params: {
            current: 1, //当前页面
            size: total.value //页面信息条数
        }
    })
        .then(function (res) {
            loading.value = true
            console.log(res.data)
            total.value = res.data.data.total
            getShift()
        })
}


//发送数据分班情况
function sentClass() {

    loading.value = true

    axios.post('http://localhost:9090/fb/setFb', {
        kshList: [ksh.msg],
        bjdh: BJ.value
    })
        .then(function (res) {

            if (res.data.code === "200") {
                dialogVisible.value = true
                loading.value = false
                informate.value = "分班成功"
                check.value = false
            }
            else {
                dialogVisible.value = true
                loading.value = false
                informate.value = "分班失败"
                check.value = false
            }
        })
}

// 数据传输处理
function DataProcessing() {

    if (get.msg.xm.length === 2) {
        xm.value = get.msg.xm.substr(0, 1) + "*"
        // console.log(xm)
    }

    else if (get.msg.xm.length > 2) {
        xm.value = get.msg.xm.substr(0, 1) + "*" + get.msg.xm.substr(2, get.msg.xm.split('').length)
        // console.log(xm)
    }
}


//发送查询
function SentMultipleSearch() {

    if (clearFlag.value === true) {
        get.msg.zy = ''
    }

    if (get.msg.zy === '' || get.msg.yx === '' || get.msg.dmmc === '') {
        alert('类别、院系、专业都需要选择！')
    }

    //发送请求
    else {
        axios.get('http://localhost:9090/fb', {
            params: {
                current: 1,
                size: user.size,
                // ksh: get.msg.ksh,
                // xm: xm.value,
                jh: get.msg.dmmc,
                yx: get.msg.yx,
                zy: get.msg.zy
            }
        })
            .then(function (res) {
                // console.log(xm.value)
                multipleStudent.msg = toRaw(res.data.data.records)

                console.log(res.data)

                for (let index = 0; index < res.data.data.total; index++) {
                    multipleKsh.msg[index] = toRaw(res.data.data.records[index].ksh)
                }

                searchFlag.value = true
                user.Page = 1
                clearFlag.value = false
                show.value = true
            })

    }
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
            option1.msg = res.data
        })
}

// 发送院系获取专业
function sentCollage() {

    get.msg.zy = ''

    axios.get('http://localhost:9090/zd/getZyByYx', {
        params: {
            yx: get.msg.yx,
            year: get.msg.year
        }
    }).then(function (res) {
        console.log("Have Used")
        ZyOption.msg = res.data
        console.log(ZyOption.msg)
    })
}

// 重置查询
function reset() {
    get.msg.dmmc = ''
    get.msg.yx = ''
    // get.msg.ksh = ''
    // get.msg.xm = ''
    get.msg.zy = ''

    axios.get('http://localhost:9090/fb', {
        params: {
            current: 1,
            size: user.size,
        }
    })
        .then(function (res) {
            // console.log(xm.value)
            student.msg = toRaw(res.data.data.records)
            // user.total = Math.ceil(res.data.data.total / user.size)
            searchFlag.value = true
            user.Page = 1
        })

}

// 获取要修改班级学生的考生号
function getDetail(row) {
    ksh.msg = row
    console.log(row)
    console.log("调用")
}

// 获取要修改班级学生的班级
function getBJ(row) {
    BJ.value = row
    console.log(row)
}

//分班班级
function getBjList(zy) {
    axios.get('/fb/manmade/getBjdhList', {
        params: {
            zymc: zy
        }
    })
        .then(function (res) {
            bj.msg = res.data.data
            console.log("test" + res.data.data)
        })
}

function getSelection() {

    if (mutipleClass.value !== '') {
        axios.post('http://localhost:9090/fb/setFb', {
            kshList: multipleKsh.msg,
            bjdh: mutipleClass.value
        })
            .then(function (res) {

                if (res.data.code === "200") {
                    dialogVisible.value = true
                    loading.value = false
                    informate.value = "分班成功"
                    check.value = false
                }

                else {
                    dialogVisible.value = true
                    loading.value = false
                    informate.value = "分班失败"
                    check.value = false
                }
            })
    }
    else {
        alert("请选择需分配的班级")
    }

}

function SentSearch() {
    DataProcessing()

    //发送请求

    axios.get('http://localhost:9090/fb', {
        params: {
            current: 1,
            size: user.size,
            ksh: get.msg.ksh,
            xm: get.msg.xm,
        }
    })
        .then(function (res) {
            // user.size = res.data.data.total
            console.log(user.size)
            student.msg = toRaw(res.data.data.records)
            console.log(student.msg)
            searchFlag.value = true
            user.Page = 1
            clearFlag.value = false
            show.value = true
        })

}



//考生号不能有空格，粘贴的也不行
watch(() => get.msg.ksh, () => {
    get.msg.ksh = get.msg.ksh.replace(/\s/g, "")
})

</script>

<style  scoped>
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

