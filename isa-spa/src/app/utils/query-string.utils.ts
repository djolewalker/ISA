import { encode } from 'querystring';

export type KeyValue<T> = { [key: string]: T };

export const buildUrl = (endpoint: string, queryParameters?: KeyValue<string | number>) => {
  if (queryParameters) {
    return `${endpoint}?${encode(queryParameters)}`;
  }
  return endpoint;
};
