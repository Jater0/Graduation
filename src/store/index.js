import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    adminInfo: localStorage.getItem('adminInfo') || {},
    isRemember: localStorage.getItem('isRemember') || false
  },
  mutations: {
    SET_ADMIN_INFO(state, adminInfo) {
      state.adminInfo = adminInfo
    },
    CLEAR_ADMIN_INFO(state) {
      state.adminInfo = {}
    },
    SET_ISREMEMBER(state, isRemember) {
      state.isRemember = isRemember
    }
  },
  actions: {
    set_adminInfo({commit}, adminInfo) {
      localStorage.setItem('adminInfo', adminInfo)
      commit('SET_ADMIN_INFO', adminInfo)
    },
    clear_adminInfo({commit}) {
      localStorage.removeItem('adminInfo')
      commit('CLEAR_ADMIN_INFO')
    },
    set_isRemember({commit}, isRemember) {
      localStorage.setItem('isRemember', isRemember)
      commit('SET_ISREMEMBER', isRemember)
    }
  }
})