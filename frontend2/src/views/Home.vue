<template>
  <div class="container"><br>
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
    <b-card :title="room" v-for="(room,index) in content" :key="index" class="col-4 d-inline-flex" style="margin:10px">
      <b-card-text>
        <b-button v-on:click="buttonClick(room)" variant="primary">Data</b-button>
      </b-card-text>
      </b-card>
  </div>
</template>

<script>
import UserService from '../services/user.service'

export default {
  name: 'Home',
  data () {
    return {
      content: '',
      metrics: '',
      display: false // display table
    }
  },
  methods: {
    buttonClick (room) {
      var roomURL = room.replaceAll(' ', '-').toLowerCase()
      // This is bad, need to more securely define role in user service rather than define here?
      var role = 'public'
      UserService.getRoomMetric(roomURL, role).then(
        response => {
          if (response.data.metrics.length === 0) {
            this.$toast('No data to display')
            this.metrics = ''
            this.display = false
          } else {
            this.metrics = response.data.metrics
            this.display = true
          }
          document.location.href = '#'
        }
      )
    }
  },
  mounted () {
    UserService.getPublicContent().then(
      response => {
        this.content = response.data.public
      },
      error => {
        this.content =
          (error.response && error.response.data) ||
          error.message ||
          error.toString()
      }
    )
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
