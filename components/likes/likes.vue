<template>
	<view class="icons" @click.stop="likeTap">
		<uni-icons :type="like?'heart-filled':'heart'" size="20" color="#f07373"></uni-icons>
	</view>
</template>

<script>
	import {mapState} from 'vuex'
	export default {
		computed: {
			...mapState(['userid'])
		},
		props: {
			item: {
				type: Object,
				default() {
					return {}
				}
			},
			types: {
				type: String,
				default: ''
			}
		},
		data() {
			return {
				like: false
			};
		},
		watch: {
			item(newVal) {
				if (this.userid != null) {
					this.like = this.item.is_like
				} else {
					this.like = false
				}
			}
		},
		created() {
			if (this.userid != null) {
				this.like = this.item.is_like
			} else {
				this.like = false
			}
		},
		methods: {
			likeTap() {
				if (this.userid != null) {
					this.like = !this.like
					this.updateLikes()
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
			},
			updateLikes() {
				uni.showLoading()
				uni.request({
					url: this.$api.address + `forum/update_like/${this.userid}/${this.item._id}`,
					success: (res) => {
						uni.hideLoading()
						if (res.data.code === 200) {
							uni.showToast({
								title: this.like?'收藏成功':'取消收藏',
								icon: this.like?'success':'none'
							})
							uni.$emit('update_article_like', this.types)
						} else {
							this.like = !this.like
							uni.showToast({
								title: '收藏失败, 请稍后再试',
								icon:'none'
							})
						}
					},
					fail: () => {
						uni.hideLoading()
						this.like = !this.like
						uni.showToast({
							title: '收藏失败, 请稍后再试',
							icon:'none'
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	.icons {
		position: absolute;
		right: 0;
		top: 0;
		display: flex;
		justify-content: center;
		align-items: center;
		width: 40rpx;
		height: 40rpx;
	}
</style>
