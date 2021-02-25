<template>
  <div class="card-body">
    <div class="row">
      <div class="text-md-right" style="margin-left: 20px">
        <label>
          <input
            type="search"
            class="form-control form-control-sm"
            aria-controls="dataTable"
            placeholder="Search"
          />
        </label>
      </div>
    </div>
    <div
      class="table-responsive table mt-2"
      id="dataTable"
      role="grid"
      aria-describedby="dataTable_info"
    >
      <Loading v-if="load"></Loading>
      <table v-else class="table my-0" id="dataTable">
        <thead>
          <tr>
            <th v-for="(item, index) in theader" :key="index">{{ item }}</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in tbody" :key="index">
            <td v-for="(item2, index2) in tid" :key="index2">
              <img
                v-if="index2 === 0 && item.avatar !== null"
                class="rounded-circle mr-2"
                width="30"
                height="30"
                :src="item.avatar"
              />
              <img
                v-if="index2 === 0 && item.avatar === null && role === 'admin'"
                class="rounded-circle mr-2"
                width="30"
                height="30"
                src="https://objective-forum-oss.oss-cn-shenzhen.aliyuncs.com/forum-avatar/adminDefault.png"
              />
              {{ item[item2] }}
            </td>
            <td>
              <button
                v-if="role === 'admin'"
                type="default"
                class="btn btn-primary btn-sm"
                @click="manageAdmin(item)"
              >
                查看
              </button>
              <button
                v-if="
                  role === 'admin' && item.professional > adminInfo.professional
                "
                type="default"
                style="margin-left: 10px"
                class="btn btn-danger btn-sm"
              >
                删除
              </button>
            </td>
          </tr>
        </tbody>
        <tfoot>
          <tr>
            <td v-for="(item, index) in theader" :key="index">
              <strong>{{ item }}</strong>
            </td>
            <td>
              <strong>操作</strong>
            </td>
          </tr>
        </tfoot>
      </table>
    </div>
    <div class="row">
      <nav
        class="d-lg-flex justify-content-lg-end dataTables_paginate paging_simple_numbers"
        style="margin-left: 20px"
      >
        <ul class="pagination">
          <li class="page-item disabled" v-if="localPage !== 1" @click="prev">
            <a class="page-link" aria-label="Previous"
              ><span aria-hidden="true">«</span></a
            >
          </li>
          <li class="page-item active">
            <a class="page-link">{{ localPage }}</a>
          </li>
          <li class="page-item" @click="next" v-if="localPage !== endPage">
            <a class="page-link" aria-label="Next">
              <span aria-hidden="true">»</span>
            </a>
          </li>
        </ul>
      </nav>
    </div>
  </div>
</template>

<script>
import Loading from "@/components/loading/loading.vue";
export default {
  components: {
    Loading,
  },
  props: {
    theader: {
      type: Array,
      default() {
        return [];
      },
    },
    tbody: {
      type: Array,
      default() {
        return [];
      },
    },
    tid: {
      type: Array,
      default() {
        return [];
      },
    },
    role: {
      type: String,
      default: "admin",
    },
    load: {
      type: Boolean,
      default: false,
    },
    count: {
      type: Number,
      default: 10,
    },
    adminInfo: {
      type: Object,
      default() {
        return {};
      },
    },
  },
  created() {
    this.endPage = Math.ceil(this.count / 3);
  },
  data() {
    return {
      localPage: 1,
      endPage: 1,
    };
  },
  methods: {
    next() {
      this.localPage++;
      this.$emit("pageChange", this.localPage);
    },
    prev() {
      this.localPage--;
      this.$emit("pageChange", this.localPage);
    },
    manageAdmin(data) {
      this.$emit("manage", data);
    },
  },
};
</script>