import * as React from 'react';
import Grid from '@mui/material/Grid';
import Paper from '@mui/material/Paper';
import { Avatar, Button, Link, TextField, Typography } from '@mui/material';
import LockOutlinedIcon from '@mui/icons-material/LockOutlined';
import { NavLink, Navigate } from 'react-router-dom'
import LibraryBooksIcon from '@mui/icons-material/LibraryBooks';
// import { Button, Form, Grid, Segment, Message } from 'semantic-ui-react'
import { parseJwt, handleLogError } from '../../Helpers'
import { useAuth } from '../context/AuthContext';
import { libmasApi } from '../apiconsumer/LibmasApi';
import { useState } from 'react';

function Login() {
  const Auth = useAuth()
  const isLoggedIn = Auth.userIsAuthenticated()

  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [isError, setIsError] = useState(false)

  function handleInputChange(e) {
        const { name, value } = e.target;
        if (name === 'username') {
            setUsername(value)
          } else if (name === 'password') {
            setPassword(value)
          }
   }


  

  const handleSubmit = async (e) => {
    e.preventDefault()

    if (!(username && password)) {
      setIsError(true)
      return
    }

    try {
      const response = await libmasApi.authenticate(username, password)
      const { jwtToken } = response.data
      console.log('response jwt token'+ jwtToken);
      const data = parseJwt(jwtToken)
      console.log('parsed jwt token'+ data);
      const authenticatedUser = { data, jwtToken }

      Auth.userLogin(authenticatedUser)

      setUsername('')
      setPassword('')
      setIsError(false)
    } catch (error) {
      handleLogError(error)
      setIsError(true)
    }
  }

  if (isLoggedIn) {
    return <Navigate to={'/'} />
  }

      const paperStyle={padding :20,height:'70vh',width:280,margin:"19px auto" ,backgroundColor:'#E6F4F1', borderRadius: '12px', boxShadow: '0px 0px 8px rgba(0, 0, 0, 25)'}
    const avatarStyle={backgroundColor:'#D9D9D9'}
    const btnstyle={backgroundColor:'#1B6DA1',margin:'12px 0'}
    const logoStyle={backgroundColor:'#D9D9D9', margin:'10px 0', width: 70, height: 70}


      return(
        
          <Grid>
                  <Grid align='center'>
                      <Avatar style={logoStyle}><LibraryBooksIcon style={{ color: '#002A57', width: 56, height: 56}}/></Avatar>
                      <h2>LibMaS</h2>
                  </Grid>  
               
              
              <Paper elavation={12} style={paperStyle}>
                  <Grid align='center'>
                      <Avatar style={avatarStyle}><LockOutlinedIcon style={{ color: '#002A57' }}/></Avatar>
                      <h2>Login</h2>
                  </Grid>
                  <TextField id="username" name="username" label="Username" variant="standard" placeholder='Enter Your Username' fullWidth required onChange={handleInputChange}/>
                  <TextField id="password" name="password" label="Password" variant="standard" placeholder='Enter Your Password' type='password' fullWidth required onChange={handleInputChange}/>
                  <Button style={btnstyle} type='submit' color='primary' variant="contained" fullWidth onClick={handleSubmit}>Login</Button>
              </Paper>
          </Grid>
          
      )
    
} 

// const Login=({saveToken})=>{

//     const paperStyle={padding :20,height:'70vh',width:280,margin:"19px auto" ,backgroundColor:'#E6F4F1', borderRadius: '12px', boxShadow: '0px 0px 8px rgba(0, 0, 0, 25)'}
//     const avatarStyle={backgroundColor:'#D9D9D9'}
//     const btnstyle={backgroundColor:'#1B6DA1',margin:'12px 0'}
//     const logoStyle={backgroundColor:'#D9D9D9', margin:'10px 0', width: 70, height: 70}
//     const [formData, setFormData] = React.useState({
//         "username" : "",
//         "password" : ""
//     });
    
//     function handleInputChange(e) {
//         const { name, value } = e.target;
//         setFormData(formData => {return {
//           ...formData,
//           [name]: value, // Update the specific form field by name
//         }});
//       };


//     function userLogin(){
//         console.log('formData:'+JSON.stringify(formData));
//         fetch('http://localhost:8080/authenticate',
//             {
//                 method: 'POST',
//                 headers: {
//                   'Content-Type': 'application/json',
//                 },
//                 body: JSON.stringify(formData),
//               }
//         )
//     .then(response => response.json())
//     .then(data => {saveToken(data);})
//     .catch(error => console.error('Error fetching data:', error));
//     }    

//     return(
        
//         <Grid>
//                 <Grid align='center'>
//                     <Avatar style={logoStyle}><LibraryBooksIcon style={{ color: '#002A57', width: 56, height: 56}}/></Avatar>
//                     <h2>LibMaS</h2>
//                 </Grid>  
             
            
//             <Paper elavation={12} style={paperStyle}>
//                 <Grid align='center'>
//                     <Avatar style={avatarStyle}><LockOutlinedIcon style={{ color: '#002A57' }}/></Avatar>
//                     <h2>Login</h2>
//                 </Grid>
//                 <TextField id="username" name="username" label="Username" variant="standard" placeholder='Enter Your Username' fullWidth required onChange={handleInputChange}/>
//                 <TextField id="password" name="password" label="Password" variant="standard" placeholder='Enter Your Password' type='password' fullWidth required onChange={handleInputChange}/>
//                 <Button style={btnstyle} type='submit' color='primary' variant="contained" fullWidth onClick={userLogin}>Login</Button>
//             </Paper>
//         </Grid>
        
//     )
// }

export default Login