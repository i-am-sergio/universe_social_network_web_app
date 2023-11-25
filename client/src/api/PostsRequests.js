import axios from 'axios'


const API = axios.create({ baseURL: 'http://localhost:5000' });

API.interceptors.request.use((req) => {
    if (localStorage.getItem('profile')) {
      req.headers.Authorization = `Bearer ${JSON.parse(localStorage.getItem('profile')).token}`;
    }
    req.credentials = 'include'; 
    return req;
  });

export const getTimelinePosts= (id)=> API.get(`/posts/${id}/timeline`, { withCredentials: true });
export const likePost=(id, userId)=>API.put(`posts/${id}/like`, {userId: userId})