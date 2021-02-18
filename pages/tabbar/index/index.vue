<template>
	<view class="home">
		<navbar></navbar>
		<tab :list="tabList" @tab="tab" :tabIndex="tabIndex"></tab>
		<view class="home-list">
			<list :tab="tabList" @change="change" :activeIndex="activeIndex"></list>
		</view>
	</view>
</template>

<script>
	import NavBar from '@/components/navbar/navbar.vue'
	import Tab from '@/components/tab/tab.vue'
	import List from '@/components/list/list.vue'
	import {mapState} from 'vuex'
	export default {
		components: {
			NavBar, Tab, List
		},
		computed: {
			...mapState(['isLogin', 'userid', 'systemUserInfo', 'labelList', 'labelAll'])
		},
		data() {
			return {
				tabList: [],
				tabIndex: 1,
				activeIndex: 1
			}
		},
		onLoad() {
			if (this.userid === null) {
				this.getLabelStateless()
			} else {
				this.getLabel()
			}
		},
		watch: {
			userid(newVal) {
				this.getLabel()
			},
			labelAll(newVal) {
				var data = newVal.filter(item => item.current)
				data.unshift(
					{"_id": "000001", "label_name": "关注"},
					{"_id": "000002", "label_name": "推荐"}
				)
				this.tabList = data
			}
		},
		methods: {
			// stateful methods
			getLabel() {
				uni.request({
					url: 'http://localhost:8000/forum/get_label',
					data: {
						id: this.userid
					},
					success: (res) => {
						const {data} = res.data
						// this storage contains label without '关注' & '推荐'
						this.$store.dispatch('set_label_all', data)
						// uni.setStorageSync('systemUserInfo', suserInfo)
						let newData = data.filter(item => item.current)
						newData.unshift(
							{"_id": "000001", "label_name": "关注"},
							{"_id": "000002", "label_name": "推荐"}
						)
						this.tabList = newData
						this.$store.dispatch('set_label_list', newData)
						// uni.setStorageSync('label_list', newData)
					}
				})
			},
			// stateless methods
			getLabelStateless() {
				uni.request({
					url: 'http://localhost:8000/forum/stateless/get_label',
					success: (res) => {
						const {data} = res.data
						let newData = data
						newData.unshift(
							{"_id": "000001", "label_name": "关注"},
							{"_id": "000002", "label_name": "推荐"}
						)
						this.tabList = newData
					}
				})
			},
			// public methods
			tab({data, index}) {
				this.activeIndex = index
			},
			change(current) {
				this.tabIndex = current
				this.activeIndex = current
			},
		}
	}
</script>

<style lang="scss">
	page {
		height: 100%;
		display: flex;
	}
	.home {
		display: flex;
		flex-direction: column;
		flex: 1;
		overflow: hidden;
		.home-list {
			flex: 1;
			box-sizing: border-box;
		}
	}
</style>
