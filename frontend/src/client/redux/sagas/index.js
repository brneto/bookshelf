import { takeEvery, all } from 'redux-saga/effects';
import { commands } from '../actions';
import * as workers from './book-workers';

function* rootSaga() {
  try {
    yield all({
      fetchBooksWatcher: takeEvery(commands.fetchBooks, workers.fetchBooks),
      addBookWatcher: takeEvery(commands.addBook, workers.addBook),
      editBookWatcher: takeEvery(commands.editBook, workers.editBook),
      removeBookWatcher: takeEvery(commands.removeBook, workers.removeBook),
    });
  } catch(e) {
    throw new Error('One of the Effects was rejected before all the effects complete: ' + e);
  }
}

export default rootSaga;
