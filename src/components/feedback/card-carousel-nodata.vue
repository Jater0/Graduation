<template>
  <div class="carousel slide" data-ride="carousel" :id="'carousel' + idSetter">
    <div class="carousel-inner" role="listbox">
      <div class="carousel-item active">
        <img
          class="w-100 d-block"
          src="../../assets/img/avatars/tym_logo.png"
          alt="Slide Image"
          width="100%"
          height="200px"
        />
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
  },
};
</script>