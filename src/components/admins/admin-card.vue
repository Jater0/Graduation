<template>
  <div class="dialog" v-show="showMask">
    <div class="dialog-container">
      <div class="dialog-title">ID: {{ adminInfo._id }}</div>
      <div class="dialog-title">
        昵称: {{ adminInfo.admin_name }}; 权限等级: {{ adminInfo.professional }}
      </div>
      <div class="dialog-title">Email: {{ adminInfo.admin_email }}</div>
      <div class="dialog-title">手机: {{ adminInfo.admin_phone }}</div>
      <div class="content">
        <div class="action-box">
          <div class="action-title">他的权限:</div>
          <div class="no-data" v-if="myAction.length === 0">当前没有数据了</div>
          <div class="action-content" v-else>
            <div
              class="action-content-item"
              v-for="(item, index) in myAction"
              :key="index"
            >
              {{ item }}
              <i
                class="fas fa-times icon-close"
                v-if="item !== '个人信息'"
                @click="removeFormMyAction(index, item)"
              ></i>
            </div>
          </div>
          <hr />
          <div class="action-title">可添加权限:</div>
          <div class="no-data" v-if="notMyAction.length === 0">
            当前没有数据了
          </div>
          <div class="action-content" v-else>
            <div
              class="action-content-item"
              v-for="(item, index) in notMyAction"
              :key="index"
            >
              {{ item }}
              <i
                class="fas fa-plus icon-plus"
                @click="addToMyAction(index, item)"
              ></i>
            </div>
          </div>
        </div>
      </div>
      <div class="btns">
        <div class="default-btn" @click="closeBtn">取消</div>
        <div class="confirm-btn" @click="confirmBtn">确认</div>
      </div>
      <div class="close-btn" @click="closeBtn">
        <i class="fas fa-times"></i>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    value: {},
    adminInfo: {
      type: Object,
      default() {
        return {};
      },
    },
  },
  created() {},
  data() {
    return {
      showMask: false,
      myAction: [],
      notMyAction: [],
      actionDo: {
        1: "数据中心",
        2: "个人信息",
        3: "管理员列表",
        4: "用户列表",
        5: "添加管理员",
        6: "文章编辑",
        7: "话题编辑",
        8: "用户反馈",
      },
    };
  },
  mounted() {
    this.showMask = this.value;
  },
  watch: {
    value(newVal) {
      this.showMask = newVal;
    },
    adminInfo(newVal) {
      if (JSON.stringify(this.adminInfo) === "{}") return;
      this.getMyAction();
      this.getNotMyAction();
    },
  },
  methods: {
    closeMask() {
      this.showMask = false;
      this.myAction = [];
      this.notMyAction = [];
    },
    closeBtn() {
      this.$emit("cancel");
      this.closeMask();
    },
    confirmBtn() {
      this.$emit("confirm", this.getMyActionID());
      this.closeMask();
    },
    getMyAction() {
      var action = this.adminInfo.action;
      for (var i = 0; i < action.length; i++) {
        this.myAction.push(this.actionDo[Number(action.charAt(i))]);
      }
    },
    getNotMyAction() {
      for (var i = 1; i <= 8; i++) {
        if (this.myAction.includes(this.actionDo[i])) {
          continue;
        }
        this.notMyAction.push(this.actionDo[i]);
      }
    },
    removeFormMyAction(index, item) {
      if (this.myAction.length === 1) return;
      this.myAction.splice(index, 1);
      this.notMyAction.push(item);
    },
    addToMyAction(index, item) {
      this.notMyAction.splice(index, 1);
      this.myAction.push(item);
    },
    getMyActionID() {
      var output = "";
      for (var i = 1; i <= 8; i++) {
        if (this.myAction.includes(this.actionDo[i])) output += i;
      }
      return output;
    },
  },
};
</script>

<style lang="scss" scoped>
.dialog {
  position: fixed;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0, 0, 0, 0.6);
  z-index: 9999;
  .dialog-container {
    width: 500px;
    height: 600px;
    overflow-y: scroll;
    background: #ffffff;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    border-radius: 8px;
    .dialog-title {
      width: 100%;
      height: 60px;
      font-size: 18px;
      color: #696969;
      font-weight: 600;
      padding: 16px 50px 0 20px;
      box-sizing: border-box;
    }
    .content {
      color: #797979;
      line-height: 26px;
      padding: 0 20px;
      box-sizing: border-box;
      word-break: break-all;
      white-space: normal;
      .action-box {
        background-color: #fff;
        margin-bottom: 10px;
        .action-title {
          display: flex;
          justify-content: space-between;
          padding: 10px 15px;
          font-size: 14px;
        }
        .action-content {
          display: flex;
          flex-wrap: wrap;
          padding: 15px;
          padding-top: 0;
          .action-content-item {
            position: relative;
            padding: 2px 5px;
            margin-top: 12px;
            margin-right: 10px;
            border-radius: 5px;
            border: 1px #666 solid;
            font-size: 14px;
            .icon-close {
              position: absolute;
              right: -4px;
              top: -6px;
              background-color: #fff;
              border-radius: 50%;
              color: red;
            }
            .icon-plus {
              position: absolute;
              right: -4px;
              top: -6px;
              background-color: #fff;
              border-radius: 50%;
              color: green;
            }
          }
          .no-data {
            text-align: center;
            width: 100%;
            padding: 100px 0;
            color: #999;
            font-size: 28px;
          }
        }
      }
    }
    .btns {
      width: 100%;
      height: 60px;
      position: absolute;
      bottom: 0;
      left: 0;
      text-align: right;
      padding: 0 16px;
      box-sizing: border-box;
      div {
        display: inline-block;
        height: 40px;
        line-height: 40px;
        padding: 0 14px;
        color: #ffffff;
        background: #f1f1f1;
        border-radius: 8px;
        margin-right: 12px;
        cursor: pointer;
      }
      .default-btn {
        color: #787878;
        &:hover {
          color: #509ee3;
        }
      }
      .danger-btn {
        background: #ef8c8c;
        &:hover {
          background: rgb(224, 135, 135);
        }
        &:active {
          background: #ef8c8c;
        }
      }
      .confirm-btn {
        color: #ffffff;
        background: #509ee3;
        &:hover {
          background: #6fb0eb;
        }
      }
    }
    .close-btn {
      position: absolute;
      top: 16px;
      right: 16px;
      width: 30px;
      height: 30px;
      line-height: 30px;
      text-align: center;
      font-size: 18px;
      cursor: pointer;
      &:hover {
        font-weight: 600;
      }
    }
  }
}
</style>