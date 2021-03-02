<template>
	<view class="list-author" @click="click">
		<view class="list-author-avatar">
			<image :src="item.avatar" mode="aspectFill"></image>
		</view>
		<view class="list-author-content">
			<view class="list-author-content-title">{{item.author_name}}</view>
			<view class="list-author-content-des">
				<view class="list-author-content-des-label">{{item.professional}}</view>
				<view class="list-author-content-des-info">
					<text>博客: {{item.article_count}}</text>
					<text>粉丝: {{item.fans_count}}</text>
					<text>关注: {{item.follow_count}}</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		props: {
			item:{
				type: Object,
				default() {
					return {}
				}
			}
		},
		data() {
			return {
				
			};
		},
		methods: {
			click() {
				this.$emit("click", this.item)
			}
		}
	}
</script>

<style lang="scss">
	.list-author {
		display: flex;
		padding: 20rpx 0;
		margin: 0 20rpx;
		border-radius: 10rpx;
		box-sizing: border-box;
		border-bottom: 1px #f5f5f5 solid;
		.list-author-avatar {
			flex-shrink: 0;
			width: 80rpx;
			height: 80rpx;
			overflow: hidden;
			image {
				width: 100%;
				height: 100%;
			}
		}
		.list-author-content {
			display: flex;
			// 垂直方向的左右对齐
			flex-direction: column; // 垂直排列
			justify-content: space-between; // 左右对齐
			padding-left: 20rpx;
			width: 100%;
			.list-author-content-title {
				position: relative;
				padding-right: 60rpx;
				font-size: 26rpx;
				color: #333;
				font-weight: bold;
				line-height: 1.2; // 行距
			}
			.list-author-content-des {
				// 水平方向的左右对齐
				display: flex;
				justify-content: space-between; // 左右对齐
				font-size: 24rpx;
				.list-author-content-des-label {
					color: #666;
				}
				.list-author-content-des-info {
					color: #999;
					line-height: 1.5;
					text{
						&:first-child {
							margin-right: 10rpx;
						}
						&:last-child {
							margin-left: 10rpx;
						}
					}
				}
			}
		}
	}
</style>
