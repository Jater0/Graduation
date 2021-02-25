<template>
  <div class="carousel slide" data-ride="carousel" :id="'carousel' + idSetter">
    <div class="carousel-inner" role="listbox">
      <div
        class="carousel-item"
        :class="index === 0 ? 'active' : ''"
        v-for="(item, index) in covers"
        :key="index"
      >
        <img
          class="w-100 d-block"
          :src="item"
          width="100%"
          height="200px"
          alt="Slide Image"
        />
      </div>
    </div>
    <div v-if="covers.length > 1">
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
    <ol class="carousel-indicators" v-if="covers.length > 1">
      <li
        v-for="(item, index) in covers"
        :key="index"
        :data-target="'#carousel' + idSetter"
        :data-slide-to="index"
        :class="index === 0 ? 'active' : ''"
      ></li>
    </ol>
  </div>
</template>

<script>
export default {
  props: {
    covers: {
      type: Array,
      default() {
        return [];
      },
    },
    idSetter: {
      type: Number,
      default: 0,
    },
  },
};
</script>