<template>
<!-- 	<view class="grid">
		<view class="grid-item" v-for="(item, index) in list" :key="item.id">
			<view class="grid-item-image">
				<image :src="item.img" mode="aspectFill"></image>
			</view>
			<view class="grid-item-title">{{item.title}}</view>
		</view>
		<view class="feedback-image-box">
			<view class="feedback-image-item" @click="chooseImage" v-if="imageList.length !== 9">
				<view class="item-image-box">
					<uni-icons type="plusempty" size="50" color="#eee"></uni-icons>
				</view>
			</view>
		</view>
	</view> -->
	<button type="default" @click="openWriteTopicPage">Write Topic</button>
</template>

<script>
	export default {
		data() {
			return {
				list: [
					{id: 1, img: '../../../static/logo.png', title: 'Test01', url: ''},
					{id: 2, img: '../../../static/logo.png', title: 'Test02', url: ''},
					{id: 3, img: '../../../static/logo.png', title: 'Test03', url: ''},
					{id: 4, img: '../../../static/logo.png', title: 'Test04', url: ''},
					{id: 5, img: '../../../static/logo.png', title: 'Test05', url: ''},
					{id: 6, img: '../../../static/logo.png', title: 'Test06', url: ''},
					{id: 7, img: '../../../static/logo.png', title: 'Test07', url: ''},
					{id: 8, img: '../../../static/logo.png', title: 'Test08', url: ''},
					{id: 9, img: '../../../static/logo.png', title: 'Test09', url: ''}
				]
			};
		},
		methods: {
			openWriteTopicPage() {
				uni.navigateTo({
					url: '/pages/topic-imple/topic-imple'
				})
			}
		}
	}
</script>

<style lang="scss">
	.grid {
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
		align-content: space-between;
		background-color: #f7f7f7;
		padding: 10rpx 0;
		.grid-item {
			width: 33.33%;
			height: 213rpx;
			text-align: center;
			border: 1px solid #ccc;
			box-sizing: border-box;
			.grid-item-image {
				width: 96rpx;
				height: 96rpx;
				margin-top: 42rpx;
				margin-left: 50rpx;
				image {
					width: 100%;
					height: 100%;
					overflow: hidden;
				}
			}
			.grid-item-title {
				width: 100%;
				height: 34rpx;
				line-height: 34rpx;
				color: #06121e;
				font-size: 24rpx;
				margin-top: 20rpx;
			}
		}
	}
</style>
