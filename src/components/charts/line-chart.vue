<template>
  <div class="col-lg-7 col-xl-8">
    <div class="card shadow mb-4">
      <div
        class="card-header d-flex justify-content-between align-items-center"
      >
        <h6 class="text-primary font-weight-bold m-0">用户浏览总量</h6>
      </div>
      <div class="card-body">
        <div id="line-card" :style="{ width: '500px', height: '450px' }"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: "用户浏览总量",
    },
    lineValue: {
      type: Object,
      default() {
        return {};
      },
    },
  },
  mounted() {
    this.drawLineChart(this.lineValue);
  },
  methods: {
    drawLineChart(data) {
      let pieChart = this.$echarts.init(document.getElementById("line-card"));
      pieChart.setOption({
        xAxis: {
          type: "category",
          data: data["x"],
        },
        yAxis: {
          type: "value",
        },
        series: [
          {
            data: data["y"],
            type: "line",
            smooth: true,
          },
        ],
      });
    },
  },
};
</script>