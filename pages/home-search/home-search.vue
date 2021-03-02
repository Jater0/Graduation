<template>
	<view class="home">
		<navbar :isSearch="true" @search="search" @backspace="backspace" @scroll="getHotList" v-model="value"></navbar>
		<view class="home-list">
			<view v-if="isShowHistory" class="label-box">
				<view class="label-header">
					<text class="label-title bold">搜索推荐</text>
				</view>
				<view class="label-recommend">
					<view class="label-recommend-item" :class="{posright: (index+1)%2==0}" v-for="(item, index) in recommendList" :key="index" @click="openHotList(item)">
						<text>{{index+1}}{{space}}</text>{{item.title.length > 10?item.title.substring(0, 13)+'...':item.title}}
					</view>
				</view>
			</view>
			<view v-if="isShowHistory" class="label-box">
				<view class="label-header">
					<text class="label-title">搜索历史</text>
					<text class="label-clear" @click="clearHistory">清空</text>
				</view>
				<view v-if="historys.length > 0" class="label-content">
					<view class="label-content-item" v-for="(item, index) in historys" :key="index" @click="openHistory(item)">{{item.name}}</view>
				</view>
				<view v-else class="no-data">
					没有搜索历史
				</view>
			</view>
			<list-scroll v-if="!isShowHistory" class="list-scroll">
				<uni-load-more v-if="loading" status="loading" iconType="snow"></uni-load-more>
				<view v-if="searchList.length > 0">
					<list-card mode="base" v-for="item in searchList" :key="item._id" :item="item" types="follow"></list-card>
				</view>
				<view v-if="searchList.length === 0 && !loading" class="no-data">
					没有搜索到具体内容
				</view>
			</list-scroll>
		</view>
	</view>
</template>

<script>
	import Navbar from '@/components/navbar/navbar.vue'
	import {mapState} from 'vuex'
	export default {
		components: {
			Navbar
		},
		data() {
			return {
				isShowHistory: true,
				searchList: [],
				value: '',
				loading: false,
				recommendList: [],
				space: '  '
			};
		},
		computed: {
			...mapState(['historys'])
		},
		methods: {
			// 实现当用户键入backspace键时, 取消搜索界面
			backspace(value) {
				if (value) {
					console.log("Stop Search");
				}
			},
			// 接收navbar组件里的事件和值
			search(value) {
				this.$store.dispatch('set_history', {
					name: value
				})
			},
			// 清除搜索记录
			clearHistory() {
				this.$store.dispatch('clear_history')
				uni.showToast({
					title: '搜索记录已清除'
				})
			},
			// 实现点击搜索历史记录里的选项, 搜索框再次搜索
			openHistory(item) {
				this.value = item.name
			},
			openHotList(item) {
				this.value = item.title
				uni.request({
					url: this.$api.address + ''
				})
			},
			getHotList(data) {
				this.recommendList = data
			}
		}
	}
</script>

<style lang="scss">
	page {
		height: 100%;
		display: flex;
		background-color: #f5f5f5;
	}
	.home {
		display: flex;
		flex-direction: column;
		flex: 1;
		.label-box {
			background-color: #fff;
			margin-bottom: 20rpx;
			.label-header {
				display: flex;
				justify-content: space-between;
				font-size: 28rpx;
				color: #666;
				padding: 20rpx 30rpx;
				border-bottom: 1px #f5f5f5 solid;
				.label-title {
					color: $forum-base-color-old;
					&.bold {
						font-weight: bold;
					}
				}
				.label-clear {
					color: #30b33a;
					font-weight: bold;
				}
			}
			.label-content {
				display: flex;
				flex-wrap: wrap;
				padding: 30rpx;
				padding-top: 0;
				.label-content-item {
					padding: 4rpx 20rpx;
					margin-top: 24rpx;
					margin-right: 20rpx;
					border-radius: 10rpx;
					border: 1px #666 solid;
					font-size: 28rpx;
					color: #666;
				}
			}
			.label-recommend{
				display: flex;
				flex-wrap: wrap;
				padding-left: 10rpx;
				padding-right: 10rpx;
				padding-bottom: 30rpx;
				padding-top: 0;
				.label-recommend-item {
					padding-top: 20rpx;
					overflow: hidden;
					font-size: 28rpx;
					color: #666;
					width: 49%;
					text {
						color: #dbf338;
						font-size: 30rpx;
						font-weight: bold;
						height: 100%;
					}
				}
				.posright {
					border-left: 1px #eee solid;
				}
			}
		}
		.no-data {
			height: 200rpx;
			line-height: 200rpx;
			width: 100%;
			text-align: center;
			color: #666;
			font-size: 24rpx;
		}
	}
</style>
