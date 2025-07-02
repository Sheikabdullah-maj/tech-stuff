import { Box, Container } from "@mui/material";
import { useAuth } from "../context/AuthContext";
import { libmasBookIssueApi } from "../apiconsumer/LibmasBookIssueApi";




export default function BookIssue() {
  
  const auth = useAuth();
  const currentUser = auth.getUser();

  function issueBook(event){
    event.preventDefault();
    const formData = new FormData(event.target);
    const userEnteredData = Object.fromEntries(formData.entries());
    invokeBookIssue(userEnteredData);
}
  
  async function invokeBookIssue(requestData){
    const restResponse = await libmasBookIssueApi.issueBook(currentUser,requestData);
    console.log(restResponse.status);
  }
    

    return <>
    <Container>
        <Box>
          <form onSubmit={issueBook}>
        <div className="control">
          <label htmlFor="memberId">Member Id</label>
            <input
              type="text"
              id="memberId"
              name="memberId"
            />
        </div>
          <div className="control">
          <label htmlFor="bookId">Book Id</label>
            <input
              type="text"
              id="bookId"
              name="bookId"
            />
          </div>
          <div className="control">
          <label htmlFor="dateOfIssue">Date of Issue</label>
          <input
              type="date"
              id="dateOfIssue"
              name="dateOfIssue"
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