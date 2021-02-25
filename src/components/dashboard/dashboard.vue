<template>
  <div>
    <div class="d-sm-flex justify-content-between align-items-center mb-4">
      <h3 class="text-dark mb-0">数据中心</h3>
      <a
        class="btn btn-primary btn-sm d-none d-sm-inline-block text-white"
        role="button"
        ><i class="fas fa-download fa-sm text-white-50"></i>&nbsp;下载报告</a
      >
    </div>
    <small
      class="form-text text-muted"
      style="text-align: center"
      @click="isMoreCard"
      >{{ showMoreCard ? "点击收起更多卡片数据" : "点击加载更多卡片数据" }}
      <i :class="showMoreCard ? 'fas fa-times' : 'fas fa-undo'"></i>
    </small>
    <div class="row">
      <InfoCard
        :title="'昨天新增BLOG'"
        :values="'100'"
        :color="'primary'"
        :icons="'fa-bookmark'"
      ></InfoCard>
      <InfoCard
        :title="'今日新增BLOG'"
        :values="'100'"
        :color="'success'"
        :icons="'fa-bookmark'"
        :isDark="'fas'"
      ></InfoCard>
      <InfoCard
        :title="'一周新增用户'"
        :values="'100'"
        :color="'info'"
        :icons="'fa-address-book'"
      ></InfoCard>
      <InfoCard
        :title="'一月新增话题'"
        :values="'100'"
        :color="'warning'"
        :icons="'fa-comments'"
      ></InfoCard>
      <InfoCard
        v-if="showMoreCard"
        :title="'昨天新增BLOG'"
        :values="'100'"
        :color="'primary'"
        :icons="'fa-bookmark'"
      ></InfoCard>
      <InfoCard
        v-if="showMoreCard"
        :title="'今日新增BLOG'"
        :values="'100'"
        :color="'success'"
        :icons="'fa-bookmark'"
        :isDark="'fas'"
      ></InfoCard>
      <InfoCard
        v-if="showMoreCard"
        :title="'一周新增用户'"
        :values="'100'"
        :color="'info'"
        :icons="'fa-address-book'"
      ></InfoCard>
      <InfoCard
        v-if="showMoreCard"
        :title="'一月新增话题'"
        :values="'100'"
        :color="'warning'"
        :icons="'fa-comments'"
      ></InfoCard>
    </div>
    <div class="row">
      <Histogram
        :title="'用户浏览总量'"
        :histogramValue="histogramValue"
        idSetter="histogram-1"
      ></Histogram>
      <PieChart
        :title="'Top5 标签'"
        :pieValue="pipValue"
        idSetter="pie-chart-1"
      ></PieChart>
    </div>
    <div class="row">
      <div class="col-lg-6 mb-4">
        <div class="card shadow mb-4">
          <div class="card-header py-3">
            <h6 class="text-primary font-weight-bold m-0">Projects</h6>
          </div>
          <div class="card-body">
            <h4 class="small font-weight-bold">
              Server migration<span class="float-right">20%</span>
            </h4>
            <div class="progress mb-4">
              <div
                class="progress-bar bg-danger"
                aria-valuenow="20"
                aria-valuemin="0"
                aria-valuemax="100"
                style="width: 20%"
              >
                <span class="sr-only">20%</span>
              </div>
            </div>
            <h4 class="small font-weight-bold">
              Sales tracking<span class="float-right">40%</span>
            </h4>
            <div class="progress mb-4">
              <div
                class="progress-bar bg-warning"
                aria-valuenow="40"
                aria-valuemin="0"
                aria-valuemax="100"
                style="width: 40%"
              >
                <span class="sr-only">40%</span>
              </div>
            </div>
            <h4 class="small font-weight-bold">
              Customer Database<span class="float-right">60%</span>
            </h4>
            <div class="progress mb-4">
              <div
                class="progress-bar bg-primary"
                aria-valuenow="60"
                aria-valuemin="0"
                aria-valuemax="100"
                style="width: 60%"
              >
                <span class="sr-only">60%</span>
              </div>
            </div>
            <h4 class="small font-weight-bold">
              Payout Details<span class="float-right">80%</span>
            </h4>
            <div class="progress mb-4">
              <div
                class="progress-bar bg-info"
                aria-valuenow="80"
                aria-valuemin="0"
                aria-valuemax="100"
                style="width: 80%"
              >
                <span class="sr-only">80%</span>
              </div>
            </div>
            <h4 class="small font-weight-bold">
              Account setup<span class="float-right">Complete!</span>
            </h4>
            <div class="progress mb-4">
              <div
                class="progress-bar bg-success"
                aria-valuenow="100"
                aria-valuemin="0"
                aria-valuemax="100"
                style="width: 100%"
              >
                <span class="sr-only">100%</span>
              </div>
            </div>
          </div>
        </div>
        <div class="card shadow mb-4">
          <div class="card-header py-3">
            <h6 class="text-primary font-weight-bold m-0">Todo List</h6>
          </div>
          <ul class="list-group list-group-flush">
            <li class="list-group-item">
              <div class="row align-items-center no-gutters">
                <div class="col mr-2">
                  <h6 class="mb-0"><strong>Lunch meeting</strong></h6>
                  <span class="text-xs">10:30 AM</span>
                </div>
                <div class="col-auto">
                  <div class="custom-control custom-checkbox">
                    <input
                      class="custom-control-input"
                      type="checkbox"
                      id="formCheck-1"
                    /><label
                      class="custom-control-label"
                      for="formCheck-1"
                    ></label>
                  </div>
                </div>
              </div>
            </li>
            <li class="list-group-item">
              <div class="row align-items-center no-gutters">
                <div class="col mr-2">
                  <h6 class="mb-0"><strong>Lunch meeting</strong></h6>
                  <span class="text-xs">11:30 AM</span>
                </div>
                <div class="col-auto">
                  <div class="custom-control custom-checkbox">
                    <input
                      class="custom-control-input"
                      type="checkbox"
                      id="formCheck-2"
                    /><label
                      class="custom-control-label"
                      for="formCheck-2"
                    ></label>
                  </div>
                </div>
              </div>
            </li>
            <li class="list-group-item">
              <div class="row align-items-center no-gutters">
                <div class="col mr-2">
                  <h6 class="mb-0"><strong>Lunch meeting</strong></h6>
                  <span class="text-xs">12:30 AM</span>
                </div>
                <div class="col-auto">
                  <div class="custom-control custom-checkbox">
                    <input
                      class="custom-control-input"
                      type="checkbox"
                      id="formCheck-3"
                    /><label
                      class="custom-control-label"
                      for="formCheck-3"
                    ></label>
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import InfoCard from "@/components/info-card/info-card.vue";
import PieChart from "@/components/charts/pie-chart.vue";
import Histogram from "@/components/charts/histogram.vue";
export default {
  components: {
    InfoCard,
    PieChart,
    Histogram,
  },
  data() {
    return {
      showMoreCard: false,
      pipValue: [
        { value: 1048, name: "Java" },
        { value: 735, name: "Kotlin" },
        { value: 580, name: "Python" },
        { value: 484, name: "Javascript" },
        { value: 300, name: "IOS" },
      ],
      histogramValue: {
        x: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
        y: [120, 200, 150, 80, 70, 110, 130],
      },
    };
  },
  methods: {
    isMoreCard() {
      this.showMoreCard = !this.showMoreCard;
    },
  },
};
</script>