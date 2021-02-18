import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
	state: {
		systemUserInfo: uni.getStorageSync('systemUserInfo') || {},
		historys: uni.getStorageSync('searchHistory') || [],
		isLogin: uni.getStorageSync('isLogin') || false,
		userid: uni.getStorageSync('userid') || null,
		labelAll: uni.getStorageSync('labelAll') || [],
		labelList: uni.getStorageSync('labelList') || []
	},
	mutations: {
		SET_HISTORY(state, history) {
			state.historys = history
		},
		CLEAR_HISTORY(state) {
			state.historys = []
		},
		SET_SYSTEM_USERINFO(state, systemUserInfo) {
			state.systemUserInfo = systemUserInfo
		},
		SET_ISLOGIN(state, isLogin) {
			state.isLogin = isLogin
		},
		SET_USERID(state, userid) {
			state.userid = userid
		},
		SET_LABEL_ALL(state, labelAll) {
			state.labelAll = labelAll
		},
		SET_LABEL_LIST(state, labelList) {
			state.labelList = labelList
		}
	},
	actions: {
		set_history({commit, state}, history) {
			let list = state.historys
			for (var i = 0; i < list.length; i++) {
				if (list[i].name === history.name) {
					list.splice(i, 1)
				}
			}
			list.unshift(history)
			uni.setStorageSync('searchHistory', list)
			commit('SET_HISTORY', list)
		},
		clear_history({commit}) {
			uni.removeStorageSync('searchHistory')
			commit('CLEAR_HISTORY')
		},
		set_system_userinfo({commit}, systemUserInfo) {
			uni.setStorageSync('systemUserInfo', systemUserInfo)
			commit('SET_SYSTEM_USERINFO', systemUserInfo)
		},
		set_is_login({commit}, isLogin) {
			uni.setStorageSync('isLogin', isLogin)
			commit('SET_ISLOGIN', isLogin)
		},
		set_userid({commit}, userid) {
			uni.setStorageSync('userid', userid)
			commit('SET_USERID', userid)
		},
		set_label_all({commit}, labelAll) {
			uni.setStorageSync('labelAll', labelAll)
			commit('SET_LABEL_ALL', labelAll)
		},
		set_label_list({commit}, labelList) {
			uni.setStorageSync('labelList', labelList)
			commit('SET_LABEL_LIST', labelList)
		}
	}
})

export default store