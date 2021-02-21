<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>
import Login from "@/views/Login.vue";
import Index from "@/views/Index.vue";
export default {
  components: {
    Login,
    Index,
  },
};
</script>

<style lang="scss">
</style>
