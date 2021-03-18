<template>
  <div id='app'>
    <app-header></app-header>
    <!-- PAGES SHOWN THROUGH HERE IN ROUTER VIEW -->
    <b-container>
      <router-view />
    </b-container>
    <app-footer></app-footer>
  </div>
</template>
<script>
import Header from './components/app-header'
import Footer from './components/app-footer'
export default {
  components: {
    'app-header': Header,
    'app-footer': Footer
  },
  methods: {
    logOut () {
      this.$store.dispatch('auth/logout')
      this.$router.push('/login')
    }
  }
}
</script>
<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  justify-content: center;
}
#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
