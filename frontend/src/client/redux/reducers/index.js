import { combineReducers } from 'redux';
import { connectRouter , createMatchSelector } from 'connected-react-router';
import * as routes from '../../routes';
import byId, * as fromById from './by-id';
import idList, * as fromIdList from './id-list';

const book = combineReducers({
  byId,
  idList,
});

// Recommendation: The reducer function is always default export.
const createRootReducer = history => combineReducers({
  router: connectRouter(history),
  book,
});

// Recommendation: Always put the selectors together with its related reducer.
// matchSelector :: state => {
//   isExact: boolean;
//   params: {}; // contains the parameters from /:id?
//   path: string;
//   url: string;
// }
const matchSelector = createMatchSelector({ ...routes.form });
const getPathId = (state) => matchSelector(state)?.params?.id;

const getBooks = ({ book }) => book.idList.ids.map(id => book.byId[id]);

const getBookById = state => fromById.getBookById(state.book.byId, getPathId(state));

const getBookIdList = ({ book: { idList } }) => fromIdList.getIds(idList);

const getFetchStatus = ({ book: { idList } }) => fromIdList.getFetchStatus(idList);

const getError = ({ book: { idList } }) => fromIdList.getError(idList);

export {
  createRootReducer as default,
  getPathId,
  getBooks,
  getBookById,
  getBookIdList,
  getFetchStatus,
  getError,
};
