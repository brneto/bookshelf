import { createActions } from 'redux-actions';
import { createActionFunction } from './functions';

const actionFunction = createActionFunction('COMMAND');

// Command actions message
// Naming Convention: <imperative verb><subject>
export const {
  fetchBooks, addBook, editBook, removeBook
} = createActions({
  'FETCH_BOOKS': actionFunction,
  'ADD_BOOK': actionFunction,
  'EDIT_BOOK': actionFunction,
  'REMOVE_BOOK': actionFunction,
});
