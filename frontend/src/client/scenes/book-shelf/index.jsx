import React from 'react';
import { css } from '@emotion/core';
import { BookTable } from './components';

const pageStyle = css`
  position: relative;
  margin: 130px 0 40px 0;
  background: #fff;
  box-shadow:
    0 2px 4px 0 rgba(0, 0, 0, .2),
    0 25px 50px 0 rgba(0, 0, 0, .1);
`;

const BookShelf = () => (
  <>
    <header>
      <h1>Your Personal Book Shelf</h1>
    </header>
    <article css={pageStyle}>
      <h2>Book Table List</h2>
      <BookTable />
    </article>
  </>
);

export default BookShelf;
