<template>
	<view>
		<uni-popup ref="popup" type="bottom" :maskClick="false">
			<view class="popup-wrap">
				<view class="popup-header">
					<text class="popup-header-item" style="color: red;" @click="close">取消</text>
					<text class="popup-header-item" style="color: green;" @click="publish">发布</text>
				</view>
				<view class="popup-content">
					<textarea class="popup-textarea" v-model="commentValue" maxlength="200" fixed placeholder="请输入评论内容..." />
					<view class="popup-count">{{commentValue.length}}/200</view>
				</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	export default {
		props: {
			show: {
				type: Boolean,
				default: false
			}
		},
		data() {
			return {
				commentValue: ''
			};
		},
		methods: {
			open(){
				// 打开窗口之前清空内容 
				this.commentValue = ''
				this.$refs.popup.open()
			},
			close(){
				// 关闭窗口清空内容 
				this.commentValue = ''
				this.$refs.popup.close()
			},
			publish(){
				if(!this.commentValue){
					uni.showToast({
						title:'请输入评论内容',
						icon:"none"
					})
					return
				}
				this.$emit('publish',this.commentValue)
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
