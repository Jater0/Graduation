<template>
	<view>
		<view class="my-header">
			<view class="my-header-logo-background">
				<image :src="systemUserInfo.avatar" mode="aspectFill"></image>
			</view>
			<view class="my-header-logo">
				<view class="my-header-logo-box">
					<!-- 
						about @getuserinfo and open-type
						when the frist time of the user to use this program
						Wechat will show the toast of the grant for user
						But next time will not been show
					 -->
					<button v-if="!isLogin" type="default" open-type="getUserInfo" @getuserinfo="login" withCredentials="true">Login</button>
					<image v-else :src="systemUserInfo.avatar" mode="aspectFill"></image>
				</view>
				<text class="my-header-name">{{systemUserInfo.author_name}}</text>
			</view>
			<view class="my-header-info">
				<view class="my-header-info-box">
					<text class="my-header-info-title">关注: </text>
					<text>{{systemUserInfo.follow_count}}</text>
				</view>
				<view class="my-header-info-box">
					<text class="my-header-info-title">粉丝: </text>
					<text>{{systemUserInfo.fans_count}}</text>
				</view>
				<view class="my-header-info-box">
					<text class="my-header-info-title">积分: </text>
					<text>{{systemUserInfo.integral_count}}</text>
				</view>
			</view>
		</view>
		<view class="my-content" v-if="isLogin">
			<view class="my-content-list" @click="openMyTopic">
				<view class="my-content-list-title">
					<uni-icons class="icons" type="contact" size="16" color="#666"></uni-icons>
					<text>我的话题</text>
				</view>
				<uni-icons type="arrowright" size="14" color="#666"></uni-icons>
			</view>
			<view class="my-content-list" @click="openMyArticle">
				<view class="my-content-list-title">
					<uni-icons class="icons" type="contact" size="16" color="#666"></uni-icons>
					<text>我的文章</text>
				</view>
				<uni-icons type="arrowright" size="14" color="#666"></uni-icons>
			</view>
			<view class="my-content-list" @click="openArticleFavorites">
				<view class="my-content-list-title">
					<uni-icons class="icons" type="heart-filled" size="16" color="#666"></uni-icons>
					<text>收藏文章</text>
				</view>
				<uni-icons type="arrowright" size="14" color="#666"></uni-icons>
			</view>
			<view class="my-content-list" @click="openFeedback">
				<view class="my-content-list-title">
					<uni-icons class="icons" type="help" size="16" color="#666"></uni-icons>
					<text>反馈中心</text>
				</view>
				<uni-icons type="arrowright" size="14" color="#666"></uni-icons>
			</view>
		</view>
	</view>
</template>

<script>
	import {mapState} from 'vuex'
	import uniIcons from '@/components/uni-icons/uni-icons.vue'
	export default {
		components: {
			uniIcons
		},
		computed: {
			...mapState(['systemUserInfo', 'isLogin'])
		},
		data() {
			return {
				appId: 'wx8f244f3c21c96dd9',
				secret: '6db0a6c24b162f142ed96e355a4c1767',
			};
		},
		onLoad() {
			
		},
		methods: {
			// step 1: click the login button
			login() {
				let that = this
				uni.login({
					provider: 'weixin',
					success: (res) => {
						const {code} = res
						that.wxGetUserOpenId(code)
					}
				})
			},
			// step 2: get user's openid and session-key
			wxGetUserOpenId(code) {
				let that = this
				uni.showLoading()
				uni.request({
					url: 'https://api.weixin.qq.com/sns/jscode2session',
					data: {
						appid: that.appId,
						secret: that.secret,
						js_code: code,
						grant_type: 'authorization_code'
					},
					header: {
						'content-type': 'application/json'
					},
					success: (res) => {
						const {data} = res
						that.systemGetUserInfo(data.openid)
					}
				})
			},
			// step 3: get the user info by the user open-id from backend system
			systemGetUserInfo(openId) {
				var that = this
				uni.request({
					url: 'http://localhost:8000/forum/get_user',
					data: {
						id: openId
					},
					success: (res) => {
						const {data} = res
						if (data.code === 404) {
							that.wxGetUserInfoForCreate(openId)
						} else if (data.code === 200) {
							this.$store.dispatch('set_system_userinfo', data.data)
							this.$store.dispatch('set_is_login', true)
							this.$store.dispatch('set_userid', data.data._id)
						}
						uni.hideLoading()
					}
				})
			},
			// do this when the user 404 on the backend
			wxGetUserInfoForCreate(openId) {
				let that = this
				uni.getUserInfo({
					provider: 'weixin',
					success: (res) => {
						const {userInfo} = res
						var gender = userInfo.gender===1?'男':'女'
						that.createNewAccount(openId, userInfo.nickName, gender, userInfo.avatarUrl)
					}
				})
			},
			createNewAccount(id, name, gender, avatar) {
				var that = this
				uni.request({
					url: 'http://localhost:8000/forum/create_user',
					data: {
						id: id,
						name: name, 
						gender: gender,
						avatar: avatar
					},
					success: (res) => {
						if (res.data.code === 500) {
							uni.showToast({
								title: '对不起, 创建新用户失败, 如有问题请去反馈',
								icon: 'none'
							})
						} else {
							uni.showToast({
								title: '创建新用户成功',
							})
							var data = {
								_id: id,
								author_name: name,
								author_phone: null,
								author_email: null,
								gender: gender,
								avatar: avatar,
								explain: null,
								professional: null,
								integral_count: 0,
								follow_count: 0,
								fans_count: 0,
								userLabels: [],
								authorLikes: [],
								articleLikes: [],
								articleThumbs: [],
								articlePersonals: []
							}
							this.$store.dispatch('set_system_userinfo', data)
							this.$store.dispatch('set_is_login', true)
							this.$store.dispatch('set_userid', id)
						}
					},
					fail: (res) => {
						uni.showToast({
							title: '对不起, 创建新用户失败, 如有问题请去反馈',
							icon: 'none'
						})
					}
				})
			},
			openFeedback() {
				uni.navigateTo({
					url:'/pages/feedback/feedback'
				})
			},
			openArticleFavorites() {
				uni.navigateTo({
					url: '/pages/article-list/article-list?params=favorites'
				})
			},
			openMyArticle() {
				uni.navigateTo({
					url:'/pages/article-list/article-list?params=myarticle'
				})
			},
			openMyTopic() {
				uni.navigateTo({
					url: '/pages/article-list/article-list?params=mytopic'
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
				font-size: 24rpx;
				color: #999;
				.my-header-info-title {
					font-size: 28rpx;
					color: #333;
				}
			}
		}
	}
	.my-content {
		padding-top: 20rpx;
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