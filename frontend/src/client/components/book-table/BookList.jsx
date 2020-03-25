import React from 'react';
import PropTypes from 'prop-types';
import styled from '@emotion/styled';
import Book from './Book';

const TBody = styled.tbody`
  color: blue;
`;

const
  propTypes = {
    books: PropTypes.array.isRequired,
    onEdit: PropTypes.func.isRequired,
    onDelete: PropTypes.func.isRequired,
  },
  BookList = ({ books, onEdit, onDelete }) => (
    <TBody>
      {books.map(({ id, ...rest }) => (
        <Book key={id} {...rest}
          onEdit={() => onEdit(id)}
          onDelete={() => onDelete(id)}
        />
      ))}
    </TBody>
  );

BookList.propTypes = propTypes;

export default BookList;
