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
					<button class="my-header-info-button" @click="openFollow('following')">
						<text>关注: </text>
						<text class="my-header-info-count">{{systemUserInfo.follow_count}}</text>
					</button>
				</view>
				<view class="my-header-info-box">
					<button class="my-header-info-button" @click="openFollow('fans')">
						<text>粉丝: </text>
						<text class="my-header-info-count">{{systemUserInfo.fans_count}}</text>
					</button>
				</view>
				<view class="my-header-info-box">
					<button class="my-header-info-button" @click="openIntegral">
						<text>积分: </text>
						<text class="my-header-info-count">{{systemUserInfo.integral_count}}</text>
					</button>
				</view>
			</view>
		</view>
		<view class="my-content" v-if="isLogin">
			<view class="my-content-list" @click="openMyDetail">
				<view class="my-content-list-title">
					<uni-icons class="icons" type="gear" size="16" color="#666"></uni-icons>
					<text>个人信息设置</text>
				</view>
				<uni-icons type="arrowright" size="14" color="#666"></uni-icons>
			</view>
			<view class="my-content-list" @click="openArticleList('mytopic')">
				<view class="my-content-list-title">
					<uni-icons class="icons" type="contact" size="16" color="#666"></uni-icons>
					<text>我的话题</text>
				</view>
				<uni-icons type="arrowright" size="14" color="#666"></uni-icons>
			</view>
			<view class="my-content-list" @click="openArticleList('myarticle')">
				<view class="my-content-list-title">
					<uni-icons class="icons" type="contact" size="16" color="#666"></uni-icons>
					<text>我的文章</text>
				</view>
				<uni-icons type="arrowright" size="14" color="#666"></uni-icons>
			</view>
			<view class="my-content-list" @click="openArticleList('favorites')">
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
	import {showLoginModel} from '@/utils/index.js'
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
						uni.hideLoading()
						const {data} = res
						that.systemGetUserInfo(data.openid)
					},
					fail: (err) => {
						uni.hideLoading()
						uni.showToast({
							title: '微信小程序获取用户信息失败',
							icon: 'none'
						})
					}
				})
			},
			// step 3: get the user info by the user open-id from backend system
			systemGetUserInfo(openId) {
				var that = this
				uni.request({
					url: that.$api.address + `forum/get_user/${openId}`,
					success: (res) => {
						const {data, code} = res.data
						if (code === 404) {
							that.wxGetUserInfoForCreate(openId)
						} else if (code === 200) {
							uni.hideLoading()
							this.$store.dispatch('set_system_userinfo', data)
							this.$store.dispatch('set_is_login', true)
							this.$store.dispatch('set_userid', data._id)
						}
					},
					fail: (err) => {
						uni.hideLoading()
						uni.showToast({
							title: '登录失败, 请稍后再试',
							icon: 'none'
						})
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
					},
					fail: (err) => {
						uni.hideLoading()
						uni.showToast({
							title: '微信小程序 获取用户信息失败',
							icon: 'none'
						})
					}
				})
			},
			createNewAccount(id, name, gender, avatar) {
				var that = this
				uni.request({
					url: this.$api.address + 'forum/create_user',
					data: {
						id: id,
						name: name, 
						gender: gender,
						avatar: avatar
					},
					success: (res) => {
						uni.hideLoading()
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
						uni.hideLoading()
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
			openMyDetail() {
				uni.navigateTo({
					url: '/pages/my-detail/my-detail'
				})
			},
			openArticleList(type) {
				uni.navigateTo({
					url: '/pages/article-list/article-list?params=' + type
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
			},
			openFollow(type) {
				if (!this.isLogin) {
					showLoginModel()
					return
				}
				uni.navigateTo({
					url: '/pages/follow-list/follow-list?params=' + type
				})
			},
			openIntegral() {
				if (!this.isLogin) {
					showLoginModel()
					return
				}
				uni.showModal({
					title: '关于积分问题的说明',
					content: `你的积分为${this.systemUserInfo.integral_count}; 分数等级体现在文章编写的字符support, 为未来可能会添加其他的内容, 敬请期待`
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