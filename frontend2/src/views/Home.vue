<template>
  <div class="container">
    <!--<p v-for="(room,index) in content" :key="index">{{room.name}}</p>-->
    <!--<p v-for="(metrics,index) in content" :key="index">{{content[0].metrics}}</p>-->
  <b-card
    :title="room"
    v-for="(room,index) in content" :key="index">
    <b-card-text>
      {{room}}
    </b-card-text>

    <b-button href="#" variant="primary">Go somewhere</b-button>
  </b-card>
  </div>
</template>

<script>
import UserService from '../services/user.service'

export default {
  name: 'Home',
  data () {
    return {
      content: ''
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
