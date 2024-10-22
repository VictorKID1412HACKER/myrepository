import Cookie from "vue-cookies";
import router from "./router";

export default {
    install(Vue){
        Vue.mixin({
            methods: {
                back(){
                    const token = Cookie.get('token');
                    let name = sessionStorage.getItem('userName');

                    if(token){
                        if(name === "招生人员a"){
                            router.push('/mainpage/initialize');
                        }
                        if(name === "招生人员b"){
                            router.push('/workerpage');
                        }
                    }
                    if(!token){
                        this.$router.back();
                    }

                }
            },
        })
    }
}