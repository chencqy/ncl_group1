<template>
    <div class="dashboard">
        <br>
        <br>
        <!--Graph feature for admin -->
        <roomComp v-if="this.currentUser && this.currentUser.user.power.includes('ROLE_BuildingManager,')"></roomComp>
        <b-container>
            <b-row>
                <b-col>
                  <table class="metricTable" v-if="display">
                    <tr>
                        <th>Metric</th>
                        <th>Value</th>
                    </tr>
                    <tr class="dataInf" v-for="(metric,index) in metrics" :key="index">
                      <td>{{metric.name}}</td>
                      <td>{{metric.value}}</td>
                    </tr>
                  </table>
                  <br>
                  <b-card>
                    <b-card
                      :title="room"
                      v-for="(room,index) in cardData" :key="index"
                      class="col-4 d-inline-flex" style="margin:10px">
                      <b-card-text>
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
import UserService from '../services/user.service'
import Room from '../components/room'
/* eslint-disable */ 
export default {
  components: {
    'roomComp': Room,
  },
  name: 'Dashboard',
  data () {
    return {
      content: '',
      metrics: '',
      display: false
    }
  },  
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    },
    // Api returns data with all floors first in json when admin, dont want this displayed in cards
    cardData () {
      if (this.currentUser.user.power.includes('ROLE_BuildingManager,')) {
        return this.content.slice(8)
      } else
      return this.content
    }
  },
  methods: {
    logOut () {
      this.$store.dispatch('auth/logout')
      this.$router.push('/login')
    },
    linkGen(pageNum) {
      return pageNum === 1 ? '?' : `?page=${pageNum}`
    },
    // Board methods
    showStudentBoard () {
      if (this.currentUser && this.currentUser.user.power.includes('ROLE_Student,')) {
        UserService.getStudentBoard().then(
          response => {
            // console.log(response.data.student)
            this.content = response.data.student
          } 
        ) 
      }
    },
    showResearchBoard () {
      if (this.currentUser && this.currentUser.user.power.includes('ROLE_Researcher,')) {
        UserService.getResearchBoard().then(
          response => {
            // console.log(response.data.student)
            this.content = response.data.researcher
          } 
        ) 
      }
    },
    showAdminBoard () {
      if (this.currentUser && this.currentUser.user.power.includes('ROLE_BuildingManager,')) {
        UserService.getAdminBoard().then(
          response => {
            // console.log(response.data.student)
            this.content = response.data.admin
          } 
        ) 
      }
    },
    buttonClick(room){
      var roomURL = room.replaceAll(' ', '-').toLowerCase()
      var role = UserService.getRole(this.currentUser.user.power)
        UserService.getRoomMetric(roomURL , role).then(
          response => {
            if (response.data.metrics.length === 0 ){
              this.$toast('No data to display');
              this.metrics = ''
              this.display = false
            } else {
                this.metrics = response.data.metrics
                this.display = true
            }
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

.metricTable {
  margin-left: auto;
  margin-right: auto;
  border-collapse: collapse;
  font-size: 0.9em;
  font-family: sans-serif;
  min-width: 400px;
  box-shadow: 0 0 20px rgba(0, 0, 0, 0.15);
}

.metricTable th,
.metricTable td {
    padding: 12px 15px;
}

.metricTable th {
    background-color: #42b98370;
    color: #ffffff;
    text-align: left;
}
</style>
