<template>
  <div class="col-3" style="margin-top: 20px" @click="more">
    <div class="card-group">
      <div class="card">
        <div class="card-body">
          <div class="carousel slide" data-ride="carousel">
            <div class="carousel-inner" role="listbox">
              <div class="carousel-item active">
                <img
                  class="w-100 d-block"
                  src="../../assets/img/plus.png"
                  width="100%"
                  height="250px"
                  alt="Slide Image"
                  v-if="!isLoading"
                />
                <img
                  class="w-100 d-block"
                  src="../../assets/loading/Rolling-1s-200px.gif"
                  width="100%"
                  height="250px"
                  alt="Slide Image"
                  v-if="isLoading"
                />
              </div>
            </div>
          </div>
          <p v-if="!isLoading" class="more-data text-center">更多数据</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    idSetter: {
      type: Number,
      default: 0,
    },
    isLoading: {
      type: Boolean,
      default: false,
    },
  },
  methods: {
    more() {
      this.$emit("more");
    },
  },
};
</script>

<style lang="scss" scoped>
.more-data {
  font-size: 15px;
}
</style>