<template>
    <div class="dashboard">
        <b-container>
            <b-row cols="4">
                <b-col>{{currentUser.user.power}}</b-col>
                <b-col>Column</b-col>
                <b-col>Column</b-col>
                <b-col>Column</b-col>
                <b-col>Column</b-col>
                <b-col>Column</b-col>
                <b-col>Column</b-col>
                <b-col>Column</b-col>
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
    /* currentUser () {
      return this.$store.state.auth.user
    },
    showAdminBoard () {
      if (this.currentUser && this.currentUser.user.power) {
      }
      return false
    },
    showResearcherBoard () {
      if (this.currentUser && this.currentUser.user.power) {
      }
      return false
    },
    showStudentBoard () {
      if (this.currentUser && this.currentUser.user.power) {
      }
      return false
    } */
  },
  // Add logout button?
  methods: {
    logOut () {
      this.$store.dispatch('auth/logout')
      this.$router.push('/login')
    }
  },
  // get power here then call user service?
  mounted () {
    UserService.runBoard(this.currentUser.user.power).then(
      response => {
        console.log(response)
        this.content = response.data

      },
      error => {
        this.content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString()
      }
    )
    console.log(this.currentUser.user.power)
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
