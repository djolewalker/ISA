import { createContext, useCallback, useContext, useEffect, useState } from 'react';
import SockJS from 'sockjs-client';
import Stomp, { Client } from 'stompjs';

const createClient = () => Stomp.over(new SockJS(process.env.REACT_APP_WS_URL));

type WsContextType = {
  connected: boolean;
  client: Client;
  reconnect: () => void;
};

const WsContext = createContext<WsContextType>({} as WsContextType);

type WsProviderProps = {
  children: React.ReactNode;
};

const WsProvider = ({ children }: WsProviderProps) => {
  const [client, setClient] = useState<Client>(createClient);
  const [attemptinProgress, setAttemptInProgress] = useState(false);

  useEffect(() => {
    if (!client.connected && !attemptinProgress) {
      setAttemptInProgress(true);
      client.connect(
        {},
        () => setAttemptInProgress(false),
        () => setAttemptInProgress(false)
      );
    }
  }, [attemptinProgress, client]);

  const reconnect = useCallback(() => {
    if (client.connected) client.disconnect(() => setClient(createClient()));
  }, [client]);

  return <WsContext.Provider value={{ client, connected: client.connected, reconnect }}>{children}</WsContext.Provider>;
};

export const useWs = () => useContext(WsContext);
export default WsProvider;
