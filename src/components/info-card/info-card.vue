<template>
  <div class="col-md-3 col-xl-3 mb-3">
    <div class="card shadow py-2" :class="colorObject[color][0]">
      <div class="card-body">
        <div class="row align-items-center no-gutters">
          <div class="col mr-2">
            <div
              class="text-uppercase text-primary font-weight-bold text-xs mb-1"
              :class="colorObject[color][1]"
            >
              <span>{{ title }}</span>
            </div>
            <div class="text-dark font-weight-bold h5 mb-0">
              <span>{{ values }}</span>
            </div>
          </div>
          <div class="col-auto">
            <i class="fa-2x text-gray-300" :class="iconsType"></i>
          </div>
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
    values: {
      type: String,
      default: "0",
    },
    color: {
      type: String,
      default: "primary",
    },
    icons: {
      type: String,
      default: "fa-bookmark",
    },
    isDark: {
      type: String,
      default: "far",
    },
  },
  data() {
    return {
      colorObject: {
        primary: ["border-left-primary", "text-primary"],
        success: ["border-left-success", "text-success"],
        info: ["border-left-info", "text-info"],
        warning: ["border-left-warning", "text-warning"],
        danger: ["border-left-danger", "text-danger"],
        secondary: ["border-left-secondary", "text-secondary"],
      },
      iconsType: "far fa-bookmark",
    };
  },
  created() {
    this.iconsType = this.icons + " " + this.isDark;
  },
};
</script>