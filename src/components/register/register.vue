<template>
  <div>
    <div class="text-center">
      <h4 class="text-dark mb-4">添加新的管理员</h4>
    </div>
    <div class="card">
      <div class="card-body">
        <div class="col-6">
          <div class="p-5">
            <div class="user">
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
  </div>
</template>

<script>
import { getID } from "@/utils/index.js";
export default {
  props: {
    adminInfo: {
      type: Object,
      default() {
        return {};
      },
    },
  },
  data() {
    return {
      adminName: "",
      email: "",
      password: "",
      passwordCheck: "",
    };
  },
  methods: {
    register() {
      if (
        this.password === "" ||
        this.passwordCheck === "" ||
        this.email === "" ||
        this.adminName === ""
      ) {
        return;
      }
      if (this.password !== this.passwordCheck) {
        return;
      }
      var id = "adm" + getID(9);
      this.$axios({
        method: "get",
        url: "http://localhost:8000/admin/create_admin",
        params: {
          id: id,
          name: this.adminName,
          email: this.email,
          password: this.password,
          level: this.adminInfo.professional + 1,
        },
      })
        .then((result) => {
          const { data, code } = result.data;
          if (code === 200) {
            console.log("Success");
          } else if (code === 202) {
            console.log("Same Email");
          } else if (code === 500) {
            console.log("System Error");
          }
        })
        .catch((err) => {
          console.log("System Error");
        });
    },
  },
};
</script>