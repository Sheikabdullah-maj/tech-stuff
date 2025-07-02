import axios from 'axios'
import { config } from '../../Constants'
import { parseJwt } from '../../Helpers'
import { useAuth } from '../context/AuthContext'

export const libmasApi = {
  authenticate,
  getBookList,
  getAllCategories,
  saveBook,
  getBookDetailsById,
  saveCategory
}


function authenticate(username, password) {
  return instance.post('/authenticate', { username, password }, {
    headers: { 'Content-type': 'application/json' }
  })
}


function getAllCategories(user){
  console.log('get all catg api');
  const url = '/books/getAllCategories'  
  return instance.get(url, {
    headers: { 'Authorization': bearerAuth(user),
      'Content-Type': 'application/json'
     }
  })

}

function saveBook(user,requestData){
  return instance.post('/books/saveBook', JSON.stringify(requestData), {
    headers: {
      'Content-type': 'application/json',
      'Authorization': bearerAuth(user)
    }
  });
}

function getBookDetailsById(user, id){
  if(id != undefined && id.length >0){
  console.log('Book details will be fetched for Book Id:'+id);
  const url = '/books/getBookDetails/'+id;
  return instance.get(url, {
    headers: { 'Authorization': bearerAuth(user)
     }
  }) 
}
}

function saveCategory(user,requestData){
  return instance.post('/books/saveCategory', JSON.stringify(requestData), {
    headers: {
      'Content-type': 'application/json',
      'Authorization': bearerAuth(user)
    }
  });
}



function getBookList(user, requestData,pageNumber,pageSize ){
  return instance.post('/books/listBooks?pageNumber='+((pageNumber))+'&pageSize='+(pageSize), requestData, {
    headers: {
      'Content-type': 'application/json',
      'Authorization': bearerAuth(user)
    }
  })
}

// -- Axios

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

// -- Helper functions

function bearerAuth(user) {
  return `Bearer ${user.jwtToken}`
}