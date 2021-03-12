import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VuePapaParse from 'vue-papa-parse'
import Chart from 'vue2-frappe'

// Add bootstrap stuff
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(VuePapaParse)
Vue.use(Chart)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
