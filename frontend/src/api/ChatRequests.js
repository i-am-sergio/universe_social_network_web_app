import axios from 'axios'


const API = axios.create({ baseURL: 'http://localhost:5000' });

export const createChat = (senderId, receiverId) => API.post('/chat/', {senderId, receiverId});

export const deleteChat = (chatId) => API.delete(`/chat/${chatId}`,);

export const userChats = (id) => API.get(`/chat/${id}`);

export const findChat = (firstId, secondId) => API.get(`/chat/find/${firstId}/${secondId}`);