<template>
  <div class="bg-gradient-primary">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-md-9 col-lg-12 col-xl-10">
          <div class="card shadow-lg o-hidden border-0 my-5">
            <div class="card-body p-0">
              <div class="row">
                <div class="col-lg-6 d-none d-lg-flex">
                  <div
                    class="flex-grow-1 bg-login-image"
                    :style="{ 'background-image': 'url(' + urlIcon + ')' }"
                  ></div>
                </div>
                <div class="col-lg-6">
                  <div class="p-5">
                    <div class="text-center">
                      <h4 class="text-dark mb-4">Objective-Admin控制台</h4>
                    </div>
                    <div class="user" id="login_form">
                      <div class="form-group">
                        <input
                          class="form-control form-control-user"
                          type="text"
                          id="exampleInputEmail"
                          aria-describedby="emailHelp"
                          placeholder="请输入管理员邮箱/手机号"
                          name="email"
                          v-model="email"
                        />
                      </div>
                      <div class="form-group">
                        <input
                          class="form-control form-control-user"
                          type="password"
                          id="exampleInputPassword"
                          placeholder="请输入密码"
                          name="password"
                          v-model="password"
                        />
                      </div>
                      <div class="form-group">
                        <div class="custom-control custom-checkbox small">
                          <div class="form-check">
                            <input
                              class="form-check-input custom-control-input"
                              type="checkbox"
                              :checked="remember"
                            />
                            <label
                              class="form-check-label custom-control-label"
                              for="formCheck-1"
                              @click="doRemember"
                              >Remember Me</label
                            >
                          </div>
                        </div>
                      </div>
                      <button
                        class="btn btn-primary btn-block text-white btn-user"
                        type="submit"
                        @click="loginCheck"
                      >
                        Login
                      </button>
                      <hr />
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  name: "Login",
  computed: {
    ...mapState(["isRemember", "adminInfo"]),
  },
  data() {
    return {
      icon: "logo.png",
      urlIcon: "",
      email: "",
      password: "",
      isLogin: 0,
      remember: null,
    };
  },
  created() {
    this.urlIcon = require("../assets/" + this.icon);
  },
  mounted() {
    console.log(this.adminInfo);
    this.remember = this.isRemember;
    this.remember
      ? (this.email = JSON.parse(this.adminInfo).admin_email)
      : (this.email = "");
  },
  methods: {
    loginCheck() {
      let email = this.email;
      let password = this.password;
      this.$axios
        .get(
          "http://localhost:8000/admin/login?account=" +
            email +
            "&password=" +
            password
        )
        .then((result) => {
          const { data, code } = result.data;
          if (code === 200) {
            if (data === null) {
              console.log("no account");
            } else {
              this.$store.dispatch("set_adminInfo", JSON.stringify(data));
              this.$store.dispatch("set_isRemember", this.remember);
              this.$router.push({ path: "/index" });
            }
          } else {
            console.log("system error");
          }
        })
        .catch((err) => {
          console.log("system error");
        });
    },
    doRemember() {
      this.remember = !this.remember;
    },
  },
};
</script>

<style scoped lang="scss">
</style>
