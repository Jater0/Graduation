<template>
  <div>
    <h3 class="text-dark mb-4">个人信息</h3>
    <div class="row mb-3">
      <div class="col-lg-4">
        <div class="card mb-3">
          <div class="card-body text-center shadow">
            <img
              class="rounded-circle mb-3 mt-4"
              :src="adminInfo.avatar"
              width="160"
              height="160"
            />
            <div class="mb-3">
              <input
                type="file"
                name="avatar-change"
                id="avatar-change"
                class="btn btn-primary btn-sm"
                style="display: none"
                accept=".jpg, .jpeg, .png"
                @change="changeAvatar"
              />
              <input
                class="btn btn-primary btn-sm"
                type="button"
                value="更新头像"
                @click="changeCheck"
              />
            </div>
          </div>
        </div>
        <div class="card shadow mb-4">
          <div class="card-header py-3">
            <h6 class="text-primary font-weight-bold m-0">Projects</h6>
          </div>
          <div class="card-body">
            <h4 class="small font-weight-bold">
              Server migration<span class="float-right">20%</span>
            </h4>
            <div class="progress progress-sm mb-3">
              <div
                class="progress-bar bg-danger"
                aria-valuenow="20"
                aria-valuemin="0"
                aria-valuemax="100"
                style="width: 20%"
              >
                <span class="sr-only">20%</span>
              </div>
            </div>
            <h4 class="small font-weight-bold">
              Sales tracking<span class="float-right">40%</span>
            </h4>
            <div class="progress progress-sm mb-3">
              <div
                class="progress-bar bg-warning"
                aria-valuenow="40"
                aria-valuemin="0"
                aria-valuemax="100"
                style="width: 40%"
              >
                <span class="sr-only">40%</span>
              </div>
            </div>
            <h4 class="small font-weight-bold">
              Customer Database<span class="float-right">60%</span>
            </h4>
            <div class="progress progress-sm mb-3">
              <div
                class="progress-bar bg-primary"
                aria-valuenow="60"
                aria-valuemin="0"
                aria-valuemax="100"
                style="width: 60%"
              >
                <span class="sr-only">60%</span>
              </div>
            </div>
            <h4 class="small font-weight-bold">
              Payout Details<span class="float-right">80%</span>
            </h4>
            <div class="progress progress-sm mb-3">
              <div
                class="progress-bar bg-info"
                aria-valuenow="80"
                aria-valuemin="0"
                aria-valuemax="100"
                style="width: 80%"
              >
                <span class="sr-only">80%</span>
              </div>
            </div>
            <h4 class="small font-weight-bold">
              Account setup<span class="float-right">Complete!</span>
            </h4>
            <div class="progress progress-sm mb-3">
              <div
                class="progress-bar bg-success"
                aria-valuenow="100"
                aria-valuemin="0"
                aria-valuemax="100"
                style="width: 100%"
              >
                <span class="sr-only">100%</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-lg-8">
        <div class="row">
          <div class="col">
            <div class="card shadow mb-3">
              <div class="card-header py-3">
                <p class="text-primary m-0 font-weight-bold">管理员基本信息</p>
              </div>
              <div class="card-body">
                <div>
                  <div class="form-row">
                    <div class="col">
                      <div class="form-group">
                        <label for="username"
                          ><strong>ID: {{ adminInfo._id }}</strong></label
                        >
                      </div>
                    </div>
                    <div class="col">
                      <div class="form-group">
                        <label for="email"
                          ><strong
                            >邮箱: {{ adminInfo.admin_email }}</strong
                          ></label
                        ><input
                          v-if="showUpdateInfo"
                          v-model="email"
                          class="form-control"
                          type="email"
                          :placeholder="adminInfo.admin_email"
                          name="email"
                        />
                      </div>
                    </div>
                  </div>
                  <div class="form-row">
                    <div class="col">
                      <div class="form-group">
                        <label for="first_name">
                          <strong> 用户名: {{ adminInfo.admin_name }} </strong>
                        </label>
                        <input
                          v-if="showUpdateInfo"
                          class="form-control"
                          type="text"
                          :placeholder="adminInfo.admin_name"
                          v-model="name"
                          name="first_name"
                        />
                      </div>
                    </div>
                    <div class="col">
                      <div class="form-group">
                        <label for="last_name"
                          ><strong
                            >手机号: {{ adminInfo.admin_phone }}</strong
                          ></label
                        ><input
                          v-if="showUpdateInfo"
                          class="form-control"
                          type="text"
                          v-model="phone"
                          :placeholder="adminInfo.admin_phone"
                          name="last_name"
                        />
                      </div>
                    </div>
                  </div>
                  <div class="form-group">
                    <button
                      class="btn btn-primary btn-sm"
                      type="default"
                      @click="updateInfo"
                    >
                      {{ showUpdateInfo ? "保存" : "修改个人信息" }}
                    </button>
                    <button
                      class="btn btn-secondary btn-sm"
                      type="default"
                      @click="cancel"
                      v-if="showUpdateInfo"
                      style="margin-left: 15px"
                    >
                      取消
                    </button>
                  </div>
                </div>
              </div>
            </div>
            <div class="card shadow mb-3">
              <div class="card-header py-3">
                <p class="text-primary m-0 font-weight-bold">
                  管理员其他信息(点击修改)
                </p>
              </div>
              <div class="card-body">
                <div>
                  <div class="form-row">
                    <div class="col">
                      <div class="form-group">
                        <label for="role-level">
                          <strong>
                            权限等级: {{ adminInfo.professional }}
                          </strong>
                        </label>
                      </div>
                    </div>
                    <div class="col">
                      <div class="form-group">
                        <label for="gender" style="margin-right: 10px">
                          <strong>性别: {{ adminInfo.gender }}</strong>
                        </label>
                        <select
                          id="select-option"
                          class="form-control form-control-sm custom-select custom-select-sm"
                          style="width: 30%"
                        >
                          <option value="null" selected>选择性别类型</option>
                          <option value="男">男</option>
                          <option value="女">女</option>
                        </select>
                        <button
                          type="default"
                          class="btn btn-primary btn-sm"
                          style="margin-left: 10px"
                          @click="updateGender"
                        >
                          更新
                        </button>
                      </div>
                    </div>
                  </div>
                  <div class="form-row">
                    <div class="col">
                      <div class="form-group">
                        <label for="role-action">
                          <strong>我能干什么: </strong>
                        </label>
                        <br />
                        <label>
                          {{ actionList }}
                        </label>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div class="card shadow">
              <div class="card-header py-3">
                <p class="text-primary m-0 font-weight-bold">个人签名</p>
              </div>
              <div class="card-body">
                <div>
                  <div class="form-group">
                    <label for="signature">
                      <strong>Signature: </strong>
                      <br />
                    </label>
                    <br />
                    <label for="signature" v-if="!showSignatureEditor">
                      {{ adminInfo.explain }}
                    </label>
                    <textarea
                      v-if="showSignatureEditor"
                      class="signature-content-textarea form-control"
                      v-model="signatureContent"
                      placeholder="请输入..."
                      maxlength="150"
                      name="signature"
                    ></textarea>
                    <label
                      for="signature"
                      class="signature-content-count"
                      v-if="showSignatureEditor"
                      >{{ signatureContent.length }}/150</label
                    >
                  </div>
                  <div class="form-group">
                    <button
                      class="btn btn-primary btn-sm"
                      type="default"
                      @click="updateSignature"
                    >
                      {{ showSignatureEditor ? "确认" : "修改" }}
                    </button>
                    <button
                      class="btn btn-secondary btn-sm"
                      type="default"
                      @click="cancelUpdateSignature"
                      v-if="showSignatureEditor"
                      style="margin-left: 15px"
                    >
                      取消
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <Dialog
      v-model="showConfirmUpdate"
      type="default"
      title="确认更新"
      content="更新之前确保已保存文件, 更新之后会回到登录界面"
      @cancel="clickCancel"
      @confirm="clickConfirm"
      confirmText="更新确认"
      cancelText="取消"
    ></Dialog>
    <Dialog
      v-model="showUpdateInfoError"
      type="confirm"
      title="更新失败"
      content="邮箱/手机号已被使用, 请换一个"
      @cancel="clickConfirmError"
      @confirm="clickConfirmError"
      confirmText="确认"
    ></Dialog>
    <Dialog
      v-model="showUpdateAvatarStatus"
      type="confirm"
      title="头像更新反馈"
      :content="showUpdateAvatarStatusContent"
      @cancel="closeDialogUpdateAvatarStatus"
      @confirm="closeDialogUpdateAvatarStatus"
    >
    </Dialog>
    <Dialog
      v-model="showUpdateGenderStatus"
      type="confirm"
      title="性别更新反馈"
      :content="showUpdateGenderStatusContent"
      @cancel="closeDialogUpdateGenderStatus"
      @confirm="closeDialogUpdateGenderStatus"
    ></Dialog>
    <LoadingDialog v-if="showLoadingDialog"></LoadingDialog>
  </div>
</template>

<script>
import Dialog from "@/components/dialog/dialog.vue";
import LoadingDialog from "@/components/loading-dialog/loading-dialog.vue";
import { uploadOSS } from "@/server/oss";
export default {
  components: {
    Dialog,
    LoadingDialog,
  },
  props: {
    adminInfo: {
      type: Object,
      default() {
        return {};
      },
    },
  },
  created() {
    this.analysisAction();
  },
  data() {
    return {
      showUpdateInfo: false,
      showConfirmUpdate: false,
      showUpdateInfoError: false,
      showSignatureEditor: false,
      showLoadingDialog: false,
      email: "",
      phone: "",
      name: "",
      gender: "",
      signatureContent: "",
      localAvatar: "",
      showUpdateAvatarStatus: false,
      showUpdateAvatarStatusContent: "",
      showUpdateGenderStatus: false,
      showUpdateGenderStatusContent: "",
      actionList: [],
      actionDo: {
        1: "数据中心",
        2: "个人信息",
        3: "管理员列表",
        4: "用户列表",
        5: "添加管理员",
        6: "文章编辑",
        7: "话题编辑",
        8: "用户反馈",
      },
    };
  },
  methods: {
    clickConfirmError() {
      this.showUpdateInfoError = false;
    },
    clickCancel() {
      this.showUpdateInfo = false;
    },
    clickConfirm() {
      this.showLoadingDialog = true;
      this.$axios({
        method: "get",
        url: "http://localhost:8000/admin/updateInfo",
        params: {
          id: this.adminInfo._id,
          email: this.email,
          phone: this.phone,
          name: this.name,
        },
      })
        .then((result) => {
          const { code } = result.data;
          this.showLoadingDialog = false;
          if (code === 200) {
            this.$store.dispatch("clear_adminInfo");
            this.$store.dispatch("set_isRemember", false);
            this.$router.push({ path: "/" });
          } else if (code === 202) {
            this.showUpdateInfoError = true;
          }
        })
        .catch((err) => {
          this.showLoadingDialog = false;
        });
    },
    updateSignature() {
      this.showLoadingDialog = true;
      this.$axios({
        method: "get",
        url: "http://localhost:8000/admin/updateExplain",
        params: {
          id: this.adminInfo._id,
          explain: this.signatureContent,
        },
      })
        .then((result) => {
          this.showLoadingDialog = false;
          const { code } = result.data;
          if (code === 200) {
            this.adminInfo.explain = this.signatureContent;
            this.$store.dispatch(
              "set_adminInfo",
              JSON.stringify(this.adminInfo)
            );
            this.showSignatureEditor = !this.showSignatureEditor;
          } else {
          }
        })
        .catch((err) => {
          this.showLoadingDialog = false;
        });
    },
    cancelUpdateSignature() {
      this.showSignatureEditor = !this.showSignatureEditor;
    },
    updateInfo() {
      if (this.showUpdateInfo) {
        this.showConfirmUpdate = true;
      } else {
        this.showUpdateInfo = !this.showUpdateInfo;
        this.email = this.adminInfo.admin_email;
        this.phone = this.adminInfo.admin_phone;
        this.name = this.adminInfo.admin_name;
      }
    },
    cancel() {
      this.showUpdateInfo = !this.showUpdateInfo;
    },
    async changeAvatar() {
      this.showLoadingDialog = true;
      const files = document.getElementById("avatar-change");
      if (files.files) {
        const fileLen = files.files;
        if (fileLen.length === 0) return;
        const file = fileLen[0];
        var data = await uploadOSS(file);
        this.updateAvatar(data);
      }
    },
    changeCheck() {
      let avatar = document.getElementById("avatar-change");
      avatar.click();
    },
    updateAvatar(avatar) {
      const params = {
        id: this.adminInfo._id,
        avatar: avatar,
      };
      this.$axios
        .post(
          "http://localhost:8000/admin/update_avatar",
          this.$qs.stringify(params)
        )
        .then((result) => {
          this.showLoadingDialog = false;
          const { code } = result.data;
          if (code === 200) {
            this.adminInfo.avatar = avatar;
            this.$store.dispatch(
              "set_adminInfo",
              JSON.stringify(this.adminInfo)
            );
            this.showUpdateAvatarStatusContent = "更新成功";
            this.showUpdateAvatarStatus = true;
          } else if (code === 500) {
            this.showUpdateAvatarStatusContent = "更新失败, 请稍后再试";
            this.showUpdateAvatarStatus = true;
          }
        })
        .catch((err) => {
          this.showLoadingDialog = false;
          this.showUpdateAvatarStatusContent = "更新失败, 请稍后再试";
          this.showUpdateAvatarStatus = true;
        });
    },
    closeDialogUpdateAvatarStatus() {
      this.showUpdateAvatarStatusContent = "";
      this.showUpdateAvatarStatus = false;
    },
    analysisAction() {
      var action = this.adminInfo.action;
      for (var i = 0; i < action.length; i++) {
        this.actionList.push(this.actionDo[Number(action.charAt(i))]);
      }
    },
    updateGender() {
      var mySelect = document.getElementById("select-option");
      var index = mySelect.selectedIndex;
      var labelSelect = mySelect.options[index].value;
      if (labelSelect === "null") {
        this.showUpdateGenderStatusContent = "请选择性别";
        this.showUpdateGenderStatus = true;
        return;
      }
      this.showLoadingDialog = true;
      this.$axios({
        method: "get",
        url: `http://localhost:8000/admin/update_gender/${this.adminInfo._id}/${labelSelect}`,
      })
        .then((result) => {
          this.showLoadingDialog = false;
          const { data, code } = result.data;
          if (code === 200) {
            this.adminInfo.gender = labelSelect;
            this.$store.dispatch(
              "set_adminInfo",
              JSON.stringify(this.adminInfo)
            );
            this.showUpdateGenderStatusContent = "更新性别成功";
            this.showUpdateGenderStatus = true;
          } else if (code === 500) {
            this.showUpdateGenderStatusContent = "更新性别失败";
            this.showUpdateGenderStatus = true;
          }
        })
        .catch((err) => {
          this.showLoadingDialog = false;
          this.showUpdateGenderStatusContent = "更新性别失败";
          this.showUpdateGenderStatus = true;
        });
    },
    closeDialogUpdateGenderStatus() {
      this.showUpdateGenderStatusContent = "";
      this.showUpdateGenderStatus = false;
    },
  },
};
</script>

<style lang="scss" scoped>
.signature-content-textarea {
  font-size: 10px;
  width: 100%;
  height: 150px;
}
.signature-content-count {
  display: flex;
  justify-content: flex-end;
  font-size: 4px;
  color: #999;
}
</style>