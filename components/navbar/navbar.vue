<template>
	<view class="navbar">
		<view class="navbar-fixed">
			<view :style="{height: statusBarHeight + 'px'}"></view>
			<view class="navbar-content" :class="{search: isSearch}" :style="{height: navbarHeight + 5 + 'px', width: windowWidth + 'px'}" @click.stop="openHomeSearch">
				<view v-if="isSearch" class="navbar-content-search-back" @click="backToIndex">
					<uni-icons type="back" size="22" color="#fff"></uni-icons>
				</view>
				<view v-if="!isSearch" class="navbar-search-input">
					<view class="search-input-icon">
						<uni-icons type="search" size="16" color="#999"></uni-icons>
					</view>
					<view class="search-input-text">{{searchInputText}}</view>
				</view>
				<view v-else class="navbar-search-input">
					<input class="search-input-text" type="text" v-model="searchVal" placeholder="请输入搜索内容..." @confirm="submitSearch"></input>
				</view>
			</view>
		</view>
		<view :style="{height: navbarHeight + statusBarHeight + 'px'}"></view>
	</view>
</template>

<script>
	export default {
		props: {
			value: {
				type: [String, Number],
				default: ''
			},
			isSearch: {
				type: Boolean,
				default: false
			}
		},
		watch: {
			// 实现点击搜索历史记录里的选项, 搜索框再次搜索
			value(newVal) {
				this.searchVal = newVal
			},
			searchVal(newVal, oldVal) {
				let value = false
				if (oldVal.length > newVal.length) {
					value = true
				}
				this.$emit('backspace', value)
			}
		},
		data() {
			return {
				statusBarHeight: 20,
				navbarHeight: 45,
				windowWidth: 375,
				searchVal: '',
				searchInputText: '',
				hotList: [],
				hotListIndex: 2
			};
		},
		created() {
			this.setNavbarSize()
			this.getSearchScrollText()
		},
		methods: {
			setNavbarSize() {
				const info = uni.getSystemInfoSync()
				const statusBarHeight = info.statusBarHeight
				const windowWidth = info.windowWidth
				this.statusBarHeight = statusBarHeight
				this.windowWidth = windowWidth
				// #ifndef H5 || APP-PLUS || MP-ALIPAY
				const menuButtonInfo = uni.getMenuButtonBoundingClientRect()
				this.navbarHeight = (menuButtonInfo.bottom - statusBarHeight) + (menuButtonInfo.top- statusBarHeight)
				this.windowWidth = menuButtonInfo.left
				// #endif
			},
			searchTextScroll() {
				setInterval(() => {
					setTimeout(() => {
						if (this.hotListIndex == this.hotList.length - 2) {
							this.hotListIndex = 0
						}
					  this.searchInputText = this.hotList[this.hotListIndex] + " | " + this.hotList[this.hotListIndex + 1]
						this.hotListIndex += 2
					}, 0)
				}, 5000)
			},
			getSearchScrollText() {
				this.hotList = ["Java", "Python", "Kotlin", "Javascript", "Vue", "React", "Redis", "iOS"]
				this.searchInputText = this.hotList[0] + ' | ' + this.hotList[1]
				this.$emit('scroll', this.hotList)
				this.searchTextScroll()
			},
			backToIndex() {
				uni.switchTab({
					url: '/pages/tabbar/index/index'
				})
			},
			openHomeSearch() {
				if (this.isSearch) return
				uni.navigateTo({
					url: '/pages/home-search/home-search'
				})
			},
			submitSearch() {
				this.$emit('search', this.searchVal)
			}
		}
	}
</script>

<style lang="scss">
	.navbar {
		.navbar-fixed {
			position: fixed;
			top: 0;
			left: 0;
			z-index: 99;
			width: 100%;
			background-color: $forum-base-color;
			.navbar-content {
				display: flex;
				justify-content: center;
				align-items: center;
				padding: 0 30rpx;
				// height: 45px;
				box-sizing: border-box;
				.navbar-search-input {
					border-radius: 60rpx;
					display: flex;
					align-items: center;
					padding: 0 20rpx;
					width: 100%;
					height: 60rpx;
					background-color: #fff;
					.search-input-icon {
						margin-right: 20rpx;
					}
					.search-input-text {
						width: 100%;
						font-size: 28rpx;
						color: #999;
					}
				}
				&.search {
					padding-left: 0;
					.navbar-content-search-back {
						margin-left: 20rpx;
						margin-right: 20rpx;
					}
					.navbar-search-input {
						border-radius: 10rpx;
						width: 100%;
					}
				}
			}
		}
	}
</style>