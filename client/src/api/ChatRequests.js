import axios from 'axios'


const API = axios.create({ baseURL: 'http://localhost:5000' });

API.interceptors.request.use((req) => {
    if (localStorage.getItem('profile')) {
      req.headers.Authorization = `Bearer ${JSON.parse(localStorage.getItem('profile')).token}`;
    }
    req.credentials = 'include'; 
    return req;
  });


export const createChat = (senderId, receiverId) => API.post('/chat/create', {senderId, receiverId});

export const deleteChat = (chatId) => API.delete(`/chat/${chatId}`,);

export const userChats = (id) => API.get(`/chat/${id}`);

export const findChat = (firstId, secondId) => API.get(`/chat/find/${firstId}/${secondId}`);