import React from 'react';
import PropTypes from 'prop-types';
import Book from './Book';

const
  propTypes = {
    books: PropTypes.array.isRequired,
    onEdit: PropTypes.func.isRequired,
    onDelete: PropTypes.func.isRequired,
  },
  BookList = ({ books, onEdit, onDelete }) => (
    <>
      {books.map(({ id, ...rest }) => (
        <Book key={id} {...rest}
          onEdit={() => onEdit(id)}
          onDelete={() => onDelete(id)}
        />
      ))}
    </>
  );

BookList.propTypes = propTypes;

export default BookList;
