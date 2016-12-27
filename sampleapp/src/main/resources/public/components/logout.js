const React = require('react');

module.exports = React.createClass({
	render: function(){
		return (
				<form id="formLogout" action="/logout/openid/appdirect" method="post">
					<button type='submit' name='logout' value='Logout'>Logout</button>
				</form>
			)
	}
});