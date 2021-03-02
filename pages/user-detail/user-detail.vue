<template>
	<view class="content">
		<view class="info-title">基本信息: </view>
		<list-author :item="authorInfo"></list-author>
		<view class="info-title">他的文章: </view>
		<list-scroll class="list-scroll" @loadmore="loadmore">
			<view class="" v-for="(item, index) in listCacheData" :key="index" @click="openDetail(item, index)">
				<view class="list-card mode-cloumn">
					<view class="list-card-content">
						<view class="list-card-content-title">
							<text>{{item.title}}</text>
							<likes :item="item" types="follow"></likes>
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
	import {mapState} from "vuex"
	export default {
		computed: {
			...mapState(['authorInfoCache', 'userid'])
		},
		onLoad(data) {
			const {params} = data
			if (params === "follow") {
				this.authorInfo = this.authorInfoCache
				this.$store.dispatch('clear_author_info_cache')
				console.log(this.authorInfo);
				this.getAuthorArticleList(this.authorInfo._id)
				uni.setNavigationBarTitle({
					title: `${this.authorInfo.author_name} 的 空间`
				})
			} else if (params === "article") {
				const {id} = data
				this.getAuthorInfo(id)
			}
		},
		data() {
			return {
				authorInfo: {},
				listCacheData: [],
				load: {},
				pageSize: 10
			};
		},
		methods: {
			getAuthorInfo(id) {
				uni.showLoading()
				uni.request({
					method: 'GET',
					url: this.$api.address + `forum/get_info_others/${this.userid}/${id}`,
					success: (res) => {
						uni.hideLoading()
						const {code, data} = res.data
						if (code === 200) {
							this.authorInfo = data
							this.getAuthorArticleList(this.authorInfo._id)
							uni.setNavigationBarTitle({
								title: `${this.authorInfo.author_name} 的 空间`
							})
						} else if (code === 500) {
							uni.showToast({
								title: '获取用户信息失败, 请稍后再试',
								icon: 'none'
							})
							uni.navigateBack()
						}
					},
					fail: (err) => {
						uni.showToast({
							title: '获取用户信息失败, 请稍后再试',
							icon: 'none'
						})
						uni.navigateBack()
					}
				})
			},
			getAuthorArticleList(id) {
				if (!this.load || JSON.stringify(this.load) === "{}") {
					this.load = {
						loading: "loading",
						page: 1
					}
				}
				uni.request({
					method:'GET',
					url: this.$api.address + `forum/get_article_others/${this.userid}/${id}/${this.load.page}/${this.pageSize}`,
					success: (res) => {
						const {data, code} = res.data
						if (code === 200) {
							console.log(data);
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
							uni.showToast({
								title: "获取用户文章列表失败",
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
							title: "获取用户文章列表失败",
							icon: 'none'
						})
					}
				})
			},
			loadmore() {
				if (this.load.loading === 'noMore') return
				this.load.page++
				this.getAuthorArticleList(this.authorInfo._id)
			},
			openDetail(item, index) {
				this.$store.dispatch('set_topic_detail_cache', item)
				uni.navigateTo({
					url: '/pages/article-detail/article-detail?params=' + item._id + '&mode=' + item.mode + '&index=' + index
				})
			},
		}
	}
</script>

<style lang="scss">
	.content {
		height: 100%;
		overflow: hidden;
		.info-title {
			margin: 30rpx;
			margin-bottom: 0;
			font-size: 28rpx;
			color: #666;
			font-weight: bold;
		}
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
