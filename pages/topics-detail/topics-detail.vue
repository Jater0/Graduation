<template>
	<view class="topic">
		<view class="topic-area">
			<view class="topic-content">
				<view class="topic-content-title">{{topicDetail.title}}</view>
				<view class="topic-content-cover" v-if="topicDetail.cover.length !== 0">
					<view class="topic-content-cover-item" v-if="index < 3" v-for="(item, index) in topicDetail.cover" :key="index">
						<image :src="item" mode="aspectFill"></image>
					</view>
				</view>
				<view class="topic-content-detail">
					<view class="topic-content-detail-des">
						<view class="topic-content-detail-des-name" @click="test">
							<text>{{topicDetail.author_name}}</text>
						</view>
						<view class="topic-content-detail-des-info">
							<text>{{topicDetail.create_time | formatTime}}</text>
							<view>
								<text>浏览: {{topicDetail.browse_count}}</text>
								<text>点赞: {{topicDetail.thumbs_up_count}}</text>
							</view>
						</view>
					</view>
					<button type="default" class="topic-content-detail-write" @click="writeArticleForTopic">写回复</button>
				</view>
			</view>
		</view>
		<view class="article-area" v-if="!noDataShow">
			<swiper :style="{height: articleAreaHeight +'px'}" @change="loadmore">
				<swiper-item v-for="(item, index) in articles" :key="index">
					<scroll-view scroll-y style="height: 100%;">
						<article-detail :article="item"></article-detail>
					</scroll-view>
				</swiper-item>
			</swiper>
		</view>
		<view class="article-area" v-else>
			<view class="no-data">
				暂时还没有文章, 请回复此话题吧
			</view>
		</view>
	</view>
</template>

<script>
	import ArticleDetail from '@/components/article-detail/article-detail.vue'
	import {convertDate, showLoginModel} from '@/utils/index.js'
	import {mapState} from 'vuex'
	export default {
		components: {
			ArticleDetail
		},
		data() {
			return {
				articles: [],
				load: {},
				tabIndex: 0,
				articleAreaHeight: 0,
				topicDetail: {},
				pageSize: 5,
				noDataShow: false,
				requestTime: 0
			};
		},
		filters: {
			formatTime(time) {
				return convertDate(time)
			}
		},
		computed: {
			...mapState(['userid', 'topicDetailCache'])
		},
		watch: {
			articles(newVal) {
				this.articles = newVal
			}
		},
		onLoad(data) {
			this.topicDetail = this.topicDetailCache
			this.getArticleByTopicId(data.params)
		},
		onReady() {
			this.heightHandler()
		},
		methods: {
			getTopicDetailById(topic_id) {
				uni.showLoading()
				uni.request({
					url: 'http://localhost:8000/forum/get_topic',
					data: {
						id: topic_id
					},
					success: (res) => {
						const {code, data} = res.data
						if (code === 200) {
							uni.hideLoading()
							this.topicDetail = data
							this.heightHandler()
							this.getArticleByTopicId(topic_id)
						} else {
							uni.hideLoading()
							uni.showToast({
								title: '获取数据失败, 请稍后再试',
								icon: 'none'
							})
						}
					},
					fail: (res) => {
						uni.hideLoading()
						uni.showToast({
							title: '获取数据失败, 请稍后再试',
							icon: 'none'
						})
					}
				})
			},
			getArticleByTopicId(topic_id) {
				if (!this.load || JSON.stringify(this.load) === '{}') {
					this.load = {
						page: 1,
						loading: 'loading'
					}
				}
				uni.request({
					method: 'GET',
					url: this.$api.address + `forum/get_article/${this.userid}/${topic_id}/${this.load.page}/${this.pageSize}`,
					success: (res) => {
						const {data, code} = res.data
						if (code === 200) {
							if (data.length === 0) {
								if (this.requestTime === 0) {
									this.noDataShow = true
									return
								}
								let oldLoad = {}
								oldLoad.loading = 'noMore'
								oldLoad.page = this.load.page
								this.load = oldLoad
								this.$forceUpdate()
								uni.showToast({
									title: '已经全部加载',
									icon: 'none'
								})
								return
							}
							this.requestTime++
							let oldList = this.articles
							oldList.push(...data)
							this.articles = oldList
							this.$forceUpdate()
						} else if (code === 500) {
							let oldLoad = {}
							oldLoad.loading = 'noMore'
							oldLoad.page = this.load.page
							this.load = oldLoad
							this.$forceUpdate()
							uni.showToast({
								title: '数据请求失败, 请稍后再试',
								icon: 'none'
							})
						}
					},
					fail: (err) => {
						let oldLoad = {}
						oldLoad.loading = 'noMore'
						oldLoad.page = this.load.page
						this.load = oldLoad
						this.$forceUpdate()
						uni.showToast({
							title: '数据请求失败, 请稍后再试',
							icon: 'none'
						})
					}
				})
			},
			change(e) {
				this.tabIndex = e.detail.current
			},
			showHeight(e) {
				var index = e.tabIndex
				var height = e.height
				this.articleDetailHeightMap[index] = height
				this.tabIndex = this.tabIndex
			},
			heightHandler() {
				const query = uni.createSelectorQuery().in(this)
				query.select('.topic-area').boundingClientRect(data => {
					const info = uni.getSystemInfoSync()
					this.articleAreaHeight = this.articleAreaHeight + info.windowHeight - data.height
				}).exec()
			},
			writeArticleForTopic() {
				var paramsObj = {
					id: this.topicDetail._id,
					type: this.topicDetail.classify,
					title: this.topicDetail.title
				}
				this.$store.dispatch('set_answerTopic_cache', paramsObj)
				uni.navigateTo({
					url: '/pages/article-imple/article-imple?params=answer'
				})
			},
			loadmore(data) {
				console.log("no-more");
				if (this.load.loading === 'noMore') return
				if (data.detail.current === this.articles.length) {
					this.load.page++
					this.getArticleByTopicId(this.topicDetail._id)
				}
			}
		}
	}
</script>

<style lang="scss">
	page {
		
	}
	.topic {
		display: flex;
		flex-direction: column;
		flex: 1;
		overflow: hidden;
		.topic-area {
			background-color: #FFCCCC;
			padding-bottom: 30rpx;
			.topic-content-title {
				padding: 0 30rpx;
				font-size: 36rpx;
				font-weight: bold;
				color: #333;
			}
			.topic-content-cover {
				display: flex;
				margin-top: 20rpx;
				width: 100%;
				height: 140rpx;
				.topic-content-cover-item {
					margin-left: 20rpx;
					width: 100%;
					border-radius: 10rpx;
					overflow: hidden;
					&:first-child {
						margin-left: 10rpx;
					}
					&:last-child {
						margin-right: 10rpx;
					}
					image {
						width: 100%;
						height: 100%;
					}
				}
			}
			.topic-content-detail {
				display: flex;
				align-items: center;
				margin-top: 20rpx;
				padding: 0 30rpx;
				.topic-content-detail-des {
					width: 100%;
					padding-left: 20rpx;
					display: flex;
					flex-direction: column;
					justify-content: space-between;
					font-size: 24rpx;
					.topic-content-detail-des-name {
						text {
							font-size: 36rpx;
							color: #007AFF;
							text-decoration: underline;
						}
					}
					.topic-content-detail-des-info {
						text {
							margin-right: 20rpx;
						}
					}
				}
				.topic-content-detail-write {
					flex-shrink: 0; // 不去挤压
					height: 60rpx;
					font-size: 24rpx;
					color: #fff;
					background-color: $forum-base-color;
				}
			}
		}
		.article-area {
			flex: 1;
			box-sizing: border-box;
			swiper-item {
				overflow: scroll;
			}
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
