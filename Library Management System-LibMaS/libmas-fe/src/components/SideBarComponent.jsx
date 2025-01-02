import * as React from 'react';
import Box from '@mui/material/Box';
import Drawer from '@mui/material/Drawer';
import Divider from '@mui/material/Divider';
import SideBarMenuItem from './SideBarMenuItem';
import { useAuth } from './context/AuthContext';

function SideBarComponent({stateIndicator,toggleDrawer}) {
    
  const { userLogout} = useAuth();

  const DrawerList = (
    <Box role="presentation" onClick={() =>toggleDrawer(false)}>
        <SideBarMenuItem menuLabel={'Books & Category'} linkTo='/book'/>
      <Divider />
        <SideBarMenuItem menuLabel={'Member'} linkTo='/member'/>
        <Divider/>
        <SideBarMenuItem menuLabel={'Issue/Collection'} linkTo='/lend'/>
        <Divider/>
        <SideBarMenuItem menuLabel={'Logout'} onClickCallback={userLogout}/>
      <Divider/>
    </Box>
  );

  return (
    <div>
      <Drawer open={stateIndicator} onClose={() =>toggleDrawer(false)}>
        {DrawerList}
      </Drawer>
    </div>
  );
}


export default SideBarComponent;