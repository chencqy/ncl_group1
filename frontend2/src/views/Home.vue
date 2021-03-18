<template>
  <div class="container">
    <br>
    <tr v-for="(metric,index) in metrics" :key="index">{{metric.name}}:{{metric.value}}</tr>
    <br>
    <!--<p v-for="(room,index) in content" :key="index">{{room.name}}</p>-->
    <!--<p v-for="(metrics,index) in content" :key="index">{{content[0].metrics}}</p>-->
  <b-card
    :title="room"
    v-for="(room,index) in content" :key="index">
    <b-card-text>
    </b-card-text>
    <b-button  v-on:click="buttonClick(room)" variant="primary">Data</b-button>
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
      metrics: ''
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
          console.log(response)
          this.metrics = response.data.metrics
        }
      )
    }
  },
  mounted () {
    UserService.getPublicContent().then(
      response => {
        console.log(response)
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
</style>
