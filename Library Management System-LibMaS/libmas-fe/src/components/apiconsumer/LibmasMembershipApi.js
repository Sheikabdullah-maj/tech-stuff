import axios from 'axios'
import { config } from '../../Constants'
import { parseJwt } from '../../Helpers'
import { useAuth } from '../context/AuthContext'

export const libmasMembershipApi = {
  getAllMembers,
  saveMember,
  deleteMember
}

function getAllMembers(user, requestData, pageNumber, pageSize){
    const url = '/member/getAllMembers?pageNumber='+((pageNumber))+'&pageSize='+(pageSize)  
    return instance.post(url, 
      JSON.stringify(requestData),
      {
      headers: { 'Authorization': bearerAuth(user),
        'Content-Type': 'application/json'
       }
    })
  
  }
  
  function saveMember(user,requestData){
    return instance.post('/member/saveMember', JSON.stringify(requestData), {
      headers: {
        'Content-type': 'application/json',
        'Authorization': bearerAuth(user)
      }
    });
  }

  function deleteMember(user,memberId){
    return instance.delete('member/deleteMember/'+memberId, {
      headers: {
        'Content-type': 'application/json',
        'Authorization': bearerAuth(user)
      }
    });
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