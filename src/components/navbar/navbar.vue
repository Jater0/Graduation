<template>
  <nav
    class="navbar navbar-dark align-items-start sidebar sidebar-dark accordion bg-gradient-primary p-0"
  >
    <div class="container-fluid d-flex flex-column p-0">
      <a
        class="navbar-brand d-flex justify-content-center align-items-center sidebar-brand m-0"
        href="#"
      >
        <div class="sidebar-brand-icon rotate-n-15">
          <i class="fas fa-database"></i>
        </div>
        <div class="sidebar-brand-text mx-3"><span>Objective-Admin</span></div>
      </a>
      <hr class="sidebar-divider my-0" />
      <ul class="nav navbar-nav text-light" id="accordionSidebar">
        <li class="nav-item" role="presentation">
          <a
            class="nav-link"
            :class="activeIndex === 1 ? 'active' : ''"
            id="index_url"
            @click="open(1)"
          >
            <i class="fas fa-tachometer-alt"></i>
            <span>数据中心</span>
          </a>
        </li>
        <li class="nav-item" role="presentation">
          <a
            class="nav-link"
            :class="activeIndex === 2 ? 'active' : ''"
            id="profile_url"
            @click="open(2)"
          >
            <i class="fas fa-user"></i>
            <span>个人信息</span>
          </a>
        </li>
        <li class="nav-item" role="presentation">
          <a
            class="nav-link"
            :class="activeIndex === 3 ? 'active' : ''"
            id="admins_url"
            @click="open(3)"
          >
            <i class="fas fa-table"></i>
            <span>管理员列表</span>
          </a>
        </li>
        <li class="nav-item" role="presentation">
          <a
            class="nav-link"
            :class="activeIndex === 4 ? 'active' : ''"
            id="users_url"
            @click="open(4)"
          >
            <i class="fas fa-table"></i>
            <span>用户列表</span>
          </a>
        </li>
        <li class="nav-item" role="presentation" v-if="level === 0">
          <a
            class="nav-link"
            :class="activeIndex === 5 ? 'active' : ''"
            id="register_url"
            @click="open(5)"
          >
            <i class="fas fa-user-circle"></i>
            <span>添加管理员</span>
          </a>
        </li>
        <li class="nav-item" role="presentation">
          <a
            class="nav-link"
            :class="activeIndex === 6 ? 'active' : ''"
            id="editor_url"
            @click="open(6)"
          >
            <i class="fas fa-sticky-note"></i>
            <span>编辑</span>
          </a>
        </li>
        <li class="nav-item" role="presentation">
          <a
            class="nav-link"
            :class="activeIndex === 7 ? 'active' : ''"
            id="editor_url"
            @click="open(7)"
          >
            <i class="fas fa-sticky-note"></i>
            <span>用户反馈</span>
          </a>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
export default {
  props: {
    level: {
      type: Number,
      default: 0,
    },
  },
  data() {
    return {
      activeIndex: 1,
    };
  },
  methods: {
    open(index) {
      this.activeIndex = index;
      this.$emit("navIndex", index);
    },
  },
};
</script>