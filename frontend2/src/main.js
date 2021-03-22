import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VuePapaParse from 'vue-papa-parse'
import Chart from 'vue2-frappe'
import VeeValidate from 'vee-validate'

// Add bootstrap stuff
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import 'vue2-toast/lib/toast.css'
import Toast from 'vue2-toast'

Vue.use(Toast)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(VuePapaParse)
Vue.use(Chart)
Vue.use(VeeValidate)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
