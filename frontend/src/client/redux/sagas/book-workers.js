/* eslint-disable import/namespace */
import { normalize } from 'normalizr';
import { call, put, select, cancel } from 'redux-saga/effects';
import { events, documents } from '../actions';
import * as selectors from '../reducers';
import * as api from '../../api';
import * as schemas from './book-schemas';

function* fetchBooks({ payload: id }) {
  try {
    const fetchStatus = yield select(selectors.getFetchStatus);
    if (fetchStatus.isLoading) yield cancel();

    yield put(events.startedFetch());

    const
      response = yield isNaN(id) ? call(api.book.fetchBooks) : call(api.book.fetchBookById, id),
      data = normalize(response, isNaN(id) ? schemas.bookList : schemas.book);

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
    yield call(api.book.removeBook, id);

    yield put(documents.bookRemoved(id));
  } catch(error) {
    yield put(documents.bookRemoved(error));
  }
}

export { fetchBooks, addBook, editBook, removeBook };
