import React, { useState } from 'react';
import PropTypes from 'prop-types';
import Book from './Book';

const
  propTypes = {
    books: PropTypes.array.isRequired,
    onEdit: PropTypes.func.isRequired,
    onDelete: PropTypes.func.isRequired,
  },
  BookList = ({ books, onEdit, onDelete }) => {
    const
      [sortBy, setSortBy] = useState(''),
      [filterBy, setFilterBy] = useState(''),
      [filter, setFilter] = useState(''),
      handleSortBy = (event) => console.log(event),
      handleFilterBy = (event) => console.log(event),
      handleSearch = (event) => console.log(event);

    return (<>
      <tr>
        <td>
          <label>Sort by:
            <select value={sortBy} onBlur={handleSortBy}>
              <option value="">--Sort By--</option>
              <option value="title">Title</option>
              <option value="author">Author</option>
            </select>
          </label>
        </td>
        <td>
          <label>Sort by:
            <select value={filterBy} onBlur={handleFilterBy}>
              <option value="">--Filter By--</option>
              <option value="title">Title</option>
              <option value="author">Author</option>
            </select>
          </label>
        </td>
        <td><input type="text" value={filter} /></td>
        <td><button onChange={handleSearch}>Search</button></td>
      </tr>
      {books.map(({ id, ...rest }) => (
        <Book key={id} {...rest}
          onEdit={() => onEdit(id)}
          onDelete={() => onDelete(id)}
        />
      ))}
    </>);
  };

BookList.propTypes = propTypes;

export default BookList;
