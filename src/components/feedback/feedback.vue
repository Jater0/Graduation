<template>
  <div>
    <h3 class="text-dark mb-4">Feedback</h3>
    <small
      class="form-text text-muted"
      style="text-align: center"
      @click="showWarning"
      >{{ isShowWarning ? "收起 举报反馈" : "显示 举报反馈" }}
      <i :class="isShowWarning ? 'fas fa-angle-up' : 'fas fa-angle-down'"></i>
    </small>
    <div class="row" v-if="isShowWarning">
      <FeedbackCard
        v-for="(item, index) in 10"
        :key="index"
        :detail="detail"
        :idSetter="index"
      ></FeedbackCard>
    </div>
    <hr />
    <small
      class="form-text text-muted"
      style="text-align: center"
      @click="showBug"
      >{{ isShowBug ? "收起 BUG反馈" : "显示 BUG反馈" }}
      <i :class="isShowBug ? 'fas fa-angle-up' : 'fas fa-angle-down'"></i>
    </small>
    <div class="row" v-if="isShowBug">
      <FeedbackCard
        v-for="(item, index) in 10"
        :key="index"
        :detail="detail"
        :idSetter="index"
      ></FeedbackCard>
    </div>
    <hr />
    <small
      class="form-text text-muted"
      style="text-align: center"
      @click="showIdea"
      >{{ isShowIdea ? "收起 IDEA反馈" : "显示 IDEA反馈" }}
      <i :class="isShowIdea ? 'fas fa-angle-up' : 'fas fa-angle-down'"></i>
    </small>
    <div class="row" v-if="isShowIdea">
      <FeedbackCard
        v-for="(item, index) in 10"
        :key="index"
        :detail="detail"
        :idSetter="index"
      ></FeedbackCard>
    </div>
    <hr />
    <small
      class="form-text text-muted"
      style="text-align: center"
      @click="showFinish"
      >{{ isShowFinish ? "收起 已解决反馈" : "显示 已解决反馈" }}
      <i :class="isShowFinish ? 'fas fa-angle-up' : 'fas fa-angle-down'"></i>
    </small>
    <div class="row" v-if="isShowFinish">
      <FeedbackCard
        v-for="(item, index) in 10"
        :key="index"
        :detail="detail"
        :idSetter="index"
      ></FeedbackCard>
    </div>
    <hr />
  </div>
</template>

<script>
import FeedbackCard from "@/components/feedback/feedback-card.vue";
export default {
  components: {
    FeedbackCard,
  },
  data() {
    return {
      detail: {
        covers: [
          "https://objective-forum-oss.oss-cn-shenzhen.aliyuncs.com/forum-avatar/NV2850R4G4.png",
          "https://objective-forum-oss.oss-cn-shenzhen.aliyuncs.com/forum-avatar/NV2850R4G4.png",
          "https://objective-forum-oss.oss-cn-shenzhen.aliyuncs.com/forum-avatar/NV2850R4G4.png",
        ],
        user_name: "Jater",
        content: "这是Bug",
        isReply: false,
      },
      isShowWarning: false,
      isShowBug: false,
      isShowIdea: false,
      isShowFinish: false,
    };
  },
  methods: {
    showWarning() {
      this.isShowWarning = !this.isShowWarning;
    },
    showBug() {
      this.isShowBug = !this.isShowBug;
    },
    showIdea() {
      this.isShowIdea = !this.isShowIdea;
    },
    showFinish() {
      this.isShowFinish = !this.isShowFinish;
    },
  },
};
</script>