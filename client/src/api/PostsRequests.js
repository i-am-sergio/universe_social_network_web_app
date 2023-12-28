import axios from 'axios'

const baseURL = process.env.BACKEND_URL;

const API = axios.create({ baseURL: baseURL, withCredentials: true, });

API.interceptors.request.use((req) => {
    if (localStorage.getItem('profile')) {
      req.headers.Authorization = `Bearer ${JSON.parse(localStorage.getItem('profile')).token}`;
    }
    req.credentials = 'include'; 
    return req;
  });

export const getTimelinePosts= (id)=> API.get(`/posts/${id}/timeline`, { withCredentials: true });
export const likePost=(id, userId)=>API.put(`posts/${id}/like`, {userId: userId})