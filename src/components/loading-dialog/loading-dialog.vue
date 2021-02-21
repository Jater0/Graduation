<template>
  <div class="dialog" v-show="showMask">
    <div class="dialog-container">
      <Loading></Loading>
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
    value: {},
  },
  data() {
    return {
      showMask: false,
    };
  },
  mounted() {
    this.showMask = this.value;
  },
  watch: {
    value(newVal) {
      this.showMask = newVal;
    },
  },
};
</script>

<style lang="scss" scoped>
.dialog {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0, 0, 0, 0.6);
  z-index: 9999;
  .dialog-container {
    width: 500px;
    height: 380px;
    background: #ffffff;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    border-radius: 8px;
    position: relative;
  }
}
</style>