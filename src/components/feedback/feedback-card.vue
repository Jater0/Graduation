<template>
  <div class="col-lg-3 mb-4">
    <div class="card-group">
      <div class="card">
        <div class="card-body">
          <div
            class="carousel slide"
            data-ride="carousel"
            :id="'carousel' + idSetter"
          >
            <div class="carousel-inner" role="listbox">
              <div
                class="carousel-item"
                :class="index === 0 ? 'active' : ''"
                v-for="(item, index) in detail.covers"
                :key="index"
              >
                <img class="w-100 d-block" :src="item" alt="Slide Image" />
              </div>
            </div>
            <div>
              <a
                class="carousel-control-prev"
                :href="'#carousel' + idSetter"
                role="button"
                data-slide="prev"
                ><span class="carousel-control-prev-icon"></span
                ><span class="sr-only">Previous</span></a
              ><a
                class="carousel-control-next"
                :href="'#carousel' + idSetter"
                role="button"
                data-slide="next"
                ><span class="carousel-control-next-icon"></span
                ><span class="sr-only">Next</span></a
              >
            </div>
            <ol class="carousel-indicators">
              <li
                v-for="(item, index) in detail.covers"
                :key="index"
                :data-target="'#carousel' + idSetter"
                :data-slide-to="index"
                :class="index === 0 ? 'active' : ''"
              ></li>
            </ol>
          </div>
          <h4 class="card-title">{{ detail.user_name }}</h4>
          <p class="card-text">
            {{ detail.content }}
          </p>
          <button
            class="btn btn-primary feedback-button"
            :class="isFix ? 'btn-secondary' : 'btn-primary'"
            type="button"
            @click="fixBug"
          >
            {{ isFix ? "已处理" : "点击完成处理" }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    detail: {
      type: Object,
      default() {
        return {};
      },
    },
    idSetter: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      isFix: false,
    };
  },
  created() {
    this.isFix = this.detail.isReply;
  },
  methods: {
    fixBug() {
      if (this.isFix === true) {
        return;
      }
      this.isFix = !this.isFix;
    },
  },
};
</script>

<style lang="scss" scoped>
.card-title {
  font-size: 20px;
}
.card-text {
  font-size: 10px;
}
.feedback-button {
  font-size: 10px;
}
</style>