import axios from "axios";

const API = axios.create({ baseURL: "http://localhost:5000" });

API.interceptors.request.use((req) => {
  if (localStorage.getItem("profile")) {
    req.headers.Authorization = `Bearer ${
      JSON.parse(localStorage.getItem("profile")).token
    }`;
  }
  //req.headers["Content-Type"] = "multipart/form-data";
  return req;
});
/*export const uploadImage = (file, name) => {
  const formData = new FormData();
  formData.append("file", file);
  formData.append("name", name);

  // En lugar de pasar un objeto, pasamos directamente el FormData
  return API.post("/upload", formData);
};*/
export const uploadImage = (data) => API.post("/upload/", data);
export const uploadPost = (data) => API.post("/posts", data);
