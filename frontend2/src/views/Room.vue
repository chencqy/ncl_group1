<template>
        <div class="main">
          <br>
          <!------------------------------------------------------------------------------------------------------->
          <form name="form1" id="form" action="/action_page.php">
            Floor:
            <select name="subject" v-model="floorSelect" @change="onChangeFloor($event)">
              <option  v-for="(floor) in floors" :key="floor.index" :value="floor.index" >{{floor.number}}</option>
              <!-- For loop loops through all the available room numbers and inputs these into the dropdown - these should be limited for each person, general public should only have access to first floor, students to all available rooms up to 3rd floor, staff/phd rooms to 4th floor, manager/building supervisor has access to all rooms including receptions  -->
            </select>
            <br /><br/>
          </form>
          <!--  ----------------USER FILTERED RESULT------------------------------------------------------------------------------------->
          <form name="form2" id="form" action="/action_page.php">
            Room:
            <select name="subject" v-model="roomSelect"  @change="onChangeRoom($event)">
              <option  v-for="(room,index) in filterResult" :key="index" :value="room" >{{room}}</option>
              <!-- For loop loops through all the available room numbers and inputs these into the dropdown - these should be limited for each person, general public should only have access to first floor, students to all available rooms up to 3rd floor, staff/phd rooms to 4th floor, manager/building supervisor has access to all rooms including receptions  -->
            </select>
            <br /><br/>
          </form>
          <form name="form3" id="form" action="/action_page.php">
            Metric:
            <select name="subject" v-model="metricSelect"  @change="onChangeMetric($event)">
              <!--Do we need :value here -->
              <option  v-for="(metric,index) in metrics" :key="index" :value="metric.name" >{{metric.name}}</option>
              <!-- For loop loops through all the available room numbers and inputs these into the dropdown - these should be limited for each person, general public should only have access to first floor, students to all available rooms up to 3rd floor, staff/phd rooms to 4th floor, manager/building supervisor has access to all rooms including receptions  -->
            </select>
          </form>
          <date-picker v-model="time1" type="date" @change="onChangeDate($event)" format="YYYY-MM-DD"></date-picker>
          <date-picker v-model="time2" type="date" @change="onChangeDate($event)" format="YYYY-MM-DD"></date-picker>
            <b-row>
                <b-col>
                <vue-frappe v-if="showgraph"
                  id="test"
                  type="line"
                  :height="500"
                  :labels="labels[0].values"
                  :lineOptions="{regionFill: 1}"
                  :colors="['red']"
                  :dataSets="content.datasets"
                ></vue-frappe>
                </b-col>
            </b-row>
        </div>
</template>

<script>
import axios from 'axios'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/index.css'
import UserService from '../services/user.service'
// import { response } from 'express'

export default {
  components: { DatePicker },
  name: 'Room',
  data () {
    return {
      showgraph: false,
      graph_data: null,
      x_axis: [],
      y_axis: [],
      content: {
        datasets: []
      },
      labels: [],
      time1: null,
      time2: null,
      time: { time1: { date: '', month: '', year: '' }, time2: { date: '', month: '', year: '' }, start: '', end: '' },
      floorSelect: null,
      apiRoom: null,
      apiMetric: null,
      roomSelect: null,
      metricSelect: null,
      rooms: null,
      metrics: null,
      filterResult: null, // Used for floor change
      // store these better?
      floors: [{ number: 'G', index: 0 }, // Options for floor picks
        { number: 1, index: 1 },
        { number: 2, index: 2 },
        { number: 3, index: 3 },
        { number: 4, index: 4 },
        { number: 5, index: 5 },
        { number: 6, index: 6 },
        { number: 'Roof', index: 7 }]
    }
  },
  computed: {
    currentUser () {
      return this.$store.state.auth.user
    }
  },
  async mounted () {
    await this.createInput()
  },
  methods: {
    // Load room names from CSV file, stored in rooms.data
    createInput () {
      // edit to default rooms for user role? or just have room.vue for admin only
      var url = 'https://raw.githubusercontent.com/tomrob1/Prototype/main/src/assets/allTheRooms.json'
      // Temporary fix
      axios({
        method: 'get',
        url: url,
        withCredentials: false
      }).then(response => {
        this.rooms = response.data
      })
      /* axios.get(url)
        .then(response => {
          this.rooms = response.data
        }) */
    },
    // When select option changes, cut array ro return rooms
    onChangeFloor (event) {
      this.filterResult = this.rooms[event.target.value].rooms
    },
    // tidy up
    onChangeRoom (event) {
      this.apiRoom = event.target.value.replaceAll(' ', '-').toLowerCase()
      var role = UserService.getRole(this.currentUser.power)
      UserService.getRoomMetric(this.apiRoom, role).then(response => {
        this.metrics = response.data.metrics
      })
    },
    // Need to change room value to fit USB uni api
    // room-6.025
    onChangeMetric (event) {
      this.showgraph = false
      this.apiMetric = event.target.value.replaceAll(' ', '-').toLowerCase()
      if (this.time1 !== null && this.time2 !== null) {
        UserService.getRoomMetricSeries(this.apiRoom, this.apiMetric, this.time.start, this.time.end, this.currentUser.user.power).then(response => {
          if (this.graph_data === null) {
            console.log(response)
            this.graph_data = response.data.historic
          } else {
            // need to clear up the variables used for the graph
            this.graph_data = []
            this.x_axis = []
            this.y_axis = []
            this.labels = []
            this.content.datasets = []
            this.graph_data = response.data.historic
          }
          this.setGraphData()
        })
      }
    },
    // Need to tidy up
    onChangeDate (event) {
      this.time.time1.date = this.changeNumber(this.time1.getDate())
      this.time.time1.month = this.changeNumber(this.time1.getMonth().valueOf() + 1)
      this.time.time1.year = this.time1.getFullYear()
      // var start = String(this.time.time1.year + '-' + this.time.time1.month + '-' + this.time.time1.date)
      this.time.start = String(this.time.time1.year + '-' + this.time.time1.month + '-' + this.time.time1.date)

      this.time.time2.date = this.changeNumber(this.time2.getDate())
      this.time.time2.month = this.changeNumber(this.time2.getMonth().valueOf() + 1)
      this.time.time2.year = this.time2.getFullYear()
      // var end = String(this.time.time2.year + '-' + this.time.time2.month + '-' + this.time.time2.date)
      this.time.end = String(this.time.time2.year + '-' + this.time.time2.month + '-' + this.time.time2.date)
      // this.setGraphData()

      console.log(this.apiRoom, this.apiMetric, this.time.start, this.time.end, this.currentUser.user.power)
      UserService.getRoomMetricSeries(this.apiRoom, this.apiMetric, this.time.start, this.time.end).then(response => {
        // console.log(response.data.historic)
        if (this.graph_data == null) {
          this.graph_data = response.data.historic
        } else {
          // need to clear up the variables used for the graph
          this.graph_data = []
          this.x_axis = []
          this.y_axis = []
          this.labels = []
          this.content.datasets = []
          this.graph_data = response.data.historic
        }
        if (this.time1 !== null && this.time2 !== null) {
          this.setGraphData()
        }
      })
    },
    setGraphData () {
      // Add to x,y axis, labels and dataset
      let i = 0
      for (i; i < this.graph_data.length; i++) {
        this.y_axis.push(this.graph_data[i].value)
        this.x_axis.push(this.graph_data[i].time)
      }
      this.x_axis.reverse() // puts x-axis in right order
      this.content.datasets.push({ values: this.y_axis })
      this.labels.push({ values: this.x_axis })
      this.showgraph = true
    },
    changeNumber (number) {
      if (number.toString().length === 1) {
        number = '0' + number
      }
      return number
    }
  }

}
</script>
<style>
p{
  font-size:0.8rem;
  color:#000000;
  text-align:center
}
</style>
