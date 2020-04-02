import { combineActions, handleActions } from 'redux-actions';
import { produce } from 'immer';
import { documents } from '../actions';

const
  { booksFetched, bookAdded, bookEdited, bookRemoved } = documents,
  byId = handleActions(
    {
      [combineActions(booksFetched, bookAdded, bookEdited)]: {
        next: (state, { payload: { entities: { book } } }) => ({ ...state, ...book }),
      },
      [bookRemoved]: {
        next: produce((draft, { payload }) => void delete draft[payload]),
      },
    }, {} // Initial state
  );

// SELECTORS
const getBookById = (state, id) => isNaN(id) || !state[id] ? {} : state[id];

export {
  byId as default,
  getBookById,
};
