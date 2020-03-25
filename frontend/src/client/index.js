import React from 'react';
import ReactDOM from 'react-dom';
import Root from './components/Root';
import * as state from './redux/store';

ReactDOM.render(
  <Root {...state} />,
  document.getElementById('root')
);
