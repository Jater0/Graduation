<template>
  <div>
    <div class="text-center">
      <h4 class="text-dark mb-4">添加新的管理员</h4>
    </div>
    <div class="card">
      <div class="card-body">
        <div class="col-6 m-auto">
          <div class="p-5">
            <div class="user">
              <label for="topic-type" style="margin-right: 10px">
                <strong>选择管理员权限:</strong>
              </label>
              <select
                id="select-option"
                class="form-control form-control-sm custom-select custom-select-sm"
                style="width: 50%; margin-bottom: 20px"
              >
                <option value="null" selected>-- 选择管理员权限 --</option>
                <option
                  :value="item"
                  v-for="(item, index) in roleList"
                  :key="index"
                >
                  {{ item }}
                </option>
              </select>
              <br />
              <div class="form-group">
                <input
                  class="form-control form-control-user"
                  type="text"
                  placeholder="请输入管理员姓名/昵称"
                  name="adminName"
                  v-model="adminName"
                />
              </div>
              <div class="form-group">
                <input
                  class="form-control form-control-user"
                  type="email"
                  aria-describedby="emailHelp"
                  placeholder="请输入管理员邮箱"
                  name="email"
                  v-model="email"
                />
              </div>
              <div class="form-group">
                <input
                  class="form-control form-control-user"
                  type="password"
                  placeholder="请输入密码"
                  name="password"
                  v-model="password"
                />
              </div>
              <div class="form-group">
                <input
                  class="form-control form-control-user"
                  type="password"
                  placeholder="请再次输入密码确认"
                  name="password"
                  v-model="passwordCheck"
                />
              </div>
              <button
                class="btn btn-primary btn-block text-white btn-user"
                type="default"
                @click="register"
              >
                创建
              </button>
              <hr />
            </div>
          </div>
        </div>
      </div>
    </div>
    <Dialog
      v-model="showNullCheck"
      title="请填写/选择"
      :content="showNullCheckContent"
      type="confirm"
      @cancel="closeDialogShowNullCheck"
      @confirm="closeDialogShowNullCheck"
    ></Dialog>
    <Dialog
      v-model="showPasswordCheck"
      title="请填写/选择"
      content="密码不一致"
      type="confirm"
      @cancel="closeDialogShowPasswordCheck"
      @confirm="closeDialogShowPasswordCheck"
    ></Dialog>
    <LoadingDialog v-model="showLoading"></LoadingDialog>
    <Dialog
      v-model="showRegisterStatus"
      title="注册管理员反馈"
      :content="showRegisterStatusContent"
      type="confirm"
      @cancel="closeDialogShowRegisterStatus"
      @confirm="closeDialogShowRegisterStatus"
    ></Dialog>
  </div>
</template>

<script>
import { getID } from "@/utils/index.js";
import { mapState } from "vuex";
import Dialog from "@/components/dialog/dialog.vue";
import LoadingDialog from "@/components/loading-dialog/loading-dialog.vue";
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
  computed: {
    ...mapState(["roleIdListCache"]),
  },
  created() {
    if (this.roleIdListCache.length === 0) {
      this.getRoleIdList();
    } else {
      this.roleList = this.roleIdListCache;
    }
  },
  data() {
    return {
      adminName: "",
      email: "",
      password: "",
      passwordCheck: "",
      roleListDefaultInApp: [0, 1, 2],
      roleList: [],
      showLoading: false,
      showNullCheck: false,
      showNullCheckContent: "",
      showPasswordCheck: false,
      showRegisterStatus: false,
      showRegisterStatusContent: "",
    };
  },
  methods: {
    register() {
      var mySelect = document.getElementById("select-option");
      var index = mySelect.selectedIndex;
      var labelSelect = mySelect.options[index].value;
      if (labelSelect === "null") {
        this.showNullCheckContent = "请选择管理员的权限等级";
        this.showNullCheck = true;
        return;
      }
      if (this.adminName === "") {
        this.showNullCheckContent = "请输入管理员名称";
        this.showNullCheck = true;
        return;
      }
      if (this.email === "") {
        this.showNullCheckContent = "请输入管理员邮箱";
        this.showNullCheck = true;
        return;
      }
      if (this.password === "" || this.passwordCheck === "") {
        this.showNullCheckContent = "请输入管理员账号密码";
        this.showNullCheck = true;
        return;
      }
      if (this.password !== this.passwordCheck) {
        return;
      }
      this.showLoading = true;
      var id = "adm" + getID(9);
      var params = {
        id: id,
        name: this.adminName,
        email: this.email,
        password: this.password,
        level: labelSelect,
      };
      this.$axios
        .post(
          "http://localhost:8000/admin/create_admin",
          this.$qs.stringify(params)
        )
        .then((result) => {
          this.showLoading = false;
          const { data, code } = result.data;
          if (code === 200) {
            this.showRegisterStatusContent = "注册成功";
            this.showRegisterStatus = true;
            this.adminName = "";
            this.email = "";
            this.password = "";
            this.passwordCheck = "";
            mySelect.selectedIndex = 0;
          } else if (code === 202) {
            this.showRegisterStatusContent =
              "注册失败, 输入的email已存在, 请换一个";
            this.showRegisterStatus = true;
          } else if (code === 500) {
            this.showRegisterStatusContent = "注册失败, 系统繁忙, 请稍后再试";
            this.showRegisterStatus = true;
          }
        })
        .catch((err) => {
          this.showLoading = false;
          this.showRegisterStatusContent = "注册失败, 系统繁忙, 请稍后再试";
          this.showRegisterStatus = true;
        });
    },
    getRoleIdList() {
      this.$axios({
        method: "get",
        url: "http://localhost:8000/admin/get_list/role_id",
      })
        .then((result) => {
          const { code, data } = result.data;
          if (code === 200) {
            this.roleList = data;
            this.$store.dispatch("set_roleId_list_cache", data);
          } else if (code === 500) {
            this.roleList = this.roleListDefaultInApp;
          }
        })
        .catch((err) => {
          this.roleList = this.roleListDefaultInApp;
        });
    },
    closeDialogShowNullCheck() {
      this.showNullCheckContent = "";
      this.showNullCheck = false;
    },
    closeDialogShowPasswordCheck() {
      this.showPasswordCheck = false;
    },
    closeDialogShowRegisterStatus() {
      this.showRegisterStatusContent = "";
      this.showRegisterStatus = false;
    },
  },
};
</script>