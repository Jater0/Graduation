<template>
  <div class="page-top">
    <div id="wrapper">
      <Navbar
        @navIndex="getNavIndex"
        :level="adminInfoObject.professional"
        :adminInfo="adminInfoObject"
      ></Navbar>
      <div class="d-flex flex-column" id="content-wrapper">
        <div id="content">
          <Navigation
            :adminInfo="adminInfoObject"
            @jump="jump"
            @logout="logout"
          ></Navigation>
          <div class="container-fluid">
            <Dashboard v-if="tabIndex === 1"></Dashboard>
            <Profile
              v-if="tabIndex === 2"
              :adminInfo="adminInfoObject"
            ></Profile>
            <Admins v-if="tabIndex === 3" :adminInfo="adminInfoObject"></Admins>
            <Users v-if="tabIndex === 4"></Users>
            <Register
              v-if="tabIndex === 5"
              :adminInfo="adminInfoObject"
            ></Register>
            <Editor v-if="tabIndex === 6"></Editor>
            <EditorTopic v-if="tabIndex === 7"></EditorTopic>
            <Feedback v-if="tabIndex === 8"></Feedback>
          </div>
        </div>
        <Footer></Footer>
      </div>
    </div>
  </div>
</template>

<script>
import Navbar from "@/components/navbar/navbar.vue";
import Navigation from "@/components/navigation/navigation.vue";
import Footer from "@/components/footer/footer.vue";
import Dashboard from "@/components/dashboard/dashboard.vue";
import Profile from "@/components/profile/profile.vue";
import Admins from "@/components/admins/admins.vue";
import Users from "@/components/users/users.vue";
import Editor from "@/components/editor/editor.vue";
import EditorTopic from "@/components/editor-topic/editor-topic.vue";
import Register from "@/components/register/register.vue";
import Feedback from "@/components/feedback/feedback.vue";
import { mapState } from "vuex";
export default {
  name: "Index",
  components: {
    Navbar,
    Navigation,
    Footer,
    Dashboard,
    Profile,
    Admins,
    Users,
    Register,
    Feedback,
    Editor,
    EditorTopic,
  },
  computed: {
    ...mapState(["adminInfo"]),
  },
  created() {
    this.adminInfoObject = JSON.parse(this.adminInfo);
  },
  data() {
    return {
      tabIndex: 1,
      adminInfoObject: {},
    };
  },
  methods: {
    getNavIndex(data) {
      this.tabIndex = data;
    },
    jump(index) {
      this.tabIndex = index;
    },
    logout() {
      this.$router.push({ path: "/" });
    },
  },
};
</script>
