<template>
	<view @click="openDetail">
		<view class="list-card" :class="(item.cover.length === 1)?'mode-image':'mode-cloumn'">
			<view class="list-card-image" v-if="item.cover.length === 1">
				<image :src="item.cover[0]" mode="aspectFill"></image>
			</view>
			<view class="list-card-content">
				<view class="list-card-content-title">
					<text>{{item.title}}</text>
					<likes :item="item" :types="types" v-if="item.mode === 'article'"></likes>
				</view>
				<view class="list-card-image" v-if="item.cover.length > 1">
					<view v-if="index < 3" v-for="(image, index) in item.cover" :key="index" class="list-card-image-item">
						<image :src="image" mode="aspectFill"></image>
					</view>
				</view>
				<view class="list-card-content-des">
					<view class="list-card-content-des-label">
						<view class="list-card-content-des-label-item">{{item.classify}}</view>
						<view class="list-card-content-des-label-item">{{item.mode==='article'?"文章":"话题"}}</view>
					</view>
					<view class="list-card-content-des-browse">{{item.browse_count}}浏览</view>
				</view>
			</view>
		</view>
		<!-- <view class="list-card" :class="(item.cover.length === 1)?'mode-image':'mode-cloumn'" v-if="item.mode === 'topic'">
			<view class="list-card-image" v-if="item.cover.length === 1">
				<image :src="item.cover[0]" mode="aspectFill"></image>
			</view>
			<view class="list-card-content">
				<view class="list-card-content-title">
					<text>{{item.title}}</text>
				</view>
				<view class="list-card-image" v-if="item.cover.length > 1">
					<view v-if="index < 3" v-for="(image, index) in item.cover" :key="index" class="list-card-image-item">
						<image :src="image" mode="aspectFill"></image>
					</view>
				</view>
				<view class="list-card-content-des">
					<view class="list-card-content-des-label">
						<view class="list-card-content-des-label-item">{{item.classify}}</view>
						<view class="list-card-content-des-label-item">话题</view>
					</view>
					<view class="list-card-content-des-browse">{{item.browse_count}}浏览</view>
				</view>
			</view>
		</view> -->
	</view>
</template>

<script>
	import Likes from '@/components/likes/likes.vue'
	export default {
		components: {
			Likes
		},
		props: {
			item: {
				type: Object,
				default() {
					return {}
				}
			},
			types: {
				type: String,
				default: ""
			}
		},
		data() {
			return {
				
			};
		},
		methods: {
			openDetail() {
				const item = this.item
				this.$store.dispatch('set_topic_detail_cache', item)
				switch (item.mode) {
					case 'article':
						uni.navigateTo({
							url: '/pages/article-detail/article-detail?params=' + item._id
						})
						break
					case 'topic':
						uni.navigateTo({
							url: '/pages/topics-detail/topics-detail?params=' + item._id
						})
						break
					default:
						uni.navigateTo({
							url: '/pages/article-detail/article-detail?params=' + item._id
						})
				}
			}
		}
	}
</script>

<style lang="scss">
	.list-card {
		display: flex;
		padding: 20rpx;
		margin: 20rpx;
		border-radius: 10rpx;
		box-shadow: 0 0 10rpx 2rpx rgba($color: #000000, $alpha: 0.1);
		box-sizing: border-box;
		.list-card-image {
			flex-shrink: 0;
			width: 120rpx;
			height: 120rpx;
			border-radius: 10rpx;
			overflow: hidden;
			image {
				width: 100%;
				height: 100%;
			}
		}
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
			d {
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
		&.mode-image {
			flex-direction: column;
			.list-card-image {
				width: 100%;
				height: 300rpx;
			}
			.list-card-content {
				padding-left: 0;
				margin-top: 20rpx;
				.list-card-content-des {
					display: flex;
					align-items: center;
					margin-top: 20rpx;
				}
			}
		}
	}
</style>