import axios from 'axios'

//const API_URL = 'http://18.132.43.65:8090/'
const API_URL = 'http://localhost:8090/'

axios.defaults.withCredentials = true

class AuthService {
  login (user) {
    return axios
    // Add login endpoint
      .post(API_URL + 'api/login', {
        username: user.username,
        password: user.password
      })
      .then(response => {
        if (response.data.accessToken) {
          localStorage.setItem('user', JSON.stringify(response.data))
        }

        return response.data
      })
  }

  logout () {
    localStorage.removeItem('user')
  }

  // do we need register
  register (user) {
    return axios.post(API_URL + 'signup', {
      username: user.username,
      email: user.email,
      password: user.password
    })
  }
}

export default new AuthService()
