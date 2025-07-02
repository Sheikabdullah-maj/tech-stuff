import React from 'react';
import { BrowserRouter as Router, Route, Routes, Navigate } from 'react-router-dom';
import AppHeaderSection from './components/AppHeaderSection';
import SideBarComponent from './components/SideBarComponent';
import BookList from './components/books/BookList';
import BookMainComponent from './components/books/BookMainComponent';
import Box from '@mui/material/Box';
import MemberMaintenance from './components/membership/MemberMaintenance';
import Home from './components/Home';
import LendingLayout from './components/lending/LendingLayout';
import Login from './components/security/Login';
import PrivateRoute from './components/util/PrivateRoute';
import { AuthProvider } from './components/context/AuthContext';

function App() {

  const [drawerOpen, setDrawerOpen] = React.useState(false);
  const [activetoken, setActiveToken] = React.useState(null);
  const toggleDrawer = (newOpen) => {
    setDrawerOpen(newOpen);
  };

  function updateTokenInStorage(tkn) {
     localStorage.setItem('libmasToken',tkn);
     setActiveToken(tkn);
  }

  function loadToken(){
    setActiveToken(localStorage.getItem('libmasToken'));
  }


  return (
    <AuthProvider>
  <Box>
      <AppHeaderSection toggleDrawer={toggleDrawer}/>
      
      <Router>
      <Box sx={{ display: 'flex', flexDirection: 'column', padding: 3 }}>
      <SideBarComponent toggleDrawer={toggleDrawer} stateIndicator={drawerOpen} />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/book" element={<PrivateRoute><BookMainComponent /></PrivateRoute>} />
            <Route path="/member" element={<PrivateRoute><MemberMaintenance/></PrivateRoute>} />
            <Route path="/lend" element={<PrivateRoute><LendingLayout/></PrivateRoute>} />
            <Route path="*" element={<Navigate to="/" />} />
          </Routes>
        </Box>
        </Router>  
      
      
      </Box>
      </AuthProvider>
  );
}

export default App;
