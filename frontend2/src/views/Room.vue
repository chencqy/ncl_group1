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
              <!--Do we need :value here -->
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
          <date-picker v-model="time1" type="date" @change="onChangeDate()" format="YYYY-MM-DD"></date-picker>
          <date-picker v-model="time2" type="date" format="YYYY-MM-DD"></date-picker>
            <b-row>
                <b-col>
                <vue-frappe v-if="showgraph"
                  id="test"
                  type="line"
                  :height="500"
                  :labels="labels[0].values"
                  :lineOptions="{regionFill: 1}"
                  :colors="['red']"
                  :dataSets="co2.datasets"
                ></vue-frappe>
                </b-col>
            </b-row>
        </div>
</template>

<script>
import axios from 'axios'
import DatePicker from 'vue2-datepicker'
import 'vue2-datepicker/index.css'

export default {
  components: { DatePicker },
  name: 'Room',
  data () {
    return {
      showgraph: false,
      graph_data: null,
      x_axis: [],
      y_axis: [],
      co2: {
        datasets: []
      },
      labels: [],
      time1: null,
      time2: null,
      floorSelect: null,
      apiRoom: null,
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
  async mounted () {
    await this.createInput()
  },
  methods: {
    // Load room names from CSV file, stored in rooms.data
    createInput () {
      var url = 'https://raw.githubusercontent.com/tomrob1/Prototype/main/src/assets/allTheRooms.json'
      axios.get(url)
        .then(response => {
          this.rooms = response.data
        })
    },
    // When select option changes, cut array ro return rooms
    onChangeFloor (event) {
      // console.log(event.target.value)
      this.filterResult = this.rooms[event.target.value].rooms
    },
    // tidy up
    onChangeRoom (event) {
      // console.log(event.target.value)
      var res = event.target.value.replaceAll(' ', '-')
      var res2 = event.target.value.replaceAll(' ', '-').toLowerCase()
      this.apiRoom = res2
      // console.log(res2)

      var url = 'http://18.132.43.65:8090/get_data/' + res
      // console.log(url)
      axios.get(url)
        .then(response => {
          this.metrics = response.data.metrics
        // this.metrics = response.data.metircs
        })
    },
    // Need to change room value to fit USB uni api
    // room-6.025
    onChangeMetric (event) {
      this.showgraph = false
      var res = event.target.value.replaceAll(' ', '-').toLowerCase() // Hyphenate and lowercase metric value
      // Need to add date part
      console.log(this.time1.toTimeString())
      var url = 'https://api.usb.urbanobservatory.ac.uk/api/v2/sensors/timeseries/' + this.apiRoom + '/' + res + '/raw/historic?startTime=2019-05-27T00:00:00Z&endTime=2019-05-29T23:59:59'
      // need to parse date/month so that single digit is souble eg 04
      // var url = 'https://api.usb.urbanobservatory.ac.uk/api/v2/sensors/timeseries/' + this.apiRoom + '/' + res + '/raw/historic?startTime=' + this.time1.getFullYear() + '-' + this.time1.getMonth() + '-' + this.time1.getDate() + 'T00:00:00Z&endTime=' + this.time2.getFullYear() + '-' + this.time2.getMonth() + '-' + this.time2.getDate() + 'T23:59:59'
      // 2019-05-27
      console.log(url)
      axios.get(url)
        .then(response => {
          // console.log(response.data.historic)
          if (this.graph_data == null) {
            this.graph_data = response.data.historic.values
            var type = response.data.timeseries.parentFeed.metric
            console.log(type)
          } else {
            // need to clear up the variables used for the graph
            this.graph_data = []
            this.x_axis = []
            this.y_axis = []
            this.labels = []
            this.co2.datasets = []
            this.graph_data = response.data.historic.values
          }
          this.setGraphData()
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
      // add if statemtent here for if graph data is empty?
      this.co2.datasets.push({ values: this.y_axis })
      this.labels.push({ values: this.x_axis })
      this.showgraph = true
    },
    onChangeDate (event) {
      console.log(this.time1.getDate())
      console.log(this.time1.getMonth())
      console.log(this.time1.getFullYear())
    }
  }

}
</script>
