<template>
  <div>
    <h3 class="text-dark mb-4">管理员列表</h3>
    <div class="card shadow">
      <RoleTable
        :theader="theader"
        :tbody="tbody"
        :tid="tid"
        v-if="tbody.length !== 0"
      ></RoleTable>
      <Loading v-if="tbody.length === 0"></Loading>
    </div>
  </div>
</template>

<script>
import RoleTable from "@/components/role-table/role-table.vue";
import Loading from "@/components/loading/loading.vue";
export default {
  components: {
    RoleTable,
    Loading,
  },
  created() {
    this.getList();
  },
  data() {
    return {
      theader: ["姓名", "邮箱", "性别", "级别"],
      tid: ["admin_name", "admin_email", "gender", "professional"],
      tbody: [],
    };
  },
  methods: {
    getList() {
      this.$axios({
        method: "get",
        url: "http://localhost:8000/admin/get_admins",
      })
        .then((result) => {
          const { data, code } = result.data;
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