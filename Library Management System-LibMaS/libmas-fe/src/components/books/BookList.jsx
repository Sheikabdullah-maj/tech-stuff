import * as React from 'react';
import CustomStickyTable from '../util/StickyTable';
import Category from './Category';
import Box from '@mui/material/Box';
import axios from 'axios';
import {libmasApi} from '../apiconsumer/LibmasApi'
import { useAuth } from '../context/AuthContext';

const columns = [
  { id: 'id', label: 'Id', minWidth: 170 },
  { id: 'title', label: 'Title', minWidth: 170 },
  { id: 'author', label: 'Author(s)', minWidth: 170 },
  { id: 'category', label: 'category', minWidth: 170 },
  { id: 'publications', label: 'Publications', minWidth: 170 },
  { id: 'totalCopies', label: 'Total Copies', minWidth: 170 }
];

// function createData(id, title, author,copiesAvailable,publications,publicationDate) {
//   return { id, title, author,copiesAvailable,publications,publicationDate };
// }

// const rows = [
//   createData(1,'Thirukkural In Tamil','Thiruvalluvar,Varatharasan',50,'Murasoli Pathipagam','01-10-1991'),
//   createData(2,'Porthozhil Pazhagu','V.Iraiyanbu',30,'Konar Pathipagam','10-07-2007')
// ];

export default function BookList() {

  const Auth = useAuth()
  const user = Auth.getUser()

  const [categories,SetCategories] = React.useState([]);
  const [bookInfo, SetBookInfo] = React.useState({
    books: [],
    isLastPage : false,
    pageNumber :0,
    pageSize: 10,
    totalPages: 0,
    totalElements: 0
  })

  const formRef = React.useRef(null);

  async function getBookList(requestData){
     try {
        const response = await libmasApi.getBookList(user, requestData,bookInfo.pageNumber,bookInfo.pageSize )
        const responseData = response.data;
        SetBookInfo(info => {return {
          books: responseData.content,
          isLastPage: responseData.last,
          pageNumber: responseData.pageable.pageNumber,
          pageSize: responseData.pageable.pageSize,
          totalPages: responseData.totalPages,
          totalElements: responseData.totalElements
        }})
      } catch (error) {
        handleLogError(error)
      } 
    }

  async function getCategories() {
    const response = await libmasApi.getAllCategories(user); 
    const responseData = response.data; 
    SetCategories(responseData);
  }

  const submitForm = () => {
    if (formRef.current) {
      formRef.current.dispatchEvent(new Event('submit', { cancelable: true, bubbles: true }));
    }
  };
  
  React.useEffect(() => {
    // Fetch data from the API
    getBookList({});
    getCategories();      
}, []);

function searchBooks(event){
    const formData = new FormData(event.target);
    const userEnteredData = Object.fromEntries(formData.entries());
    getBookList({
      "title": userEnteredData.title,
      "author": userEnteredData.author,
      "category": userEnteredData.category
    });
}

  return <>
  <Box>
          <Box display={'flex'}>
          <form onSubmit={(event) => {event.preventDefault();searchBooks(event);}} ref={formRef}>
          <div className="control search" >
          <label htmlFor="category">Category</label>
          <select id="category" name="category" onChange={submitForm}>
          <option value='' key='0'></option>
          {categories.map(
            (category) => (
              <option value={category.id} key={category.id}>{category.description}</option>
            )
          )}
          </select>
        </div>
        <div className="control search">
          <label htmlFor="title">Title</label>
            <input
              type="text"
              id="title"
              name="title"
              onChange={submitForm}
            />
        </div>
          <div className="control search">
          <label htmlFor="author">Author(s)</label>
            <input
              type="text"
              id="author"
              name="author"
              onChange={submitForm}
            />
          </div>
          <p className="form-actions">
          <button type="reset" onClick={() => getBookList({})} className="button button-flat">
            Reset
          </button>
          <button type="submit" className="button">
            Search
          </button>
        </p>
          </form> 
          </Box>
          <CustomStickyTable columns={columns} rows={bookInfo.books}/>
        </Box>
  
  </>
}
