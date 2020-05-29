  
<template>
  <section id="user">
    <div class="col-12 mt-10">
      <div class="card">
        <div class="card-body">
          <table class="table">
            <colgroup>
              <col style="width: 20%" />
              <col style="width: 80%" />
            </colgroup>
            <thead>
            </thead>
            <tbody>
              <tr>
                <th>이메일</th>
                <td>
                  <span class="lfloat">{{$store.state.userInfo.email}}</span>
                </td>
              </tr>
              <tr>
                <th>닉네임</th>
                <td>
                  <div class="lfloat" v-if="!changeNicknameFlag">
	                <span>{{$store.state.userInfo.nickname}}</span>
	                <button class="btn btn-outline-warning" @click="changeNicknameFlag=true">닉네임 변경</button>
	                </div>
	                <div class="lfloat" v-else>
	                <input class="form-control" v-model="userRequest.nickname" maxlength="10">
	                <button class="btn btn-outline-warning" @click="changeUserInfo">변경</button>
	                </div>
	            </td>
              </tr>
              <tr>
                <th>비밀번호 변경</th>
                <td>
                  <div class="flex float">
	                <input class="form-control w-30 mr-1" type="password" v-model="userRequest.pwd" placeholder="변경할 비밀번호 입력" minlength="8" maxlength="30">
                  <input class="form-control w-30 mr-1" type="password" v-model="userRequest.pwdRe" placeholder="변경할 비밀번호 재입력" minlength="8" maxlength="30">
	                <button class="btn btn-outline-warning" v-show="userRequest.pwd==userRequest.pwdRe && userRequest.pwd!=''" @click="changeUserInfo">변경</button>
	                </div>
	            </td>
              </tr>
              <tr>
                <th>관리자 여부</th>
                <td>
                  <div class="lfloat">
                    <span class="mr-1">{{$store.state.userInfo.admin==0?'X':'O'}}</span>
                    <button class="btn btn-outline-warning" @click="changeAdmin">변경</button>
                  </div>
                </td>
              </tr>
              <tr>
                <th>회원탈퇴</th>
                <td>
                  <div class="lfloat">
                    <button class="btn btn-danger" @click="modalFlag=true">회원탈퇴</button>
                  </div>
                </td>
              </tr>
            </tbody>
          </table>

          <button class="btn btn-outline-dark btn-sm" @click="moveHome">홈으로</button>
          
        </div>
      </div>
    </div>

    <custom_modal @close="callbackFunction" v-if="modalFlag" p_title="회원탈퇴" p_message="회원탈퇴하시겠습니까? 작성한 글도 삭제됩니다. 복구되지 않습니다." v-bind:p_confirm="true" v-bind:p_callback="Withdrawal"></custom_modal>
    
  </section>
</template>

<script>
import CusotmModal from '@/Components/common/CustomModal'

export default {
  name: 'User',
  created() {
    var self = this;
    self.userRequest.email = self.$store.state.userInfo.email;
    self.userRequest.nickname = self.$store.state.userInfo.nickname;
    self.userRequest.admin = self.$store.state.userInfo.admin;

  },
  mounted() {
  },
  components: {
    'custom_modal' : CusotmModal,
  },
  data () {
    return {
      modalFlag : false,
      changeNicknameFlag : false,
      userRequest : {
        email : "",
        nickname : "",
        pwd : "",
        pwdRe : "",
        admin : 0,
      }
    }
  },
  methods : {
    moveHome: function(){
      this.navigate("/list");
    },
    callbackFunction : function(){
      var self = this;
      self.modalFlag = false;
    },
    changeAdmin: function(){
      var self = this;

      if(this.$store.state.userInfo.admin==0) self.userRequest.admin = 1;
      else self.userRequest.admin = 0;

      self.changeUserInfo();
    },
    changeUserInfo : function(){
      var self = this;
      this.$axios.put("/api/users", self.userRequest, {
        headers: {
          Authorization: this.$store.state.token
        }
      }).then((response) => {
        var result = response.data;
        this.$store.commit('userInfo', result.object);
        self.changeNicknameFlag = false;
      }).catch(err => {
         console.log(err);
      })
    },
    Withdrawal : function(){
      var self = this;
      var seq = self.$store.state.userInfo.seq;
      this.$axios.delete("/api/users/"+seq, {
        headers: {
          Authorization: this.$store.state.token
        }
      }).then((response) => {
        console.log(response);
        this.logOut();
      }).catch(err => {
         console.log(err);
      })
    }
  }
}
</script>

<style scope>
.flex { display: flex;}
.lfloat { float : left; }
.w-10 { width: 10%; }
.w-20 { width: 20%; }
.w-30 { width: 30%; }
.w-50 { width: 50%; }
.mt-10 { margin-top: 10px; }
.mt-20 { margin-top: 20px; }
.mt-30 { margin-top: 30px; }
</style>