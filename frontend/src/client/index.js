import React from 'react';
import ReactDOM from 'react-dom';
import App from './App';
import * as state from './redux/store';

ReactDOM.render(
  <App {...state} />,
  document.getElementById('root')
);
