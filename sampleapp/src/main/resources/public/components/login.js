const React = require('react');

module.exports = React.createClass({
	render: function(){
		return (
			<form id="formLogin" action="/login/openid/appdirect" method="get">
				<button type='submit' name='login' value='login'>Login</button>
			</form>
		)
	}
});