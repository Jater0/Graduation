<template>
  <div class="col-lg-5 col-xl-4">
    <div class="card shadow mb-4">
      <div
        class="card-header d-flex justify-content-between align-items-center"
      >
        <h6 class="text-primary font-weight-bold m-0">{{ title }}</h6>
      </div>
      <div class="card-body">
        <div id="pie-chart" :style="{ width: '300px', height: '300px' }"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: "Top5 标签",
    },
    pieValue: {
      type: Array,
      default() {
        return [];
      },
    },
  },
  data() {
    return {};
  },
  mounted() {
    this.drawLine(this.pieValue);
  },
  methods: {
    drawLine(data) {
      let pieChart = this.$echarts.init(document.getElementById("pie-chart"));
      pieChart.setOption({
        tooltip: {
          trigger: "item",
        },
        legend: {
          orient: "horizontal",
          left: "left",
        },
        series: [
          {
            name: "访问来源",
            type: "pie",
            radius: "50%",
            data: data,
            emphasis: {
              itemStyle: {
                shadowBlur: 10,
                shadowOffsetX: 0,
                shadowColor: "rgba(0, 0, 0, 0.5)",
              },
            },
          },
        ],
      });
    },
  },
};
</script>