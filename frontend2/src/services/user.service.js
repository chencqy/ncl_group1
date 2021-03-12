import axios from 'axios'
import authHeader from './auth-header'

// edit this
const API_URL = 'http://localhost:8080/api/test/'
// http://18.132.43.65:8090/

// edit these methods
class UserService {
  // Base level request - for home page
  getPublicContent () {
    // get_default_rooms?
    return axios.get(API_URL + 'all')
  }

  getUserBoard () {
    return axios.get(API_URL + 'user', { headers: authHeader() })
  }

  getModeratorBoard () {
    return axios.get(API_URL + 'mod', { headers: authHeader() })
  }

  getAdminBoard () {
    return axios.get(API_URL + 'admin', { headers: authHeader() })
  }
}

export default new UserService()
