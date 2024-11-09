<template>
    <div class="box">
        <button class="btn btn-info btn-style" v-for="val in ability" :key="val.id" @click="jump(val.path)">
                      <span>
                          {{ val.name }}
                      </span>

        </button>

       

        <router-view></router-view>
    </div>
</template>

<script>

export default {
    name: 'StuBanding',

    data() {
        return {
            ability: [
                {
                    id: 1,
                    name: '导入分班配置',
                    path: '/mainpage/uploadclassband',
                }
                ,
                {
                    id: 2,
                    name: '手动分班',
                    path: '/mainpage/manualshift',
                }
                ,
                {
                    id: 3,
                    name: '自动分班',
                    path: '/mainpage/automaticshift',
                }
                ,
                {
                    id: 4,
                    name: '导出新生信息',
                    path: '/mainpage/informationexport',
                }
                ,
                {
                    id: 5,
                    name: '查看分班情况',
                    path: '/mainpage/shiftview',
                }
                ,

            ]
        };
    },

    mounted() {

    },

    methods: {
        jump(path) {
            this.$router.push({
                path,
            })
        },
    },
};
</script>

<style  scoped>
.box {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    justify-content: center;
}

.btn-style {
    height: 30%;
    width: 30%;
    margin: 15px;
    font-size: 20px;
}
</style>