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
      <!--<p v-for="(room,index) in content" :key="index">{{room.name}}</p>-->
      <!--<p v-for="(metrics,index) in content" :key="index">{{content[0].metrics}}</p>-->
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
      display: false
    }
  },
  methods: {
    buttonClick (room) {
      var fix = room.replaceAll(' ', '-').toLowerCase()
      console.log(fix)
      var role = 'public'
      // console.log(this.currentUser.user.power)
      console.log(role)
      UserService.getRoomMetric(fix, role).then(
        response => {
          console.log(response.data.metrics)
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
