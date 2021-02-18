<template>
	<view>
		<uni-popup ref="popup" type="bottom" :maskClick="false">
			<view class="popup-wrap">
				<view class="popup-header">
					<text class="popup-header-item" style="color: red;" @click="close">取消</text>
					<text class="popup-header-item" style="color: green;" @click="publish">发布</text>
				</view>
			</view>
			<view class="popup-content">
				<view>
					<view style="background-color: #fff;">
						<view class="popup-title">请选择类型: </view>
					</view>
					<radio-group style="background-color: #fff; flex-direction: column; ">
						<radio v-for="(item, index) in list" :key="index" class="popup-radio" :value="index + 1" :checked="topicTypeIndex === index + 1" @click="radio(index + 1)"><text>{{item}}</text></radio>
					</radio-group>
					<view style="height: 150rpx; background-color: #fff;"></view>
				</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	export default {
		props: {
			list: {
				type: Array,
				default() {
					return []
				}
			}
		},
		data() {
			return {
				topicTypeIndex: 0
			};
		},
		methods: {
			open(){
				this.topicTypeIndex = 0
				this.$refs.popup.open()
			},
			close(){
				this.topicTypeIndex = 0
				this.$refs.popup.close()
			},
			publish() {
				if (this.topicTypeIndex === 0) {
					this.$emit('publish', 0)
					return
				}
				this.$emit('publish', this.list[this.topicTypeIndex - 1])
			},
			radio(index) {
				this.topicTypeIndex = index
			}
		}
	}
</script>

<style lang="scss" scoped>
	.popup-wrap {
		background-color: #fff;
		.popup-header {
			display: flex;
			justify-content: space-between;
			font-size: 28rpx;
			color: #666;
			border-bottom: 1px #F5F5F5 solid;
			.popup-header-item {
				height: 100rpx;
				line-height: 100rpx;
				padding: 0 30rpx;
			}
		}
		.popup-content {
			width: 100%;
			background-color: #fff;
		}
	}
	.popup-title {
		margin-bottom: 0;
		padding-left: 15rpx;
		font-size: 28rpx;
		color: #666;
		background-color: #fff;
	}
	.popup-radio {
		transform: scale(0.7);
		text {
			font-size: 35rpx;
		}
	}
</style>
