<template>
	<article-detail :article="article"></article-detail>
</template>

<script>
	import ArticleDetail from '@/components/article-detail/article-detail.vue'
	import {mapState} from 'vuex'
	export default {
		computed: {
			...mapState(['userid'])
		},
		components: {
			ArticleDetail
		},
		onLoad(data) {
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
					url: 'http://localhost:8000/forum/get_article',
					data: {
						id: this.userid,
						topic: topic_id,
						page: 1,
						size: 1
					},
					success: (res) => {
						this.article = res.data.data[0]
						this.title = this.article.title
						uni.setNavigationBarTitle({
							title: this.title
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss">

</style>
