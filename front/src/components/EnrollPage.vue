<!--查询当年录取考生情况-->
<template>
  <div v-loading=loading>
    <WebHeader></WebHeader>
    <div style="background-color:brown; width: 100%; height: 40px; display: flex;
			align-items: center;padding-left: 6%; font-size:medium; font-weight: 600; color: aliceblue;">
      <span style="color: aliceblue;">
        当前位置: <router-link to="" @click="back" style="text-decoration: none">
          <span style="color:aliceblue">首页</span>
        </router-link>  >> 
        <router-link to="/enrollPage" style="text-decoration: none">
           <span style="color:aliceblue">录取考生信息</span>
        </router-link>
      </span>
   </div>
    <!-- 搜索框 -->
    <div>
      <el-row style="margin-top:15px;align-items: center;justify-content: center;">

        <label>考生号</label>

        <el-col :span="5">
          <el-input placeholder="请输入考生号" v-model="msg.sentMsg.ksh" class="w-50 m-2" size="medium" maxlength="15" >
          </el-input>
        </el-col>

        <label>身份证号后六位</label>

        <el-col :span="5">
          <el-input placeholder="请输入身份证号后六位" v-model="msg.sentMsg.zjhm" class="w-50 m-2"  maxlength="6"
          size="medium">
          </el-input>
        </el-col>
        <div>
          <el-button
              type="primary"
              :icon="Search"
              round
              @click="toSearch()"
              style="margin: 15px"
          >查询</el-button>
          <el-button
              type="primary"
              @click="back"
              style="margin: 15px; border-radius: 16px"
          >返回
          </el-button>
        </div>


      </el-row>
    </div>
    <table class="table1 table table-striped" :class="flag">
      <tr>
        <th>
          考生号
        </th>

        <td>
          {{ student.msg.ksh }}
        </td>
      </tr>

      <tr style="background-color:#ccc">
        <th>
          姓名
        </th>

        <td>
          {{ student.msg.xm }}
        </td>
      </tr>

      <tr>
        <th>
          录取专业
        </th>

        <td>
          {{ student.msg.zy }}
        </td>
      </tr>
    </table>
        <!-- 弹框 -->
        <el-dialog v-model="dialogVisible" title="Tips" width="30%">
            <span>{{ tip.msg }}</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button type="primary" @click="dialogVisible = false">确定</el-button>
                </span>
            </template>
        </el-dialog>


  </div>
</template>

<script setup>
import axios from 'axios';
import WebHeader from '@/components/WebHeader.vue';
import { ref, reactive, watch } from 'vue'

//记录账号密码
const msg = reactive({
  sentMsg: {
    ksh: '',
    zjhm: ''
  }
})

//返回信息
const student = reactive({
  msg: ''
})

//返回提示信息
const tip = reactive({
    msg: ''
})

//控制考生信息显示
const flag = ref('close')

//控制loading显示
const loading = ref(false)

//控制提示信息显示
const dialogVisible = ref(false)

function toSearch() {

  //对发送给后端得数据进行处理
  let newZjhm = '**' + msg.sentMsg.zjhm.substring(2, msg.sentMsg.zjhm.length)

  //考生号和证件号必须都要有值
  if(msg.sentMsg.ksh === ''  || msg.sentMsg.zjhm === '' ){
    tip.msg = '考生号或证件号不能为空'
    dialogVisible.value = true
    return
  }

  loading.value = true

  axios.get('http://localhost:9090/fb', {
    params: {
      current: 1, //当前页面
      size: 1,//页面信息条数
      ksh: msg.sentMsg.ksh,
      zjhm: newZjhm
    }
  })
      .then(function (res) {
        // console.log(res.data.data.records)

        loading.value = false

        if (res.data.data.records.length == 1) {
          flag.value = 'open'
          student.msg = res.data.data.records[0]
        }

        else{
          tip.msg = '请输入正确得考生号或证件信息'
          dialogVisible.value = true
        }
            if (res.data.data.records.length == 1) {
                flag.value = 'open'
                student.msg = res.data.data.records[0]
            }
            else {
                tip.msg = '请输入正确得考生号或证件信息'
                dialogVisible.value = true
            }
        })
      }

//监视考生号不能为空格
watch(() => msg.sentMsg.ksh, () => {
  msg.sentMsg.ksh = msg.sentMsg.ksh.replace(/\s/g, "")
})

//监视证件号不能为空格
watch(() => msg.sentMsg.zjhm, () => {
  msg.sentMsg.zjhm = msg.sentMsg.zjhm.replace(/\s/g, "")
})
</script>

<style  scoped>
label {
  font-size: 18px;
  margin: 15px;
}

.search-box {
  font-size: 16px;
  margin: 15px;
  height: 10%;
}

.close {
  display: none;
}

.open {
  display: block;
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  margin: auto;
  text-align: center;
  width: 40%;
}

.table1 {
  position: absolute;
  top: 50%;
  bottom: 0;
  left: 10%;
  right: 0;
  margin: auto;
  text-align: center;
  width: 40%;

}

th {
  border: 1px solid black;
  text-align: center;
  width: 30%;
  padding: 15px;
}

td {
  border: 1px solid black;
}

.bottom {
  position: absolute;
  bottom: 0;
  right: 0;
  margin: 20px;
}
</style>