import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import { MenuItem, Select } from '@mui/material';
import { useAuth } from '../context/AuthContext';
import { libmasApi } from '../apiconsumer/LibmasApi';
import { libmasMembershipApi } from '../apiconsumer/LibmasMembershipApi';

export default function MembershipEditDialog({operation, detailsToEdit, reloadCallback}) {
  const [open, setOpen] = React.useState(true);
  const [memberData, setMemberData] = React.useState(detailsToEdit);

  const auth = useAuth();
    const user = auth.getUser();

  const handleClose = () => {
    setOpen(false);
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setMemberData((currData) => {return {
      ...currData,
      [name]: value, // Update the specific form field by name
    }});
  };

  async function processSaveFlow(memberRecord){
      const recordSaved = await saveMember(memberRecord);
      if(recordSaved){
        await reloadCallback();
      }
      handleClose();
  }
  async function saveMember(memberRecord){
    const restResponse = await libmasMembershipApi.saveMember(user, memberRecord);
    console.log('Member save status :'+restResponse.status);
  }

  return (
    <React.Fragment>
      <Dialog
        open={open}
        onClose={handleClose}
        PaperProps={{
          component: 'form',
          onSubmit: (event) => {
            event.preventDefault();
            console.log('Submit option called');
            const formData = new FormData(event.target);
            const formObj = Object.fromEntries(formData.entries());
            saveMember({...memberData,id : memberData ? memberData.id : null, status: formObj.status });
                    handleClose();
                    reloadCallback({});
                  }
        }}
      >
        <DialogTitle>{operation == 'C' ? 'New Member' : 'Update Member Details'}</DialogTitle>
        <DialogContent sx={{width: '700px'}}>
        <div className="control">
          <label htmlFor="name">Name</label>
            <input
              type="text"
              id="name"
              name="name"
              value={memberData ? memberData.name : ''}
              onChange={handleInputChange}
            />
        </div>
        <div className="control">
        <label htmlFor="mobileNumber">Mobile Number</label>
        <input
            type="text"
            id="mobileNumber"
            name="mobileNumber"
            value={memberData ? memberData.mobileNumber : ''}
            onChange={handleInputChange}
        />
        </div>
        <div className="control">
        <label htmlFor="dateOfJoining">Date Of Joining</label>
        <input
            type="date"
            id="dateOfJoin"
            name="dateOfJoin"
            value={memberData ? memberData.dateOfJoin : ''}
            onChange={handleInputChange}
        />
        </div>
        <div className="control">
            <label htmlFor="status">Status</label>
          <Select
                id="status" name='status'
                label="Status"
                value={memberData ? memberData.status : ''}
                onChange={handleInputChange} defaultValue={'Active'}>
                <MenuItem value={'Active'}>Active</MenuItem>
                <MenuItem value={'Inactive'} >Inactive</MenuItem>
            </Select>
          </div>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose}>Cancel</Button>
          <Button type="submit">Save</Button>
        </DialogActions>
      </Dialog>
    </React.Fragment>
  );
}
