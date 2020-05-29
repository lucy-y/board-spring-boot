import Vue from 'vue'
import App from '@/App'
import router from '@/lib/router'
import store from '@/lib/store'
import axios from 'axios'
import VueAxios from 'vue-axios'
import moment from 'vue-moment'
import BootstrapVue from 'bootstrap-vue'

Vue.use(moment)
Vue.use(VueAxios, axios)
Vue.use(BootstrapVue)
Vue.config.productionTip = false

Vue.prototype.$axios = axios

Vue.mixin({
  created(){
    //this.$axios.defaults.headers.common['Authorization'] = `${this.$store.state.token}`;
  },
  methods : {
    navigate(path) {
      this.$router.push(path)
    },
    navigate(path, paramObject) {
      // paramObject example : { type : 'paramData' }
      this.$router.push({ path: path, query: paramObject })
    },
    logOut(){
      this.deleteCookie('board-access-key')
      this.$store.commit('logout')
      this.navigate("/")
    },
    deleteCookie(name) {
      document.cookie = name + '=; expires=Thu, 01 Jan 1999 00:00:10 GMT;';
    },
  }
});

new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>',
  store,
  created: function () {
    var token = this.getCookie('board-access-key');
	  if(token != null){
      this.$store.commit('loginAfter',token);
	  } else {
      this.navigate("/");
    }
	},
	methods : {
	  getCookie(name) {
	    var v = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
	    return v ? v[2] : null;
    },
   }
})
