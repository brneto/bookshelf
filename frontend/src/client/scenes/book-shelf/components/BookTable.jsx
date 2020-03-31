import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { useHistory } from 'react-router-dom';
import styled from '@emotion/styled';
import { commands } from '../../../redux/actions';
import {
  getFetchStatus,
  getError,
  getBooks,
} from '../../../redux/reducers';
import { LoadingDots } from '../../../components';
import BookList from './BookList';

const Table = styled.table`
  & > head {
    color: red;
  }
`;

const
  mapStateToProps = state => ({
    books: getBooks(state),
    status: getFetchStatus(state),
    error: getError(state),
  }),
  mapDispatchToProps = {
    fetchBooks: commands.fetchBooks,
    editBook: commands.editBook,
    removeBook: commands.removeBook,
  },
  subscribe = connect(
    mapStateToProps,
    mapDispatchToProps
  ),
  propTypes = {
    books: PropTypes.array.isRequired,
    status: PropTypes.object.isRequired,
    error: PropTypes.instanceOf(Error),
    fetchBooks: PropTypes.func.isRequired,
    editBook: PropTypes.func.isRequired,
    removeBook: PropTypes.func.isRequired,
  };

const BookTable = ({
  books, status, error,
  fetchBooks, editBook, removeBook,
}) => {
  useEffect(() => void fetchBooks(), [fetchBooks]);

  const history = useHistory();
  const handleGoToForm = () => history.push('/books');

  let render = <p>No books fetched from shelf yet!</p>;

  if (status.isLoading) render = (<LoadingDots>Loading the shelf</LoadingDots>);

  if (status.isRejected) render = <p>The server failed with the message: {error.message}</p>;

  if (status.isResolved) render = (
    <Table>
      <caption>List of Books</caption>
      <head>
        <tr>
          <th>Title</th>
          <th>Author</th>
          <th>Publisher</th>
          <th><button onClick={handleGoToForm}>New Book</button></th>
        </tr>
      </head>
      {
        books.length
          ? <BookList books={books} onEdit={editBook} onDelete={removeBook} />
          : <tr><td colSpan={3}>No books in the shelf at the moment.</td></tr>
      }
    </Table>
  );

  return <section>{render}</section>;
};
BookTable.propTypes = propTypes;

export default subscribe(BookTable);
