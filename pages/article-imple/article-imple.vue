<template>
	<view style="height: 100%;">
		<view v-if="isAnswerTopic && !showConvert" style="font-size: 50rpx; color: #F07373;">回复话题: {{topic_title}}</view>
		<input v-if="!showConvert" type="text" v-model="title" placeholder="输入文章标题..." style="font-size: 35rpx; margin: 20rpx"/>
		<view style="float: right; width: 50%;">
			<button type="default" @click="convert" style="line-height: 60rpx; margin: 15rpx; background-color: #30b33a; color: #FFFFFF;">{{showConvert?'回到编辑器':'查看效果'}}</button>
		</view>
		<view style="float: left; width: 50%;">
			<button type="default" @click="submit" style="line-height: 60rpx; margin: 15rpx; background-color: #F07373; color: #FFFFFF;">发布</button>
		</view>
		<view v-if="!showConvert">
			<article-editor-item @type="toolBarClick"></article-editor-item>
			<view class="input-content" style="height: 100%;">
				<textarea class="textarea" v-model="textareaData" :maxlength="maxlength" :placeholder="maxlength"/>
				<view class="count">{{textareaData.length}}/{{maxlength}}</view>
			</view>
		</view>
		<view v-if="showConvert">
			<view class="input-content" style="width: 100%;">
				<towxml :nodes="nodes"></towxml>
			</view>
		</view>
		<classify-selector ref="popup" @publish="publishArticle" :list="typeArray"></classify-selector>
	</view>
</template>

<script>
	import EditorItem from '@/components/article-editor-item/article-editor-item.vue'
	import uploadImage from '@/js_sdk/yushijie-ossutil/ossutil/uploadFile.js'
	import ClassifySelector from '@/components/classify-selector/classify-selector.vue'
	import {mapState} from 'vuex'
	import towxml from '../../static/towxml/towxml'
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
			ClassifySelector,
			towxml
		},
		data() {
			return {
				textareaData: "",
				showConvert: false,
				isAnswerTopic: false,
				topic_id: "",
				topic_title: "",
				maxlength: 500,
				title: "",
				typeArray: [],
				typeName: '',
				updateImageList: [],
				nodes: {}
			}
		},
		watch: {
			
		},
		methods: {
			getLabelName() {
				for (var i = 0; i < this.labelAll.length; i++) {
					this.typeArray.push(this.labelAll[i].label_name)
				}
			},
			convert() {
				this.showConvert = !this.showConvert
				if (this.showConvert) {
					this.nodes = this.towxml(this.textareaData, 'markdown', {})
				}
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
					this.answerTopic(this.userid, this.topic_id, this.title, this.typeName, content)
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
					var covers = this.updateImageList.length > 3?this.updateImageList.slice(0, 3):this.updateImageList
					this.writeArticle(this.userid, this.title, data, content, covers)
				}, err => {
					uni.hideLoading()
				})
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
			answerTopic(userid, topic_id, title, classify, content) {
				uni.showLoading()
				uni.request({
					url: this.$api.address + `forum/answer_topic/${userid}/${topic_id}/${classify}`,
					method: 'POST',
					header: {
						'content-type': 'application/json'
					},
					data: {
						title: title,
						content: content
					},
					success: (res) => {
						uni.hideLoading()
						const {code, data} = res.data
						if (code === 200) {
							uni.showToast({
								title: `回复话题: ${this.topic_title}成功`
							})
							var cache = {
								
							}
							uni.$emit('answer_topic', )
							setTimeout(()=> {
								uni.navigateBack()
							}, 2000)
						} else if (code === 500) {
							uni.showToast({
								title: '回复话题失败',
								icon: 'none'
							})
						}
					},
					fail: (err) => {
						uni.showToast({
							title: '回复话题失败',
							icon: 'none'
						})
					}
				})
			},
			writeArticle(userid, title, classify, content, covers) {
				uni.showLoading()
				uni.request({
					url: this.$api.address + `forum/create_article/${userid}/${classify}`,
					method: 'POST',
					header: {
						'content-type': 'application/json'
					},
					data: {
						title: title,
						content: content,
						covers: covers
					},
					success: (res) => {
						const {code, data} = res.data
						uni.hideLoading()
						if (code === 200) {
							uni.showToast({
								title: "文章 上传成功"
							})
							this.title = ""
							this.textareaData = ""
							this.updateImageList = []
							this.nodes = {}
							this.showConvert = false
						} else if (code === 500) {
							uni.showToast({
								title: "文章上传失败, 请稍后再试",
								icon: 'none'
							})
						}
					},
					fail: (err) => {
						uni.hideLoading()
						uni.showToast({
							title: "文章上传失败, 请稍后再试",
							icon: 'none'
						})
					}
				})
			},
			toolBarClick(type) {
				switch (type) {
					case 'header':
						uni.showActionSheet({
							itemList: ["标题1", "标题2", "标题3", "标题4", "标题5", "标题6"],
							success: (res) => {
								switch (res.tapIndex) {
									case 0:
										this.textareaData += "\n# 标题1\r"
										break
									case 1:
										this.textareaData += "\n## 标题2\r"
										break
									case 2:
										this.textareaData += "\n### 标题3\r"
										break
									case 3:
										this.textareaData += "\n#### 标题4\r"
										break
									case 4:
										this.textareaData += "\n##### 标题5\r"
										break
									case 5:
										this.textareaData += "\n####### 标题6\r"
										break
									}
							},
						})
						break
					case 'bold':
						this.textareaData += "**粗体文字**"
						break
					case 'italic':
						this.textareaData += "*斜体*"
						break
					case 'strikethrough':
						this.textareaData += "~~删除线~~"
						break
					case 'blockquote':
						this.textareaData += "> 引用"
						break
					case 'bulletedlist':
						this.textareaData += "\n- 无序列表"
						break
					case 'numberedlist':
						this.textareaData += "\n0. 有序列表"
						break
					case 'table':
						this.textareaData += "\n\n|列1|列2|列3|\n|-|-|-|\n|单元格1|单元格2|单元格3|\n"
						break
					case 'hr':
						this.textareaData += "\n-----\n"
						break
					case 'link':
						this.textareaData += "[description](http://)"
						break
					case 'image':
						uni.chooseImage({
							count: 1,
							success: (res) => {
								const tempfilepaths = res.tempFilePaths
								this.updateImage(tempfilepaths[0])
							},
							fail: (err) => {
								uni.showToast({
									title: '选取照片失败, 权限问题',
									icon: 'none'
								})
							}
						})
						break
					case 'code':
						this.textareaData += "\n``` 代码块 \n\n```\n"
						break
					case 'clear':
						uni.showModal({
							title: "提示",
							content: "确认清空???",
							success: (res) => {
								if (res.confirm) {
									this.textareaData = ""
								}
							}
						})
						break
				}
			},
			async updateImage(imagePath) {
				uni.showLoading()
				await uploadImage(imagePath, 'forum-cover/', result => {
					uni.hideLoading()
					this.updateImageList.push(result)
					this.textareaData += `\n![description](${result})\n`
				}, err => {
					uni.hideLoading()
					uni.showToast({
						title: '上传图片失败, 稍后再试',
						icon: 'none'
					})
				})
			},
		}
	}
</script>

<style lang="scss">
	.input-content {
		width: 100%;
	}

	.input-content {
		.textarea {
			padding: 16rpx 25rpx 15rpx 25rpx;
			min-height: 700rpx;
			line-height: 1.5;
			font-size: 24rpx;
			width: 100%;
		}
		.count {
			display: flex;
			justify-content: flex-end;
			font-size: 24rpx;
			color: #999;
		}
	}

	.preview {
		border-top: 1rpx solid #e0e0e0;
		width: 100%;
	}
</style>
