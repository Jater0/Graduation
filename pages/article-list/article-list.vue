<template>
	<view class="content">
		<list-scroll class="list-scroll">
			<view class="" v-for="(item, index) in listCacheData" :key="index">
				<view class="list-card mode-cloumn">
					<view class="list-card-content">
						<view class="list-card-content-title">
							<text>{{item.title}}</text>
							<likes :item="item" :types="types"></likes>
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
	</view>
</template>

<script>
	import ListScroll from '@/components/list-scroll/list-scroll.vue'
	import ListCard from '@/components/list-card/list-card.vue'
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
		},
		components: {
			ListScroll, ListCard, Likes
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
						
					}
					var url = this.$api.address + `forum/${type}/${this.userid}/${this.load.page}/${this.pageSize}`
					console.log(url);
					uni.request({
						method: 'GET',
						url:url,
						success: (res) => {
							const {data, code} = res.data
							console.log(data);
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
								console.log(this.listCacheData);
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
