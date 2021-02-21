import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import '/src/assets/bootstrap/css/bootstrap.min.css'
import '/src/assets/fonts/fontawesome-all.min.css'
import '/src/assets/js/jquery.min.js'
import '/src/assets/bootstrap/js/bootstrap.min.js'
import '/src/assets/js/chart.min.js'
// import '/src/assets/js/script.min.js'

import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import BootstrapVue from 'bootstrap-vue'
import Jquery from 'jquery'
import echarts from 'echarts'
import axios from 'axios'

Vue.prototype.$echarts = echarts
Vue.prototype.$axios = axios
Vue.config.productionTip = false
Vue.use(BootstrapVue, Jquery)

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
