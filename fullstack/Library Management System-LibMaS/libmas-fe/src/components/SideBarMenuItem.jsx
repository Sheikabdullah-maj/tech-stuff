import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemButton from '@mui/material/ListItemButton';
import ListItemIcon from '@mui/material/ListItemIcon';
import ListItemText from '@mui/material/ListItemText';
import { Link } from 'react-router-dom';

function SideBarMenuItem({menuLabel,linkTo, onClickCallback}) {
    return (<List>
        <ListItem key={menuLabel} disablePadding component={Link} to={linkTo}>
            <ListItemButton onClick={onClickCallback}>
              <ListItemText primary={menuLabel} />
            </ListItemButton>
          </ListItem>
      </List>);
}

export default SideBarMenuItem;