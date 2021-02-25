<template>
  <div>
    <h3 class="text-dark mb-4">管理员列表</h3>
    <div class="card shadow">
      <RoleTable
        :theader="theader"
        :tbody="tbodyCacheData[page]"
        :tid="tid"
        :load="showLoading"
        :count="count"
        :adminInfo="adminInfo"
        @pageChange="pageChange"
        role="admin"
        @manage="manageAdmin"
      ></RoleTable>
    </div>
    <Dialog
      v-model="showError"
      title="错误"
      content="请求列表失败, 请稍后再试"
      @cancel="closeDialogShowError"
      @confirm="closeDialogShowError"
    ></Dialog>
    <AdminCard
      v-model="showAdminCard"
      :adminInfo="targetAdminCard"
      @cancel="closeAdminCard"
      @confirm="updateAdminRoleChange"
    ></AdminCard>
  </div>
</template>

<script>
import RoleTable from "@/components/role-table/role-table.vue";
import Loading from "@/components/loading/loading.vue";
import Dialog from "@/components/dialog/dialog.vue";
import AdminCard from "@/components/admins/admin-card.vue";
export default {
  components: {
    RoleTable,
    Loading,
    Dialog,
    AdminCard,
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
    this.getList(1);
  },
  data() {
    return {
      theader: ["姓名", "邮箱", "性别", "级别"],
      tid: ["admin_name", "admin_email", "gender", "professional"],
      tbodyCacheData: {},
      pageSize: 3,
      page: 1,
      showLoading: false,
      count: 10,
      showError: false,
      showAdminCard: false,
      targetAdminCard: {},
    };
  },
  methods: {
    getList(current) {
      if (this.tbodyCacheData[current]) {
        return;
      }
      this.showLoading = true;
      this.$axios({
        method: "get",
        url: `http://localhost:8000/admin/get_admins/${current}/${this.pageSize}`,
      })
        .then((result) => {
          this.showLoading = false;
          const { data, code } = result.data;
          if (code === 200) {
            var oldList = this.tbodyCacheData[current] || [];
            oldList.push(...data["admins"]);
            this.count = data["count"];
            this.$set(this.tbodyCacheData, current, oldList);
            this.$forceUpdate();
          } else if (code === 404) {
          } else if (code === 500) {
            this.showError = true;
          }
        })
        .catch((err) => {
          this.showLoading = false;
          this.showError = true;
        });
    },
    pageChange(data) {
      this.page = data;
      this.getList(data);
    },
    closeDialogShowError() {
      this.showError = false;
    },
    manageAdmin(data) {
      this.targetAdminCard = data;
      this.showAdminCard = true;
    },
    closeAdminCard() {
      this.targetAdminCard = {};
      this.showAdminCard = false;
    },
    updateAdminRoleChange(data) {
      console.log(data);
      this.targetAdminCard = {};
      this.showAdminCard = false;
    },
  },
};
</script>