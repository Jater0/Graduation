<template>
	<article-detail :article="article"></article-detail>
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
			uni.setNavigationBarTitle({
				title: this.topicDetailCache.title
			})
			this.getArticleDetail(data.params)
		},
		data() {
			return {
				article: {},
				title: "Loading..."
			};
		},
		methods: {
			getArticleDetail(topic_id) {
				uni.request({
					method: 'GET',
					url: this.$api.address + `forum/get_article/${this.userid}/${topic_id}/1/1`,
					success: (res) => {
						const {data, code} = res.data
						this.$store.dispatch('clear_topic_detail_cache')
						if (code === 200) {
							this.article = data[0]
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
			}
		}
	}
</script>

<style lang="scss">

</style>
