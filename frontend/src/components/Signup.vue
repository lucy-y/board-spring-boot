<template>
  <section id="signup">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-7">
          <div class="card shadow-lg border-0 rounded-lg mt-5">
            <div class="card-header">
              <h3 class="text-center font-weight-light my-4">회원가입</h3>
            </div>
            <div class="card-body">
              <div class="form-group">
                <label class="small mb-1 float-left" for="inputEmailAddress">Email</label>
                <input class="form-control py-4" id="inputEmailAddress" type="email" placeholder="이메일을 입력하세요" v-model="user.email" minlength="8" maxlength="30" />
              </div>
              <div class="form-group">
                <label class="small mb-1 float-left" for="inputNickname">Nickname</label>
                <input class="form-control py-4" id="inputNickname" type="text" placeholder="닉네임을 입력하세요" v-model="user.nickname" maxlength="10" />
              </div>
              <div class="form-row">
                <div class="col-md-6">
                  <div class="form-group">
                    <label class="small mb-1 float-left" for="inputPassword">Password</label>
                    <input
                      class="form-control py-4" id="inputPassword" type="password"
                      placeholder="비밀번호를 입력하세요" v-model="user.pwd" @change="checkPassword" minlength="8" maxlength="30" />
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="form-group">
                    <label class="small mb-1 float-left" for="inputConfirmPassword">Confirm Password</label>
                    <input class="form-control py-4" id="inputConfirmPassword" type="password"
                      placeholder="비밀번호 확인" v-model="user.pwdCheck" @change="checkPassword" minlength="8" maxlength="30" />
                    <span class="small" v-bind:class="pwd.err.class">{{pwd.err.msg}}</span>
                  </div>
                </div>
              </div>
              <div class="form-group mt-4 mb-0">
                <a class="btn btn-outline-primary btn-block" @click="makeAccount">가입하기</a>
              </div>
            </div>

            <div class="form-group">
              <span class="small" v-bind:class="err.class">{{err.msg}}</span>
            </div>

            <div class="card-footer">
              <div class="small">
                <a href="/findpwd" class="float-left">비밀번호 찾기</a>
                <a href="/login" class="float-right">로그인/취소</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <custom_modal @close="callbackFunction" v-if="modalFlag" p_title="회원가입 완료" p_message="회원가입이 완료되었습니다. 로그인 후 이용해주세요." v-bind:p_confirm="true" v-bind:p_cancel="false" v-bind:p_callback="callbackFunction"></custom_modal>
  </section>
</template>

<script>
import CusotmModal from '@/Components/common/CustomModal'

export default {
  name: "Signup",
  data() {
    return {
      modalFlag : false,
      user: {
        email: "",
        pwd: "",
        nickname: "",
        pwdCheck: ""
      },
      pwd: {
        err: {
          msg: "",
          class: "color-black"
        }
      },
      err: {
        msg: "",
        class: "color-red"
      },
      pwdFlag: false
    };
  },
  components: {
    'custom_modal' : CusotmModal,
  },
  methods: {
    callbackFunction : function(){
      this.navigate("/");
    },
    checkPassword: function() {
      var self = this;
      if (self.user.pwdCheck == "") {
        self.pwdFlag = false;
        self.pwd.err.msg = "";
        self.pwd.err.class = "color-black";
      } else if (self.user.pwd != self.user.pwdCheck) {
        self.pwdFlag = false;
        self.pwd.err.msg = "비밀번호가 일치하지 않습니다.";
        self.pwd.err.class = "color-red";
      } else if (self.user.pwd == self.user.pwdCheck) {
        self.pwdFlag = true;
        self.pwd.err.msg = "비밀번호가 일치합니다.";
        self.pwd.err.class = "color-blue";
      } else {
        self.pwdFlag = false;
        self.pwd.err.msg = "";
        self.pwd.err.class = "color-black";
      }
    },
    makeAccount: function() {
      var self = this;
      if (self.user.email == "") {
        self.err.msg = "이메일을 입력해주세요.";
        self.err.class = "color-red";
        return;
      } else if (self.user.nickname == "") {
        self.err.msg = "닉네임을 입력해주세요.";
        self.err.class = "color-red";
        return;
      } else if (!self.pwdFlag) {
        self.err.msg = "비밀번호를 확인해주세요.";
        self.err.class = "color-red";
        return;
      } else if(self.user.email){
        var regExp = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
        var result = regExp.test(self.user.email);
        if(result==0){
          self.err.msg = "이메일 형식을 확인해주세요.";
          self.err.class = "color-red";
          return;
        }
      }else {
        self.err.msg = "";
        self.err.class = "color-black";
      }

      this.$axios
        .post("/api/users/signup", this.user)
        .then(response => {
          var result = response.data;
          console.log(result);
          if (result.result == "00") {
            self.modalFlag = true;
          } else {
            self.err.msg = result.errormsg;
          }
        })
        .catch(err => {
          console.log(err.response);
          self.err.msg = "이미 가입된 아이디거나, 에러가 발생하였습니다.";
        });
    }
  }
};
</script>

<style scope>
.float-left {
  float: left;
}
.float-right {
  float: right;
}
.color-red {
  color: red;
}
.color-blue {
  color: blue;
}
.color-black {
  color: black;
}
</style>

