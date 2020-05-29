  
<template>
  <section id="article">
    <div class="col-12 mt-10">
      <div class="card">
        <div class="card-body">
          <table class="table">
            <colgroup>
              <col style="width: 20%;" />
              <col style="width: 80%;" />
            </colgroup>
            <thead>
            </thead>
            <tbody>
              <tr>
                <th>제목</th>
                <td><input type="text" class="form-control w-100" v-model="article.title" placeholder="제목을 입력하세요" /></td>
              </tr>
              <tr>
                <th>내용</th>
                <td><textarea class="form-control w-100 h-20 mh-300" v-model="article.content" placeholder="내용을 입력하세요" /></td>
              </tr>
            </tbody>
          </table>
          <button class="btn btn-outline-primary btn-sm" v-if="type=='I'" @click="addArticle">등록</button>
          <button class="btn btn-outline-warning btn-sm" v-if="type=='U'" @click="updArticle">수정</button>
          <button class="btn btn-outline-dark btn-sm" @click="navigate('/list')">목록으로/취소</button>
        </div>
      </div>
    </div>
    <custom_modal @close="moveListPage" v-if="modalFlag" v-bind:p_title="title" v-bind:p_message="message" v-bind:p_confirm="true" v-bind:p_cancel="false" v-bind:p_callback="moveListPage"></custom_modal>

  </section>
</template>

<script>
import CusotmModal from '@/Components/common/CustomModal'

export default {
  name: 'Article',
  created() {
    var self = this;
    if(this.$route.query.type=="U"){
      self.getArticle(this.$route.query.seq);
      self.article.seq = this.$route.query.seq;
      self.type = "U"
    }

  },
  mounted() {
  },
  components: {
    'custom_modal' : CusotmModal,
  },
  data () {
    return {
      modalFlag : false,
      type : 'I',
      article : {
        seq : "",
        title : "",
        content : ""
      }
    }
  },
  methods : {
    moveListPage : function(){
      this.navigate("/list");
    },
    getArticle : function(seq){
      var self = this;
      var url = "/api/articles/" + seq;
      this.$axios.get(url, {}, {
        headers: {
          Authorization: this.$store.state.token
        }
      }).then((response) => {
        var result = response.data.object;
        if(result==null){ this.navigate("/"); }
        self.article.title = result.title;
        self.article.content = result.content;
      }).catch(err => {
         console.log(err);
      })
    },
    addArticle : function(){
      var self = this;
      self.article.writer = self.$store.state.userInfo.nickname;
      self.article.writerSeq = self.$store.state.userInfo.seq;
      this.$axios.post("/api/articles", self.article, {
        headers: {
          Authorization: this.$store.state.token
        }
      }).then((response) => {
        self.title = "저장";
        self.message = "저장되었습니다. 목록으로 이동합니다.";
        self.modalFlag = true;
      }).catch(err => {
         console.log(err);
      })
    },
    updArticle: function(){
      var self = this;
      self.article.writer = self.$store.state.userInfo.nickname;
      self.article.updateSeq = self.$store.state.userInfo.seq;
      console.log(self.article);

      this.$axios.put("/api/articles", self.article, {
        headers: {
          Authorization: this.$store.state.token
        }
      }).then((response) => {
        self.title = "수정";
        self.message = "수정되었습니다. 목록으로 이동합니다.";
        self.modalFlag = true;
      }).catch(err => {
         console.log(err);
      })
    },
    delArticle: function(seq){
      var self = this;
      this.$axios.delete("/api/articles/"+seq, {}, {
        headers: {
          Authorization: this.$store.state.token
        }
      }).then((response) => {
        console.log(response);
      }).catch(err => {
         console.log(err);
      })
    }
  }
}
</script>

<style scope>
.w-100 { width: 100%; }
.mh-300 { min-height: 300px; }
.mt-10 { margin-top: 10px; }
.mt-20 { margin-top: 20px; }
.mt-30 { margin-top: 30px; }
</style>