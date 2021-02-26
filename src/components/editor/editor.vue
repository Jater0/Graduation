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
    <div class="no-data" v-if="workspace === ''">
      请选择workspace
      <button type="default" class="btn btn-primary" @click="chooseWorkspace">
        选择
      </button>
    </div>
    <div v-else>
      <label for="workspave">
        <strong>Workspace:</strong> {{ workspace }}\{{ title }}.txt
        <button
          type="default"
          class="btn btn-primary"
          style="margin-left: 10px; font-size: 10px"
          @click="chooseWorkspace"
        >
          修改路径
        </button>
      </label>
      <br />
      <label for="topic-title" v-if="mode === 'topic'"
        ><strong>回复话题: </strong>{{ topic.title }}</label
      ><br v-if="mode === 'topic'" />
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
      <label for="title"
        ><strong>文章标题: {{ confirmTitle ? title : "" }}</strong></label
      >
      <input
        type="text"
        v-model="title"
        style="margin-left: 10px"
        v-if="!confirmTitle"
      />
      <button
        type="default"
        class="btn"
        :class="confirmTitle ? 'btn-danger' : 'btn-primary'"
        style="margin-left: 10px; font-size: 10px"
        @click="clickConfirmTitle"
      >
        {{ confirmTitle ? "修改" : "创建/读取文件" }}
      </button>
      <MdEditor
        v-model="text"
        height="400px"
        :disabled-menus="[]"
        @upload-image="handleUploadImage"
        @save="saveFile"
      ></MdEditor>
    </div>
    <LoadingDialog v-model="showLoading"></LoadingDialog>
    <Dialog
      v-model="showNullCheck"
      title="请选择/填写"
      :content="showNullCheckContent"
      @cancel="closeDialogNullCheck"
      @confirm="closeDialogNullCheck"
    ></Dialog>
    <Dialog
      v-model="showCreateArticleStatus"
      title="上传文章反馈"
      :content="showCreateArticleStatusContent"
      @cancel="closeDialogCreateArticleStatus"
      @confirm="closeDialogCreateArticleStatus"
    ></Dialog>
  </div>
</template>

<script>
import MdEditor from "@kangc/v-md-editor";
import LoadingDialog from "@/components/loading-dialog/loading-dialog.vue";
import Dialog from "@/components/dialog/dialog.vue";
import { uploadOSS } from "@/server/oss";
import { mapState } from "vuex";
const { ipcRenderer } = window.require("electron");
const fs = window.require("fs");
export default {
  props: {
    mode: {
      type: String,
      default: "article",
    },
    topic: {
      type: Object,
      default() {
        return {
          id: "",
          title: "",
        };
      },
    },
  },
  components: { MdEditor, LoadingDialog, Dialog },
  computed: {
    ...mapState(["label", "workspaceCache"]),
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
    if (this.workspaceCache !== "") {
      this.workspace = this.workspaceCache;
    }
  },
  mounted() {
    ipcRenderer.on("workspace-path", (event, data) => {
      if (data !== null || data !== undefined) {
        this.workspace = data;
        this.$store.dispatch("set_workspace_cache", this.workspace);
      }
    });
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
      workspace: "",
      confirmTitle: false,
      showCreateArticleStatus: false,
      showCreateArticleStatusContent: "",
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
    chooseWorkspace() {
      ipcRenderer.send("choose-workspace");
    },
    saveFile(text, html) {
      console.log(text);
      if (this.workspace === "") {
        this.chooseWorkspace();
        return;
      }
      if (this.title === "") {
        this.showNullCheckContent = "请选择 文章标题";
        this.showNullCheck = true;
        return;
      }
      var exists = fs.existsSync(`${this.workspace}/${this.title}.txt`);
      if (exists) {
        fs.writeFile(
          `${this.workspace}/${this.title}.txt`,
          text,
          { flag: "a" },
          (err) => {
            if (err) {
              console.log("Failed");
            } else {
              console.log("Success");
            }
          }
        );
      } else {
        fs.writeFile(
          `${this.workspace}/${this.title}.txt`,
          text,
          { flag: "a" },
          (err) => {
            if (err) {
              console.log("Failed");
            } else {
              console.log("Success");
            }
          }
        );
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
    async publishArticle() {
      var mySelect = document.getElementById("select-option");
      var index = mySelect.selectedIndex;
      var labelSelect = mySelect.options[index].value;
      if (labelSelect === "null") {
        this.showNullCheckContent = "请选择 文章标签";
        this.showNullCheck = true;
        return;
      }
      if (this.title === "") {
        this.showNullCheckContent = "请填写 文章标题";
        this.showNullCheck = true;
        return;
      }
      try {
        var contentBody = document.getElementsByClassName(
          "v-md-editor-preview"
        );
        var images = contentBody[0].getElementsByTagName("img");
        for (var i = 0; i < 3; i++) {
          if (images[i] === undefined) break;
          this.covers.push(images[i].currentSrc);
        }
      } catch (error) {}
      this.showLoading = true;
      var file = new File([this.text], `${this.workspace}/${this.title}.txt`);
      var content = "";
      try {
        content = await uploadOSS(file, "forum-article/");
      } catch (error) {
        console.log(error);
      }
      if (this.mode === "article") {
        this.createArticle(this.title, content, labelSelect, this.covers);
      } else if (this.mode === "topic") {
      }
    },
    closeDialogNullCheck() {
      this.showNullCheckContent = "";
      this.showNullCheck = false;
    },
    clickConfirmTitle() {
      if (this.title === "") {
        this.showNullCheckContent = "请选择 文章标题";
        this.showNullCheck = true;
        return;
      }
      this.confirmTitle = !this.confirmTitle;
      if (this.confirmTitle) {
        this.showLoading = true;
        var exists = fs.existsSync(`${this.workspace}/${this.title}.txt`);
        if (exists) {
          fs.readFile(
            `${this.workspace}/${this.title}.txt`,
            { flag: "r+", encoding: "utf-8" },
            (err, data) => {
              this.showLoading = false;
              if (err) {
                return;
              }
              this.text = data;
              this.change = false;
            }
          );
        } else {
          fs.writeFile(
            `${this.workspace}/${this.title}.txt`,
            this.text,
            { flag: "a" },
            (err) => {
              this.showLoading = false;
              if (err) {
                console.log("Failed");
              } else {
                console.log("Success");
              }
            }
          );
        }
      }
    },
    createArticle(title, content, classify, covers) {
      var params = {
        title: title,
        classify: classify,
        content: content,
        covers: JSON.stringify(covers),
      };
      this.$axios
        .post(
          "http://localhost:8000/admin/create_article",
          this.$qs.stringify(params)
        )
        .then((result) => {
          this.showLoading = false;
          const { code, data } = result.data;
          if (code === 200) {
            this.refresh();
            this.showCreateArticleStatusContent = "上传成功";
            this.showCreateArticleStatus = true;
          } else if (code === 500) {
            this.showCreateArticleStatusContent = "上传失败, 请稍后再试";
            this.showCreateArticleStatus = true;
          }
        })
        .catch((err) => {
          this.showLoading = false;
          this.showCreateArticleStatusContent = "上传失败, 请稍后再试";
          this.showCreateArticleStatus = true;
        });
    },
    refresh() {
      this.text = "";
      this.change = false;
      this.title = "";
      this.confirmTitle = false;
      this.covers = [];
    },
    closeDialogCreateArticleStatus() {
      this.showCreateArticleStatus = false;
      this.showCreateArticleStatusContent = "";
    },
  },
};
</script>

<style lang="scss" scoped>
.no-data {
  text-align: center;
  width: 100%;
  padding: 100px 0;
  color: #999;
  font-size: 28px;
}
</style>