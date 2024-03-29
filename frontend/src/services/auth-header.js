export default function authHeader () {
  const user = JSON.parse(localStorage.getItem('user'))

  // If there is a logged in user with accessToken (JWT), return HTTP Authorization header.
  // Otherwise, return an empty object.
  if (user && user.accessToken) {
    // Bearer token = value that goes into the authorization header of any  HTTP requests
    return { Authorization: 'Bearer ' + user.accessToken }
  } else {
    return {}
  }
}
