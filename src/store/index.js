import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

const store = new Vuex.Store({
  state: {
    treeKey :'root'


  },
  mutations: {
    setTreeKey(state,treeKey){
      state.treeKey = treeKey;
    }
  },
  actions: {},
  modules: {


  },
});

export default store;
