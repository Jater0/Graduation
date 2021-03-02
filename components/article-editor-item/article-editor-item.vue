<template>
	<view class="toolbar">
		<view class="iconfont icon-waimai" @click="toolBarClick('header')"></view>
		<view class="iconfont icon-bold" @click="toolBarClick('bold')"></view>
		<view class="iconfont icon-italic" @click="toolBarClick('italic')"></view>
		<view class="iconfont icon-strikethrough" @click="toolBarClick('strikethrough')"></view>
		<view class="iconfont icon-quotation_marks" @click="toolBarClick('blockquote')"></view>
		<view class="iconfont icon-richtextbulletedlist" @click="toolBarClick('bulletedlist')"></view>
		<view class="iconfont icon-richtextnumberedlist" @click="toolBarClick('numberedlist')"></view>
		<view class="iconfont icon-764bianjiqi_charubiaoge" @click="toolBarClick('table')"></view>
		<view class="iconfont icon-758bianjiqi_fengexian" @click="toolBarClick('hr')"></view>
		<view class="iconfont icon-link" @click="toolBarClick('link')"></view>
		<view class="iconfont icon-image" @click="toolBarClick('image')"></view>
		<view class="iconfont icon-insert_tag_field" @click="toolBarClick('code')"></view>
		<view class="iconfont icon-ziyuan285" @click="toolBarClick('clear')"></view>
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
	@import '@/static/md-icons.css';
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
