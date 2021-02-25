import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    adminInfo: localStorage.getItem('adminInfo') || {},
    isRemember: localStorage.getItem('isRemember') || false,
    label: [],
    feedbackCache: {},
    feedbackLoadCache: {},
    roleIdListCache: [],
    articleContentCache: ""
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
    },
    SET_LABEL(state, label) {
      state.label = label
    },
    SET_FEEDBACKCACHE(state, feedback) {
      state.feedbackCache = feedback
    },
    SET_FEEDBACK_LOADCACHE(state, load) {
      state.feedbackLoadCache = load
    },
    SET_ROLEIDLIST_CACHE(state, roleIds) {
      state.roleIdListCache = roleIds
    },
    SET_ARTCILE_CONTENT_CACHE(state, content) {
      state.articleContentCache = content
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
    },
    set_label({commit}, label) {
      commit('SET_LABEL', label)
    },
    set_feedback_cache({commit}, feedback) {
      commit('SET_FEEDBACKCACHE', feedback)
    },
    set_feedback_load_cache({commit}, load) {
      commit('SET_FEEDBACK_LOADCACHE', load)
    },
    set_roleId_list_cache({commit}, roleIds) {
      commit('SET_ROLEIDLIST_CACHE', roleIds)
    },
    set_article_content_cache({commit}, content) {
      commit("SET_ARTCILE_CONTENT_CACHE", content)
    }
  }
})