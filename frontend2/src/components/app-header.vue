<template>
  <nav>
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/room">Room</router-link> |
      <router-link to="/dashboard">Dashboard</router-link> |
      <router-link to="/login">Login</router-link> <br>
      <b-button v-on:click="logOut()" variant="outline-primary">Logout</b-button>
    </div>
  </nav>
</template>
<script>
export default {
  methods: {
    logOut () {
      this.$store.dispatch('auth/logout')
      this.$router.push('/login')
    }
  }
}
</script>
<style>
#nav{
background-color:#42b98370;
}
</style>
