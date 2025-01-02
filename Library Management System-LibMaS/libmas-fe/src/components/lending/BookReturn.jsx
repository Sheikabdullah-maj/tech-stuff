import * as React from 'react';
import { Box, Button, Checkbox, Container, MenuItem, Select } from "@mui/material";
import { useAuth } from '../context/AuthContext';
import { libmasBookIssueApi } from '../apiconsumer/LibmasBookIssueApi';



export default function BookReturn() {

  const auth = useAuth();
  const currentUser = auth.getUser();

    const [lendRecord, setLendRecord] = React.useState([]);
    const [formData, setFormData] = React.useState({
        memberId: '',
        bookId: '',
        expectedDateOfReturn : '',
        actualDateOfReturn : '',
        isFineCheck : false,
        finePaymentCheck: false
    });

    async function getBooksLendedForMember(){
        const restResponse = await libmasBookIssueApi.consumeBooksLendedForMember(currentUser, formData.memberId);
        const responseData = restResponse.data;
        setLendRecord(responseData);
    }

    function applyBookProps(bookId){
        console.log('book record lookup for book : '+bookId);
        const bookRecord = lendRecord[bookId];
        console.log('book record: '+bookRecord);
        const fineChk = bookRecord.fineCheckNeeded == 'Y' ? true : false;
        setFormData((formData) => { return {
            ...formData,
            expectedDateOfReturn: bookRecord.expectedDateOfReturn,
            isFineCheck : fineChk
          }});
    }

    const handleInputChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData((formData) => { return {
          ...formData,
          [name]: type === 'checkbox' ? checked : value
        }});
        console.log('cond1:'+(formData.isFineCheck && formData.finePaymentCheck))
        console.log('cond 2:'+(!formData.isFineCheck && formData.actualDateOfReturn));
      };
    
      function returnBook(event){
        event.preventDefault();
        console.log('formData:'+formData.expectedDateOfReturn);
        console.log('formData:'+formData.actualDateOfReturn);
        console.log('formData:'+formData.isFineCheck);
        console.log('formData:'+formData.finePaymentCheck);
        invokeBookReturn(formData);
    }
    

    function invokeBookReturn(requestData){
        console.log('request data for book entry :'+requestData);
        const restResponse = libmasBookIssueApi.returnBook(currentUser, requestData);
        console.log(' rest response :'+restResponse.status);
    }

    return <>
    <Container>
    <Box>
          <form onSubmit={returnBook}>
        <div className="control">
          <label htmlFor="memberId">Member Id</label>
            <input
              type="text"
              id="memberId"
              name="memberId"
              onChange={handleInputChange}
              value={formData.memberId}
            />
           <Button onClick={getBooksLendedForMember}>Find Books</Button>
        </div>
          <div className="control">
          <label htmlFor="bookId">Select Book</label>
          <Select
          labelId="Select Book"
          id="bookId"
          name='bookId'
          value={formData.bookId}
          onChange={(e) => {handleInputChange(e);applyBookProps(e.target.value);}}
        >
            {Object.entries(lendRecord).map(([key, record])   => {
                console.log(record);
                console.log(record.key);
                return <MenuItem value={record.bookId} key={key}>{record.bookId}</MenuItem>
            })}
        </Select>
          </div>
          <div className="control">
          <label htmlFor="expectedDateOfReturn">Expected Date of Return</label>
          <input
              type="date"
              id="expectedDateOfReturn"
              name="expectedDateOfReturn"
              value={formData.expectedDateOfReturn}
              readOnly
            />
          </div>
          <div className="control">
          <label htmlFor="actualDateOfReturn">Actual Date of Return</label>
          <input
              type="date"
              id="actualDateOfReturn"
              name="actualDateOfReturn"
              value={formData.actualDateOfReturn}
              onChange={handleInputChange}
            />
          </div>
          { formData.isFineCheck == true ?  <div>
          <label htmlFor="finePaymentCheck">Fine Amount Paid ?</label>
          <Checkbox id='finePaymentCheck' name='finePaymentCheck'
           label={'Ensure fine is paid'} onChange={handleInputChange} checked={formData.finePaymentCheck}/>
          </div> : <div/>}
          <p className="form-actions">
          <Button type="reset" variant="contained">Reset</Button>
          { ((formData.actualDateOfReturn) && (( formData.isFineCheck && formData.finePaymentCheck) ||
           (!formData.isFineCheck))) ? 
          <Button variant="contained" type="submit">
            Save
            </Button>  : <Button variant="contained" type="submit" disabled>Save</Button>
            }
        </p>
          </form> 
        </Box>
      </Container>
    </>
}