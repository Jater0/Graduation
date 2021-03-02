<template>
	<view class="label">
		<view class="label-box">
			<view class="label-header">
				<view class="label-title">我的标签</view>
				<view class="label-choose">
					<text class="label-cancel" @click="cancel" v-if="editTextShow">取消</text>
					<text class="label-edit" @click="editTabLabelManage">{{editTextShow?'完成':'编辑'}}</text>
				</view>
			</view>
			<uni-load-more v-if="loading" status="loading" iconType="snow"></uni-load-more>
			<view v-if="!loading" class="label-content">
				<view class="label-content-item" v-for="(item, index) in myTab" :key="item.id">
					{{item.label_name}}
					<uni-icons v-if="editTextShow" class="icons-close" type="clear" size="20" color="red" @click="delTabFromMine(index)"></uni-icons>
				</view>
				<view class="no-data" v-if="myTab.length === 0 && !loading">
					当前没有数据
				</view>
			</view>
		</view>
		<view class="label-box">
			<view class="label-header">
				<view class="label-title">标签推荐</view>
			</view>
			<uni-load-more v-if="loading" status="loading" iconType="snow"></uni-load-more>
			<view v-if="!loading" class="label-content">
				<view class="label-content-item" v-for="(item, index) in notMyTab" :key="item.id">
					{{item.label_name}}
					<uni-icons v-if="editTextShow" class="icons-close" type="plus" size="20" color="red" @click="addTagForMine(index)"></uni-icons>
				</view>
				<view class="no-data" v-if="notMyTab.length === 0 && !loading">
					当前没有数据
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {mapState} from 'vuex'
	export default {
		computed: {
			...mapState(['labelList', 'labelAll', 'userid'])
		},
		data() {
			return {
				editTextShow: false,
				myTab: [],
				notMyTab: [],
				updateTab: [],
				loading: true
			}
		},
		onLoad() {
			this.getLabel()
		},
		onShow() {
			this.getLabel()
		},
		methods: {
			editTabLabelManage() {
				if(this.editTextShow) {
					this.editTextShow = false
					console.log(this.updateTab);
					this.updateLabel(this.updateTab)
				} else {
					this.editTextShow = true
				}
			},
			updateLabel(updateTab) {
				uni.showLoading()
				uni.request({
					url: this.$api.address + 'forum/update_label',
					data: {
						id: this.userid,
						updates: updateTab
					},
					success: (res) => {
						this.updateTab = []
						uni.hideLoading()
						uni.showToast({
							title: '数据更新成功'
						})
						var newLabelList = []
						newLabelList[0] = this.labelList[0]
						newLabelList[1] = this.labelList[1]
						var newLabelAll = this.myTab.concat(this.notMyTab)
						for (var i = 0; i < newLabelAll.length; i++) {
							newLabelList[i+2] = newLabelAll[i]
						}
						this.$store.dispatch('set_label_all', newLabelAll)
						this.$store.dispatch('set_label_list', newLabelList)
						uni.$emit('newLabel', newLabelList)
					}
				})
			},
			delTabFromMine(index) {
				var data = this.myTab[index]
				data.current = false
				this.notMyTab.push(data)
				this.myTab.splice(index, 1)
				this.setUpdateTab(data)
			},
			addTagForMine(index) {
				var data = this.notMyTab[index]
				data.current = true
				this.myTab.push(data)
				this.notMyTab.splice(index, 1)
				this.setUpdateTab(data)
			},
			setUpdateTab(data) {
				var updateTabLength = this.updateTab.length
				var label_name = data.label_name
				if (updateTabLength === 0) {
					this.updateTab.push(label_name)
				} else {
					var contains = 0
					for (var i = 0; i < updateTabLength; i++) {
						if (this.updateTab[i] === label_name) {
							contains = 1
							this.updateTab.splice(i, 1)
							break
						}
					}
					if (contains === 0) {
						this.updateTab.push(label_name)
					}
				}
			},
			getLabel() {
				this.loading = true
				let data = this.labelAll
				this.myTab = data.filter(item => item.current)
				this.notMyTab = data.filter(item => !item.current)
				this.loading = false
			},
			cancel() {
				this.editTextShow = !this.editTextShow
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #f5f5f5;
	}
	.label {
		.label-box {
			background-color: #fff;
			margin-bottom: 20rpx;
			.label-header {
				display: flex;
				justify-content: space-between;
				padding: 20rpx 30rpx;
				font-size: 28rpx;
				color: #666;
				border-bottom: 1px #f5f5f5 solid;
				.label-cancel {
					color: $forum-base-color-old;
					font-weight: bold;
					padding-right: 10rpx;
				}
				.label-edit {
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
					position: relative;
					padding: 4rpx 10rpx;
					margin-top: 24rpx;
					margin-right: 20rpx;
					border-radius: 10rpx;
					border: 1px #666 solid;
					font-size: 28rpx;
					.icons-close {
						position: absolute;
						right: -16rpx;
						top: -16rpx;
						background-color: #fff;
						border-radius: 50%;
					}
				}
			}
		}
	}
	.no-data {
		text-align: center;
		width: 100%;
		padding: 100rpx 0;
		color: #999;
		font-size: 28rpx;
	}
</style>