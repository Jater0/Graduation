<script>
import { mapState } from "vuex";
export default {
  computed: {
    ...mapState(["isLogin", "userid"]),
  },
  onLaunch: function () {
    if (this.isLogin) {
      console.log("App Login");
      this.systemGetUserInfo(this.userid);
    }
  },
  onShow: function () {},
  onHide: function () {
    console.log("App Hide");
  },
  methods: {
    systemGetUserInfo(openId) {
      var that = this;
      uni.request({
        url: "http://localhost:8000/forum/get_user",
        data: {
          id: openId,
        },
        success: (res) => {
          const { data } = res;
          if (data.code === 200) {
            this.$store.dispatch("set_system_userinfo", data.data);
            this.$store.dispatch("set_is_login", true);
          }
        },
      });
    },
  },
};
</script>

<style>
/*每个页面公共css */
@import "components/gaoyia-parse/parse.css";
</style>
