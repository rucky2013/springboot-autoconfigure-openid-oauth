var path = require('path');
var webpack = require('webpack');

module.exports = {
	entry : './src/main/resources/public/app.js',
	cache : true,
	debug : true,
	devtool : "#eval-source-map",
	output : {
		path : "./src/main/resources/public/dist",
		filename : "bundle.min.js"
	},
	plugins : [ new webpack.optimize.UglifyJsPlugin({
		minimize : true
	}) ],
	module : {
		loaders: [{
            test: /\.jsx?$/,
            loader: 'babel',
            query:
            {
                presets:['es2015', 'react']
            }
        }]
	},
};