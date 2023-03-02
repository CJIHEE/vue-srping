// vue
import Vue from 'vue';
// element-ui
import ElementUI from 'element-ui';
import locale from 'element-ui/lib/locale/lang/ko';
// chart
import HighchartsVue from 'highcharts-vue';
import highcharts from 'highcharts';
// styles
import 'normalize.css/normalize.css';
import '@fortawesome/fontawesome-free/css/all.css';
import 'element-ui/lib/theme-chalk/index.css';
import './assets/scss/index.scss';

import App from './App';
import router from './router';
import store from './store';
import Axios from "axios";

Vue.prototype.$axios = Axios;

Vue.use(ElementUI, {
  size: 'mini',
  locale,
});

Vue.use(HighchartsVue, { highcharts });

new Vue({ // eslint-disable-line no-new
  el: '#app',
  components: { App },
  template: '<App/>',
  store,
  router,
});
