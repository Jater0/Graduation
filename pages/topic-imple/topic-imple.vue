<template>
	<view>
		<view class="topic-title">话题描述: </view>
		<view class="topic-content">
			<textarea class="topic-textarea" v-model="topicValue" maxlength="100" fixed placeholder="请输入话题内容..." />
			<view class="topic-count">{{topicValue.length}}/100</view>
		</view>
		<view class="topic-title">话题图片(max==3): </view>
		<view class="topic-image-box">
			<view class="topic-image-item" v-for="(item, index) in imageList" :key="index">
				<view class="close-icon" @click="removeImage(index)">
					<uni-icons type="closeempty" size="18" color="#fff"></uni-icons>
				</view>
				<view class="item-image-box">
					<image :src="item.url" mode="aspectFill"></image>
				</view>
			</view>
			<view class="topic-image-item" @click="chooseImage" v-if="imageList.length !== 3">
				<view class="item-image-box">
					<uni-icons type="plusempty" size="50" color="#eee"></uni-icons>
				</view>
			</view>
		</view>
		<button type="primary" class="topic-button" @click="submit">创建话题</button>
		<classify-selector ref="popup" @publish="publish" :list="typeArray"></classify-selector>
	</view>
</template>

<script>
	import {mapState} from 'vuex'
	import ClassifySelector from '@/components/classify-selector/classify-selector.vue'
	import uploadImage from '@/js_sdk/yushijie-ossutil/ossutil/uploadFile.js'
	export default {
		components: {
			ClassifySelector
		},
		data() {
			return {
				topicValue: '',
				typeArray: [],
				imageList: [],
				updateImageList: [],
				typeName: ''
			};
		},
		watch: {
			updateImageList(newVal) {
				if (newVal.length === this.imageList.length) {
					this.updateTopic(this.topicValue, this.typeName, newVal)
				}
			}
		},
		computed: {
			...mapState(['userid', 'labelAll'])
		},
		onLoad() {
			this.getLabelName()
		},
		methods: {
			getLabelName() {
				for (var i = 0; i < this.labelAll.length; i++) {
					this.typeArray.push(this.labelAll[i].label_name)
				}
			},
			chooseImage() {
				const count = 3 - this.imageList.length
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
			submit() {
				if (this.topicValue === '' || this.topicValue.length === 0) {
					uni.showToast({
						title: '请输入提交的内容',
						icon: 'none'
					})
					return
				}
				this.$refs.popup.open()
			},
			closeCommentView() {
				this.$refs.popup.close()
				this.typeName = ''
			},
			async publish(data) {
				if (data === 0) {
					uni.showToast({
						title: '请选择类型',
						icon: 'none'
					})
					return
				}
				uni.showLoading()
				this.typeName = data
				if (this.imageList.length != 0) {
					for (let i = 0; i < this.imageList.length; i++) {
						const filePath = this.imageList[i].url
						await this.updateFiles(filePath)
					}
				} else {
					this.updateTopic(this.topicValue, data, [])
				}
			},
			async updateFiles(filePath) {
				await uploadImage(filePath, 'forum-cover/',
					result => {
					this.updateImageList.push(result)
				})
			},
			updateTopic(title, classify, covers) {
				uni.request({
					url: 'http://localhost:8000/forum/update_topic',
					data: {
						uid: this.userid,
						title: title,
						classify: classify,
						covers: covers
					},
					success: (res) => {
						const {data, code} = res.data
						if (code === 200) {
							
							uni.hideLoading()
							this.closeCommentView()
							uni.showToast({
								title: '创建话题成功'
							})
							setTimeout(() => {
								uni.switchTab({
									url:"/pages/tabbar/index/index"
								})
							},2000)
						} else {
							uni.hideLoading()
							this.closeCommentView()
							uni.showToast({
								title: '创建话题成功失败, 请稍后再试',
								icon: 'none'
							})
						}
					},
					fail: (res) => {
						uni.hideLoading()
						this.closeCommentView()
						uni.showToast({
							title: '创建话题成功失败, 请稍后再试',
							icon: 'none'
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	.topic-title {
		margin: 30rpx;
		margin-bottom: 0;
		font-size: 28rpx;
		color: #666;
	}
	.topic-content {
		margin: 30rpx;
		padding: 20rpx;
		box-sizing: border-box;
		border: 1px #eee solid;
		.topic-textarea {
			font-size: 24rpx;
			width: 100%;
			height: 200rpx;
		}
		.topic-count {
			display: flex;
			justify-content: flex-end;
			font-size: 24rpx;
			color: #999;
		}
	}
	.topic-image-box {
		display: flex;
		flex-wrap: wrap;
		padding: 20rpx;
		.topic-image-item {
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
	.topic-button {
		margin: 0 30rpx;
		background-color: $forum-base-color;
	}
</style>
