const prod = {
  url: {
    API_BASE_URL: 'http://localhost:8080/quiz',
  }
}

const dev = {
  url: {
    API_BASE_URL: 'http://localhost:8080/quiz'
  }
}

export const config = process.env.NODE_ENV === 'development' ? dev : prod