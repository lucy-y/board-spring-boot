  
<template>
  <section id="list">
    <div class="col-12 mt-10">
      <div class="card">
        <div class="card-body">
          <button class="btn btn-outline-primary btn-sm float-right mb-2" @click="navigate('/article')">등록</button>
          <table role="table" class="table">
            <colgroup>
              <col style="width: 20%" />
              <col style="width: 30%" />
              <col style="width: 20%" />
              <col style="width: 20%" />
              <col style="width: 10%" />
            </colgroup>
            <thead class="">
              <tr class="">
                <th scope="col" class="">SEQ</th>
                <th scope="col" class="">제목</th>
                <th scope="col" class="">작성자</th>
                <th scope="col" class="">작성일</th>
                <th scope="col" class="">-</th>
              </tr>
            </thead>
            <tbody>
              <tr v-if="articles.length==0">
                <td colspan="5">No Result</td>
              </tr>
              <template v-else v-for="(data, index) in articles">
                <tr @click="setViewSeq(data.seq, data)" class="pointer">
                  <td>{{data.seq}}</td>
                  <td><span>{{data.title}}</span></td>
                  <td>{{data.writer}}</td>
                  <td>{{$moment(data.regDate).format('YYYY-MM-DD hh:mm:ss')}}</td>
                  <td>
                    <button class="btn btn-sm btn-outline-warning" v-if="data.writerSeq==$store.state.userInfo.seq || $store.state.userInfo.admin" @click="updArticle(data.seq)">수정</button>
                    <button class="btn btn-sm btn-outline-danger" v-if="data.writerSeq==$store.state.userInfo.seq || $store.state.userInfo.admin" @click="seq=data.seq; checkDelBtn();">삭제</button>
                  </td>
                </tr>
                <tr v-if="expandLists.includes(data.seq)" class="bgc-blue">
                  <td colspan="5">
                    <div>
                      <span>{{expandDataLists[expandLists.indexOf(data.seq)].content}}</span>
                      <div v-if="expandDataLists[expandLists.indexOf(data.seq)].updDate">
                      <hr>
                      <span>수정일 : {{$moment(expandDataLists[expandLists.indexOf(data.seq)].updDate).format('YYYY-MM-DD hh:mm:ss')}}</span>
                    </div>
                    </div>
                  </td>
                </tr>
              </template>
            </tbody>
          </table>

          <div class="row">
            <div class="col-7">
              <paginate
              v-model="pageable.pageNumber"
              :page-count="pageable.totalPage"
              :page-range="5"
              :margin-pages="1"
              :click-handler="pageMove"
              :hide-prev-next="true"
              :prev-text="'이전'"
              :next-text="'다음'"
              :container-class="'pagination'"
              :page-class="'page-item'"
              >
              </paginate>
            </div>
            <div class="col-5 row">
              <select class="form-control w-30 mr-1" v-model="searchRequest.type">
                <option v-for="data in selectOption" :value="data.code">{{data.desc}}</option>
              </select>
              <input class="form-control w-50 mr-1" type="text" v-model="searchRequest.text" @keydown.enter="getArticleList(1)">
              <button class="btn btn-outline-dark btn-sm" @click="getArticleList(1)">검색</button>
            </div>
          </div>

        </div>
      </div>
    </div>
    <custom_modal @close="modalFlag?modalFlag=false:modalFlag=true" v-if="modalFlag" p_title="삭제" p_message="삭제하시겠습니까?" v-bind:p_confirm="true" v-bind:p_callback="delArticle"></custom_modal>
  </section>
</template>

<script>
import Paginate from 'vuejs-paginate'
import CusotmModal from '@/Components/common/CustomModal'
import VueMoment from 'vue-moment'

export default {
  name: 'List',
  created() {
    var self = this;
  },
  mounted() {
    this.getArticleList();
  },
  components: {
    "paginate" : Paginate,
    'custom_modal' : CusotmModal,
  },
  data () {
    return {
      expandLists : [],
      expandDataLists : [],
      viewSeq : "",
      modalFlag : false,
      seq : "",
      articles : [],
      searchRequest : {
        type : "T", // T: title, C: content, W: writer
        text : "",
      },
      pageable : {
        offset: 0,
        pageNumber: 0,
        pageSize: 10,
        paged: true,
        sort: "",
        totalCnt: 0,
        totalPage: 0
      },
      selectOption : [
        {
          code : "T",
          desc : "제목",
        },
        {
          code : "C",
          desc : "내용",
        },
        {
          code : "W",
          desc : "작성자",
        },
      ]
    }
  },
  methods : {
    setViewSeq : function(seq, data){
      var self = this;
      const index = self.expandLists.indexOf(seq);
      if (index > -1) {
      	self.expandLists.splice(index, 1)
      	self.expandDataLists.splice(index, 1)
      } else {
        self.expandLists.push(seq)
        self.expandDataLists.push(data)
      }
    },
    getArticleList : function(page){
      var self = this;

      var url = "/api/articles";
      if(page != undefined){
        url += "?page=" + page;
      }
      if(self.searchRequest.text){
        url += "&type="+self.searchRequest.type;
        url += "&text="+self.searchRequest.text;
      }

      this.$axios.get(url, {
        headers: {
          Authorization: this.$store.state.token
        }
      }).then((response) => {
        var result = response.data;
        console.log(result);
        self.articles = result.object.content;
        var pagingObject = result.object.pageable;
        pagingObject.pageNumber = pagingObject.pageNumber + 1; //JPA paging은 Zero base
        self.pageable = pagingObject;
        self.pageable.totalCnt = result.object.totalElements;
        self.pageable.totalPage = result.object.totalPages;
      }).catch(err => {
         console.log(err);
      })
    },
    updArticle: function(seq){
      var self = this;
      var object = { 'seq' : seq , 'type' : "U" };
      this.navigate("/article", object);
    },
    checkDelBtn : function(){
      var self = this;
      self.modalFlag = true;
    },
    delArticle: function(){
      var self = this;
      var seq = self.seq; 
      this.$axios.delete("/api/articles/"+seq, {}, {
        headers: {
          Authorization: this.$store.state.token
        }
      }).then((response) => {
        self.modalFlag = false;
        self.getArticleList();
      }).catch(err => {
         console.log(err);
      })
    },
    pageMove: function(page){
      console.log(page);
      var self = this;
      self.getArticleList(page);
    },
  }
}
</script>

<style scope>
.pointer { cursor: pointer; }
.flex { display: flex;}
.w-10 { width: 10%; }
.w-20 { width: 20%; }
.w-30 { width: 30%; }
.w-50 { width: 50%; }
.mt-10 { margin-top: 10px; }
.mt-20 { margin-top: 20px; }
.mt-30 { margin-top: 30px; }
.bgc-blue { background-color: #f1f8f9; }
</style>