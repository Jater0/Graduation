<template>
  <div>
    <h3 class="text-dark mb-4">
      Feedback
      <button
        type="button"
        class="btn btn-primary"
        style="margin-left: 30px"
        @click="refresh"
      >
        刷新
      </button>
    </h3>
    <Item
      :isShow="isShowWarning"
      :details="listCacheData[1] || []"
      :showLoad="load[1]"
      showTitle="收起 举报反馈"
      notShowTitle="显示 举报反馈"
      @show="showWarning"
      @more="moreWarning"
      @updateFb="updateWarningFeedback"
    ></Item>
    <Item
      :isShow="isShowBug"
      :details="listCacheData[2] || []"
      :showLoad="load[2]"
      showTitle="收起 Bug反馈"
      notShowTitle="显示 Bug反馈"
      @show="showBug"
      @more="moreBug"
      @updateFb="updateBugFeedback"
    ></Item>
    <Item
      :isShow="isShowIdea"
      :details="listCacheData[3] || []"
      :showLoad="load[3]"
      showTitle="收起 Idea反馈"
      notShowTitle="显示 Idea反馈"
      @show="showIdea"
      @more="moreIdea"
      @updateFb="updateIdeaFeedback"
    ></Item>
    <Item
      :isShow="isShowFinish"
      :details="listCacheData[4] || []"
      :showLoad="load[4]"
      showTitle="收起 已解决反馈"
      notShowTitle="显示 已解决反馈"
      @show="showFinish"
      @more="moreFinish"
    ></Item>
    <Dialog
      v-model="showGetFeedbackStatus"
      title="获取数据失败"
      content="获取反馈数据失败, 请稍后再试"
      @cancel="closeDialogGetFeedback"
      @confirm="closeDialogGetFeedback"
    ></Dialog>
  </div>
</template>

<script>
import Item from "@/components/feedback/feedback-item.vue";
import Dialog from "@/components/dialog/dialog.vue";
import { mapState } from "vuex";
export default {
  computed: {
    ...mapState(["feedbackCache", "feedbackLoadCache"]),
  },
  components: {
    Item,
    Dialog,
  },
  data() {
    return {
      listCacheData: {},
      load: {},
      pageSize: 3,
      isShowWarning: false,
      isShowBug: false,
      isShowIdea: false,
      isShowFinish: false,
      showGetFeedbackStatus: false,
    };
  },
  created() {
    if (JSON.stringify(this.feedbackCache) === "{}") {
      this.getFeedbackList(1);
      this.getFeedbackList(2);
      this.getFeedbackList(3);
      this.getFeedbackList(4);
    } else {
      this.load = this.feedbackLoadCache;
      this.listCacheData = this.feedbackCache;
    }
  },
  methods: {
    refresh() {
      this.listCacheData = {};
      this.load = {};
      this.isShowWarning = false;
      this.isShowBug = false;
      this.isShowIdea = false;
      this.isShowFinish = false;
      this.getFeedbackList(1);
      this.getFeedbackList(2);
      this.getFeedbackList(3);
      this.getFeedbackList(4);
    },
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
    getFeedbackList(current) {
      var url = "http://localhost:8000/admin/get_feedback/by";
      if (current === 4) {
        url = "http://localhost:8000/admin/get_feedback";
      }
      if (!this.load[current]) {
        this.load[current] = {
          page: 1,
          plus: true,
          loading: true,
          plusLoading: false,
        };
      }
      if (!this.load[current].plus) {
        return;
      }
      this.$axios({
        methods: "get",
        url: url,
        params: {
          type: current,
          page: this.load[current].page,
          size: this.pageSize,
        },
      })
        .then((result) => {
          const { code, data } = result.data;
          if (code === 200) {
            let dataLen = data.length;
            if (dataLen === 0) {
              this.forceUpdateWhenRequestDataShort(current);
              return;
            }
            if (dataLen < this.pageSize && dataLen !== 0) {
              this.forceUpdateWhenRequestDataShort(current);
              this.$forceUpdate();
            }
            if (dataLen === this.pageSize) {
              let oldLoad = {};
              oldLoad.plus = true;
              oldLoad.loading = true;
              oldLoad.plusLoading = false;
              oldLoad.page = this.load[current].page;
              this.$set(this.load, current, oldLoad);
              this.$forceUpdate();
              this.$store.dispatch("set_feedback_load_cache", this.load);
            }
            let oldList = this.listCacheData[current] || [];
            oldList.push(...data);
            this.$set(this.listCacheData, current, oldList);
            this.$store.dispatch("set_feedback_cache", this.listCacheData);
          } else {
            this.showGetFeedbackStatus = true;
          }
        })
        .catch((err) => {
          this.showGetFeedbackStatus = true;
        });
    },
    moreWarning() {
      this.load[1].page++;
      this.load[1].plusLoading = true;
      this.getFeedbackList(1);
    },
    moreBug() {
      this.forceUpdateWhenMore(2);
      this.load[2].page++;
      this.getFeedbackList(2);
    },
    moreIdea() {
      this.load[3].page++;
      this.load[3].plusLoading = true;
      this.getFeedbackList(3);
    },
    moreFinish() {
      this.load[4].page++;
      this.load[4].plusLoading = true;
      this.getFeedbackList(4);
    },
    forceUpdateWhenMore(current) {
      let oldLoad = {};
      oldLoad.plus = true;
      oldLoad.loading = true;
      oldLoad.plusLoading = true;
      oldLoad.page = this.load[current].page;
      this.$set(this.load, current, oldLoad);
      this.$forceUpdate();
    },
    forceUpdateWhenRequestDataShort(current) {
      let oldLoad = {};
      oldLoad.plus = false;
      oldLoad.loading = true;
      oldLoad.plusLoading = false;
      oldLoad.page = this.load[current].page;
      this.$set(this.load, current, oldLoad);
      this.$forceUpdate();
      this.$store.dispatch("set_feedback_load_cache", this.load);
    },
    updateWarningFeedback(data) {
      let oldList = this.listCacheData[1];
      let updateData = oldList[data];
      oldList.splice(data, 1);
      this.$set(this.listCacheData, 1, oldList);
      this.updateDoneFeedback(updateData);
    },
    updateBugFeedback(data) {
      let oldList = this.listCacheData[2];
      let updateData = oldList[data];
      oldList.splice(data, 1);
      this.$set(this.listCacheData, 2, oldList);
      this.updateDoneFeedback(updateData);
    },
    updateIdeaFeedback(data) {
      let oldList = this.listCacheData[3];
      let updateData = oldList[data];
      oldList.splice(data, 1);
      this.$set(this.listCacheData, 3, oldList);
      this.updateDoneFeedback(updateData);
    },
    updateDoneFeedback(data) {
      data.is_check = true;
      let oldList = this.listCacheData[4] || [];
      oldList.push(data);
      this.$set(this.listCacheData, 4, oldList);
    },
    closeDialogGetFeedback() {
      this.showGetFeedbackStatus = false;
    },
  },
};
</script>