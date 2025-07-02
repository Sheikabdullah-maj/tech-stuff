export function parseJwt(token) {
  if (!token) { return }
  console.log('token to be parsed '+token);
  const base64Url = token.split('.')[1]
  console.log('base64Url '+base64Url);
  const base64 = base64Url.replace('-', '+').replace('_', '/')
  console.log('base64 '+base64);
  return JSON.parse(window.atob(base64))
}

export const handleLogError = (error) => {
  if (error.response) {
    console.log(error.response.data)
  } else if (error.request) {
    console.log(error.request)
  } else {
    console.log(error.message)
  }
}