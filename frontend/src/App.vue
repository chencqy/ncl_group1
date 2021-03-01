<template>
  <div id="app">
    <navbar />
    <mainSection />
  </div>
</template>

<script>
import navbar from "./components/navbar.vue";
import mainSection from "./components/mainSection.vue";
export default {
  name: "App",
  components: {
    navbar,
    mainSection,
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 2px;
}
</style>
