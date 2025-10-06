import axios from "axios";
import { getDateInLocalDate } from "../utils/Utility";

export const fetchEmployeesAccessCardTrackerData = async () => {
  const queryDate = getDateInLocalDate();
  const { data } = await axios.get("http://localhost:8080/trackerService/getTrackerData?date="+queryDate);
  return data;
};