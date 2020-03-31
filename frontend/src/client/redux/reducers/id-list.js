import { combineReducers } from 'redux';
import { combineActions, handleActions } from 'redux-actions';
import { produce } from 'immer';
import { events, documents } from '../actions';

const fetchStatus = handleActions(
  {
    [events.startedFetch]: produce(() => 'pending'),
    [events.succeedFetch]: produce(() => 'resolved'),
    [events.failedFetch]: produce(() => 'rejected'),
  },
  'idle' // Initial state
);

const { booksFetched, bookAdded, bookRemove } = documents;

const ids = handleActions(
  {
    [documents.booksFetched]: { // return an entirely new state
      next: produce((draft, { payload }) => payload.result),
      throw: () => []
    },
    [documents.bookAdded]: { // modify the current draft state
      next: produce((draft, { payload }) => void draft.push(payload.result)),
    },
    [documents.bookRemove]: { // return an entirely new state
      next: produce((draft, { payload }) => draft.filter(i => i !== payload.result)),
    },
  }, [] // Initial state
);

const error = handleActions(
    {
      [combineActions(booksFetched, bookAdded, bookRemove)]: {
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
