// Import Pages as they're created and add to router list
import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Dashboard from '../views/Dashboard.vue'
import store from '../store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/dashboard',
    name: 'Dashboard',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: Dashboard
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  }
]

const router = new VueRouter({
  routes
})
// need to access vuex store $store.state.auth.status.loggedIn
router.beforeEach((to, from, next) => {
  // pages you can access without being logged in
  const publicPages = ['/login', '/register', '/home']
  const authRequired = !publicPages.includes(to.path)

  const loggedIn = store.state.auth.status.loggedIn

  if (authRequired && !loggedIn) {
    next('/home')
  } else {
    next()
  }
})
export default router
