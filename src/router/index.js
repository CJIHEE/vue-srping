/**
 * ? REVIEW:
 * 코드 인덴트 확인
 * lint rule은 항상 확인 후 모두 처리
 */

import Vue from 'vue';
import Router from 'vue-router';
import guide from '../views/guide/index';
import event from '../views/event/index';
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
        path: ':treeKey',
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
        path: ':treeKey',
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
