import { ActionMatchingPattern } from '@redux-saga/types';
import { ActionPattern } from 'redux-saga/effects';

export const safe = <P extends ActionPattern>(
  callback: (action: ActionMatchingPattern<P>) => any,
  options?: {
    catch?: () => void;
    finally?: () => void;
  }
) =>
  function* (action: ActionMatchingPattern<P>) {
    try {
      yield* callback(action);
    } catch (error) {
      if (options?.catch) yield options?.catch();
    } finally {
      if (options?.finally) yield options?.finally();
    }
  };
