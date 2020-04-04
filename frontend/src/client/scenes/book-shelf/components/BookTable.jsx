/* eslint-disable jsx-a11y/no-onchange */
import React, { useEffect, useState } from 'react';
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
  th,
  td {
    border: 1px solid black;
  }

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

  const
    [sortBy, setSortBy] = useState(''),
    [filterBy, setFilterBy] = useState(''),
    [filter, setFilter] = useState(''),
    handleSortBy = e => setSortBy(e.target.value),
    handleFilterBy = e => setFilterBy(e.target.value),
    handleClear = () => setFilter(''),
    handleFilterChange = e => setFilter(e.target.value);

  const
    sortBooks = books => {
      const compare = field => (a, b) => {
        const af = a[field], bf = b[field];
        if (af > bf) return 1;
        if (af < bf) return -1;
        return 0;
      };

      if (sortBy) return books.sort(compare(sortBy));

      return books;
    },
    filterBooks = books => {
      const createFilter = field => book => book[field].includes(filter);

      return filterBy && filter ? books.filter(createFilter(filterBy)) : books;
    };

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
          <th>
            <label>Sort by:&nbsp;
              <select value={sortBy} onChange={handleSortBy}>
                <option value="">--Sort By--</option>
                <option value="title">Title</option>
                <option value="author">Author</option>
              </select>
            </label>
          </th>
          <th>
            <label>Filter by:&nbsp;
              <select value={filterBy} onChange={handleFilterBy}>
                <option value="">--Filter By--</option>
                <option value="title">Title</option>
                <option value="author">Author</option>
              </select>
            </label>
          </th>
          <th colSpan="3">
            <input type="text" value={filter} onChange={handleFilterChange} />&nbsp;
            <button onClick={handleClear}>Clear</button>
          </th>
        </tr>
        <tr>
          <th>Title</th>
          <th>Author</th>
          <th>Publisher</th>
          <th colSpan="2">
            <button onClick={handleAddBook}>New</button>
          </th>
        </tr>
      </thead>
      <tbody>
      {
        books.length
          ? <BookList
              books={sortBooks(filterBooks(books))}
              onEdit={handleEditBook}
              onDelete={removeBook}
            />
          : <tr>
              <td colSpan="3">No books in the shelf at the moment.</td>
            </tr>
      }
      </tbody>
    </Table>
  );

  return <section>{render}</section>;
};
BookTable.propTypes = propTypes;

export default subscribe(BookTable);
