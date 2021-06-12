module.exports = {
	root: true,
	env: {
		node: true
	},
	'extends': [
		'plugin:vue/recommended',
		'airbnb-base',
		'@vue/typescript/recommended',
		'@vue/prettier',
		'@vue/prettier/@typescript-eslint'
	],
	rules: {
		'no-var' : 2,
		'import/no-cycle': 2,
		/* There _are_ situations in our code, where this occurs (file-upload.vue), I don't really know if I like it or
		not */
		'import/no-duplicates': 0,
		/* I'm unsure about this, but we have some explicit dependencies on e.g dropzone wich is no explicit dependency
		 but transitive through vue-dropzone */
		'import/no-extraneous-dependencies': 0,
		/* this is a bit sad, but there are currently still problems around .vue extensions in imports, which I don't
		want to resolve at this point */
		'import/no-unresolved': 0,
		'no-console': 'off',
		'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
		'object-shorthand': 0,
		'lines-between-class-members': 0,
		'prefer-template': 0,
		'func-names': 0,
		'@typescript-eslint/no-inferrable-types': 0,
		'no-restricted-syntax': 0, // TODO
		'no-param-reassign': ['error', { 'props': false }],
		'prefer-destructuring': 0,
		'no-plusplus': 0,
		'import/extensions': 0,
		'class-methods-use-this': 0,
		'one-var': 0,
		'no-else-return': 0,
		'@typescript-eslint/no-use-before-define': 0,
		'@typescript-eslint/no-empty-interface': 0,
		'@typescript-eslint/no-explicit-any': 0, // TODO
		'@typescript-eslint/no-this-alias': 0,
		/* maybe we want to activate this in the future. currently, we just have to use the type assertion operator
		too often  */
		'@typescript-eslint/no-non-null-assertion': 'off',
		'default-case': 0,
		'dot-notation': 0,
		'global-require': 0,
		'no-nested-ternary': 0,
		'no-restricted-globals': 0,
		'no-useless-constructor': 0
	},
	parserOptions: {
		parser: '@typescript-eslint/parser'
	}
};
