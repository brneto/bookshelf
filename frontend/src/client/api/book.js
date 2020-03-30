import axios from 'axios';

// {
//   "_embedded": {
//     "books": [
//       {
//         "id": 1,
//         "title": "test title2",
//         "author": "John NoBody",
//         "publisher": "Classic Children",
//         "pages": 56,
//         "language": "English",
//         "description": "bla bla bla",
//         "_links": {
//           "self": {
//             "href": "http://localhost:8080/api/books/1"
//           },
//           "book": {
//             "href": "http://localhost:8080/api/books/1"
//           }
//         }
//       }
//     ]
//   },
//   "_links": {
//     "self": {
//       "href": "http://localhost:8080/api/books{?page,size,sort}",
//       "templated": true
//     },
//     "profile": {
//       "href": "http://localhost:8080/api/profile/books"
//     }
//   },
//   "page": {
//     "size": 20,
//     "totalElements": 1,
//     "totalPages": 1,
//     "number": 0
//   }
// }
const url = new URL('http://localhost:8080/api/books');

const instance = axios.create({
  baseURL: url.href,
  headers: {
    post: {
      'Content-Type': 'application/json',
    },
  },
});

const createIdUrl = idPath => isNaN(idPath) ? '/' : `/${idPath}`;

const extractBook = halBook => ({
  id: halBook.id,
  title: halBook.title,
  author: halBook.author,
  publisher: halBook.publisher,
  description: halBook.description,
  language: halBook.language,
  pages: halBook.pages,
});

const fetchBooks = async id => (await instance.get(createIdUrl(id)))
  .data['_embedded']
  .books
  .map(extractBook);

const addBook = async book => extractBook((await instance.post('/', book)).data);

const editBook = async (id, book) => extractBook((await instance.put(`/${id}`, book)).data);

const removeBook = async id => (await instance.delete(`/${id}`).statusText);

export {
  fetchBooks,
  addBook,
  editBook,
  removeBook,
};
