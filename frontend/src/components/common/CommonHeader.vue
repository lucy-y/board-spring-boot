<template>
  <header>
    <div>
	  <b-navbar toggleable="lg" type="dark" variant="info">
	    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
	
	    <b-collapse id="nav-collapse" is-nav>
	
	      <!-- Right aligned nav items -->
	      <b-navbar-nav class="ml-auto">
	        <b-nav-item-dropdown right>
	          <template v-slot:button-content>
	            <em v-if="$store.state.userInfo.nickname!=null || $store.state.userInfo.nickname==''">{{$store.state.userInfo.nickname}}</em>
	            <em v-else>User</em>
	          </template>
	          <b-dropdown-item @click="navigate('/user')">내 정보</b-dropdown-item>
	          <b-dropdown-item @click="logOut">로그아웃</b-dropdown-item>
	        </b-nav-item-dropdown>
	      </b-navbar-nav>
	    </b-collapse>
	  </b-navbar>
	</div>
  </header>
</template>

<script>

export default {
  name: 'CommonHeader',
  data () {
    return {
      
    }
  },
  created() {
  },
  methods : {
  }
}
</script>