<template>
  <div class="container">
    <!--<p v-for="(room,index) in content" :key="index">{{room.name}}</p>-->
    <!--<p v-for="(metrics,index) in content" :key="index">{{content[0].metrics}}</p>-->
  <b-card
    :title="room"
    v-for="(room,index) in content" :key="index">
    <b-card-text>
      {{room}}
      <p>{{metrics}}</p>
    </b-card-text>

    <b-button  v-on:click="buttonClick(room)" variant="primary">Go somewhere</b-button>
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
          let i = 0
          // Filter metrics and remove null values
          // use .filter method?
          for (i; i < this.metrics.length; i++) {
            if (this.metrics[i].value === null) {
              console.log('splice')
              // delete this.metrics[i]
              this.metrics.splice(i, i)
              // this.metrics =
            }
          }
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
