import Vue from 'vue'
import Vuex from 'vuex'
//import createPersistedState from 'vuex-persistedstate'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isToken : false,
    token: "",
    userInfo : {
      seq : "",
      nickname : "",
      email : "",
      admin : 0,
    }
  },
  mutations: {
    loginAfter (state, payload) {
      state.token = payload;
    },
    userInfo (state, userinfo) {
      state.isToken = true;
      state.userInfo.seq = userinfo.seq;
      state.userInfo.email = userinfo.email;
      state.userInfo.nickname = userinfo.nickname;
      state.userInfo.admin = userinfo.admin;
    },
    getUserInfo(state){
      axios.get("/api/users", {
        headers: {
          Authorization: state.token
        }
      }).then((response) => {
        var result = response.data;
        if(typeof(result.object)=="undefined"){
          state.isToken = false;
        } else {
          state.isToken = true;
          state.userInfo.seq = result.object.seq;
          state.userInfo.email = result.object.email;
          state.userInfo.nickname = result.object.nickname;
          state.userInfo.admin = result.object.admin;
        }
      }).catch(err => {
        state.isToken = false;
        console.log(err);
      })
    },
    logout (state) {
      state.token = "";
      var tmp = {
        seq : "",
        nickname : "",
        email : "",
        admin : 0,
      }
      state.userInfo = tmp;
    }
  },
  /* plugins: [
    createPersistedState()
  ] */
})