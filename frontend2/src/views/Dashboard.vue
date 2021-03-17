<template>
    <div class="dashboard">
        <b-container>
            <b-row cols="1">
                <b-col>{{currentUser.user.power}}</b-col>
                <b-col>
                  <b-card
                    :title="room"
                    v-for="(room,index) in content" :key="index">
                    <b-card-text>
                      {{room}}
                    </b-card-text>

                    <b-button href="#" variant="primary">Go somewhere</b-button>
                  </b-card>
                </b-col>
            </b-row>
        </b-container>
  </div>

</template>

<script>
// import { response } from 'express'
import UserService from '../services/user.service'

/* eslint-disable */ 
export default {
  name: 'Dashboard',
  data () {
    return {
      content: ''
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
</style>
