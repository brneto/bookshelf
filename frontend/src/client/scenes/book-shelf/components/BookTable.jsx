import React from 'react';
import styled from '@emotion/styled';
import BookList from './BookList';

const Table = styled.table`
  & > head {
    color: red;
  }
`;

const BookTable = () => (
  <Table>
    <caption>List of Books</caption>
    <head>
      <tr>
        <th>Title</th>
        <th>Author</th>
        <th>Publisher</th>
      </tr>
    </head>
    <BookList />
  </Table>
);

export default BookTable;
