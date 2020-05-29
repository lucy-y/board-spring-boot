<template>
  <div id="app">
    <common_header v-if="hiddenFlag" />
    <router-view class="overflow pb-20"></router-view>
    <common_footer/>
  </div>
</template>

<script>
import CommonHeader from '@/Components/common/CommonHeader'
import CommonFooter from '@/Components/common/CommonFooter'

export default {
  name: "App",
  data () {
    return {
    }
  },
  created() {
    this.checkRoute();
  },
  watch: {
    '$route' : 'checkRoute'
  },
  methods : {
    checkRoute() {
      var self = this;
      this.$store.commit('getUserInfo');

      var routerName = this.$route.name;
      if(routerName=="Login" || routerName=="Findpassword" || routerName=="Signup"){
        self.hiddenFlag = false

      } else {
        if(this.$store.state.isToken==false){
          self.hiddenFlag = false;
          this.navigate("/");
          return
        }
        self.hiddenFlag = true
        this.userInfo = this.$store.state.userInfo;
      }
    }
  },
  components: {
    'common_header' : CommonHeader,
    'common_footer' : CommonFooter,
  },
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  height: 100%;
  background-color: #e9ecef;
}

.pb-10 { padding-bottom: 10px; }
.pb-20 { padding-bottom: 20px; }

.overflow {
  overflow-y: auto;
  height: 88%;
}
</style>
