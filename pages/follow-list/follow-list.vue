<template>
	<view class="content">
		<list-scroll class="list-scroll" @loadmore="loadmore" v-for="(item,index) in userList" :key="index">
			<list-author :item="item" @click="getAuthorDetail"></list-author>
		</list-scroll>
		<uni-load-more iconType="snow" :status="this.load.loading"></uni-load-more>
	</view>
</template>

<script>
	import ListAuthor from "@/components/list-author/list-author.vue"
	import ListScroll from "@/components/list-scroll/list-scroll.vue"
	import {mapState} from "vuex"
	export default {
		computed: {
			...mapState(['userid'])
		},
		components: {
			ListAuthor, ListScroll
		},
		data() {
			return {
				pageSize: 10,
				userList: [],
				load: {},
				reqUrl: ""
			};
		},
		onLoad(data) {
			const {params} = data
			var title = ""
			if (params === "following") {
				this.reqUrl = this.$api.address + `forum/get_following_user/${this.userid}`
				title = "关注用户"
			} else if (params === "fans") {
				this.reqUrl = this.$api.address + `forum/get_fans_user/${this.userid}`
				title = "我的粉丝"
			}
			uni.setNavigationBarTitle({
				title: title
			})
			this.getList(this.userid)
		},
		methods: {
			getList(userid) {
				if (!this.load || JSON.stringify(this.load === "{}")) {
					this.load = {
						loading: 'loading',
						page: 1
					}
				}
				uni.request({
					method: 'GET',
					url: this.reqUrl + `/${this.load.page}/${this.pageSize}`,
					success: (res) => {
						const {code, data} = res.data
						if (code === 200) {
							if (data.length === 0) {
								let oldLoad = {};
								oldLoad.loading = "noMore";
								oldLoad.page = this.load.page;
								this.load = oldLoad
								this.$forceUpdate()
								return
							}
							let oldList = this.userList
							oldList.push(...data)
							this.userList = oldList
						} else if (code === 500) {
							let oldLoad = {}
							oldLoad.loading = 'noMore',
							oldLoad.page = this.load.page
							this.load = oldLoad
							this.$forceUpdate()
							uni.showToast({
								title: '获取用户列表失败, 请稍后再试',
								icon: 'none'
							})
						}
					},
					fail: (err) => {
						let oldLoad = {}
						oldLoad.loading = 'noMore',
						oldLoad.page = this.load.page
						this.load = oldLoad
						this.$forceUpdate()
						uni.showToast({
							title: '获取用户列表失败, 请稍后再试',
							icon: 'none'
						})
					}
				})
			},
			loadmore() {
				if (this.load.loading === 'noMore') return
				this.load.page++
				this.getList(this.userid)
			},
			getAuthorDetail(item) {
				this.$store.dispatch('set_author_info_cache', item)
				uni.navigateTo({
					url: '/pages/user-detail/user-detail?params=follow'
				})
			}
		}
	}
</script>

<style lang="scss">
	.content {
		height: 100%;
		overflow: hidden;
		.list-scroll {
			height: 100%;
		}
	}	
</style>
