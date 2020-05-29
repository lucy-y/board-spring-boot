<template>
  <section id="Findpassword">
    <div class="container">
      <div class="row justify-content-center">
        <div class="col-lg-5">
          <div class="card shadow-lg border-0 rounded-lg mt-5">
            <div class="card-header">
              <h3 class="text-center font-weight-light my-4">비밀번호 찾기</h3>
            </div>
            <div class="card-body">
              <div class="small mb-3 text-muted">
                <p>가입 시 입력했던 이메일을 기입하시면,
                  <br />임시비밀번호를 발송해드립니다.
                </p>
              </div>
              <form>
                <div class="form-group">
                  <label class="small mb-1 float-left" for="inputEmailAddress">Email</label>
                  <input class="form-control py-4" id="inputEmailAddress" type="email"
                    placeholder="이메일을 입력하세요" v-model="user.email"
                  />
                </div>
                <div
                  class="form-group d-flex align-items-center justify-content-between mb-0 float-right"
                >
                  <a class="btn btn-outline-primary" @click="findPwd">메일 발송</a>
                </div>
              </form>
            </div>
            <div class="form-group">
              <span class="small" v-bind:class="err.class">{{err.msg}}</span>
            </div>
            <div class="card-footer text-center">
              <div class="small">
                <a href="/signup" class="float-left">회원가입</a>
                <a href="/login" class="float-right">로그인/취소</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
export default {
  name: "Findpassword",
  data() {
    return {
      user: {
        email: ""
      },
      waiting: false,
      err: {
        msg: "",
        class: "color-red"
      }
    };
  },
  methods: {
    findPwd: function() {
      var self = this;
      if (self.user.email == "" || self.user.email == undefined) {
        self.err.msg = "이메일을 기입해주세요.";
        self.err.class = "color-red";
      } else if (self.waiting) {
        self.err.msg =
          "기입하신 메일로 임시 비밀번호 발송중입니다. 잠시만 기다려주세요.";
        self.err.class = "color-red";
      } else {
        self.waiting = true;
        this.$axios
          .post("/api/users/send-temp-pwd", this.user)
          .then(response => {
            self.waiting = false;
            var result = response.data;
            self.err.msg ="메일이 발송되었습니다. 메일함 혹은 스팸을 확인해주세요.";
            self.err.class = "color-blue";
          })
          .catch(err => {
            self.waiting = false;
            self.err.msg ="유저 정보가 없거나, 에러가 발생했습니다.";
            self.err.class = "color-red";
          });
      }
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

