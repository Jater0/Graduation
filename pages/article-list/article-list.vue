<template>
	<view class="content">
		<list-scroll class="list-scroll" @loadmore="loadmore">
			<view class="" v-for="(item, index) in listCacheData" :key="index" @click="openDetail(item, index)">
				<view class="list-card mode-cloumn">
					<view class="list-card-content">
						<view class="list-card-content-title">
							<text>{{item.title}}</text>
							<likes :item="item" types="follow" v-if="params === 'favorites'"></likes>
							<view class="icons" v-else @click.stop="confirmMoveTrash(item._id, index)">
								<uni-icons type="trash" size="20"></uni-icons>
							</view>
						</view>
						<view class="list-card-content-des">
							<view class="list-card-content-des-label">
								<view class="list-card-content-des-label-item">{{item.classify}}</view>
							</view>
							<view class="list-card-content-des-browse">{{item.browse_count}}浏览</view>
						</view>
					</view>
				</view>
			</view>
		</list-scroll>
		<uni-load-more iconType="snow" :status="this.load.loading"></uni-load-more>
	</view>
</template>

<script>
	import ListScroll from '@/components/list-scroll/list-scroll.vue'
	import Likes from '@/components/likes/likes.vue'
	import {mapState} from 'vuex'
	export default {
		computed: {
			...mapState(['userid'])
		},
		onLoad(data) {
			this.load = {}
			this.listCacheData = []
			const {params} = data
			this.params = params
			this.getArticle(params)
			if (params === 'favorites') {
				uni.setNavigationBarTitle({
					title: '我的收藏'
				})
			} else if (params === 'myarticle') {
				uni.setNavigationBarTitle({
					title: '我的文章'
				})
			} else if (params === 'mytopic') {
				uni.setNavigationBarTitle({
					title: "我的话题"
				})
			}
			uni.$on('is_delete', (data) => {
				var oldList = this.listCacheData
				oldList.splice(data, 1)
				this.listCacheData = oldList
			})
		},
		components: {
			ListScroll, Likes
		},
		data() {
			return {
				params: "",
				listCacheData: [],
				load: {},
				pageSize: 10,
			};
		},
		methods: {
			getArticle(params) {
				if (!this.load || JSON.stringify(this.load) === '{}') {
					this.load = {
						page: 1,
						loading: 'loading'
					}
					var type = ""
					if (params === 'favorites') {
						type = "get_article_like"
					} else if (params === 'myarticle') {
						type = "get_article_own"
					} else if (params === 'mytopic') {
						type = "get_topic_own"
					}
					var url = this.$api.address + `forum/${type}/${this.userid}/${this.load.page}/${this.pageSize}`
					uni.request({
						method: 'GET',
						url:url,
						success: (res) => {
							const {data, code} = res.data
							if (code === 200) {
								if (data.length === 0) {
									let oldLoad = {}
									oldLoad.loading = 'noMore',
									oldLoad.page = this.load.page
									this.load = oldLoad
									this.$forceUpdate()
									return
								}
								let oldList = this.listCacheData
								oldList.push(...data)
								this.listCacheData = oldList
							} else if (code === 500) {
								let oldLoad = {}
								oldLoad.loading = 'noMore',
								oldLoad.page = this.load.page
								this.load = oldLoad
								this.$forceUpdate()
							}
						},
						fail: (err) => {
							let oldLoad = {}
							oldLoad.loading = 'noMore',
							oldLoad.page = this.load.page
							this.load = oldLoad
							this.$forceUpdate()
						}
					})
				}
			},
			loadmore() {
				if (this.load.loading === 'noMore') return
				this.load.page++
				this.getArticle(this.params)
			},
			openDetail(item, index) {
				this.$store.dispatch('set_topic_detail_cache', item)
				if (this.params === "mytopic") {
					uni.navigateTo({
						url: '/pages/topics-detail/topics-detail?params=' + item._id
					})
				} else {
					uni.navigateTo({
						url: '/pages/article-detail/article-detail?params=' + item._id + '&mode=' + item.mode + '&index=' + index
					})
				}
			},
			confirmMoveTrash(id, index) {
				uni.showModal({
					title: '删除确认',
					content: '你确认要删除这篇文章/话题吗???如果删除其下面的数据将会一同消失',
					success: (res) => {
						if (res.confirm) {
							this.moveTrash(id, index)
						}
					}
				})
			},
			moveTrash(id, index) {
				uni.showLoading()
				var type = ""
				if (this.params === 'mytopic') {
					type = "topic"
				} else if (this.params === 'myarticle') {
					type = "article"
				}
				uni.request({
					method: 'POST',
					url: this.$api.address + `forum/delete/${type}/${id}`,
					success: (res) => {
						uni.hideLoading()
						const {code, data} = res.data
						if (code === 200) {
							uni.showToast({
								title: '删除数据成功',
								icon: 'success'
							})
							var oldList = this.listCacheData
							oldList.splice(index, 1)
							this.listCacheData = oldList
							this.$forceUpdate()
							uni.$emit('delete_topic_article')
						} else if (code === 500) {
							uni.showToast({
								title: '删除数据失败, 请稍后再试',
								icon: 'none'
							})
						}
					},
					fail: (err) => {
						uni.hideLoading()
						uni.showToast({
							title: '删除数据失败, 请稍后再试',
							icon: 'none'
						})
					}
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
			.list-card {
				display: flex;
				padding: 20rpx;
				margin: 20rpx;
				border-radius: 10rpx;
				box-shadow: 0 0 10rpx 2rpx rgba($color: #000000, $alpha: 0.1);
				box-sizing: border-box;
				.list-card-content {
					display: flex;
					flex-direction: column;
					justify-content: space-between;
					padding-left: 20rpx;
					width: 100%;
					.list-card-content-title {
						padding-right: 60rpx;
						position: relative;
						padding-right: 60rpx;
						font-size: 28rpx;
						color: #333;
						font-weight: 400;
						line-height: 1.2;
						text {
							overflow: hidden;
							text-overflow: ellipsis;
							display: -webkit-box;
							-webkit-line-clamp: 2;
							-webkit-box-orient: vertical;
						}
						.icons {
							position: absolute;
							right: 0;
							top: 0;
							display: flex;
							justify-content: center;
							align-items: center;
							width: 40rpx;
							height: 40rpx;
						}
					}
					.list-card-content-des {
						display: flex;
						justify-content: space-between;
						font-size: 24rpx;
						.list-card-content-des-label {
							display: flex;
							.list-card-content-des-label-item {
									padding: 0 10rpx;
									margin-right: 10rpx;
									border-radius: 30rpx;
									color: $forum-base-color;
									border: 2rpx $forum-base-color solid;
							}
						}
						.list-card-content-des-browse {
							color: #999;
							line-height: 1.5;
						}
					}
				}
				&.mode-cloumn {
					.list-card-content {
						width: 100%;
						padding-left: 0;
					}
					.list-card-image {
						display: flex;
						margin-top: 20rpx;
						width: 100%;
						height: 140rpx;
						.list-card-image-item {
							margin-left: 20rpx;
							width: 100%;
							border-radius: 10rpx;
							overflow: hidden;
							&:first-child {
								margin-left: 0;
							}
							image {
								width: 100%;
								height: 100%;
							}
						}
					}
					.list-card-content-des {
						margin-top: 20rpx;
					}
				}
			}
		}
	}
</style>
