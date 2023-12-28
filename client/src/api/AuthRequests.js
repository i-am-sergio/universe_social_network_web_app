import axios from 'axios'

const baseURL = process.env.BACKEND_URL;

const API = axios.create({ 
    baseURL: baseURL,
    withCredentials: true,
});

export const logIn= (formData)=> API.post('/auth/login',formData);

export const signUp = (formData) => API.post('/auth/register', formData);
