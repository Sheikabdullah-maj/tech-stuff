import axios from 'axios'
import { config } from '../../Constants'
import { parseJwt } from '../../Helpers'
import { useAuth } from '../context/AuthContext'

export const libmasBookIssueApi = {
    issueBook,
    consumeBooksLendedForMember,
    returnBook,
    getBookLendHistory
}

function issueBook(user, requestData) {
    return instance.post('/lend/issue', JSON.stringify(requestData), {
        headers: {
          'Content-type': 'application/json',
          'Authorization': bearerAuth(user)
        }
      });
}

function consumeBooksLendedForMember(user, memberId){
    return instance.get('/lend/getBooksLendedForMember/'+memberId,
        {
            headers: {
                'Content-type': 'application/json',
                'Authorization': bearerAuth(user)
              }
        }
    );
}

function returnBook(user, requestData) {
    return instance.post('/lend/return', JSON.stringify(requestData), {
        headers: {
          'Content-type': 'application/json',
          'Authorization': bearerAuth(user)
        }
      });
}

function getBookLendHistory(user, pageNumber, pageSize) {
  return instance.get('/lend/getBookTrackerRecord?pageNumber='+((pageNumber))+'&pageSize='+(pageSize),
    {
        headers: {
            'Content-type': 'application/json',
            'Authorization': bearerAuth(user)
          }
    }
);
}

const instance = axios.create({
    baseURL: config.url.API_BASE_URL
  })
  

instance.interceptors.request.use(function (config) {
    // If token is expired, redirect user to login
    if (config.headers.Authorization) {
      const token = config.headers.Authorization.split(' ')[1]
      const data = parseJwt(token)
      if (Date.now() > data.exp * 1000) {
        window.location.href = "/login"
      }
    }
    return config
  }, function (error) {
    return Promise.reject(error)
  })

  function bearerAuth(user) {
    return `Bearer ${user.jwtToken}`
  }