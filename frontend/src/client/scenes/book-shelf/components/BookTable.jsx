import React from 'react';
import styled from '@emotion/styled';
import BookList from './BookList';

const Head = styled.thead`
  color: red;
`;

const BookTable = () => (
  <table>
    <caption>List of Books</caption>
    <Head>
      <tr>
        <th>Title</th>
        <th>Author</th>
        <th>Publisher</th>
      </tr>
    </Head>
    <BookList />
  </table>
);

export default BookTable;
