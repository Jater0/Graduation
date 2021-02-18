<template>
	<view class="comments-box">
		<view class="comments-header">
			<view class="comments-header-avatar">
				<image :src="comments.avatar" mode="aspectFill"></image>
			</view>
			<view class="comments-header-info">
				<view class="info-title" v-if="!comments.is_reply">{{comments.author_name}}</view>
				<view class="info-title" v-else>{{comments.author_name}} <text class="reply-text" v-if="!(comments.tos === null || comments.tos === '')">回复</text> <text v-if="!(comments.tos === null || comments.tos === '')">{{comments.tos}}</text></view>
				<view class="info-create-time">{{comments.create_time | formatTime}}</view>
			</view>
		</view>
		<view class="comments-content">
			<view>{{comments.comment_content}}</view>
			<view class="comments-info">
				<view class="comments-info-button" @click="commentsReply({comments, is_reply: reply})">回复</view>
			</view>
			<view class="comments-reply" v-for="item in comments.reply" :key="item.comment_id" v-if="!comments.is_reply">
				<comments-box :comments="item" :reply="true" @reply="commentsReply"></comments-box>
			</view>
		</view>
	</view>
</template>

<script>
	import CommentBox from '@/components/comments-box/comments-box.vue'
	import {convertDate, showLoginModel} from '@/utils/index.js'
	import {mapState} from 'vuex'
	export default {
		name: 'comments-box',
		components: {
			CommentBox
		},
		computed: {
			...mapState(['userid'])
		},
		props: {
			comments: {
				type: Object,
				default() {
					return {}
				}
			},
			reply: {
				type: Boolean,
				default: false
			}
		},
		data() {
			return {
				
			};
		},
		filters: {
			formatTime(time) {
				return convertDate(time)
			}
		},
		methods: {
			commentsReply(comment) {
				if (this.userid === null) {
					showLoginModel()
				} else {
					this.$emit('reply', comment)
				}
			}
		}
	}
</script>

<style lang="scss">
.comments-box {
	margin: 30rpx 0;
	.comments-header {
		display: flex;
		.comments-header-avatar {
			flex-shrink: 0;
			width: 60rpx;
			height: 60rpx;
			border-radius: 10rpx;
			overflow: hidden;
			image {
				width: 100%;
				height: 100%;
			}
		}
		.comments-header-info {
			display: flex;
			flex-direction: column;
			padding-left: 30rpx;
			font-size: 24rpx;
			color: #999;
			line-height: 1;
			.info-title {
				margin-bottom: 20rpx;
				font-size: 28rpx;
				color: #333;
				.reply-text {
					margin: 0 20rpx;
					font-weight: bold;
					color: #000;
				}
			}
		}
	}
	.comments-content {
		margin-top: 20rpx;
		font-size: 28rpx;
		color: #000;
		.comments-info {
			margin-top: 30rpx;
			display: flex;
			.comments-info-button {
				padding: 4rpx 20rpx;
				font-size: 24rpx;
				color: #999;
				border-radius: 40rpx;
				border: 1px #ccc solid;
			}
		}
		.comments-reply {
			display: flex;
			margin: 30rpx 0;
			padding: 0 20rpx;
			border: 1px #eee solid;
		}
	}
}
</style>