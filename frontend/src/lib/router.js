import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/components/Login'
import Signup from '@/components/Signup'
import Findpassword from '@/components/Findpassword'
import List from '@/components/List'
import Article from '@/components/Article'
import User from '@/components/User'

Vue.use(VueRouter);

export default new VueRouter({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login,
      alias: '/login'
    },
    {
    	path: '/signup',
    	name: 'Signup',
    	component: Signup,
    },
    {
    	path: '/findpwd',
    	name: 'Findpassword',
    	component: Findpassword,
    },
    {
    	path: '/list',
    	name: 'List',
    	component: List
    },
    {
    	path: '/article',
    	name: 'Article',
    	component: Article
    },
    {
    	path: '/user',
    	name: 'User',
    	component: User
    },
  ],
  mode : 'history'
})
