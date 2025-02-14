import axios from 'axios'
import { config } from '../../Constants'


export const quizAppApi = {
    getQuestions,
    evaluateSubmission
}

function getQuestions() {
    return instance.get('/getQuestions');
}

function evaluateSubmission(requestData){
    return instance.post('/evaluateSubmission', JSON.stringify(requestData), {
        headers: {
          'Content-type': 'application/json'
        }
      });
}

const instance = axios.create({
    baseURL: config.url.API_BASE_URL
  })
  
