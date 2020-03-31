import { combineReducers } from 'redux';
import { connectRouter , createMatchSelector } from 'connected-react-router';
import * as routes from '../../routes';
import byId, * as fromById from './by-id';
import idList, * as fromIdList from './id-list';

// Recommendation: The reducer function is always default export.
const createRootReducer = history => combineReducers({
  router: connectRouter(history),
  byId,
  idList,
});

// Recommendation: Always put the selectors together with its related reducer.
// matchSelector :: state => {
//   isExact: boolean;
//   params: {}; // contains the parameters from /:id?
//   path: string;
//   url: string;
// }
const matchSelector = createMatchSelector({ ...routes.form });
const getPathId = state => matchSelector(state).params?.id;

const getBooks = ({ byId }) => byId;

const getBookById = ({ byId }, { id }) => fromById.getBookById(byId, id);

const getBookIdList = ({ idList }) => fromIdList.getIds(idList);

const getFetchStatus = ({ idList }) => fromIdList.getFetchStatus(idList);

const getError = fromIdList.getError;

export {
  createRootReducer as default,
  getPathId,
  getBooks,
  getBookById,
  getBookIdList,
  getFetchStatus,
  getError,
};
