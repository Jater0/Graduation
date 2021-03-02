<script>
import { mapState } from "vuex";
export default {
  computed: {
    ...mapState(["isLogin", "userid", 'browseAll']),
  },
  onLaunch: function () {
    if (this.isLogin) {
      this.systemGetUserInfo(this.userid);
    }
  },
  onShow: function () {
		this.$store.dispatch('set_browse_all', (new Date()).getTime())
	},
  onHide: function () {
		setTimeout(() => {
			this.updateBrowseAll((new Date()).getTime() - this.browseAll)
		}, 3000)
  },
  methods: {
    systemGetUserInfo(openId) {
			uni.showLoading()
      var that = this;
      uni.request({
				url: this.$api.address + `forum/get_user/${openId}`,
        success: (res) => {
					uni.hideLoading()
          const { data, code } = res.data;
          if (code === 200) {
            this.$store.dispatch("set_system_userinfo", data);
            this.$store.dispatch("set_is_login", true);
          } else if (code === 500) {
						uni.showToast({
							title: '获取用户信息失败, 请稍后再试',
							icon: 'none'
						})
					}
        },
				fail: (err) => {
					uni.showToast({
						title: '获取用户信息失败, 请稍后再试',
						icon: 'none'
					})
				}
      });
    },
		convertToDate(time) {
			var total = time / 1000
			console.log(total);
		},
		updateBrowseAll(timestamp) {
			var time = Math.round(timestamp/1000)
			if (time < 3) return
			var userid = this.isLogin?this.userid:'guest'
			uni.request({
				method: 'POST',
				url: this.$api.address + `data/browse_all/${this.userid}/${time}`,
				success: (res) => {
					const {data, code} = res.data
					if (code === 200) {
						console.log("SUCCESS");
					}
				}
			})
		}
  },
};
</script>

<style>
/*每个页面公共css */
@import "components/gaoyia-parse/parse.css";
</style>
