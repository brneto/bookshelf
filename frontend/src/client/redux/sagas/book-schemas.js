import { schema } from 'normalizr';

const
  book = new schema.Entity('book'),
  bookList = [book];

export { book, bookList };
