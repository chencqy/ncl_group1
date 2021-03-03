<template>
  <div id="app">
    <!--- This could be made to header and footer components and imported here -->
    <!-- EG <app-header></app-header> -->
    <div id="nav">
      <router-link to="/">Home</router-link> |
      <router-link to="/room">Room</router-link> |
      <router-link to="/dashboard">Dashboard</router-link>
    </div>
    <!-- PAGES SHOWN THROUGH HERE IN ROUTER VIEW -->
    <b-container>
      <router-view/>
    </b-container>
  </div>
  <!-- EG <app-footer></app-footer> -->
</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
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
