<template>
  <div class="col-3" style="margin-top: 20px">
    <div class="card-group">
      <div class="card">
        <div class="card-body">
          <Carousel
            :covers="detail.feedback_cover"
            :idSetter="idSetter"
            v-if="detail.feedback_cover.length !== 0"
          ></Carousel>
          <CarouselNoData v-else :idSetter="idSetter"></CarouselNoData>
          <p style="font-size: 10px">
            反馈时间: {{ detail.create_time | formatTime }}
          </p>
          <p style="font-size: 15px">
            {{ detail.feedback_content }}
          </p>
          <button
            class="btn btn-primary feedback-button"
            :class="isFix ? 'btn-secondary' : 'btn-primary'"
            type="button"
            @click="fixBug(1)"
          >
            {{ isFix ? "已处理" : "点击完成处理" }}
          </button>
          <button
            class="btn btn-secondary feedback-button"
            style="margin-left: 10px"
            v-if="!isFix"
            type="button"
            @click="fixBug(0)"
          >
            不属实
          </button>
        </div>
      </div>
    </div>
    <Dialog
      v-model="showUpdateFeedbackStatus"
      title="更新反馈"
      :content="updateFeedbackStatusContent"
      @cancel="closeDialogUpdateFeedback"
      @confirm="closeDialogUpdateFeedback"
    ></Dialog>
    <LoadingDialog v-model="showLoading"></LoadingDialog>
  </div>
</template>

<script>
import Carousel from "@/components/feedback/card-carousel.vue";
import CarouselNoData from "@/components/feedback/card-carousel-nodata.vue";
import Dialog from "@/components/dialog/dialog.vue";
import LoadingDialog from "@/components/loading-dialog/loading-dialog.vue";
import { convertDate } from "@/utils/index.js";
export default {
  components: {
    Carousel,
    CarouselNoData,
    Dialog,
    LoadingDialog,
  },
  props: {
    detail: {
      type: Object,
      default() {
        return {};
      },
    },
    idSetter: {
      type: Number,
      default: 0,
    },
  },
  filters: {
    formatTime(time) {
      return convertDate(time);
    },
  },
  data() {
    return {
      isFix: false,
      showLoading: false,
      showUpdateFeedbackStatus: false,
      updateFeedbackStatusContent: "",
    };
  },
  created() {
    this.isFix = this.detail.is_check;
  },
  methods: {
    fixBug(isWork) {
      if (this.isFix === true) {
        return;
      }
      this.updateFeedback(this.detail._id, isWork);
    },
    updateFeedback(id, isWork) {
      this.showLoading = true;
      this.$axios({
        method: "post",
        url: "http://localhost:8000/admin/update_feedback",
        params: {
          id: id,
          work: isWork,
        },
      })
        .then((result) => {
          this.showLoading = false;
          const { code } = result.data;
          if (code === 200) {
            this.$emit("updateFb", this.idSetter);
            this.isFix = !this.isFix;
            this.updateFeedbackStatusContent = "反馈更新成功";
            this.showUpdateFeedbackStatus = true;
          } else if (code === 300) {
            this.$emit("updateFb", this.idSetter);
            this.isFix = !this.isFix;
            this.updateFeedbackStatusContent =
              "反馈更新警告, 其他的管理员已经对此操作, 您的操作将被忽略";
            this.showUpdateFeedbackStatus = true;
          } else if (code === 500) {
            this.updateFeedbackStatusContent = "反馈更新失败";
            this.showUpdateFeedbackStatus = true;
          }
        })
        .catch((err) => {
          this.showLoading = false;
          this.updateFeedbackStatusContent = "反馈更新失败";
          this.showUpdateFeedbackStatus = true;
        });
    },
    closeDialogUpdateFeedback() {
      this.showUpdateFeedbackStatus = false;
      this.updateFeedbackStatusContent = "";
    },
  },
};
</script>

<style lang="scss" scoped>
.card-title {
  font-size: 20px;
}
.card-text {
  font-size: 10px;
}
.feedback-button {
  font-size: 10px;
}
</style>