import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import WatchDogGrid from '../components/WatchDogGrid';


function Layout() {
    return (
        <AppBar>
          <Toolbar>
            <Typography variant="h3" component="div">
              People Watchdog
            </Typography>
          </Toolbar>
        </AppBar>
    );
}

export default Layout;