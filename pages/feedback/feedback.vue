<template>
	<view>
		<view class="feedback-title">反馈类型: </view>
		<view>
			<radio-group>
				<radio class="feedback-radio" value="1" :checked="feedbackType === 1" @click="radio(1)"><text>举报</text></radio>
				<radio class="feedback-radio" value="2" :checked="feedbackType === 2" @click="radio(2)"><text>Bug</text></radio>
				<radio class="feedback-radio" value="3" :checked="feedbackType === 3" @click="radio(3)"><text>Idea</text></radio>
			</radio-group>
		</view>
		<view class="feedback-title">意见反馈: </view>
		<view class="feedback-content">
			<textarea v-model="content" class="feedback-content-textarea" value="" placeholder="请输入您要反馈的问题" maxlength="50" />
			<view class="feedback-content-count">{{content.length}}/50</view>
		</view>
		<view class="feedback-title">反馈图片: </view>
		<view class="feedback-image-box">
			<view class="feedback-image-item" v-for="(item, index) in imageList" :key="index">
				<view class="close-icon" @click="removeImage(index)">
					<uni-icons type="closeempty" size="18" color="#fff"></uni-icons>
				</view>
				<view class="item-image-box">
					<image :src="item.url" mode="aspectFill"></image>
				</view>
			</view>
			<view class="feedback-image-item" @click="chooseImage" v-if="imageList.length !== 9">
				<view class="item-image-box">
					<uni-icons type="plusempty" size="50" color="#eee"></uni-icons>
				</view>
			</view>
		</view>
		<button type="primary" class="feedback-button" @click="submit">提交反馈</button>
	</view>
</template>

<script>
	import uploadImage from '@/js_sdk/yushijie-ossutil/ossutil/uploadFile.js'
	import {mapState} from 'vuex'
	export default {
		data() {
			return {
				content: '',
				imageList: [],
				updateImageList: [],
				feedbackType: 0
			};
		},
		computed: {
			...mapState(['userid'])
		},
		watch: {
			updateImageList(newVal) {
				if (newVal.length === this.imageList.length) {
					this.updateFeedback(this.content, newVal)
				} else {
					console.log("not yet");
					return
				}
			}
		},
		methods: {
			chooseImage() {
				const count = 9 - this.imageList.length
				let that = this
				uni.chooseImage({
					count: count,
					success(res) {
						const tempfilepaths = res.tempFilePaths
						tempfilepaths.forEach((item, index) => {
							if (index < count) {
								that.imageList.push({
									url: item
								})
							}
						})
					}
				})
			},
			removeImage(index) {
				this.imageList.splice(index, 1)
			},
			async submit() {
				if (this.feedbackType === 0) {
					uni.showToast({
						title: '请选择你的提交的类型',
						icon: 'none'
					})
					return
				}
				if (this.content === '' || this.content.length === 0) {
					uni.showToast({
						title: '请输入提交的内容',
						icon: 'none'
					})
					return
				}
				uni.showLoading()
				if (this.imageList.length !== 0) {
					for (let i = 0; i < this.imageList.length; i++) {
						const filePath = this.imageList[i].url
						await this.updateFiles(filePath)
					}
				} else {
					this.updateFeedback(this.content, [])
				}
			},
			async updateFiles(filePath) {
				await uploadImage(filePath, 'forum-feedback/',
					result => {
					this.updateImageList.push(result)
				})
			},
			updateFeedback(content, covers) {
				console.log("updateFeedback: " + covers);
				uni.request({
					url: 'http://localhost:8000/forum/update_feedback',
					data: {
						id: this.userid,
						content: content,
						type: this.feedbackType,
						covers: covers
					},
					success: (res) => {
						uni.hideLoading()
						const {data, code} = res.data
						if (code === 200) {
							uni.showToast({
								title: '反馈成功'
							})
							setTimeout(() => {
								uni.switchTab({
									url:"/pages/tabbar/my/my"
								})
							},2000)
						} else {
							uni.showToast({
								title: '反馈失败, 请稍后再试',
								icon: 'none'
							})
						}
					},
					fail: (res) => {
						uni.hideLoading()
						uni.showToast({
							title: '反馈失败, 请稍后再试',
							icon: 'none'
						})
					}
				})
			},
			radio(value) {
				this.feedbackType = value
			}
		}
	}
</script>

<style lang="scss">
	.feedback-title {
		margin: 30rpx;
		margin-bottom: 0;
		font-size: 28rpx;
		color: #666;
	}
	.feedback-radio {
		transform: scale(0.6);
		text {
			font-size: 35rpx;
		}
	}
	.feedback-content {
		margin: 30rpx;
		padding: 20rpx;
		box-sizing: border-box;
		border: 1px #eee solid;
		.feedback-content-textarea {
			font-size: 24rpx;
			width: 100%;
			height: 200rpx;
		}
		.feedback-content-count {
			display: flex;
			justify-content: flex-end;
			font-size: 24rpx;
			color: #999;
		}
	}
	.feedback-image-box {
		display: flex;
		flex-wrap: wrap;
		padding: 20rpx;
		.feedback-image-item {
			position: relative;
			width: 33.33%;
			height: 0;
			padding-top: 33.33%;
			box-sizing: border-box;
			.close-icon {
				display: flex;
				justify-content: center;
				align-items: center;
				position: absolute;
				right: 0;
				top: 0;
				width: 44rpx;
				height: 44rpx;
				border-radius: 50%;
				background-color: #ff5a5f;
				z-index: 2;
			}
			.item-image-box {
				display: flex;
				justify-content: center;
				align-items: center;
				position: absolute;
				top: 10rpx;
				right: 10rpx;
				bottom: 10rpx;
				left: 10rpx;
				border: 1px #eee solid;
				border-radius: 10rpx;
				overflow: hidden;
				image {
					width: 100%;
					height: 100%;
				}
			}
		}
	}
	.feedback-button {
		margin: 0 30rpx;
		background-color: $forum-base-color;
	}
</style>
