<template>
  <div class="loading"></div>
</template>

<script>
export default {
  name: "Loading",
};
</script>

<style lang="scss" scoped>
.loading {
  align-self: center;
  position: relative;
  background: url("~@/assets/loading/Rolling-1s-200px.gif") center no-repeat#fff;
  height: 300px;
  width: 300px;
}
</style>