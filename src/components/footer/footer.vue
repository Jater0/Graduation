<template>
  <footer class="bg-white sticky-footer">
    <div class="container my-auto">
      <div class="text-center my-auto copyright">
        <span>{{ footerValue }}</span>
      </div>
    </div>
  </footer>
</template>

<script>
export default {
  data() {
    return {
      footerValue: "Copyright © Brand Jater-Forum-Admin ",
    };
  },
  methods: {
    getYear() {
      let date = new Date();
      let year = date.getFullYear();
      this.footerValue = this.footerValue + year;
    },
  },
  created() {
    this.getYear();
  },
};
</script>