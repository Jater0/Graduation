<template>
  <div class="card-body">
    <div class="row">
      <div class="text-md-right" style="margin-left: 20px">
        <label
          ><input
            type="search"
            class="form-control form-control-sm"
            aria-controls="dataTable"
            placeholder="Search"
        /></label>
      </div>
    </div>
    <div
      class="table-responsive table mt-2"
      id="dataTable"
      role="grid"
      aria-describedby="dataTable_info"
    >
      <table class="table my-0" id="dataTable">
        <thead>
          <tr>
            <th v-for="(item, index) in theader" :key="index">{{ item }}</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(item, index) in tbody" :key="index">
            <td v-for="(item2, index2) in tid" :key="index2">
              <img
                v-if="index2 === 0"
                class="rounded-circle mr-2"
                width="30"
                height="30"
                :src="item.avatar"
              />{{ item[item2] }}
            </td>
          </tr>
        </tbody>
        <tfoot>
          <tr>
            <td v-for="(item, index) in theader" :key="index">
              <strong>{{ item }}</strong>
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
          <li class="page-item disabled">
            <a class="page-link" aria-label="Previous"
              ><span aria-hidden="true">«</span></a
            >
          </li>
          <li class="page-item active">
            <a class="page-link">1</a>
          </li>
          <li class="page-item"><a class="page-link">2</a></li>
          <li class="page-item"><a class="page-link">3</a></li>
          <li class="page-item">
            <a class="page-link" aria-label="Next"
              ><span aria-hidden="true">»</span></a
            >
          </li>
        </ul>
      </nav>
    </div>
  </div>
</template>

<script>
export default {
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
  },
  data() {
    return {
      optionValue: 10,
    };
  },
  methods: {
    publish(data) {
      console.log(data);
    },
  },
};
</script>