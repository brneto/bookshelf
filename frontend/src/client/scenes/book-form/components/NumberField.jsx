import React from 'react';
import PropTypes from 'prop-types';
import { capitalize } from './string-utils';
import Label from './Label';

const
  propTypes = {
    input: PropTypes.object.isRequired,
    meta: PropTypes.object,
    required: PropTypes.bool,
  },
  NumberField = ({ input, meta, required }) => {
    const label = capitalize(input.name);
    return (
      <Label>{label}:&nbsp;
        <input type="number" {...input} required={required} min="1" placeholder={label} />
        {meta.touched && meta.error && <span>{meta.error}</span>}
      </Label>
    );
  };

NumberField.propTypes = propTypes;

export default NumberField;
