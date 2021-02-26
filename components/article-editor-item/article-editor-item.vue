<template>
	<view class="toolbar">
		<view class="iconfont icon-bold" @click="toolBarClick('bold')"></view>
		<view class="iconfont icon-italic" @click="toolBarClick('italic')"></view>
		<view class="iconfont icon-xiahuaxian1" @click="toolBarClick('header')"></view>
		<view class="iconfont icon-underline" @click="toolBarClick('underline')"></view>
		<view class="iconfont icon-strike" @click="toolBarClick('strike')"></view>
		<view class="iconfont icon-sup" @click="toolBarClick('sup')"></view>
		<view class="iconfont icon-sub" @click="toolBarClick('sub')"></view>
		<view class="iconfont icon-alignleft" @click="toolBarClick('alignleft')"></view>
		<view class="iconfont icon-aligncenter" @click="toolBarClick('aligncenter')"></view>
		<view class="iconfont icon-alignright" @click="toolBarClick('alignright')"></view>
		<view class="iconfont icon-link" @click="toolBarClick('link')"></view>
		<view class="iconfont icon-image" @click="toolBarClick('imgage')"></view>
		<view class="iconfont icon-code" @click="toolBarClick('code')"></view>
		<view class="iconfont icon-table" @click="toolBarClick('table')"></view>
		<view class="iconfont icon-qingkong" @click="toolBarClick('clear')"></view>
		<view class="iconfont icon-explore" @click="toolBarClick('explore')"></view>
		<view class="iconfont icon-blockquote" @click="toolBarClick('blockquote')"></view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			};
		},
		methods: {
			toolBarClick(type) {
				this.$emit('type', type)
			}
		}
	}
</script>

<style lang="scss">
	@import '@/static/markdown.css';
	@import url("@/components/mpvue-wxparse/src/wxParse.css");
	.toolbar {
		width: 100%;
		border: none;
		box-shadow: 0 0rpx 4rpx rgba(0, 0, 0, 0.157), 0 0rpx 4rpx rgba(0, 0, 0, 0.227);
		.iconfont {
			display: inline-block;
			cursor: pointer;
			height: 61.6rpx;
			width: 61.6rpx;
			margin: 13rpx 0 11rpx 0rpx;
			font-size: 33rpx;
			padding: 10rpx 13rpx 11rpx 8rpx;
			color: #757575;
			border-radius: 11rpx;
			text-align: center;
			background: none;
			border: none;
			outline: none;
			line-height: 2.2;
			vertical-align: middle;
		}
	}
</style>
