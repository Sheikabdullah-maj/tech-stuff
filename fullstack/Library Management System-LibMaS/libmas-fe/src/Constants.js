const prod = {
  url: {
    API_BASE_URL: 'https://libmas-api.onrender.com',
  }
}

const dev = {
  url: {
    API_BASE_URL: 'https://libmas-api.onrender.com'
  }
}

export const config = process.env.NODE_ENV === 'development' ? dev : prod