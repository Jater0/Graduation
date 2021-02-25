### Feedback.vue
``` vue
    <!-- <small
      class="form-text text-muted"
      style="text-align: center"
      @click="showWarning"
      >{{ isShowWarning ? "收起 举报反馈" : "显示 举报反馈" }}
      <i :class="isShowWarning ? 'fas fa-angle-up' : 'fas fa-angle-down'"></i>
    </small>
    <div class="row" v-if="isShowWarning && typeOneDetails.length !== 0">
      <FeedbackCard
        v-for="(item, index) in 10"
        :key="index"
        :detail="detail"
        :idSetter="index"
      ></FeedbackCard>
    </div>
    <NoData v-if="isShowWarning && typeOneDetails.length === 0"></NoData>
    <hr /> -->
    <!-- <small
      class="form-text text-muted"
      style="text-align: center"
      @click="showBug"
      >{{ isShowBug ? "收起 BUG反馈" : "显示 BUG反馈" }}
      <i :class="isShowBug ? 'fas fa-angle-up' : 'fas fa-angle-down'"></i>
    </small>
    <div class="row" v-if="isShowBug && typeTwoDetails.length !== 0">
      <FeedbackCard
        v-for="(item, index) in typeTwoDetails"
        :key="index"
        :detail="item"
        :idSetter="index"
      ></FeedbackCard>
    </div>
    <NoData v-if="isShowBug && typeTwoDetails.length === 0"></NoData>
    <hr />
    <small
      class="form-text text-muted"
      style="text-align: center"
      @click="showIdea"
      >{{ isShowIdea ? "收起 IDEA反馈" : "显示 IDEA反馈" }}
      <i :class="isShowIdea ? 'fas fa-angle-up' : 'fas fa-angle-down'"></i>
    </small>
    <div class="row" v-if="isShowIdea && typeThreeDetails.length !== 0">
      <FeedbackCard
        v-for="(item, index) in 10"
        :key="index"
        :detail="detail"
        :idSetter="index"
      ></FeedbackCard>
    </div>
    <NoData v-if="isShowIdea && typeThreeDetails.length === 0"></NoData>
    <hr />
    <small
      class="form-text text-muted"
      style="text-align: center"
      @click="showFinish"
      >{{ isShowFinish ? "收起 已解决反馈" : "显示 已解决反馈" }}
      <i :class="isShowFinish ? 'fas fa-angle-up' : 'fas fa-angle-down'"></i>
    </small>
    <div class="row" v-if="isShowFinish && typeDoneDetails.length !== 0">
      <FeedbackCard
        v-for="(item, index) in 10"
        :key="index"
        :detail="detail"
        :idSetter="index"
      ></FeedbackCard>
    </div>
    <NoData v-if="isShowFinish && typeDoneDetails.length === 0"></NoData>
    <hr /> -->
```