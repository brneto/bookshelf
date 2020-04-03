import { combineReducers } from 'redux';
import { combineActions, handleActions } from 'redux-actions';
import { produce } from 'immer';
import { events, documents } from '../actions';

const { startedFetch, succeedFetch, failedFetch } = events;
const fetchStatus = handleActions(
  {
    [startedFetch]: produce(() => 'pending'),
    [succeedFetch]: produce(() => 'resolved'),
    [failedFetch]: produce(() => 'rejected'),
  },
  'idle' // Initial state
);

const { booksFetched, bookAdded, bookRemoved } = documents;
const ids = handleActions(
  {
    [booksFetched]: { // return an entirely new state
      next: produce((draft, { payload: { result } }) => {
        if (Array.isArray(result)) return result;
        if (!draft.includes(result)) draft.push(result);
      }),
      throw: () => []
    },
    [bookAdded]: { // modify the current draft state
      next: produce((draft, { payload }) => void draft.push(payload.result)),
    },
    [bookRemoved]: { // return an entirely new state
      next: produce((draft, { payload }) => draft.filter(i => i !== payload)),
    },
  }, [] // Initial state
);

const error = handleActions(
    {
      [combineActions(booksFetched, bookAdded, bookRemoved)]: {
        next: () => null,
        throw: produce((draft, { payload }) => payload)
      },
    }, null // Initial state
  );

const idList = combineReducers({
  ids,
  fetchStatus,
  error,
});

// SELECTORS
// prop :: s -> {s: a} -> a | Undefined
const
  getIds = ({ ids }) => ids,
  getFetchStatus = ({ fetchStatus: status }) => ({
    isIdle: status === 'idle',
    isLoading: status === 'pending',
    isResolved: status === 'resolved',
    isRejected: status === 'rejected',
  }),
  getError = ({ error }) => error;

export {
  idList as default,
  getIds,
  getFetchStatus,
  getError,
};
