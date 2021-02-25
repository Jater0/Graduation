<template>
  <div>
    <div class="d-sm-flex justify-content-between align-items-center mb-4">
      <h3 class="text-dark mb-0">
        文章Markdown编辑器 {{ change ? "*" : ""
        }}<button
          type="button"
          class="btn btn-primary"
          style="margin-left: 30px"
          v-if="change && text !== ''"
          @click="publishArticle"
        >
          提交
        </button>
      </h3>
    </div>
    <input
      type="file"
      name="add-folder"
      id="add-folder"
      style="display: none"
      @change="addFolder"
      webkitdirectory
      multiple
    />
    <label for="article-type" style="margin-right: 10px">
      <strong>选择文章类型: </strong>
    </label>
    <select
      id="select-option"
      class="form-control form-control-sm custom-select custom-select-sm"
      style="width: 20%; margin-bottom: 20px"
    >
      <option value="null" selected>-- 选择文章类型 --</option>
      <option :value="item" v-for="(item, index) in label" :key="index">
        {{ item }}
      </option>
    </select>
    <br />
    <label for="title"><strong>文章标题: </strong></label>
    <input type="text" v-model="title" style="margin-left: 10px" />
    <MdEditor
      v-model="text"
      height="400px"
      :disabled-menus="[]"
      @upload-image="handleUploadImage"
      @save="saveFile"
    ></MdEditor>
    <LoadingDialog v-model="showLoading"></LoadingDialog>
    <Dialog
      v-model="showNullCheck"
      title="请选择/填写"
      :content="showNullCheckContent"
      @cancel="closeDialogNullCheck"
      @confirm="closeDialogNullCheck"
    ></Dialog>
  </div>
</template>

<script>
import MdEditor from "@kangc/v-md-editor";
import LoadingDialog from "@/components/loading-dialog/loading-dialog.vue";
import Dialog from "@/components/dialog/dialog.vue";
import { uploadOSS } from "@/server/oss";
import { mapState } from "vuex";
export default {
  components: { MdEditor, LoadingDialog, Dialog },
  computed: {
    ...mapState(["label"]),
  },
  watch: {
    text(newVal) {
      this.change = true;
    },
  },
  created() {
    if (this.label.length === 0) {
      this.getLabel();
      this.$store.dispatch("set_label", this.labels);
    }
  },
  data() {
    return {
      text: "",
      change: false,
      labels: [],
      title: "",
      showLoading: false,
      covers: [],
      showNullCheck: false,
      showNullCheckContent: "",
      saveDir: "",
    };
  },
  methods: {
    async handleUploadImage(event, insertImage, files) {
      this.showLoading = true;
      var url = await uploadOSS(files[0]);
      this.showLoading = false;
      insertImage({
        url: url,
        desc: "IMAGE",
      });
    },
    addFolder() {
      const files = document.getElementById("add-folder");
      console.log(files.files[0]);
      console.log(files.files[0].webkitRelativePath);
    },
    saveFile(text, html) {
      if (this.saveDir === "") {
        let folder = document.getElementById("add-folder");
        folder.click();
      }
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
    publishArticle() {
      var mySelect = document.getElementById("select-option");
      var index = mySelect.selectedIndex;
      var labelSelect = mySelect.options[index].value;
      if (labelSelect === "null") {
        this.showNullCheckContent = "请选择 文章标签";
        this.showNullCheck = true;
        return;
      }
      if (this.title === "") {
        this.showNullCheckContent = "请选择 文章标题";
        this.showNullCheck = true;
        return;
      }
      var contentBody = document.getElementsByClassName(
        "vuepress-markdown-body"
      );
      var images = contentBody[0].getElementsByTagName("img");
      for (var i = 0; i < 3; i++) {
        if (images[i] === undefined) break;
        this.covers.push(images[i].currentSrc);
      }
      console.log(this.covers);
    },
    closeDialogNullCheck() {
      this.showNullCheckContent = "";
      this.showNullCheck = false;
    },
  },
};
</script>