<template>
	<list-scroll class="list-scroll" @loadmore="loadmore">
		<view>
			<list-card v-for="item in list" :key="item._id" :item="item"></list-card>
		</view>
		<uni-load-more v-if="list.length === 0 || list.length > 9" iconType="snow" :status="load.loading || 'loading'"></uni-load-more>
	</list-scroll>
</template>

<script>
	import ListScroll from '@/components/list-scroll/list-scroll.vue'
	import ListCard from '@/components/list-card/list-card.vue'
	export default {
		components: {
			ListScroll, ListCard
		},
		props: {
			list: {
				type: Array,
				default() {
					return []
				}
			},
			load: {
				type: Object,
				default(){
					return {
						loading: 'loading'
					}
				}
			}
		},
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
	.list-scroll {
		height: 100%;
	}
</style>
