import * as React from 'react';
import Box from '@mui/material/Box';
import { DataGrid, GridCellEditStopReasons, useGridApiRef } from '@mui/x-data-grid';
import { Container } from '@mui/material';
import './../../App.css';
import axios from 'axios';
import { libmasApi } from '../apiconsumer/LibmasApi';
import { useAuth } from '../context/AuthContext';

const columns = [
  { field: 'id', headerName: 'ID', width: 30, editable: false },
  {
    field: 'description',
    headerName: 'Category',
    width: 300,
    editable: true,
  },
];



export default function Category() {
  const [categories,SetCategories] = React.useState([]);
  const apiRef = useGridApiRef();

  const Auth = useAuth()
  const user = Auth.getUser()

  async function getCategories() {
    const response = await libmasApi.getAllCategories(user); 
    const responseData = response.data; 
    SetCategories(responseData);
  }

  React.useEffect(()=> {
    getCategories();
  },[])

  const processRowUpdate = (newRow) => {
    saveCategory({id: newRow.id, description: newRow.description});
    return newRow;
  };

  async function addCategory(event){
    event.preventDefault();
    const formData = new FormData(event.target);
    const userEnteredData = Object.fromEntries(formData.entries());
    const response = await saveCategory({description: userEnteredData.category});
    getCategories();
  }

  async function saveCategory(requestData){
  return libmasApi.saveCategory(user,requestData);
  }

  const handleCellEditStop = (params, event) => {
    if (event.key === 'Enter') {
      event.defaultMuiPrevented = true;
      apiRef.current.stopCellEditMode({ id: params.id, field: params.field });
    }
  };

  return (
    <Container>
        <Box display={'flex'}>
        <form onSubmit={addCategory}>
        <div className="control">
          <label htmlFor="category">Category</label>
            <input
              type="text"
              id="category"
              name="category"
            />
            <button type="submit" className="button">
            Save
          </button>
        </div>
        </form>
        </Box>
    <Box>
      <DataGrid
        rows={categories}
        columns={columns}
        initialState={{
          pagination: {
            paginationModel: {
              pageSize: 10,
            },
          },
        }}
        processRowUpdate={processRowUpdate}
        onCellEditStop={handleCellEditStop}
        apiRef={apiRef}
        editMode="cell"
        pageSizeOptions={[10]}
        sx={{width: '30%'}}
      />
    </Box>
    </Container>
  );
}
