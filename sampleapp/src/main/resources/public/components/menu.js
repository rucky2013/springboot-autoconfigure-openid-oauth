const React = require('react');
const jquery = require('jquery');

var Login = require('./login');
var Logout = require('./logout');

module.exports = React.createClass({
	render: function() {
		console.log("menu.render - Entering");
		var result = (
			<div id="wrap">
				<nav id="nav_left">
				    <ul>
					    <li class="title">alexbt's AppDirect Sample Application</li>
					</ul>
				</nav>
				<nav id="nav_right">
					<ul>
						<li>{!this.state.waiting ? <div>{this.state.user}</div> : null}</li>
		            	<li>&nbsp;</li>
		            	<li>{!this.state.waiting ? <div>{this.state.loginButton}</div> : null}</li>
					</ul>
				</nav>
			</div>
		);

		console.log("menu.render - Exiting");
		return result;
	},
	
	componentDidMount: function() {
		console.log("menu.componentDidMount - Entering with user: " + this.state.user);
		var outerClass = this;
		
		console.log("menu.componentDidMount - Calling '/user' to retrieved Logged user");
		
		this.setState({
			waiting: true
		});
		
		jquery.ajax('/client/user', {
		    success: user => {
		    	console.log("menu.componentDidMount (async) - Retrieved logged user successfully: " + JSON.stringify(user));
	    		this.setState({
					user: user.name, 
					loginButton: <Logout />,
					waiting: false
				});
	    		console.log("menu.componentDidMount (async) - Logged user: "+ this.state.user);
		    },
		    error: () => {
		    	this.setState({
		    		user: '(not logged)',
					loginButton: <Login />,
					waiting: false
				});
		    }
		});
		console.log("menu.componentDidMount - Exiting with user: "+ this.state.user);
	},

	getInitialState: function() {
		console.log("menu.getInitialState - Entering");
	    var result = {
	    	user: null, 
	    	loginButton: null,
	    	waiting: false
		};
	    console.log("menu.getInitialState - Exiting with user: " + result.user);
	    return result;
	}
});