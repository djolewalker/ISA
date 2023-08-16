import axios from 'axios';
import { encode } from 'querystring';

type KeyValue = { [key: string]: string | number };

const client = axios.create({
  baseURL: process.env.REACT_APP_API_URL
});

const buildUrl = (endpoint: string, queryParameters?: KeyValue) => {
  if (queryParameters) {
    return `${endpoint}?${encode(queryParameters)}`;
  }
  return endpoint;
};

export const registerInterceptor = (
  onFulfilled?: (<V>(value: V) => V | Promise<V>) | null,
  onRejected?: ((error: any) => any) | null
) => client.interceptors.response.use(onFulfilled, onRejected);

export const removeInterceptor = (id: number) => client.interceptors.response.eject(id);

export const setAccessToken = (accessToken: string | null) => {
  if (accessToken) {
    client.defaults.headers.common['Authorization'] = 'Bearer ' + accessToken;
  } else {
    delete client.defaults.headers.common['Authorization'];
  }
};

export const getRequest = async (endpoint: string, queryParameters?: KeyValue) => {
  return client.get(buildUrl(endpoint, queryParameters));
};

export const postRequest = async <T>(endpoint: string, data: T, queryParameters?: KeyValue) => {
  return client.post(buildUrl(endpoint, queryParameters), data);
};
