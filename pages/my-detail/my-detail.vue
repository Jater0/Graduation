<template>
	<view>
		<view class="my-header">
			<view class="my-header-logo-background">
				<image :src="systemUserInfo.avatar" mode="aspectFill"></image>
			</view>
			<view class="my-header-logo">
				<view class="my-header-logo-box">
					<image :src="systemUserInfo.avatar" mode="aspectFill"></image>
				</view>
				<text class="my-header-name">{{systemUserInfo.author_name}}</text>
			</view>
			<view class="my-header-info">
				<view class="my-header-info-box">
					<button class="my-header-info-button">
						<text>关注: </text>
						<text class="my-header-info-count">{{systemUserInfo.follow_count}}</text>
					</button>
				</view>
				<view class="my-header-info-box">
					<button class="my-header-info-button">
						<text>粉丝: </text>
						<text class="my-header-info-count">{{systemUserInfo.fans_count}}</text>
					</button>
				</view>
				<view class="my-header-info-box">
					<button class="my-header-info-button">
						<text>积分: </text>
						<text class="my-header-info-count">{{systemUserInfo.integral_count}}</text>
					</button>
				</view>
			</view>
		</view>
		<view class="my-content">
			<view class="my-content-list">
				<view class="my-content-list-title">
					<uni-icons class="icons" type="phone-filled" size="16" color="#666"></uni-icons>
					<text>手机: </text>
					<text v-if="!showPhoneSetting">{{systemUserInfo.author_phone}}</text>
					<input v-else type="text" value="" placeholder="输入你的手机号" v-model="phone" style="font-size: 28rpx;" />
					<button v-if="showPhoneSetting" type="default" style="font-size: 20rpx;">发送验证码</button>
				</view>
				<uni-icons type="gear" size="16" color="#666" @click="openSetting('phone')"></uni-icons>
			</view>
			<view class="my-content-list">
				<view class="my-content-list-title">
					<uni-icons class="icons" type="email-filled" size="16" color="#666"></uni-icons>
					<text>邮箱: </text>
					<text v-if="!showEmailSetting">{{systemUserInfo.author_email}}</text>
					<input v-else type="text" value="" placeholder="输入你的邮箱" v-model="email" style="font-size: 28rpx;" />
					<button v-if="showEmailSetting" type="default" style="font-size: 20rpx;">发送验证码</button>
				</view>
				<uni-icons type="gear" size="16" color="#666" @click="openSetting('email')"></uni-icons>
			</view>
			<view class="my-content-list">
				<view class="my-content-list-title">
					<uni-icons class="icons" type="person-filled" size="16" color="#666"></uni-icons>
					<text>性别: </text>
					<text v-if="gender === '男' || gender == '女'">{{gender}}</text>
					<button v-else type="default" style="font-size: 20rpx;" @click="chooseGender">选择性别</button>
				</view>
			</view>
			<view class="my-content-list">
				<view class="my-content-list-title">
					<uni-icons class="icons" type="location-filled" size="16" color="#666"></uni-icons>
					<text>专业: </text>
					<text v-if="!showProfessionalSetting">{{systemUserInfo.professional}}</text>
					<input v-else type="text" value="" maxlength="15" placeholder="输入你的专业, 15字以内" v-model="professional" />
					<button v-if="showProfessionalSetting" type="default" style="font-size: 20rpx;" @click="confirmUpdateProfessional">确认修改</button>
				</view>
				<uni-icons type="gear" size="16" color="#666" @click="openSetting('professional')"></uni-icons>
			</view>
			<view class="my-explain">
				<view class="my-explain-title">
					<uni-icons class="icons" type="chatboxes-filled" size="16" color="#666"></uni-icons>
					<text>签名</text>
				</view>
				<uni-icons type="gear" size="16" color="#666" @click="openSetting('explain')"></uni-icons>
			</view>
			<view class="my-explain-content">
				<view v-if="!showExplainSetting" class="my-explain-title">
					<view style="margin-left: 20rpx;">{{systemUserInfo.explain}}</view>
				</view>
				<view v-else class="explain-content">
					<textarea v-model="explain" class="explain-content-textarea" value="" placeholder="请输入你的新签名/动态" maxlength="50" />
					<button type="default" style="font-size: 20rpx;" @click="confirmUpdateExplain">确认修改</button>
					<view class="explain-content-count">{{explain.length}}/50</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {mapState} from 'vuex'
	export default {
		computed: {
			...mapState(['systemUserInfo', 'userid'])
		},
		data() {
			return {
				showPhoneSetting: false,
				showEmailSetting: false,
				showProfessionalSetting: false,
				showExplainSetting: false,
				phone: '',
				email: '',
				gender: '',
				professional: '',
				explain: ""
			};
		},
		onLoad() {
			this.gender = this.systemUserInfo.gender
		},
		methods: {
			openSetting(type) {
				switch (type) {
					case 'phone':
						this.showPhoneSetting = !this.showPhoneSetting
						this.phone = ""
						break
					case 'email':
						this.showEmailSetting = !this.showEmailSetting
						this.email = ""
						break
					case 'professional':
						this.showProfessionalSetting = !this.showProfessionalSetting
						this.professional = ""
						break
					case 'explain':
						this.showExplainSetting = !this.showExplainSetting
						this.explain = ""
						break
				}
			},
			chooseGender() {
				uni.showActionSheet({
					itemList: ["男", "女"],
					success: (res) => {
						switch (res.tapIndex) {
							case 0:
								this.confirmUpdateGender('男')
								break
							case 1:
								this.confirmUpdateGender('女')
								break
						}
					}
				})
			},
			confirmUpdateGender(gender) {
				uni.showModal({
					title: '确认性别',
					content: `你的性别是${gender}, 一旦确认就无法修改`,
					success: (res) => {
						if (res.confirm) {
							this.updateGender(gender)
						} else if (res.cancel) {
							uni.showToast({
								title: '取消',
								icon: 'none'
							})
						}
					}
				})
			},
			updateGender(gender) {
				uni.request({
					method: 'POST',
					url: this.$api.address + `forum/update_gender/${this.userid}/${gender}`,
					success: (res) => {
						const {data, code} = res.data
						if (code === 200) {
							this.gender = gender
							var oldUserInfo = this.systemUserInfo
							oldUserInfo.gender = gender
							this.$store.dispatch('set_system_userinfo', oldUserInfo)
						} else if (code === 500) {
							uni.showToast({
								title: '更新性别失败, 请稍后再试',
								icon: 'none'
							})
						}
					},
					fail: (err) => {
						uni.showToast({
							title: '更新性别失败, 请稍后再试',
							icon: 'none'
						})
					}
				})
			},
			confirmUpdateExplain() {
				uni.showModal({
					title: '确认修改签名',
					content: `将签名修改为${this.explain}`,
					success: (res) => {
						if (res.confirm) {
							this.updateExplain()
						}
					}
				})
			},
			updateExplain() {
				uni.showLoading()
				uni.request({
					method: 'POST',
					url: this.$api.address + `forum/update_explain/${this.userid}`,
					data: {
						explain: this.explain
					},
					success: (res) => {
						uni.hideLoading()
						const {code, data} = res.data
						if (code === 200) {
							uni.showToast({
								title: '更新签名成功',
								icon: 'success'
							})
							var oldUserInfo = this.systemUserInfo
							oldUserInfo.explain = this.explain
							this.$store.dispatch('set_system_userinfo', oldUserInfo)
							this.explain = ""
							this.showExplainSetting = false
						} else if (code === 500) {
							uni.showToast({
								title: '上传签名失败, 请稍后再试',
								icon: 'none'
							})
						}
					},
					fail: (err) => {
						uni.hideLoading()
						uni.showToast({
							title: '上传签名失败, 请稍后再试',
							icon: 'none'
						})
					}
				})
			},
			confirmUpdateProfessional() {
				uni.showModal({
					title: '确认修改专业',
					content: `更新的专业为${this.professional}`,
					success: (res) => {
						if (res.confirm) {
							this.updatProfessional()
						}
					}
				})
			},
			updatProfessional() {
				uni.showLoading()
				uni.request({
					method: 'POST',
					url: this.$api.address + `forum/update_professional/${this.userid}`,
					data: {
						professional: this.professional
					},
					success: (res) => {
						uni.hideLoading()
						const {data, code} = res.data
						if (code === 200) {
							uni.showToast({
								title: '更新专业成功'
							})
							var oldUserInfo = this.systemUserInfo
							oldUserInfo.professional = this.professional
							this.$store.dispatch('set_system_userinfo', oldUserInfo)
							this.professional = ""
							this.showProfessionalSetting = false
						} else if (code === 500) {
							uni.showToast({
								title: '更新专业失败, 请稍后再试',
								icon: 'none'
							})
						}
					},
					fail: (err) => {
						uni.hideLoading()
						uni.showToast({
							title: '更新专业失败, 请稍后再试',
							icon: 'none'
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #f5f5f5;
	}
	.my-header {
		position: relative;
		padding-bottom: 30rpx;
		.my-header-logo-background {
			position: absolute;
			top: 0;
			right: 0;
			bottom: 0;
			left: 0;
			filter: blur(16rpx);
			opacity: 0.3;
			image {
				width: 100%;
				height: 100%;
			}
		}
		.my-header-logo {
			display: flex;
			flex-direction: column;
			align-items: center;
			padding-top: 60rpx;
			.my-header-logo-box {
				width: 120rpx;
				height: 120rpx;
				border-radius: 50%;
				overflow: hidden;
				image {
					width: 100%;
					height: 100%;
				}
				button {
					width: 100%;
					height: 100%;
					font-size: 25rpx;
					justify-content: center;
				}
			}
			.my-header-name {
				margin-top: 30rpx;
				font-size: 32rpx;
				font-weight: bold;
			}
		}
		.my-header-info {
			margin-top: 30rpx;
			display: flex;
			.my-header-info-box {
				display: flex;
				flex-direction: column;
				justify-content: center;
				align-items: center;
				width: 100%;
				.my-header-info-button {
					background-color:transparent;
					border:none;
					font-size: 28rpx;
					color: #333;
				}
				button::after {
					border: none;
				}
				.my-header-info-count {
					font-size: 26rpx;
					color: #999;
				}
			}
		}
	}
	.my-content {
		padding-top: 20rpx;
		.my-explain-content {
			background-color: #ffffff;
			margin-bottom: 20rpx;
			padding-bottom: 10rpx;
			.my-explain-title {
				display: flex;
				align-items: center;
				.icons {
					margin-right: 10rpx;
				}
			}
			.explain-content {
				padding: 20rpx;
				box-sizing: border-box;
				border: 1px #eee solid;
				.explain-content-textarea {
					font-size: 24rpx;
					width: 100%;
					height: 200rpx;
				}
				.explain-content-count {
					display: flex;
					justify-content: flex-end;
					font-size: 24rpx;
					color: #999;
				}
			}
		}
		.my-explain {
			display: flex;
			justify-content: space-between;
			padding: 30rpx;
			background-color: #ffffff;
			color: #333;
			font-size: 28rpx;
			.my-explain-title {
				display: flex;
				align-items: center;
				.icons {
					margin-right: 10rpx;
				}
			}
		}
		.my-content-list {
			display: flex;
			justify-content: space-between;
			padding: 30rpx;
			margin-bottom: 20rpx;
			background-color: #ffffff;
			color: #333;
			font-size: 28rpx;
			.my-content-list-title {
				display: flex;
				align-items: center;
				.icons {
					margin-right: 10rpx;
				}
			}
		}
	}
</style>