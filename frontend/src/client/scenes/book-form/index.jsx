import React from 'react';
import { Form, Field } from 'react-final-form';
import { TextField, TextareaField, NumberField } from './components';

const BookForm = () => (<Form
  onSubmit={() => {}}
  render={({ handleSubmit }) => (
    <form onSubmit={handleSubmit}>
      <h2>Book Details</h2>
      <Field name="title" label="Title" render={TextField} />
      <Field name="author" label="Author" render={TextField} />
      <Field name="publisher" label="Publisher" render={TextField} />
      <Field name="description" label="Description" render={TextareaField} />
      <Field name="pages" render={NumberField} />
      <Field name="language" label="Language" render={TextField} />
    </form>
  )}
/>);

export default BookForm;
