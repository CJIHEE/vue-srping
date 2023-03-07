import Vue from 'vue';
import Router from 'vue-router';
import guide from '../views/guide/index.vue';
import event from '../views/event/index.vue';
import RootGroupInfo from '../views/event/RootGroupInfo';

Vue.use(Router);

const routes = [
  {
    path: '/',
    redirect:'guide',
  },
  {
    path: '/guide',
    name: 'guide',
    component:guide
  },
  {
    path: '/event',
    name: 'event',
    component:event,
    children : [
      {
        path: ':id',
        name: 'RootGroupInfo',
        component:RootGroupInfo,
        props:true
      },
    ]
  },
  {
    path: '/setting',
    name: 'setting',
    component:event,
    children : [
      {
        path: ':id',
        name: 'RootGroupInfo',
        component:RootGroupInfo,
        props:true
      },
    ]
  },


];

export default new Router({
  scrollBehavior: () => ({ y: 0 }),
  routes,
});
