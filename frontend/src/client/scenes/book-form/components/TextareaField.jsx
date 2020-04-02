import React from 'react';
import PropTypes from 'prop-types';
import { capitalize } from './string-utils';
import Label from './Label';

const
  propTypes = {
    input: PropTypes.object.isRequired,
    meta: PropTypes.object,
  },
  TextareaField = ({ input, meta }) => {
    const label = capitalize(input.name);
    return (
      <Label>{label}:&nbsp;
        <textarea {...input} placeholder={label} />
        {meta.touched && meta.error && <span>{meta.error}</span>}
      </Label>
    );
  };

TextareaField.propTypes = propTypes;

export default TextareaField;
