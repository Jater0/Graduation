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
		<view class="article-area">
			<swiper :style="{height: articleAreaHeight + 'px'}">
				<swiper-item v-for="(item, index) in articles" :key="index">
					<scroll-view scroll-y :style="{height: articleAreaHeight + 'px'}">
						<article-detail :article="item"></article-detail>
					</scroll-view>
				</swiper-item>
			</swiper>
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
				tabIndex: 0,
				articleAreaHeight: 200,
				topicDetail: {}
			};
		},
		filters: {
			formatTime(time) {
				return convertDate(time)
			}
		},
		computed: {
			...mapState(['userid'])
		},
		watch: {
			articles(newVal) {
				this.articles = newVal
			}
		},
		onLoad(data) {
			this.getTopicDetailById(data.params)
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
							console.log(this.topicDetail);
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
				uni.request({
					url: 'http://localhost:8000/forum/get_article',
					data: {
						id: this.userid,
						topic: topic_id,
						page: 1,
						size: 3
					},
					success: (res) => {
						const {data} = res.data
						this.articles = data
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
					this.articleAreaHeight = info.windowHeight - data.height
				}).exec()
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
		}
	}
</style>
