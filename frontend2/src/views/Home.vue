<template>
  <div class="container">
    <!--<p v-for="(room,index) in content" :key="index">{{room.name}}</p>-->
    <!--<p v-for="(metrics,index) in content" :key="index">{{content[0].metrics}}</p>-->
  <b-card
    :title="room.name"
    v-for="(room,index) in content" :key="index">
    <b-card-text>
      {{room.name}}
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
        this.content = response.data.rooms
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
