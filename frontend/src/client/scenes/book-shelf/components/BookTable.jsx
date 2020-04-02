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
  & > thead {
    color: red;
  }

  & > tbody {
    color: blue;
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
    removeBook: PropTypes.func.isRequired,
  };

const BookTable = ({
  books, status, error,
  fetchBooks, removeBook,
}) => {
  useEffect(() => void fetchBooks(), [fetchBooks]);

  const
    handleRetry = () => fetchBooks(),
    history = useHistory(),
    handleAddBook = () => history.push('/books'),
    handleEditBook = id => history.push(`/books/${id}`);

  let render = <p>No books fetched from shelf yet!</p>;

  if (status.isLoading) render = <LoadingDots>Loading the shelf</LoadingDots>;

  if (status.isRejected) render = (
    <p>
      The server failed with the message: {error.message}.<br />
      <button onClick={handleRetry}>Try again</button>
    </p>
  );

  if (status.isResolved) render = (
    <Table>
      <caption>List of Books</caption>
      <thead>
        <tr>
          <th>Title</th>
          <th>Author</th>
          <th>Publisher</th>
          <th><button onClick={handleAddBook}>New</button></th>
        </tr>
      </thead>
      <tbody>
      {
        books.length
          ? <BookList books={books} onEdit={handleEditBook} onDelete={removeBook} />
          : <tr><td colSpan={3}>No books in the shelf at the moment.</td></tr>
      }
      </tbody>
    </Table>
  );

  return <section>{render}</section>;
};
BookTable.propTypes = propTypes;

export default subscribe(BookTable);
