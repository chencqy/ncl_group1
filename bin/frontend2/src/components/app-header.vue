<template>
  <nav>
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/room">Room</router-link> |
      <router-link to="/dashboard">Dashboard</router-link> |
      <router-link to="/login">Login</router-link> | | |
      <button v-on:click="logOut()"> Logout</button>
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
</style>
