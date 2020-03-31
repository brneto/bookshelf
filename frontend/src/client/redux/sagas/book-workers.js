/* eslint-disable import/namespace */
import { normalize } from 'normalizr';
import { call, put, select, cancel } from 'redux-saga/effects';
import { events, documents } from '../actions';
import * as selectors from '../reducers';
import * as api from '../../api';
import * as schemas from './book-schemas';

function* fetchBooks() {
  try {
    const fetchStatus = yield select(selectors.getFetchStatus);
    if (fetchStatus.isLoading) yield cancel();

    yield put(events.startedFetch());

    const
      response = yield call(api.book.fetchBooks),
      data = normalize(response, schemas.bookList);


    yield put(documents.booksFetched(data));
    yield put(events.succeedFetch());
  } catch (error) {
    yield put(documents.booksFetched(error));
    yield put(events.failedFetch());
  }
}

function* addBook({ payload: book }) {
  try {
    const
      response = yield call(api.book.addBook, book),
      data = normalize(response, schemas.book);

    yield put(documents.bookAdded(data));
  } catch (error) {
    yield put(documents.bookAdded(error));
  }
}

function* editBook({ payload: book }) {
  try {
    const
      id = yield select(selectors.getPathId),
      response = yield call(api.book.editBook, id, book),
      data = normalize(response, schemas.book);

    yield put(documents.bookEdited(data));
  } catch (error) {
    yield put(documents.bookEdited(error));
  }
}

function* removeBook({ payload: id }) {
  try {
    const response = yield call(api.book.removeBook, id);

    yield put(documents.bookRemoved(response));
  } catch(error) {
    yield put(documents.bookRemoved(error));
  }
}

export { fetchBooks, addBook, editBook, removeBook };
