<template>
  <section id="custom_modal">
    <div class="modal fade show" id="centermodal" tabindex="-1" role="dialog" aria-modal="true"
	style="display: block; padding-right: 17px; background-color: rgba(0, 0, 0, 0.25)"
      @click.self="$emit('close')">
	  <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h4 class="modal-title" id="myCenterModalLabel">{{p_title}}</h4>
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true" @click="$emit('close')">×</button>
          </div>
          <div class="modal-body">
            <p>{{p_message}}</p>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-primary" v-if="p_confirm" @click="callbackFunction()">확인</button>
            <button type="button" class="btn btn-light" data-dismiss="modal" v-if="p_cancel" @click="$emit('close')">취소</button>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<!--
  <example>
    조건1)
    - parents component data에 modal 보여주는 flag 변수 선언
    - 확인버튼 미존재, 취소버튼 존재
    data : function() {
      return {
        ..
        modalFlag1 : false,
        ..
      }
    }
    - parents component modal 호출
    <button @click="modalFlag1?modalFlag1=false:modalFlag1=true">confirm 있음</button>
    <custom_modal @close="modalFlag1?modalFlag1=false:modalFlag1=true" v-if="modalFlag1" p_title="제목" p_message="내용" v-bind:p_confirm="false"></custom_modal>
    조건2)
    - parents component data에 modal 보여주는 flag 변수 선언 및 callback 있을 때, parents methods에 호출할 function 정의
    - 확인버튼 존재, 취소버튼 미존재
    data : function() {
      return {
        ..
        modalFlag2 : false,
        ..
      }
    }
    methods : {
      ..
      callbackFunction(){
        console.log('callback!');
      },
      ...
    }
    - parents component modal 호출
    <button @click="modalFlag2?modalFlag2=false:modalFlag2=true">confirm 있음</button>
    <custom_modal @close="modalFlag2?modalFlag2=false:modalFlag2=true" v-if="modalFlag2" p_title="제목" p_message="내용" v-bind:p_confirm="true" bind:p_cancel="true" v-bind:p_callback="callbackFunction"></custom_modal>
-->


<script>
export default {
	name: "CustomModal",
	props : {
		p_title : {
			type : String,
			required : false
		},
		p_message : {
			type : String,
			required : true
		},
		p_confirm : {
			type : Boolean,
			required : false,
			default : false
		},
		p_cancel : {
			type : Boolean,
			required : false,
			default : true
		},
		p_callback : {
			type : Function,
			required : false,
		}
	},
	computed : {
		title : function(){
			return this.p_title;
		},
		message : function(){
			return this.p_message;
		},
		confirm : function(){
			return this.p_confirm;
		},
		cancel : function(){
			return this.p_cancel;
		},
		callback : function(){
			return this.p_callback;
		}
	},
	created() {
		var self = this;
	},
	data() {
		return {

		};
	},
	mounted() {
		
	},
	methods: {
		callbackFunction(){
			var self = this;
			if(self.callback()){
				self.callback;
			}
		}
	}
};
</script>