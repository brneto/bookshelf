import { createActions } from 'redux-actions';
import { createActionFunction } from './functions';

const actionFunction = createActionFunction('DOCUMENT');

// Document actions
// Naming Convention: <subject><past-tense verb>
export const {
  todosFetched, todoAdded, todoToggled
} = createActions({
  BOOKS_FETCHED: actionFunction,
  BOOK_ADDED: actionFunction,
  BOOK_EDITED: actionFunction,
  BOOK_REMOVE: actionFunction,
});
