import { Box } from '@mui/material';
import * as React from 'react';
import CustomStickyTable from '../util/StickyTable';
import { useAuth } from '../context/AuthContext';
import { libmasBookIssueApi } from '../apiconsumer/LibmasBookIssueApi';

const columns = [
    { id: 'id', label: 'Id', minWidth: 170 },
    { id: 'bookId', label: 'Book Id', minWidth: 170 },
    { id: 'memberId', label: 'Member Id', minWidth: 170 },
    { id: 'dateOfIssue', label: 'Date of Issue', minWidth: 170 },
    { id: 'expectedDateOfReturn', label: 'Expected Date of Return', minWidth: 170 },
    { id: 'actualDateOfReturn', label: 'Actual Date of Return', minWidth: 170 }
  ];
  

export default function BookLendingTracker(){
    const [lendRecord, setLendRecord] = React.useState({
    record : [],
    isLastPage : false,
    pageNumber :0,
    pageSize: 10,
    totalPages: 0,
    totalElements: 0
    });

    const authContext = useAuth();
    const currentUser = authContext.getUser();

    async function getBookTrackerRecord() {
      const restResponseForLendHistory = await libmasBookIssueApi.getBookLendHistory(currentUser, lendRecord.pageNumber, lendRecord.pageSize);
      const data = (restResponseForLendHistory).data
      setLendRecord(lRec => { return {
          isLastPage: data.last,
      pageNumber: data.pageable.pageNumber,
      pageSize: data.pageable.pageSize,
      totalPages: data.totalPages,
      totalElements: data.totalElements,
      record : data.content
      }  
      });
      }

      React.useEffect(() => {
        getBookTrackerRecord();
    }, []);

    return <><Box>
    <CustomStickyTable columns={columns} rows={lendRecord.record}/>
  </Box>
</>
    
}