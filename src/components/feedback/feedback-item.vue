<template>
  <div>
    <small class="form-text text-muted" style="text-align: center" @click="show"
      >{{ isShow ? showTitle : notShowTitle }}
      <i :class="isShow ? 'fas fa-angle-up' : 'fas fa-angle-down'"></i>
    </small>
    <div class="row" v-if="isShow && details.length !== 0">
      <FeedbackCard
        v-for="(item, index) in details"
        :key="index"
        :detail="item"
        :idSetter="index"
        @updateFb="updateFeedback"
      ></FeedbackCard>
      <FeedbackPlus
        v-if="showLoad.plus"
        :isLoading="showLoad.plusLoading"
        @more="moreFeedback"
      ></FeedbackPlus>
      <NoData v-if="!showLoad.plus" title="没有更多数据了"></NoData>
    </div>
    <div class="row" v-if="isShow && details.length === 0">
      <NoData v-if="showLoad.loading"></NoData>
      <Loading v-else></Loading>
    </div>
    <hr />
  </div>
</template>

<script>
import FeedbackCard from "@/components/feedback/feedback-card.vue";
import NoData from "@/components/feedback/feedback-nodata.vue";
import FeedbackPlus from "@/components/feedback/feedback-plus.vue";
import Loading from "@/components/loading/loading.vue";
export default {
  components: {
    FeedbackCard,
    NoData,
    FeedbackPlus,
    Loading,
  },
  props: {
    isShow: {
      type: Boolean,
      default: false,
    },
    details: {
      type: Array,
      default: [],
    },
    showTitle: {
      type: String,
      default: "收起",
    },
    notShowTitle: {
      type: String,
      default: "显示",
    },
    showLoad: {
      type: Object,
      default() {
        return {
          plus: true,
          loading: false,
        };
      },
    },
  },
  created() {},
  methods: {
    show() {
      this.$emit("show", !this.isShow);
    },
    moreFeedback() {
      this.$emit("more");
    },
    updateFeedback(data) {
      this.$emit("updateFb", data);
    },
  },
};
</script>