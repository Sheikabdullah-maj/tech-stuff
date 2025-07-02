import * as React from 'react';
import Box from '@mui/material/Box';
import Container from '@mui/material/Container';
import './../../App.css';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import { useAuth } from '../context/AuthContext';
import { libmasApi } from '../apiconsumer/LibmasApi';
function BookEntry() {
  const [categories,SetCategories] = React.useState([]);
  const [action,SetAction] = React.useState('N');

  const Auth = useAuth()
  const user = Auth.getUser()
  
  const [formData, setFormData] = React.useState({
    id: '',
    title: '',
    author:'',
    categoryId:'',
    totalCopies: '',
    publications:''
  });

  // Handler to update state on form input change
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value, // Update the specific form field by name
    });
  };

  // Function to programmatically set field values
  const setFieldValues = (fetchedData) => {
    if(fetchedData.id){
    setFormData({
    id: fetchedData.id,
    title: fetchedData.title,
    author:fetchedData.author,
    categoryId:fetchedData.categoryId,
    totalCopies: fetchedData.totalCopies,
    publications: fetchedData.publications
    });
  }
  };

  async function getCategories() {
    const response = await libmasApi.getAllCategories(user); 
    const responseData = response.data; 
    SetCategories(responseData);
  }


  async function invokeBookAPI(requestData){
    const response = await libmasApi.saveBook(user, requestData); 
    const responseData = response.data; 
    console.log('Book saved'+responseData.processed);
  }

  React.useEffect(() => {
    // Fetch data from the API
    getCategories();      
}, []);

function saveBook(event){
  event.preventDefault();
  invokeBookAPI(formData);
}

async function getBookDetailsById(id){
  const apiResponse = await libmasApi.getBookDetailsById(user, id);
  setFieldValues(apiResponse.data);
}

function toggleAction(){
  SetAction(currAction => currAction==='N' ? 'U' : 'N');
}


    return <>
    <Container>
        <Box>
        <Stack spacing={2} direction="row">
          <Button variant={action==='N' ? "contained" : "outlined"} onClick={toggleAction}>New Entry</Button>
          <Button variant={action==='U' ? "contained" : "outlined"} onClick={toggleAction}>Update Book Details</Button>
        </Stack>
          <form onSubmit={saveBook}>
          { action==='U' && 
          <div className="control">
          <label htmlFor="id">Enter Book Id to get details</label>
            <input
              type="text"
              id="id"
              name="id"
              value={formData.id}
              onChange={(e)=> {handleInputChange(e);getBookDetailsById(e.target.value);}}
            />
        </div>}
          <div className="control">
          <label htmlFor="category">Category</label>
          <select id="categoryId" name="categoryId" onChange={handleInputChange}
          value={formData.categoryId}
          >
          <option value='' key='0'></option>
          {categories.map(
            (category) => (
              <option value={category.id} key={category.id}>{category.description}</option>
            )
          )}
          </select>
        </div>
        <div className="control">
          <label htmlFor="title">Title</label>
            <input
              type="text"
              id="title"
              name="title"
              onChange={handleInputChange}
              value={formData.title}
            />
        </div>
          <div className="control">
          <label htmlFor="author">Author(s)</label>
            <input
              type="text"
              id="author"
              name="author"
              onChange={handleInputChange}
              value={formData.author}
            />
          </div>
          <div className="control">
          <label htmlFor="copiesOwned">Total Copies</label>
            <input
              type="number"
              id="totalCopies"
              name="totalCopies"
              onChange={handleInputChange}
              value={formData.totalCopies}
            />
          </div>
          <div className="control">
          <label htmlFor="publication">Publication</label>
            <input
              type="text"
              id="publications"
              name="publications"
              value={formData.publications}
              onChange={handleInputChange}
            />
          </div>
          <p className="form-actions">
          <button type="reset" className="button button-flat">
            Reset
          </button>
          <button type="submit" className="button">
            Save
          </button>
        </p>
          </form> 
        </Box>
      </Container>
    </>
}

export default BookEntry;