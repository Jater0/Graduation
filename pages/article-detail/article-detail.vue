<template>
	<article-detail :article="article" @browse="updateBrowseCount"></article-detail>
</template>

<script>
	import ArticleDetail from '@/components/article-detail/article-detail.vue'
	import {mapState} from 'vuex'
	export default {
		computed: {
			...mapState(['userid', 'topicDetailCache'])
		},
		components: {
			ArticleDetail
		},
		onLoad(data) {
			const {params, mode, index} = data
			uni.setNavigationBarTitle({
				title: this.topicDetailCache.title
			})
			if (mode === 'topic') {
				this.reqUrl = this.$api.address + `forum/get_topic_article/${this.userid}/${params}/1/1`
			} else {
				this.reqUrl = this.$api.address + `forum/get_article/${this.userid}/${params}/1/1`
			}
			this.articleIndex = index
			this.getArticleDetail()
		},
		data() {
			return {
				article: {},
				title: "Loading...",
				reqUrl: "",
				articleIndex: -1,
				reqIndex: 0
			};
		},
		methods: {
			getArticleDetail() {
				uni.request({
					method: 'GET',
					url: this.reqUrl,
					success: (res) => {
						const {data, code} = res.data
						this.$store.dispatch('clear_topic_detail_cache')
						if (code === 200) {
							if (data.length === 0) {
								uni.showToast({
									title: '这篇文章已经被删除',
									icon: 'none'
								})
								console.log(this.articleIndex);
								if (this.articleIndex >= 0) {
									uni.$emit('is_delete', this.articleIndex)
								} else {
									uni.$emit('is_delete')
								}
								setTimeout(() => {
									uni.navigateBack()
								},2000)
							} else {
								this.article = data[0]
							}
						} else if (code === 500) {
							uni.showToast({
								title: '获取文章失败',
								icon: 'none'
							})
						}
					},
					fail: (err) => {
						uni.showToast({
							title: '获取文章失败',
							icon: 'none'
						})
					}
				})
			},
			updateBrowseCount(data) {
				console.log(data[0]);
				uni.request({
					method: 'POST',
					url: this.$api.address + `forum/update_article_browse/${data[0]}`,
					success: (res) => {
						const {data, code} = res.data
						if (code === 200) {
							uni.showToast({
								title: `浏览3s`
							})
							this.article.browse_count++
							uni.$emit('update_browse_root')
						} else if (code === 500) {
							console.log("更新浏览记录失败");
						}
					},
					fail: (err) => {
						console.log("更新浏览记录失败");
					}
				})
			}
		}
	}
</script>

<style lang="scss">

</style>
