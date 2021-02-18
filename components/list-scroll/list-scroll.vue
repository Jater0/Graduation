<template>
	<view class="scroll">
		<!-- @scrolltolower 监听 上拉事件 -->
		<scroll-view class="list-scroll" scroll-y @scrolltolower="loadmore">
			<view>
				<slot></slot>
			</view>
		</scroll-view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			};
		},
		methods: {
			loadmore() {
				this.$emit('loadmore')
			}
		}
	}
</script>

<style lang="scss">
	.scroll {
		flex: 1;
		// 因为.scroll会撑开所有内容, 而外层父组件高度固定, 所以scroll高度并没有比所有内容高度小
		// 即不会触发滚动(scroll 会觉得自己已经全部展示了)
		height: 100%; // 解决无法滚动list-scroll组件
		overflow: hidden;
		box-sizing: border-box;
		.list-scroll {
			height: 100%;
			display: flex;
			flex-direction: column;
		}
	}
</style>
