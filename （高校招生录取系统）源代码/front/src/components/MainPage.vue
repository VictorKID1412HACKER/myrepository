<!--招生负责人页面-->
<template>
    <div>
        <MainHeader></MainHeader>
        <WebNav></WebNav>
    </div>
</template>

<script>
import MainHeader from './MainHeader.vue';
import WebNav from '../page/manager/WebNav.vue'


export default {
    name: "MainPage",
    data() {
        return {
            flag: true
        };
    },
    mounted() {
    },
    methods: {
    },
    components: { MainHeader, WebNav }
};
</script>

<style  scoped>

</style>