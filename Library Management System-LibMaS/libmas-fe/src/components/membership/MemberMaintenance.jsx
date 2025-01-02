import * as React from 'react';
import { Box, Button } from '@mui/material';
import CustomStickyTableWithCRUD from '../util/StickyTableWithCRUD';
import MembershipEditDialog from './MembershipEditDialog';
import { libmasMembershipApi } from '../apiconsumer/LibmasMembershipApi';
import AuthContext, { useAuth } from '../context/AuthContext';

const columns = [
    { id: 'id', label: 'Member Id', minWidth: 170 },
    { id: 'name', label: 'Member Name', minWidth: 170 },
    { id: 'mobileNumber', label: 'Mobile Number', minWidth: 170 },
    { id: 'dateOfJoin', label: 'Date of Joining', minWidth: 170 },
    { id: 'status', label: 'Status', minWidth: 170 }
  ];
  

export default function MemberMaintenance() {
    const [editOpen, setEditOpen] = React.useState(false);
    const [operation, setOperation] = React.useState('C');
    const [detailsToEdit, setDetailsToEdit] = React.useState({});
    const auth = useAuth();
    const user = auth.getUser();
    const [voContainer, setVOContainer] = React.useState({
      memberDetails :[],
      isLastPage : false,
    pageNumber :-1,
    pageSize: 10,
    totalPages: 0,
    totalElements: 0
    })
    React.useEffect(() => {
      getAllMembers({});
    },[editOpen]);

    function addMember(){
      setOperation(curr => 'C');
      setEditOpen(true);
    }

    async function getAllMembers(requestData){
      
      const restResponse = await libmasMembershipApi.getAllMembers(user, requestData, 
        voContainer.pageNumber,voContainer.pageSize);
      const data = restResponse.data;
          setVOContainer(info => {return {
            memberDetails: data.content,
            isLastPage: data.last,
            pageNumber: data.pageable.pageNumber,
            pageSize: data.pageable.pageSize,
            totalPages: data.totalPages,
            totalElements: data.totalElements
          }});
    }

    function onEditCallback(selectedRecord){
        console.log('Edit option clicked!!!'+selectedRecord.id);
        setDetailsToEdit(selectedRecord);
        setOperation(curr => 'U');
        setEditOpen(true);
    }

    async function deleteMember(memberId){
      const restResponse = await libmasMembershipApi.deleteMember(user, memberId);
      const responseStatus = restResponse.data;
        if(responseStatus){
          console.log('response.status'+response.status);
          getAllMembers({});
        }
    }

    function reloadCallback(){
      setEditOpen(false);
    }
    

    return (<Box sx={{ borderBottom: 1, borderColor: 'divider', flexGrow: 1,position: 'relative', top: 70, left: 10 }}>
    <Button variant="contained" size="small" onClick={addMember}>Add Member</Button>  
    <CustomStickyTableWithCRUD columns={columns} rows={voContainer.memberDetails} onEditCallback={onEditCallback} deleteCallback={deleteMember}/> 
    {editOpen && <MembershipEditDialog operation={operation} detailsToEdit={detailsToEdit} reloadCallback={reloadCallback}/>}
    </Box>);
}