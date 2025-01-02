import * as React from 'react';
import PropTypes from 'prop-types';
import Tabs from '@mui/material/Tabs';
import Tab from '@mui/material/Tab';
import Box from '@mui/material/Box';
import BookIssue from "./BookIssue";
import BookReturn from './BookReturn';
import BookLendingTracker from './BookLendingTracker';

function CustomTabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`simple-tabpanel-${index}`}
      aria-labelledby={`simple-tab-${index}`}
      {...other}
    >
      {value === index && <Box sx={{ p: 3 }}>{children}</Box>}
    </div>
  );
}

CustomTabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.number.isRequired,
  value: PropTypes.number.isRequired,
};

function a11yProps(index) {
  return {
    id: `simple-tab-${index}`,
    'aria-controls': `simple-tabpanel-${index}`,
  };
}

export default function LendingTabs() {
  const [value, setValue] = React.useState(0);

  const handleChange = (event, newValue) => {
    setValue(newValue);
  };

  return (
    <Box sx={{ borderBottom: 1, borderColor: 'divider', flexGrow: 1,position: 'relative', top: 65, left: 10 }}>
      <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
        <Tabs value={value} onChange={handleChange} aria-label="basic tabs example">
          <Tab label="Book Issuance" {...a11yProps(0)} />
          <Tab label="Book Return" {...a11yProps(1)} />
          <Tab label="Book Lend Tracking" {...a11yProps(2)} />
        </Tabs>
      </Box>
      <CustomTabPanel value={value} index={0}>
        <BookIssue/>
      </CustomTabPanel>
      <CustomTabPanel value={value} index={1}>
        <BookReturn/>
      </CustomTabPanel>
      <CustomTabPanel value={value} index={2}>
      <BookLendingTracker/>
      </CustomTabPanel>
    </Box>
  );
}
