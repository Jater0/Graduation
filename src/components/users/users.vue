<template>
  <div>
    <h3 class="text-dark mb-4">用户列表</h3>
    <div class="card shadow">
      <RoleTable
        :theader="theader"
        :tbody="tbodyCacheData[page]"
        :tid="tid"
        :load="showLoading"
        :count="count"
        @pageChange="pageChange"
        role="user"
      ></RoleTable>
    </div>
    <Dialog
      v-model="showError"
      title="错误"
      content="请求列表失败, 请稍后再试"
      @cancel="closeDialogShowError"
      @confirm="closeDialogShowError"
    ></Dialog>
  </div>
</template>

<script>
import RoleTable from "@/components/role-table/role-table.vue";
import Loading from "@/components/loading/loading.vue";
import Dialog from "@/components/dialog/dialog.vue";
export default {
  components: {
    RoleTable,
    Loading,
    Dialog,
  },
  data() {
    return {
      theader: ["姓名", "ID", "邮箱", "性别", "粉丝", "积分"],
      tid: [
        "author_name",
        "_id",
        "author_email",
        "gender",
        "fans_count",
        "integral_count",
      ],
      tbodyCacheData: {},
      pageSize: 10,
      page: 1,
      showLoading: false,
      count: 10,
      showError: false,
    };
  },
  created() {
    this.getList(1);
  },
  methods: {
    getList(current) {
      if (this.tbodyCacheData[current]) {
        return;
      }
      this.showLoading = true;
      this.$axios({
        method: "get",
        url: `http://localhost:8000/admin/get_users/${current}/${this.pageSize}`,
      })
        .then((result) => {
          this.showLoading = false;
          const { code, data } = result.data;
          if (code === 200) {
            var oldList = this.tbodyCacheData[current] || [];
            oldList.push(...data["users"]);
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
  },
};
</script>