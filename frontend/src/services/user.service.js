import axios from 'axios'
import authHeader from './auth-header'

// edit this
// const API_URL = 'http://localhost:8090'
const API_URL = 'http://18.132.43.65:8090/'

// const UNI_URL = 'https://api.usb.urbanobservatory.ac.uk/api/v2'
class UserService {
  // Base level request - for home page
  getPublicContent () {
    return axios.get(API_URL + 'api/public/get_default_rooms')
  }

  getStudentBoard () {
    return axios.get(API_URL + 'api/student/get_default_rooms', { headers: authHeader() })
  }

  getResearchBoard () {
    return axios.get(API_URL + 'api/researcher/get_default_rooms', { headers: authHeader() })
  }

  // Graph page?
  getAdminBoard () {
    return axios.get(API_URL + 'api/admin/get_default_rooms', { headers: authHeader() })
  }

  getRoomMetricSeries (room, metric, start, end) {
    // var endpoint = '/sensors/timeseries/' + room + '/' + metric + '/raw/historic?startTime=2019-05-27T00:00:00Z&endTime=2019-05-29T23:59:59'
    console.log('APPROVED')
    var role2 = 'admin'
    var endpoint = 'api/' + role2 + '/get_timeseries/' + room + '/' + metric + '/' + start + '/' + end
    console.log(API_URL + endpoint)
    return axios.get(API_URL + endpoint, { headers: authHeader() })
  }

  getRoomMetric (room, role) {
    var endpoint = 'api/' + role + '/get_data/' + room
    return axios.get(API_URL + endpoint, { headers: authHeader() })
  }

  // Run when Dashboard.vue is loaded after login
  getRole (role) {
    if (role.includes('ROLE_BuildingManager,')) {
      role = 'admin'
    } else if (role.includes('ROLE_Researcher,')) {
      role = 'researcher'
    } else if (role.includes('ROLE_Student,')) {
      role = 'student'
    } else role = 'ERROR'
    return role
  }
}

export default new UserService()
