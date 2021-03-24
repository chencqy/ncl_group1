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

  // Called to show cards and fill dropdowns for graph component
  getAdminBoard () {
    return axios.get(API_URL + 'api/admin/get_default_rooms', { headers: authHeader() })
  }

  // This is only called in admin board, but the role parameter means this could be used for other access levels
  getRoomMetricSeries (room, metric, start, end, role) {
    var endpoint = 'api/' + role + '/get_timeseries/' + room + '/' + metric + '/' + start + '/' + end
    return axios.get(API_URL + endpoint, { headers: authHeader() })
  }

  // Called by data button
  getRoomMetric (room, role) {
    var endpoint = 'api/' + role + '/get_data/' + room
    return axios.get(API_URL + endpoint, { headers: authHeader() })
  }

  // Run when Dashboard.vue is loaded after login
  // This method exists because the user database returns roles as ROLE_, while the API we created uses role paramaters:
  // admin ,researcher, student, public
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
