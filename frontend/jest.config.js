module.exports = {
	moduleFileExtensions: ['js', 'jsx', 'json', 'vue', 'ts', 'tsx'],

	transform: {
		'^.+\\.vue$': 'vue-jest',
		'^.+\\.svg$': '<rootDir>/src/common/util/__test_util__/svgTransform.js',
		'.+\\.(css|styl|less|sass|scss|png|jpg|ttf|woff|woff2)$': 'jest-transform-stub',
		'^.+\\.tsx?$': 'ts-jest',
		'^.+\\.jsx?$': 'babel-jest'
	},

	// Use to ignore whole test files
	testPathIgnorePatterns: [],

	transformIgnorePatterns: ['/node_modules/(?!(babel-jest|jest-vue-preprocessor|vuetify)/)'],

	moduleNameMapper: {
		'^@/(.*)$': '<rootDir>/src/$1',
		'\\.(css|less|scss|sass)$': 'identity-obj-proxy'
	},

	snapshotSerializers: ['jest-serializer-vue'],

	testURL: 'http://localhost/',

	verbose: true,

	setupFiles: ['<rootDir>/src/common/util/__test_util__/test-setup.js'],

	setupFilesAfterEnv: ['<rootDir>/src/common/util/__test_util__/test-setup-after-env.js'],

	resetModules: true,

	restoreMocks: true,

	/* Coverage settings */
	collectCoverage: false, // can be overridden with --coverage

	collectCoverageFrom: ['src/**/*.{js,jsx,ts,vue}', '!**/node_modules/**'],

	coverageReporters: ['text'],

	globals: {
		'ts-jest': {
			babelConfig: true
			// tsconfig.json is found automatically (https://kulshekhar.github.io/ts-jest/user/config/tsConfig)
		}
	}
};
