<template>
        <div class="main">
          <br>
          <!------------------------------------------------------------------------------------------------------->
          <form name="form1" id="form" action="/action_page.php">
            Floor:
            <select name="subject" v-model="floorSelect" @change="onChangeFloor($event)">
              <option  v-for="(floor) in floors" :key="floor.index" :value="floor.index" >{{floor.number}}</option>
            </select>
            <br /><br/>
          </form>
          <!--  ----------------USER FILTERED RESULT------------------------------------------------------------------------------------->
          <form name="form2" id="form" action="/action_page.php">
            Room:
            <select name="subject" v-model="roomSelect"  @change="onChangeRoom($event)">
              <option  v-for="(room,index) in filterResult" :key="index" :value="room" >{{room}}</option>
            </select>
            <br /><br/>
          </form>
          <form name="form3" id="form" action="/action_page.php">
            Metric:
            <select name="subject" v-model="metricSelect"  @change="onChangeMetric($event)">
              <option  v-for="(metric,index) in metrics" :key="index" :value="metric.name" >{{metric.name}}</option>
            </select>
          </form>
          <date-picker v-model="time1" type="date" @change="onChangeDate($event)" format="YYYY-MM-DD"></date-picker>
          <date-picker v-model="time2" type="date" @change="onChangeDate($event)" format="YYYY-MM-DD"></date-picker>
          <br>
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
            <br>
        </div>
</template>

<script>
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/index.css'
import UserService from '../services/user.service'

export default {
  components: { DatePicker },
  name: 'Room',
  data () {
    return {
      //
      showgraph: false,
      graph_data: null,
      x_axis: [],
      y_axis: [],
      content: {
        datasets: []
      },
      labels: [],
      time1: null, // selected time
      time2: null, // selected time
      time: { time1: { date: '', month: '', year: '' }, time2: { date: '', month: '', year: '' }, start: '', end: '' },
      floorSelect: null,
      roomSelect: null,
      metricSelect: null,
      apiRoom: null,
      apiMetric: null,
      rooms: null,
      metrics: null, // metrics for selected rooms
      filterResult: null, // Used for floor change
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
    createInput () {
      UserService.getAdminBoard().then(response => {
        this.rooms = response.data.admin
      })
    },
    // When select option changes, cut array ro return rooms
    onChangeFloor (event) {
      this.filterResult = this.rooms[event.target.value].rooms
    },
    // When room changes, request metrics from that room
    onChangeRoom (event) {
      this.apiRoom = event.target.value.replaceAll(' ', '-').toLowerCase()
      var role = UserService.getRole(this.currentUser.user.power)
      UserService.getRoomMetric(this.apiRoom, role).then(response => {
        this.metrics = response.data.metrics
      })
    },
    // Next two methods could be written better, a lot of repeating code for getRoomMetricSeries
    // Currently the graph will update when you change date/metric on the same room but wont update if you change room/floor
    // This would be more difficult to manage as each room has different metrics
    onChangeMetric (event) {
      this.showgraph = false
      this.apiMetric = event.target.value.replaceAll(' ', '-').toLowerCase()
      if (this.time1 !== null && this.time2 !== null) {
        var role = UserService.getRole(this.currentUser.user.power)
        UserService.getRoomMetricSeries(this.apiRoom, this.apiMetric, this.time.start, this.time.end, role).then(response => {
          if (this.graph_data === null) {
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
    onChangeDate () {
      this.showgraph = false
      this.time.time1.date = this.changeNumber(this.time1.getDate())
      this.time.time1.month = this.changeNumber(this.time1.getMonth().valueOf() + 1)
      this.time.time1.year = this.time1.getFullYear()
      this.time.start = String(this.time.time1.year + '-' + this.time.time1.month + '-' + this.time.time1.date)

      this.time.time2.date = this.changeNumber(this.time2.getDate())
      this.time.time2.month = this.changeNumber(this.time2.getMonth().valueOf() + 1)
      this.time.time2.year = this.time2.getFullYear()
      this.time.end = String(this.time.time2.year + '-' + this.time.time2.month + '-' + this.time.time2.date)

      if (this.apiMetric !== null) {
        var role = UserService.getRole(this.currentUser.user.power)
        UserService.getRoomMetricSeries(this.apiRoom, this.apiMetric, this.time.start, this.time.end, role).then(response => {
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
      }
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
    // Returns date or month as 00
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
