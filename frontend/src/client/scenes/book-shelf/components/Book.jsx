import React from 'react';
import PropTypes from 'prop-types';

const
  propTypes = {
    title: PropTypes.string.isRequired,
    author: PropTypes.string.isRequired,
    publisher: PropTypes.string.isRequired,
    onEdit: PropTypes.func.isRequired,
    onDelete: PropTypes.func.isRequired,
  },
  Book = ({ title, author, publisher, onEdit, onDelete }) => (
    <tr>
      <td>{title}</td>
      <td>{author}</td>
      <td>{publisher}</td>
      <th><button onClick={onEdit}>Edit</button></th>
      <th><button onClick={onDelete}>Delete</button></th>
    </tr>
  );

Book.propTypes = propTypes;

export default Book;
