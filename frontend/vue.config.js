const path = require('path');

console.log(`NODE_ENV: ${process.env.NODE_ENV}`);
console.log(`Node version: ${process.version}`);

module.exports = {
	pluginOptions: {
		'style-resources-loader': {
			preProcessor: 'scss',
			patterns: ['./src/common/assets/scss/_component-dependencies.scss']
		},
		moment: {
			// @see i18n.ts -> option 'whitelist'
			locales: ['en', 'de', 'fr']
		}
	},

	transpileDependencies: [
		'vuetify',
		'vue-clamp',
		'resize-detector'
	],

	devServer: {
		port: 7080,
		disableHostCheck: true,
		proxy: {
			...(['^/api'].reduce(
				(acc, ctx) => ({
					...acc,
					[ctx]: {
						target: 'http://localhost:7081',
						router: req => {
							return 'http://' + req.hostname + ':7081';
						},
						changeOrigin: false,
						xfwd: true,
						ws: true,
						secure: false
					}
				}), {}))
		}
	},

	/*
	 * https://github.com/neutrinojs/webpack-chain#getting-started
	 */
	chainWebpack: config => {
		configureIndexHtml(config);
		configureSvgLoader(config);
		configurePrefetching(config);
		configureFailOnLintError(config);

		if (process.env.NODE_ENV === 'development') {
			// mutate for development...
		} else {
			// mutate for production ...
		}
	},

	lintOnSave: true
};

function configureIndexHtml(config) {
	config.plugin('html').tap(args => {
		let minify = args[0].minify;
		if (minify) {
			// in development mode this is not available
			minify.removeAttributeQuotes = false;
		}
		return args;
	});
}

function configureSvgLoader(config) {
	const svgRule = config.module.rule('svg');

	// clear all existing loaders.
	// if you don't do this, the loader below will be appended to
	// existing loaders of the rule.
	svgRule.uses.clear();

	// add replacement loader(s)
	// TS: Also see https://github.com/visualfanatic/vue-svg-loader/issues/63
	svgRule
		.use('babel-loader')
		.loader('babel-loader')
		.end()
		.use('vue-svg-loader')
		.loader('vue-svg-loader');
}

function configureFailOnLintError(config) {
	config.module
		.rule('eslint')
		.use('eslint-loader')
		.tap(opts => ({ ...opts, failOnWarning: process.env.NODE_ENV !== 'development' }));
	// The following is also a possibility, but it leads to double-compilation of affected files. A pity!
	// fix: process.env.NODE_ENV === 'development'
}

function configurePrefetching(config) {
	// We don't want automatic prefetching of all dynamically loaded chunks - that kind of defeats the purpose of
	// loading them dynamically. We should still be able to get Webpack to prefetch dynamic imports using
	// <https://webpack.js.org/api/module-methods/#magic-comments> if we wanted to (although in testing it didn't work).
	// See also <https://github.com/vuejs/vue-cli/issues/979>.
	config.plugins.delete('prefetch');
}
