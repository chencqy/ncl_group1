<template>
    <div class="dashboard">
        <b-container>
            <b-row>
                <b-col>
                  {{this.currentUser.user.power}}
                  <b-card>
                    <b-card
                      :title="room"
                      v-for="(room,index) in content" :key="index">
                      <b-card-text>
                        {{room}}
                        <p>{{metrics}}</p>
                        <tr v-for="(metric,index) in metrics" :key="index">{{metric.name}}:{{metric.value}}</tr>
                      </b-card-text>
                      <b-button v-on:click="buttonClick(room)" variant="primary">Data</b-button>
                    </b-card>
                  </b-card>
                </b-col>
            </b-row>
        </b-container>
  </div>

</template>

<script>
// import { delete } from 'vue/types/umd'
// import { response } from 'express'
import UserService from '../services/user.service'

/* eslint-disable */ 
export default {
  name: 'Dashboard',
  data () {
    return {
      content: '',
      metrics: ''
    }
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    }
  },
  // Add logout button?
  methods: {
    logOut () {
      this.$store.dispatch('auth/logout')
      this.$router.push('/login')
    },
    linkGen(pageNum) {
      return pageNum === 1 ? '?' : `?page=${pageNum}`
    },
    showStudentBoard () {
      if (this.currentUser && this.currentUser.user.power.includes('ROLE_Student,')) {
        UserService.getStudentBoard().then(
          response => {
            console.log(response.data.student)
            this.content = response.data.student
          } 
        ) 
      }
    },
    showResearchBoard () {
      if (this.currentUser && this.currentUser.user.power.includes('ROLE_Researcher,')) {
        UserService.getResearchBoard().then(
          response => {
            console.log(response.data.student)
            this.content = response.data.researcher
          } 
        ) 
      }
    },
    showAdminBoard () {
      if (this.currentUser && this.currentUser.user.power.includes('ROLE_BuildingManager,')) {
        UserService.getAdminBoard().then(
          response => {
            console.log(response.data.student)
            this.content = response.data.admin
          } 
        ) 
      }
    },
    buttonClick(room){
      var fix = room.replaceAll(' ', '-').toLowerCase()
      console.log(fix)
      var role = UserService.getRole(this.currentUser.user.power)
      console.log(this.currentUser.user.power)
      console.log(role)
        UserService.getRoomMetric(fix , role).then(
          response => {
            console.log(response)
            this.metrics = response.data.metrics
          } 
        )
    }
  },
  mounted() {
    this.showStudentBoard()
    this.showResearchBoard()
    this.showAdminBoard()
  }
}
</script>

<style scoped>
#login {
    width: 500px;
    border: 1px solid #CCCCCC;
    background-color: #FFFFFF;
    margin: auto;
    padding: 20px;
}

.logo img{
    width: 20%;
}

p{
  font-size:0.8rem;
  color:#000000;
  text-align:center
}
</style>
