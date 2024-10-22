import Vue from 'vue'
import Vuex from 'vuex'


const store = new Vuex.Store({
    state: {
        name: 0
    },
    mutations:{
        getName(state, username){
            state.name = username
        }
    }
})
export default store
