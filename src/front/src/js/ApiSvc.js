import axios from "axios";

export default axios.create({
  baseURL: "http://localhost:8080/home",
  headers: {
    "Content-type": "application/json",
  }
});
