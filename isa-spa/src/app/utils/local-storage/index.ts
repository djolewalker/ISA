import { useEffect } from 'react';

type LocalStorageUpdateParams = {
  key: string;
  silent?: boolean;
  eventName?: string;
};

type LocalStorageAddParams = LocalStorageUpdateParams & {
  value: string;
};

export const setLocalStorage = ({ key, value, silent = true, eventName = '' }: LocalStorageAddParams) => {
  localStorage.setItem(key, value);
  if (!silent && eventName) dispatchEvent(new Event(eventName));
};

export const removeLocalStorage = ({ key, silent = true, eventName = '' }: LocalStorageUpdateParams) => {
  localStorage.removeItem(key);
  if (!silent && eventName) dispatchEvent(new Event(eventName));
};

export const LOCAL_STORAGE_EVENTS = {
  ACCESS_TOKEN_CHANGE_EVENT_NAME: 'access_token_change'
};

export const useListenLocalStorage = (eventName: string, callback: () => void) => {
  useEffect(() => {
    window.addEventListener(eventName, callback);

    return () => {
      window.removeEventListener(eventName, callback);
    };
  }, [eventName, callback]);
};
