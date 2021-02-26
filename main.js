import Vue from 'vue'
import App from './App'
import store from './store'
import api from './common'
Vue.config.productionTip = false
Vue.prototype.$api = api
App.mpType = 'app'

const app = new Vue({
	store,
	...App
})
app.$mount()