import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import LogoutIcon from '@mui/icons-material/Logout';


function AppHeaderSection({toggleDrawer}) {
    return (
        <Box sx={{ flexGrow: 1,position: 'relative', top: 0, left: 0 }}>
        <AppBar>
          <Toolbar>
            <IconButton
              size="large"
              edge="start"
              color="inherit"
              aria-label="menu"
              // sx={{ mr: 2 }}
              onClick={()=>toggleDrawer(true)}
            >
              <MenuIcon />
            </IconButton>
            <Typography variant="h3" component="div">
              LibMaS
            </Typography>
            <Button color="inherit">Admin</Button>
          </Toolbar>
        </AppBar>
      </Box>
    );
}

export default AppHeaderSection;