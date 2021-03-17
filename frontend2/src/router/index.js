// Import Pages as they're created and add to router list
import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Dashboard from '../views/Dashboard.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/room',
    name: 'Room',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/Room.vue')
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
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import(/* webpackChunkName: "about" */ '../views/Profile.vue')
  }
]

const router = new VueRouter({
  routes
})
// need to access vuex store $store.state.auth.status.loggedIn
router.beforeEach((to, from, next) => {
  // pages you can access without being logged in
  // This is part breaking login
  const publicPages = ['/login', '/register', '/home', '/room', '/dashboard']
  const authRequired = !publicPages.includes(to.path)
  const loggedIn = localStorage.getItem('user')
  console.log(loggedIn) // does this variable update?
  // trying to access a restricted page + not logged in
  // redirect to login page
  if (authRequired && !loggedIn) {
    next('/home')
  } else {
    next()
  }
})

/* function guard (to, from, next) {
  console.log(store)
  var isAuthenticated = false
  // this is just an example. You will have to find a better or
  // centralised way to handle you localstorage data handling

  if (localStorage.getItem('user')) {
    isAuthenticated = true
    console.log(localStorage.getItem('user'))
  } else {
    isAuthenticated = false
  }
  if (isAuthenticated) {
    next() // allow to enter route
  } else {
    next('/login') // go to '/login';
  }
} */
export default router
