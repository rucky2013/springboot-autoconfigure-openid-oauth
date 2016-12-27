'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');

var Menu = require('./components/menu');
var Subscriptions = require('./components/subscriptions');


ReactDOM.render(<Menu />, document.getElementById('menu'))
ReactDOM.render(<Subscriptions />, document.getElementById('subscriptions'))
