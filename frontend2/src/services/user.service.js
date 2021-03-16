import axios from 'axios'
import authHeader from './auth-header'

// edit this
// const API_URL = 'http://localhost:8090'
const API_URL = 'http://18.132.43.65:8090/'
// const UNI_URL = 'https://api.usb.urbanobservatory.ac.uk/api/v2'
// edit these methods
class UserService {
  // Base level request - for home page
  getPublicContent () {
    // public access
    return axios.get(API_URL + 'get_default_rooms')
  }

  getUserBoard () {
    return axios.get(API_URL + 'user', { headers: authHeader() })
  }

  getModeratorBoard () {
    return axios.get(API_URL + 'mod', { headers: authHeader() })
  }

  // Graph page?
  getAdminBoard () {
    return axios.get(API_URL + 'admin', { headers: authHeader() })
  }
  // var url = 'https://api.usb.urbanobservatory.ac.uk/api/v2/sensors/timeseries/' + this.apiRoom + '/' + this.apiMetric + '/raw/historic?startTime=' + this.time1.getFullYear() + '-' + this.time1.getMonth() + '-' + this.time1.getDate() + 'T00:00:00Z&endTime=' + this.time2.getFullYear() + '-' + this.time2.getMonth() + '-' + this.time2.getDate() + 'T23:59:59'

  // get roominfo method?
  getRoomMetric (room, metric, start, end) {
    // var endpoint = '/sensors/timeseries/' + room + '/' + metric + '/raw/historic?startTime=2019-05-27T00:00:00Z&endTime=2019-05-29T23:59:59'
    var endpoint = 'get_timeseries/' + room + '/' + metric + '/' + start + '/' + end
    console.log(API_URL + endpoint)
    return axios.get(API_URL + endpoint)
  }
}

export default new UserService()
