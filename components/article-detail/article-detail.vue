<template>
	<view class="article">
		<view class="article-title">{{title}}</view>
		<view class="article-header">
			<view class="article-header-logo">
				<image :src="article.avatar" mode="aspectFill"></image>
			</view>
			<view class="article-header-content">
				<view class="article-header-content-title" @click="openAuthorDetail">
					{{author_name}}
				</view>
				<view class="article-header-content-info">
					<text>{{create_time | formatTime}}</text>
					<view>
						<text>浏览: {{browse_count}}</text>
						<text>点赞: {{thumbs_up_count}}</text>
					</view>
				</view>
			</view>
			<button type="default" class="article-author-follow" @click="follow(article.author_id)">{{is_author_like?'取消关注':'关注'}}</button>
		</view>
		<view class="article-content">
			<view class="article-content-html">
				<towxml :nodes="nodes"></towxml>
			</view>
			<view class="comment">
				<view class="comment-title">最新评论</view>
				<view class="comment-content">
					<view v-for="item in commentList" :key="item.comment_id">
						<comments-box :comments="item" @reply="reply"></comments-box>
						<view class="underline"></view>
					</view>
				</view>
			</view>
		</view>
		<view class="article-action">
			<view class="article-action-comment-input" @click="openCommentView(0)">
				<text>评论</text>
				<uni-icons type="compose" size="16" color="#f07373"></uni-icons>
			</view>
			<view class="article-action-icons">
				<view class="article-action-icons-box" @click="likeTap(article._id)">
					<uni-icons :type="is_like?'heart-filled':'heart'" size="22" color="#f07373"></uni-icons>
				</view>
				<view class="article-action-icons-box" @click="thumbsup(article._id)">
					<uni-icons :type="is_thumbs_up?'hand-thumbsup-filled':'hand-thumbsup'" size="22" color="#f07373"></uni-icons>
				</view>
			</view>
		</view>
		<action-bottom ref="popup" @publish="publish"></action-bottom>
	</view>
</template>

<script>
	import UParse from '@/components/gaoyia-parse/parse.vue'
	import CommentBox from '@/components/comments-box/comments-box.vue'
	import ActionBottom from '@/components/action-bottom/action-bottom.vue'
	import towxml from '../../static/towxml/towxml'
	import {showLoginModel, getID, convertDate} from '@/utils/index.js'
	import {mapState} from 'vuex'
	export default {
		components: {
			UParse, CommentBox, ActionBottom, towxml
		},
		computed: {
			...mapState(['userid', 'systemUserInfo'])
		},
		filters: {
			formatTime(time) {
				return convertDate(time)
			}
		},
		props: {
			article: {
				type: Object,
				default() {
					return {}
				}
			},
			indexInTopic: {
				type: Number,
				default: 0
			},
			mode: {
				type: String,
				default: "article"
			}
		},
		watch: {
			article(newVal) {
				this.loadArticle(this.reqTime)
			},
			commentList(newVal) {
				this.commentList = newVal
			}
		},
		mounted() {
			if (this.mode === "topic") {
				this.loadArticle(this.reqTime)
			}
		},
		onShow() {
			console.log("show");
		},
		destroyed() {
			if (this.mode === "article") {
				clearTimeout(this.updateBrowseCount)
			}
		},
		data() {
			return {
				commentValue: '',
				commentList: [],
				updateCommentData: {},
				title: 'Loading',
				author_name: 'Loading',
				browse_count: 0,
				thumbs_up_count: 0,
				create_time: 'Loading',
				is_author_like: false,
				is_like: false,
				is_thumbs_up: false,
				updateBrowseCount: null,
				nodes: {},
				reqTime: 0
			};
		},
		methods: {
			openAuthorDetail() {
				uni.navigateTo({
					url: '/pages/user-detail/user-detail?params=article&id=' + this.article.author_id
				})
			},
			getContent() {
				var contentUrl = this.article.content
				uni.request({
					url: contentUrl,
					success: (res) => {
						const {data} = res
						this.nodes = this.towxml(data, 'markdown', {})
						if (this.mode === "article") {
							this.updateBrowseCount = setTimeout(()=> {
								var data = {}
								data[this.indexInTopic] = this.article._id
								this.$emit('browse', data)
							}, 3000)
						} else if (this.mode === "topic") {
							var d = {}
							d["index"] = this.indexInTopic
							d["id"] = this.article._id
							this.$emit('browse', d)
						}
					},
					fail: (res) => {
						uni.showToast({
							title: '请检查你的网络',
							icon: 'none'
						})
					}
				})
			},
			follow(data) {
				var uid = this.userid
				var that = this
				if (uid === null) {
					showLoginModel()
				} else {
					this.is_author_like = !this.is_author_like
					this.updateAuthorLike(uid, data)
				}
			},
			updateAuthorLike(uid, author) {
				uni.showLoading()
				uni.request({
					url: this.$api.address + `forum/author_like/${uid}/${author}`,
					success: (res) => {
						const {code, data} = res.data
						if (code === 200) {
							var userinfo = this.systemUserInfo
							uni.hideLoading()
							uni.$emit('update_author_follow')
							uni.showToast({
								title: this.is_author_like?'关注作者成功': '取消关注'
							})
							if (this.is_author_like) {
								userinfo.follow_count = userinfo.follow_count + 1
							} else {
								userinfo.follow_count = userinfo.follow_count - 1
							}
							this.$store.dispatch('set_system_userinfo', userinfo)
						} else if (code === 500) {
							uni.hideLoading()
							this.is_author_like = !this.is_author_like
							uni.showToast({
								title: '关注失败, 请稍后再试',
								icon: 'none'
							})
						}
					},
					fail: (res) => {
						uni.hideLoading()
						this.is_author_like = !this.is_author_like
						uni.showToast({
							title: '关注失败, 请稍后再试',
							icon: 'none'
						})
					}
				})
			},
			likeTap(data) {
				var uid = this.userid
				if (uid === null) {
					showLoginModel()
				} else {
					this.is_like = !this.is_like
					this.updateArticleLike(uid, data)
				}
			},
			updateArticleLike(uid, article) {
				uni.showLoading()
				uni.request({
					url: this.$api.address + `forum/update_like/${uid}/${article}`,
					success: (res) => {
						uni.hideLoading()
						if (res.data.code === 200) {
							uni.showToast({
								title: this.is_like?'收藏成功':'取消收藏',
								icon: this.is_like?'success':'none'
							})
							uni.$emit('update_article_like', 'follow')
						} else {
							this.is_like = !this.is_like
							uni.showToast({
								title: '收藏失败, 请稍后再试',
								icon:'none'
							})
						}
					},
					fail: () => {
						uni.hideLoading()
						this.like = !this.like
						uni.showToast({
							title: '收藏失败, 请稍后再试',
							icon:'none'
						})
					}
				})
			},
			thumbsup(data) {
				var uid = this.userid
				if (uid === null) {
					showLoginModel()
				} else {
					if (this.is_thumbs_up) {
						uni.showToast({
							title: '你已经点过赞了',
							icon: 'none'
						})
					} else {
						this.is_thumbs_up = !this.is_thumbs_up
						this.updateArticleThumbs(uid, data)
					}
				}
			},
			updateArticleThumbs(uid, article) {
				uni.showLoading()
				uni.request({
					url: this.$api.address + `forum/update_thumbsup/${uid}/${article}`,
					success: (res) => {
						const {code, data} = res.data
						if (code === 200) {
							uni.hideLoading()
							this.thumbs_up_count++
							uni.showToast({
								title: data,
								icon: 'none'
							})
						} else if(code === 500){
							uni.hideLoading()
							this.is_thumbs_up = !this.is_thumbs_up
							uni.showToast({
								title: '点赞失败, 请稍后再试',
								icon: 'none'
							})
						}
					},
					fail: (res) => {
						uni.hideLoading()
						this.is_thumbs_up = !this.is_thumbs_up
						uni.showToast({
							title: '点赞失败, 请稍后再试',
							icon: 'none'
						})
					}
				})
			},
			openCommentView(data) {
				if (this.userid === null) {
					showLoginModel()
					return
				}
				if (data === 0) {
					this.updateCommentData = {
						father_id: this.article._id,
						is_reply: 0,
						tos: '',
						to_user_id: ''
					}
				} else {
					if (data.is_reply) {
						this.updateCommentData = {
							father_id: data.comments.father_id,
							is_reply: 1,
							tos: data.comments.author_name,
							to_user_id: data.comments.author_id
						}
					} else {
						this.updateCommentData = {
							father_id: data.comments.comment_id,
							is_reply: 1,
							tos: '',
							to_user_id: ''
						}
					}
				}
				this.$refs.popup.open()
			},
			closeCommentView() {
				this.$refs.popup.close()
				this.updateCommentData = {}
			},
			getComment() {
				uni.request({
					url: this.$api.address + `forum/get_comment/${this.article._id}`,
					success: (res) => {
						const {code, data} = res.data
						if (code === 200) {
							this.commentList = data
						} else {
							uni.showToast({
								title: '获取评论数据失败',
								icon: 'none'
							})
						}
					},
					fail: (res) => {
						uni.showToast({
							title: '获取评论数据失败',
							icon: 'none'
						})
					}
				})
			},
			reply(e) {
				this.openCommentView(e)
			},
			publish(data) {
				this.updateComment({data, ...this.updateCommentData})
			},
			updateComment(data) {
				uni.showLoading()
				uni.request({
					method: 'GET',
					url: 'http://localhost:8000/forum/update_comment',
					data: {
						cid: 'cm' + getID(5),
						content: data.data,
						reply: data.is_reply,
						father: data.father_id,
						aid: this.userid,
						name: this.systemUserInfo.author_name,
						avatar: this.systemUserInfo.avatar,
						to: data.tos,
						to_uid: data.to_user_id
					},
					success: (res) => {
						if (res.data.code === 200) {
							this.getComment()
							uni.hideLoading()
							uni.showToast({
								title: '评论成功'
							})
							this.closeCommentView()
						} else {
							uni.hideLoading()
							uni.showToast({
								title: '评论失败, 请稍后再试',
								icon: 'none'
							})
							this.closeCommentView()
						}
					},
					fail: (res) => {
						uni.hideLoading()
						uni.showToast({
							title: '评论失败, 请稍后再试',
							icon: 'none'
						})
						this.closeCommentView()
					}
				})
			},
			loadArticle(index) {
				var data = this.article
				this.title = data.title
				this.author_name = data.author_name
				this.browse_count = data.browse_count
				this.thumbs_up_count = data.thumbs_up_count
				this.create_time = data.create_time
				this.is_like = data.is_like
				this.is_thumbs_up = data.is_thumbs_up
				this.is_author_like = data.is_author_like
				this.getComment()
				if (index === 0) {
					this.getContent()
					this.reqTime++
				}
			}
		}
	}
</script>

<style lang="scss">
	.article {
			padding: 30rpx 0;
			padding-bottom: 108rpx;
		}
		.article-title {
			padding: 0 30rpx;
			font-size: 36rpx;
			font-weight: bold;
			color: #333;
		}
		.article-header {
			display: flex;
			align-items: center;
			margin-top: 20rpx;
			padding: 0 30rpx;
			.article-header-logo {
				flex-shrink: 0;
				width: 80rpx;
				height: 80rpx;
				border-radius: 50%;
				overflow: hidden;
				image {
					width: 100%;
					height: 100%;
				}
			}
			.article-header-content {
				width: 100%;
				padding-left: 20rpx;
				display: flex;
				flex-direction: column;
				justify-content: space-between;
				font-size: 24rpx;
				.article-header-content-title {
					font-size: 28rpx;
					color: #333;
				}
				.article-header-content-info {
					color: #999;
					text {
						margin-right: 20rpx;
					}
				}
			}
			.article-author-follow {
				flex-shrink: 0; // 不去挤压
				height: 60rpx;
				font-size: 24rpx;
				color: #fff;
				background-color: $forum-base-color;
			}
		}
		.article-content {
			margin-top: 40rpx;
			min-height: 1000rpx;
			.article-content-html {
				padding: 0 30rpx;
			}
			.comment {
				margin-top: 60rpx;
				.comment-title {
					padding: 20rpx 30rpx;
					font-size: 28rpx;
					color: #f07373;
					border-top: 1px #f5f5f5 solid;
					border-bottom: 1px $forum-base-color solid;
				}
				.comment-content {
					padding: 0 30rpx;
					border-top: 1px $forum-base-color solid;
					.underline {
						border-bottom: 1px $forum-base-color solid;
					}
				}
			}
		}
		.article-action {
			position: fixed;
			bottom: 0;
			display: flex;
			align-items: center;
			height: 88rpx;
			width: 100%;
			border-top: 1px #f5f5f5 solid;
			background-color: #FFFFFF;
			box-sizing: border-box;
			.article-action-comment-input {
				display: flex;
				justify-content: space-between;
				align-items: center;
				margin-left: 20rpx;
				padding: 0 20rpx;
				width: 100%;
				height: 60rpx;
				border: 1px #ddd solid;
				border-radius: 10rpx;
				text {
					font-size: 28rpx;
					color: #999;
				}
			}
			.article-action-icons {
				display: flex;
				flex-shrink: 0;
				padding: 0 20rpx;
				.article-action-icons-box {
					position: relative;
					display: flex;
					align-items: center;
					justify-content: center;
					width: 50rpx;
				}
			}
		}
		.popup-wrap {
			background-color: #fff;
			.popup-header {
				display: flex;
				justify-content: space-between;
				font-size: 14px;
				color: #666;
				border-bottom: 1px #F5F5F5 solid;
				.popup-header-item {
					height: 50px;
					line-height: 50px;
					padding: 0 15px;
				}
			}
			.popup-content {
				width: 100%;
				padding: 30rpx;
				box-sizing: border-box;
				.popup-textarea {
					width: 100%;
					height: 400rpx;
				}
				.popup-count {
					display: flex;
					justify-content: flex-end;
					font-size: 24rpx;
					color: #999;
				}
			}
		}
</style>