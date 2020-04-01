import React from 'react';
import { Form, Field } from 'react-final-form';

const FormField = () => (
  <Form
    onSubmit={() => {}}
    render={({ handleSubmit }) => (
      <form onSubmit={handleSubmit}>
        <h2>Book Details</h2>
        <div>
          <label htmlFor="field-title">Title</label>
          <Field id="field-title" name="title" component="input" placeholder="Title" />
        </div>
      </form>
    )}
  />
);

export default FormField;
