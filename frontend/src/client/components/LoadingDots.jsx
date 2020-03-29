import React from 'react';
import PropTypes from 'prop-types';
import { timer } from './hooks';

const propTypes = {
  delay: PropTypes.number,
  length: PropTypes.number,
  children: PropTypes.string.isRequired,
};

const LoadingDots = ({ delay = 300, length = 3, children }) => {
  const count = timer.useCounterUp(delay) % (length + 1); // Custom Hook

  return <span>{children + '.'.repeat(count)}&nbsp;</span>;
};
LoadingDots.propTypes = propTypes;

export default LoadingDots;
