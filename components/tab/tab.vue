<template>
	<view class="tab">
		<scroll-view class="tab-scroll" scroll-x="">
			<view class="tab-scroll-box">
				<view class="tab-scroll-box-item" v-for="(item, index) in list" :key="index" @click="clickTab(item, index)" :class="{active: activeIndex === index}">
					{{item.label_name}}
				</view>
			</view>
		</scroll-view>
		<view class="tab-icons" @click="openTabManger">
			<uni-icons type="gear" size="26" color="#666"></uni-icons>
		</view>
	</view>
</template>

<script>
	import {mapState} from 'vuex'
	export default {
		computed: {
			...mapState(['isLogin'])
		},
		props: {
			list: {
				type: Array,
				default() {
					return []
				}
			},
			tabIndex: {
				type: Number,
				default: 1
			}
		},
		data() {
			return {
				activeIndex: 1
			};
		},
		methods: {
			clickTab(item, index) {
				this.activeIndex = index
				this.$emit('tab', {
					data: item,
					index: index
				})
			},
			openTabManger() {
				if (this.isLogin) {
					uni.navigateTo({
						url:'/pages/home-label/home-label'
					})
				} else {
					uni.showModal({
						title: '登录提示',
						content: '您现在还没登录, 请先登录再进行操作',
						success: (res) => {
							if(res.confirm) {
								uni.switchTab({
									url: '/pages/tabbar/my/my'
								})
							} else if(res.cancel) {
								console.log("Cancel");
							}
						}
					})
				}
			}
		},
		// watch可以监听 data / props 值的变化
		watch: {
			tabIndex(newVal, oldVal) {
				this.activeIndex = newVal
			}
		}
	}
</script>

<style lang="scss">
	.tab {
		display: flex;
		width: 100%;
		border-bottom: 2rpx #f5f5f5 solid;
		background-color: #fff;
		box-sizing: border-box;
		.tab-scroll {
			flex: 1;
			overflow: hidden;
			box-sizing: border-box;
			.tab-scroll-box {
				display: flex;
				align-items: center;
				flex-wrap: nowrap;
				height: 90rpx;
				box-sizing: border-box;
				.tab-scroll-box-item {
					flex-shrink: 0;
					padding: 0 20rpx;
					color: #333;
					font-size: 28rpx;
					&.active {
						color: $forum-base-color;
					}
				}
			}
		}
		.tab-icons {
			display: flex;
			justify-content: center;
			align-items: center;
			width: 90rpx;
			&::after {
				content: '';
				position: absolute;
				top: 24rpx;
				bottom: 24rpx;
				left: 0;
				width: 2rpx;
				background-color: #ddd;
			}
		}
	}
</style>