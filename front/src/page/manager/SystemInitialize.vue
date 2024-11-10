<template>
<div>

</div>
    <div class="center" v-loading=loading>
        <el-button type="success" style="font-size: 15px;" @click="dialogVisible = true">系统备份
        </el-button>

        <el-button type="danger" style="font-size: 15px;" @click="linitialize">初始化系统</el-button>

        <el-dialog v-model="dialogVisible" title="Tips" width="30%">
            <div> 是否要进行备份 </div>
            <span>备份路径为：D:\data\oldDatabase</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button type="default" @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="copy">确定</el-button>
                </span>
            </template>
        </el-dialog>

        <el-dialog v-model="check" title="Tips" width="30%">
            <span>{{informate}}</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button type="primary" @click="check = false">确定</el-button>
                </span>
            </template>
        </el-dialog>

        <el-dialog v-model="flag" title="Tips" width="30%">
            <div> 是否要进行初始化系统 </div>
           
            <template #footer>
                <span class="dialog-footer">
                    <el-button type="default" @click="flag = false">取消</el-button>
                    <el-button type="primary" @click="linitialize">确定</el-button>
                </span>
            </template>
        </el-dialog>
        
        <el-dialog v-model="csh" title="Tips" width="30%">
            <span>{{ tip }}</span>
            <template #footer>
                <span class="dialog-footer">
                    <el-button type="primary" @click="csh = false">确定</el-button>
                </span>
            </template>
        </el-dialog>

    </div>
</template>

<script setup>
import axios from 'axios';
import { ref } from 'vue'

const dialogVisible = ref(false)
const loading = ref(false)
const informate = ref('')
const check = ref(false)
const flag =  ref(false)
const tip = ref('')
const csh = ref(false)




function copy() {
    loading.value = true
    axios.get('http://localhost:9090/csh/backUp')
        .then(function (res) {
            if (res.data.code === "200") {
                check.value = true
                loading.value = false
                informate.value = "备份成功"
                dialogVisible.value = false
            }
            else {
                check.value = true
                loading.value = false
                informate.value = "备份失败"
                dialogVisible.value = false
            }
        })
}

function linitialize(){
    axios.get('/csh/initialize')
        .then(function(res){
            if (res.data.code === "200") {
                console.log(res.data)
                csh.value = true
                loading.value = false
                tip.value = "初始化成功"
                // csh.value = false
            }
            else {
                console.log(res.data)
                csh.value = true
                loading.value = false
                tip.value = "初始化失败"
                // csh.value = false
            }
        })

}
</script>

<style  scoped>
.center {
    display: flex;
    align-items: center;
    justify-content: center;
}
</style>