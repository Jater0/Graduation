<template>
  <div class="col-3" style="margin-top: 20px">
    <div class="card-group">
      <div class="card" style="height: 340px">
        <div class="card-body">
          <p style="font-size: 15px; padding-top: 140px" class="text-center">
            {{ title }}
          </p>
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
      default: "没有数据",
    },
  },
};
</script>