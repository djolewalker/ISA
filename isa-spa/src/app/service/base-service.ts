import axios from 'axios';
import { encode } from 'querystring';

const client = axios.create({
  baseURL: process.env.REACT_APP_API_URL
});

export const setAccessToken = (accessToken: string | null) => {
  if (accessToken) {
    client.defaults.headers.common['Authorization'] = 'Bearer ' + accessToken;
  } else {
    delete client.defaults.headers.common['Authorization'];
  }
};

export const getRequest = async (endpoint: string, queryParameters?: { [key: string]: string | number }) => {
  let url = endpoint;
  if (queryParameters) {
    url += `/${encode(queryParameters)}`;
  }
  return client.get(url);
};

export const postRequest = async <T>(
  endpoint: string,
  data: T,
  queryParameters?: { [key: string]: string | number }
) => {
  let url = endpoint;
  if (queryParameters) {
    url += `/${encode(queryParameters)}`;
  }
  return client.post(url, data);
};
