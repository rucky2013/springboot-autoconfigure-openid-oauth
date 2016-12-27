const React = require('react');
const jquery = require('jquery');

module.exports = React.createClass({
	getInitialState: function() {
		console.log("subscriptions.getInitialState - Entering");
	    var result = {
	    		subscriptions: null,
	    		waiting: false
		};
	    console.log("subscriptions.getInitialState - Exiting: " + result.subscriptions);
	    return result;
	  },
	  
	  componentDidMount: function() {
		  console.log("subscription.componentDidMount");
		  
		  this.setState({
  			waiting: true
		  });
		  
		  var self = this;
		  jquery.ajax('/client/subscriptions', {
			  statusCode: {
				   401: () => { 
					   console.log("subscriptions.componentDidMount.401 (async) - Retrieved subscriptions failure");
				    	this.setState({
			    			subscriptions: null,
			    			waiting: false
						});
				    	console.log("subscriptions.componentDidMount.401 (async) - subscriptions: "+ this.state.subscriptions);
				   },
				   
				   200: subscriptions => {
				    	console.log("subscriptions.componentDidMount.success (async) - Retrieved subscriptions successfully: " + JSON.stringify(subscriptions));
				    	
				    	this.setState({
			    			subscriptions: subscriptions,
			    			waiting: false
						});
			    		console.log("subscriptions.componentDidMount.success (async) - subscriptions: "+ this.state.subscriptions);
				    }
				},
			    error: subscriptions => {
			    	console.log("subscriptions.componentDidMount.error (async) - Retrieved subscriptions failure");
			    	this.setState({
		    			subscriptions: null,
		    			waiting: false
					});
		    		console.log("subscriptions.componentDidMount.error (async) - subscriptions: "+ this.state.subscriptions);
			    }
			    
			});
	  },
	  
	  render: function() {
			console.log("subscriptions.render - Entering");
			
			// Waiting for response
			if(this.state.waiting){
				return (<div className="center"></div>)
			} 
			// Was unable to retrieve the subscription
			else if(!this.state.subscriptions){ 
				return (<div className="center">You need to be logged in to view current subscriptions</div>)
			} 
			// There are no subscriptions
			else if (this.state.subscriptions.length == 0){ 
				return (<div className="center">There are no subscriptions!</div>)
			}
			
			// If we are here, it means we have subscriptions to display!
			var subs = this.state.subscriptions.map(function(sub) {
				console.log("sub: " + sub);
				var users = sub.users.map(function(user) {
					console.log("User firstname: {} " + user.firstName +" and uuid " + user.uuid);
	                return (<span><div>{user.firstName} {user.lastName} {user.uuid==sub.creatorUuid ? <div className="inline">(Creator)</div>: null}</div>
	                		<div className="table-footnote inline">Id: {user.uuid}</div>
	                		<div className="table-footnote">Email: {user.email}</div>
	                		<div>&nbsp;</div>
	                	</span>)
	            })
				
                return (<tr>
                			<td><span>
                				{sub.account ? <div>
                				<div className="lowercase">{sub.account.status}</div>
                				<div className="table-footnote inline">Id: {sub.account.accountIdentifier}</div>
                				</div> : null}</span></td>
                			<td><span>
                				{sub.company ? <div>
	                				<div>{sub.company.name}</div>
	                				<div className="table-footnote inline">Id: {sub.company.uuid}</div>
	                				<div className="table-footnote">Phone Number: {sub.company.phoneNumber}</div>
	                				<div className="table-footnote">Website: {sub.company.website}</div>
	                				<div className="table-footnote">Email: {sub.company.email}</div>
                				</div> : null}</span></td>
                			<td>{users}</td>
                			<td><span>
                				{sub.order ? <div>
                					<div className="lowercase">{sub.order.editionCode}</div>
                					{sub.order.addonOfferingCode ? <div className="table-footnote">Offering Code: <div className="lowercase">{sub.order.addonOfferingCode}</div></div>: null}
                					{sub.order.pricingDuration ? <div className="table-footnote inline">Price Duration: <div className="lowercase inline">{sub.order.pricingDuration}</div></div>: null}
                				</div> : null}</span></td>
                				
                			<td className="minwidth"><span className="maxwidth">
                				{sub.notice ? <div className="maxwidth"> 
                					<div className="lowercase">{sub.notice.type}</div>
	        						{sub.notice.message ? <div className="table-footnote">Message: {sub.notice.message}</div>: null}
	        					</div> : null}</span></td>
                		</tr>);
            });
			
            return (
					    <table className="subscriptions-table">
							<thead>
					   			<tr>
					   				<th>Account</th>
					   				<th>Company</th>
					   				<th>Users</th>
					   				<th>Order</th>
					   				<th>Last Notice</th>
					   			</tr>
					  		</thead>
							<tbody>
							    {subs}
							</tbody>
						</table>
				);
		}
});