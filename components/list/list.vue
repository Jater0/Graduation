<template>
	<swiper class="home-swiper" @change="change" :current="activeIndex">
		<swiper-item class="swiper-item" v-for="(item, index) in tab" :key="index">
			<view v-if="index === 0 && noDataShow === null" class="no-data">请先登录</view>
			<list-item v-else :list="listCatchData[index]" @loadmore="loadmore" :load="load[index]"></list-item>
		</swiper-item>
	</swiper>
</template>

<script>
	import ListItem from './list-item.vue'
	import {mapState} from 'vuex'
	export default {
		components: {
			ListItem
		},
		computed: {
			...mapState(['userid'])
		},
		props: {
			tab: {
				type: Array,
				default() {
					return []
				}
			},
			activeIndex: {
				type: Number,
				default: 1
			}
		},
		data() {
			return {
				list: [],
				listCatchData: {},
				load: {},
				pageSize: 10,
				noDataShow: null
			};
		},
		created() {
			if (this.userid !== null) {
				this.noDataShow = this.userid
			}
			uni.$on('update_article_like', (e) => {
				if (e === 'follow') {
					this.listCatchData = {}
					this.load = {}
					this.getList(this.activeIndex)
				}
			})
		},
		watch: {
			tab(newVal) {
				if (newVal.length === 0) return
				this.listCatchData = {}
				this.load = {}
				this.getList(1)
			},
			userid(newVal) {
				this.noDataShow = this.userid
				this.listCatchData = {}
				this.load = {}
				this.getList(1)
			}
		},
		methods: {
			change(e) {
				const {current} = e.detail
				this.$emit('change', current)
				if (!this.listCatchData[current] || this.listCatchData[current].length === 0) {
					this.getList(current)
				}
			},
			getList(current) {
				if (!this.load[current]) {
					this.load[current] = {
						page: 1,
						loading: 'loading'
					}
				}
				var url = ''
				var data = {}
				if (this.userid === null) {
					if (current > 1) {
						console.log("stateless Type");
						url = 'http://localhost:8000/forum/stateless/get_list'
						data = {
							name: this.tab[current].label_name,
							page: this.load[current].page,
							size: this.pageSize
						}
					} else if (current === 1) {
						console.log("Recommend List");
					} else if (current === 0) {
						console.log("Stateless Following");
					}
				} else if (this.userid !== null){
					if (current === 1) {
						console.log("Recommend List");
					} else if (current > 1) {
						console.log("Stateful Type");
						url = 'http://localhost:8000/forum/get_list'
						data = {
							id: this.userid,
							name: this.tab[current].label_name,
							page: this.load[current].page,
							size: this.pageSize
						}
					} else if (current === 0) {
						console.log("Stateful Following");
						url = 'http://localhost:8000/forum/get_following'
						data = {
							id: this.userid,
							page: this.load[current].page,
							size: this.pageSize
						}
					}
				}
				uni.request({
					url: url,
					data: data,
					success: (res) => {
						const {data} = res.data
						if (data.length === 0) {
							let oldLoad = {}
							oldLoad.loading = 'noMore'
							oldLoad.page = this.load[current].page
							this.$set(this.load, current, oldLoad)
							this.$forceUpdate()
							return
						}
						let oldList = this.listCatchData[current] || []
						oldList.push(...data)
						this.$set(this.listCatchData, current, oldList)
					}
				})
			},
			loadmore() {
				if (this.load[this.activeIndex].loading === 'noMore') return
				this.load[this.activeIndex].page++
				this.getList(this.activeIndex)
			}
		}
	}
</script>

<style lang="scss">
	.home-swiper {
		height: 100%;
		.swiper-item {
			height: 100%;
			overflow: hidden;
			.no-data {
				text-align: center;
				width: 100%;
				padding: 100rpx 0;
				color: #999;
				font-size: 28rpx;
			}
		}
	}
</style>
