<template>
  <div>
    <div class="d-sm-flex justify-content-between align-items-center mb-4">
      <h3 class="text-dark mb-0">话题编辑</h3>
    </div>
    <div>
      <div class="form-group">
        <label for="topic-type" style="margin-right: 10px">
          <strong>选择话题类型:</strong>
        </label>
        <select
          id="select-option"
          class="form-control form-control-sm custom-select custom-select-sm"
          style="width: 20%; margin-bottom: 20px"
        >
          <option value="null" selected>-- 选择话题类型 --</option>
          <option :value="item" v-for="(item, index) in label" :key="index">
            {{ item }}
          </option>
        </select>
        <br />
        <label for="topic-cover" style="margin-right: 10px">
          <strong>选择图片(max = 3)</strong>
        </label>
        <input
          type="file"
          name="add-cover"
          id="add-cover"
          class="btn btn-primary btn-sm"
          style="display: none"
          @change="addCover"
          multiple="multiple"
          accept=".jpg, .jpeg, .png"
        />
        <i class="fas fa-plus-circle" @click="addCoverButton"></i>
        <textarea
          class="signature-content-textarea form-control"
          v-model="topicContent"
          placeholder="请输入..."
          maxlength="100"
          name="signature"
        ></textarea>
        <p class="signature-content-count">{{ topicContent.length }}/100</p>
      </div>
      <button
        type="button"
        class="btn btn-primary btn-sm"
        style="font-size: 20px"
        @click="submit"
      >
        发布
      </button>
      <br />
      <label style="margin-top: 30px"><strong>图片描述: </strong></label>
      <div class="row" style="margin-bottom: 20px">
        <div class="col-4" v-for="(item, index) in coverList" :key="index">
          <div class="card-group">
            <div class="card">
              <div class="card-body">
                <i
                  class="fas fa-trash-alt close-icon"
                  @click="removeCover(index)"
                ></i>
                <img
                  :src="item"
                  style="width: 100%; height: 100%; overflow: hidden"
                />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <Dialog
      v-model="showDialogByNullSelect"
      type="confirm"
      title="空的话题类型"
      content="请选择一个话题类型"
      @cancel="closeDialogByNullSelect"
      @confirm="closeDialogByNullSelect"
    ></Dialog>
    <Dialog
      v-model="showDialogBySuccess"
      title="确认上传"
      :content="showDetailForUpdate"
      @cancel="closeDialogBySuccess"
      @confirm="publishTopic"
    ></Dialog>
    <LoadingDialog v-model="showLoading"></LoadingDialog>
    <Dialog
      v-model="showUpdateTopicStatus"
      title="上传反馈"
      :content="updateTopicStatusContent"
      type="confirm"
      @cancel="closeDialogUpdateTopicStatus"
      @confirm="closeDialogUpdateTopicStatus"
    ></Dialog>
  </div>
</template>

<script>
import { mapState } from "vuex";
import { uploadOSS } from "@/server/oss";
import Dialog from "@/components/dialog/dialog.vue";
import LoadingDialog from "@/components/loading-dialog/loading-dialog.vue";
import { convertVueHtml } from "@/utils/index.js";
export default {
  components: {
    Dialog,
    LoadingDialog,
  },
  data() {
    return {
      topicContent: "",
      labels: [],
      coverList: [],
      showDialogByNullSelect: false,
      showDialogBySuccess: false,
      showDetailForUpdate: "",
      labelType: "",
      showLoading: false,
      showUpdateTopicStatus: false,
      updateTopicStatusContent: "",
    };
  },
  computed: {
    ...mapState(["label"]),
  },
  created() {
    if (this.label.length === 0) {
      this.getLabel();
      this.$store.dispatch("set_label", this.labels);
    }
  },
  methods: {
    submit() {
      var mySelect = document.getElementById("select-option");
      var index = mySelect.selectedIndex;
      var labelSelect = mySelect.options[index].value;
      if (labelSelect === "null") {
        this.showDialogByNullSelect = true;
        return;
      }
      this.labelType = labelSelect;
      this.showDetailForUpdate = convertVueHtml(
        labelSelect,
        this.topicContent,
        this.coverList
      );
      this.showDialogBySuccess = true;
    },
    closeDialogBySuccess() {
      this.showDialogBySuccess = false;
    },
    publishTopic() {
      this.showDialogBySuccess = false;
      this.showLoading = true;
      this.createTopic();
    },
    getLabel() {
      this.$axios({
        method: "get",
        url: "http://localhost:8000/admin/get_label",
      })
        .then((result) => {
          const { data, code } = result.data;
          if (code === 200) {
            for (let i = 0; i < data.length; i++) {
              this.labels.push(data[i].label_name);
            }
          } else if (code === 500) {
          }
        })
        .catch((err) => {});
    },
    async addCover() {
      this.showLoading = true;
      const files = document.getElementById("add-cover");
      var updateFiles = files.files;
      var updateFilesLen = updateFiles.length;
      var len = this.coverList.length;
      if (updateFilesLen >= 3) {
        for (var i = 0; i < 3 - len; i++) {
          var file = await uploadOSS(updateFiles[i]);
          this.coverList.push(file);
        }
      } else {
        if (len === 0) {
          for (var i = 0; i < updateFilesLen; i++) {
            var file = await uploadOSS(updateFiles[i]);
            this.coverList.push(file);
          }
        } else if (len === 1) {
          var index;
          if (updateFilesLen >= 2) index = 2;
          if (updateFilesLen < 2) index = updateFilesLen;
          for (var i = 0; i < index; i++) {
            var file = await uploadOSS(updateFiles[i]);
            this.coverList.push(file);
          }
        } else if (len === 2) {
          var file = await uploadOSS(updateFiles[i]);
          this.coverList.push(file);
        }
      }
      this.showLoading = false;
    },
    addCoverButton() {
      if (this.coverList.length === 3) {
        return;
      }
      let avatar = document.getElementById("add-cover");
      avatar.click();
    },
    removeCover(index) {
      this.coverList.splice(index, 1);
    },
    closeDialogByNullSelect() {
      this.showDialogByNullSelect = false;
    },
    createTopic() {
      var params = {
        content: this.topicContent,
        type: this.labelType,
        covers: JSON.stringify(this.coverList),
      };
      this.$axios
        .post(
          "http://localhost:8000/admin/create_topic",
          this.$qs.stringify(params)
        )
        .then((result) => {
          this.showLoading = false;
          const { code, data } = result.data;
          if (code === 200) {
            this.updateTopicStatusContent = "上传成功";
            this.showUpdateTopicStatus = true;
            this.topicContent = "";
            this.coverList = [];
            mySelect.selectedIndex = 0;
          } else if (code === 500) {
            this.updateTopicStatusContent = "上传失败, 请稍后再试";
            this.showUpdateTopicStatus = true;
          }
        })
        .catch((err) => {
          this.showLoading = false;
          this.updateTopicStatusContent = "上传失败, 请稍后再试";
          this.showUpdateTopicStatus = true;
        });
    },
    closeDialogUpdateTopicStatus() {
      this.showUpdateTopicStatus = false;
      this.updateTopicStatusContent = "";
    },
  },
};
</script>

<style lang="scss" scoped>
.signature-content-textarea {
  font-size: 20px;
  width: 100%;
  height: 150px;
}
.signature-content-count {
  display: flex;
  justify-content: flex-end;
  font-size: 25px;
  color: #999;
}
.close-icon {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  right: 4%;
  top: 4%;
  border-radius: 50%;
  z-index: 2;
}
</style>