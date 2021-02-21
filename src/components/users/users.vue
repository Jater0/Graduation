<template>
  <div>
    <h3 class="text-dark mb-4">用户列表</h3>
    <div class="card shadow">
      <RoleTable :theader="theader" :tbody="tbody" :tid="tid"></RoleTable>
      <Loading v-if="tbody.length === 0"></Loading>
    </div>
  </div>
</template>

<script>
import RoleTable from "@/components/role-table/role-table.vue";
export default {
  components: {
    RoleTable,
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
      tbody: [],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.$axios({
        method: "get",
        url: "http://localhost:8000/admin/get_users",
        params: {
          page: 1,
          size: 10,
        },
      })
        .then((result) => {
          const { code, data } = result.data;
          if (code === 200) {
            this.tbody = data;
          } else if (code === 404) {
          } else if (code === 500) {
          }
        })
        .catch((err) => {});
    },
  },
};
</script>