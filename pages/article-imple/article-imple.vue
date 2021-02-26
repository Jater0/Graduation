<template>
	<view>
		<view v-if="isAnswerTopic && !showConvert" style="font-size: 50rpx; color: #F07373;">回复话题: {{topic_title}}</view>
		<input v-if="!showConvert" type="text" v-model="title" placeholder="输入文章标题..." style="font-size: 35rpx; margin-top: 20rpx; margin-bottom: 10rpx;"/>
		<view style="float: right; width: 50%;">
			<button type="default" @click="convert" style="line-height: 60rpx; margin: 15rpx; background-color: #30b33a; color: #FFFFFF;">{{showConvert?'回到编辑器':'查看效果'}}</button>
		</view>
		<view style="float: left; width: 50%;">
			<button type="default" @click="submit" style="line-height: 60rpx; margin: 15rpx; background-color: #F07373; color: #FFFFFF;">发布</button>
		</view>
		<view v-if="!showConvert">
			<article-editor-item @type="toolBarClick"></article-editor-item>
			<view class="input-content">
				<textarea v-model="textareaData" :maxlength="maxlength" :placeholder="maxlength" auto-height />
			</view>
		</view>
		<view v-if="showConvert">
			<view class="input-content">
				<u-parse :content="textareaHtml"></u-parse>
			</view>
		</view>
		<classify-selector ref="popup" @publish="publishArticle" :list="typeArray"></classify-selector>
	</view>
</template>

<script>
	import marked from '@/components/marked'
	import EditorItem from '@/components/article-editor-item/article-editor-item.vue'
	import UParse from '@/components/gaoyia-parse/parse.vue'
	import wxParse from '@/components/mpvue-wxparse/src/wxParse.vue'
	import uploadImage from '@/js_sdk/yushijie-ossutil/ossutil/uploadFile.js'
	import ClassifySelector from '@/components/classify-selector/classify-selector.vue'
	import {mapState} from 'vuex'
	export default {
		computed: {
			...mapState(['systemUserInfo', 'userid', 'labelAll', 'answerTopicCache'])
		},
		onLoad(data) {
			this.getLabelName()
			this.countMaxLength()
			const {params} = data
			if (params === "main") {
				this.isAnswerTopic = false
				uni.setNavigationBarTitle({
					title: '写文章'
				})
			} else if (params === 'answer'){
				this.isAnswerTopic = true
				this.topic_title = this.answerTopicCache.title
				this.typeName = this.answerTopicCache.type
				this.topic_id = this.answerTopicCache.id
				uni.setNavigationBarTitle({
					title: '回复话题'
				})
			}
		},
		components: {
			EditorItem,
			UParse,
			wxParse,
			ClassifySelector
		},
		data() {
			return {
				textareaData: "",
				textareaHtml:"",
				showConvert: false,
				isAnswerTopic: false,
				topic_id: "",
				topic_title: "",
				maxlength: 500,
				title: "",
				typeArray: [],
				typeName: ''
			}
		},
		watch: {
			textareaData(newVal) {
				this.textareaHtml = marked(newVal);
			}
		},
		methods: {
			getLabelName() {
				for (var i = 0; i < this.labelAll.length; i++) {
					this.typeArray.push(this.labelAll[i].label_name)
				}
			},
			convert() {
				this.showConvert = !this.showConvert
			},
			submit() {
				if (this.textareaData === '' || this.textareaData.length === 0) {
					uni.showToast({
						title: '请输入提交的内容',
						icon: 'none'
					})
					return
				}
				if (this.title === '' || this.title.length === 0) {
					uni.showToast({
						title: '请输入标题',
						icon: 'none'
					})
					return
				}
				this.showConvert = true
				if (this.isAnswerTopic) {
					this.publishTopicAnswer()
				} else {
					console.log("PUBLISH");
					let query = uni.createSelectorQuery()
					let queryNode = query.selectAll('.wxParse')
					queryNode.fields({
						id: true,
						dataset: true,
						computedStyle: ['height']
					}, (res) => {
						console.log(res);
					}).exec()
					this.$refs.popup.open()
				}
			},
			async publishTopicAnswer() {
				uni.showLoading()
				var content = ""
				const fs = wx.getFileSystemManager()
				fs.writeFileSync(`${wx.env.USER_DATA_PATH}/forum.text`, this.textareaData, 'utf-8')
				await uploadImage(`${wx.env.USER_DATA_PATH}/forum.text`, "forum-article/", result => {
					content = result
				}, err => {
					uni.hideLoading()
				})
			},
			async publishArticle(data) {
				if (data === 0) {
					uni.showToast({
						title: '请选择类型',
						icon: 'none'
					})
					return
				}
				uni.showLoading()
				var content = ""
				const fs = wx.getFileSystemManager()
				fs.writeFileSync(`${wx.env.USER_DATA_PATH}/forum.text`, this.textareaData, 'utf-8')
				await uploadImage(`${wx.env.USER_DATA_PATH}/forum.text`, "forum-article/", result => {
					content = result
				}, err => {
					uni.hideLoading()
				})
				this.writeArticle(this.title, data, content, covers)
			},
			countMaxLength() {
				var star = this.systemUserInfo["integral_count"]
				if (star <= 100) {
					this.maxlength = 1500
				} else if (100 < star <= 300) {
					this.maxlength = 2500
				} else if (300 < star <= 500) {
					this.maxlength = 4000
				} else {
					this.maxlength = 5000
				}
			},
			closeCommentView() {
				this.$refs.popup.close()
			},
			answerTopic(title, data, content) {
				uni.request({
					url: ''
				})
			},
			writeArticle(title, classify, content, covers) {
				uni.request({
					url: 'http://localhost:8000/forum/create_article',
					method: 'POST',
					data: {
						id: this.userid,
						title: title,
						classify: classify,
						content: content,
						covers: covers
					}
				})
			},
			toolBarClick(type) {
				switch (type) {
					case 'bold':
						this.textareaData += "**粗体文字**"
						break
					case 'italic':
						this.textareaData += "*斜体* "
						break
					case 'underline':
					
						break
					case 'imgage':
					
						break
					case 'code':
						this.textareaData += "\n``` 代码块 \n\n```\n"
						break
					case 'table':
						this.textareaData += "\n|列1|列2|列3|\n|-|-|-|\n|单元格1|单元格2|单元格3|\n"
						break
					case 'clear':
					
						break
				}
			}
		}
	}
</script>

<style>
	@import '@/static/markdown.css';
	@import url("@/components/mpvue-wxparse/src/wxParse.css");

	.input-content {
		width: 100%;
	}

	.input-content textarea {
		padding: 16rpx 25rpx 15rpx 25rpx;
		font-size: 30rpx;
		min-height: 500rpx;
		line-height: 1.5;
	}

	.preview {
		border-top: 1rpx solid #e0e0e0;
		width: 100%;
	}
</style>
