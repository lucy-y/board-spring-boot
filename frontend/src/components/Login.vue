<template>
  <section id="login">
  
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-6">
        <div class="card shadow-lg border-0 rounded-lg mt-5">
          <div class="card-header">
            <h3 class="text-center font-weight-light my-4">로그인</h3>
          </div>
          <div class="card-body">
            <div class="form-group">
              <label class="small mb-1 float-left" for="inputEmailAddress">Email</label>
              <input class="form-control py-4" id="inputEmailAddress" type="email" placeholder="이메일을 입력하세요" v-model="user.email">
            </div>
            <div class="form-group">
              <label class="small mb-1 float-left" for="inputPassword">Password</label>
              <input class="form-control py-4" id="inputPassword" type="password" placeholder="비밀번호를 입력하세요" v-model="user.pwd" @keydown.enter="signin">
            </div>
            <div class="form-group">
              <div class="custom-control custom-checkbox">
                <input class="custom-control-input" id="rememberEmailCheck" type="checkbox" v-model="rememberEmailCheck">
                <label class="custom-control-label" for="rememberEmailCheck">이메일 유지하기</label>
              </div>
            </div>
            <div class="form-group d-flex align-items-center justify-content-between mt-4 mb-0">
              <a class="small" @click="navigate('/findpwd')">비밀번호 찾기</a>
              <a class="btn btn-outline-primary" @click="signin">로그인</a>
            </div>
          </div>
          
          <div class="form-group">
            <span class="small" v-bind:class="err.class">{{err.msg}}</span>
          </div>
          
          <div class="card-footer text-center">
            <div class="small"><a @click="navigate('/signup')">회원가입</a></div>
          </div>
        </div>
      </div>
    </div>
  </div>

  </section>
</template>

<script>
export default {
  name: 'Login',
  data () {
    return {
      user : {
        email : "",
        pwd : ""
      },
      rememberEmailCheck : false,
      err : {
        msg : "",
        class : "color-black",
      }
    }
  },
  created : function(){
    var self = this;

    var rememberEmailCheck = localStorage.getItem('rememberEmailCheck');
    var email = localStorage.getItem('email');
    if(rememberEmailCheck==1){
      self.rememberEmailCheck = true;
      self.user.email = email;
    } 
  },
  mounted() {
  },
  methods : {
    signin(){
      var self = this;
      self.err.msg = "";
      
      this.$axios.post("/api/users/login", this.user).then((response) => {
        var result = response.data;
        if(result.result == "00"){
          this.$store.commit('loginAfter', result.object.token);
          //this.$store.commit('userInfo', result.object);
          
          if(self.rememberEmailCheck){
            localStorage.setItem('rememberEmailCheck', 1);
            localStorage.setItem('email', self.user.email);
          } else {
            localStorage.removeItem('rememberEmailCheck');
            localStorage.removeItem('email');
          }
          
          this.navigate('/list');
        } else {
          self.err.msg = result.errormsg;
          self.err.class= "color-red";
        }
      }).catch(err => {
         self.err.msg = "에러가 발생하였습니다. 다시 시도해주세요.";
         self.err.class= "color-red";
      })
      
    }
  }
}
</script>

<style scope>
.float-left { float: left; }
.float-right { float: right; }
.color-red { color : red; }
.color-blue { color : blue; }
.color-black { color : black; }
</style>

