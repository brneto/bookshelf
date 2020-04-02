import React, { useEffect } from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { useHistory, useParams } from 'react-router-dom';
import { FORM_ERROR } from 'final-form';
import { Form, Field } from 'react-final-form';
import { commands } from '../../redux/actions';
import { getBookById } from '../../redux/reducers';
import { TextField, TextareaField, NumberField } from './components';

const
  mapStateToProps = state => ({
    book: getBookById(state),
  }),
  mapDispatchToProps = {
    fetchBook: commands.fetchBooks,
    addBook: commands.addBook,
    editBook: commands.editBook,
  },
  subscribe = connect(
    mapStateToProps,
    mapDispatchToProps
  ),
  propTypes = {
    book: PropTypes.object.isRequired,
    fetchBook: PropTypes.func.isRequired,
    addBook: PropTypes.func.isRequired,
    editBook: PropTypes.func.isRequired,
  };

const BookForm = ({ book, fetchBook, addBook, editBook }) => {
  const
    history = useHistory(),
    { id } = useParams(),
    bookCreateOrUpdate = (typeof book.id === 'number') ? editBook : addBook,
    handleGoBack = () => history.push('/'),
    handleSubmit = async values => {
      try {
        await bookCreateOrUpdate(values);
        handleGoBack();
      } catch(error) {
        return { [FORM_ERROR]: `The server failed with the message: ${error.message}.` };
      }
    };

  useEffect(() => {
    if (!isNaN(id) && book.id !== id) fetchBook(id);
  }, [id, book.id, fetchBook]);

  return (<Form
    onSubmit={handleSubmit}
    initialValues={book}
    render={({ submitError, handleSubmit }) => (
      <form onSubmit={handleSubmit}>
        <h2>Book Details</h2>
        <Field name="title" required render={TextField} />
        <Field name="author" required render={TextField} />
        <Field name="publisher" required render={TextField} />
        <Field name="description" required render={TextareaField} />
        <Field name="pages" required render={NumberField} />
        <Field name="language" required render={TextField} />
        {submitError && <div style={{ color: 'red' }}>{submitError}</div>}
        <button type="submit">Submit</button>&nbsp;
        <button onClick={handleGoBack}>Cancel</button>
      </form>
    )}
  />);
};

BookForm.propTypes = propTypes;

export default subscribe(BookForm);
