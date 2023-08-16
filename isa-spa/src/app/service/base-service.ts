import { KeyValue, buildUrl } from 'app/utils/query-string.utils';
import axios from 'axios';

const client = axios.create({
  baseURL: process.env.REACT_APP_API_URL
});

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

export const getRequest = async (endpoint: string, queryParameters?: KeyValue<string>) => {
  return client.get(buildUrl(endpoint, queryParameters));
};

export const postRequest = async <T>(endpoint: string, data: T, queryParameters?: KeyValue<string>) => {
  return client.post(buildUrl(endpoint, queryParameters), data);
};
