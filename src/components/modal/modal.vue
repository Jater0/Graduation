<template>
  <div
    class="modal fade"
    :class="isShow ? 'show' : ''"
    role="dialog"
    tabindex="-1"
  >
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title">{{ title }}</h4>
          <button
            type="button"
            class="close"
            data-dismiss="modal"
            aria-label="Close"
            @click="closeModal"
          >
            <span aria-hidden="true">×</span>
          </button>
        </div>
        <div class="modal-body">
          <p>{{ content }}</p>
        </div>
        <div class="modal-footer">
          <button
            class="btn btn-light"
            type="button"
            data-dismiss="modal"
            @click="closeModal"
          >
            Close</button
          ><button class="btn btn-primary" type="button" @click="publish">
            Save
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: "",
    },
    content: {
      type: String,
      default: "",
    },
    isShow: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    closeModal() {
      this.isShow = false;
      this.$emit("show", false);
    },
    publish() {
      this.isShow = false;
      this.$emit("show", false);
      this.$emit("publish");
    },
  },
};
</script>