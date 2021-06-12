import Vue from 'vue';
import Vuetify from 'vuetify/lib';
import cssVars from 'css-vars-ponyfill';
import { VuetifyParsedTheme } from 'vuetify/types/services/theme';

Vue.use(Vuetify);

export const lgBreakpoint = 1440;

const vuetify = new Vuetify({
	breakpoint: {
		thresholds: {
			// Define new starting point for large breakpoint. This makes md cover a larger area.
			// This is tied to the definition in variables.scss but unlike there, here we have to set the *upper limit*
			// of the md breakpoint instead of defining the starting point for lg.
			md: lgBreakpoint
		}
	},
	theme: {
		options: {
			customProperties: true
		},
		themes: {
			light: {
				primary: '#1862ab', // vms blue
				secondary: '#424242', // grey.darken3
				accent: '#82B1FF', // blue.accent1
				error: '#FF5252', // red.accent2
				info: '#2196F3', // blue.base
				success: '#4CAF50', // green.base
				warning: '#FB8C00' // amber.base
			},
			dark: {
				primary: '#1862ab', // vms blue
				secondary: '#424242', // grey.darken3
				accent: '#FF4081', // pink.accent-2
				error: '#FF5252', // red.accent2
				info: '#2196F3', // blue.base
				success: '#4CAF50', // green.base
				warning: '#FB8C00' // amber.base
			}
		}
	},
	icons: {
		iconfont: 'mdiSvg'
	}
});

// We want to use CSS custom properties so that modern browsers get possible performance benefits and we can write
// modern CSS. However IE11 doesn't that feature. So we have to tell it about the CSS custom properties that Vuetify
// generates via a polyfill.

const parsedTheme = ((vuetify.framework.theme as unknown) as {
	parsedTheme: VuetifyParsedTheme;
}).parsedTheme;

cssVars({
	variables: {
		'v-anchor-base': parsedTheme.primary.base,
		'v-primary-base': parsedTheme.primary.base,
		'v-primary-lighten5': parsedTheme.primary.lighten5,
		'v-primary-lighten4': parsedTheme.primary.lighten4,
		'v-primary-lighten3': parsedTheme.primary.lighten3,
		'v-primary-lighten2': parsedTheme.primary.lighten2,
		'v-primary-lighten1': parsedTheme.primary.lighten1,
		'v-primary-darken1': parsedTheme.primary.darken1,
		'v-primary-darken2': parsedTheme.primary.darken2,
		'v-primary-darken3': parsedTheme.primary.darken3,
		'v-primary-darken4': parsedTheme.primary.darken4,
		'v-secondary-base': parsedTheme.secondary.base,
		'v-secondary-lighten5': parsedTheme.secondary.lighten5,
		'v-secondary-lighten4': parsedTheme.secondary.lighten4,
		'v-secondary-lighten3': parsedTheme.secondary.lighten3,
		'v-secondary-lighten2': parsedTheme.secondary.lighten2,
		'v-secondary-lighten1': parsedTheme.secondary.lighten1,
		'v-secondary-darken1': parsedTheme.secondary.darken1,
		'v-secondary-darken2': parsedTheme.secondary.darken2,
		'v-secondary-darken3': parsedTheme.secondary.darken3,
		'v-secondary-darken4': parsedTheme.secondary.darken4,
		'v-accent-base': parsedTheme.accent.base,
		'v-accent-lighten5': parsedTheme.accent.lighten5,
		'v-accent-lighten4': parsedTheme.accent.lighten4,
		'v-accent-lighten3': parsedTheme.accent.lighten3,
		'v-accent-lighten2': parsedTheme.accent.lighten2,
		'v-accent-lighten1': parsedTheme.accent.lighten1,
		'v-accent-darken1': parsedTheme.accent.darken1,
		'v-accent-darken2': parsedTheme.accent.darken2,
		'v-accent-darken3': parsedTheme.accent.darken3,
		'v-accent-darken4': parsedTheme.accent.darken4,
		'v-error-base': parsedTheme.error.base,
		'v-error-lighten5': parsedTheme.error.lighten5,
		'v-error-lighten4': parsedTheme.error.lighten4,
		'v-error-lighten3': parsedTheme.error.lighten3,
		'v-error-lighten2': parsedTheme.error.lighten2,
		'v-error-lighten1': parsedTheme.error.lighten1,
		'v-error-darken1': parsedTheme.error.darken1,
		'v-error-darken2': parsedTheme.error.darken2,
		'v-error-darken3': parsedTheme.error.darken3,
		'v-error-darken4': parsedTheme.error.darken4,
		'v-info-base': parsedTheme.info.base,
		'v-info-lighten5': parsedTheme.info.lighten5,
		'v-info-lighten4': parsedTheme.info.lighten4,
		'v-info-lighten3': parsedTheme.info.lighten3,
		'v-info-lighten2': parsedTheme.info.lighten2,
		'v-info-lighten1': parsedTheme.info.lighten1,
		'v-info-darken1': parsedTheme.info.darken1,
		'v-info-darken2': parsedTheme.info.darken2,
		'v-info-darken3': parsedTheme.info.darken3,
		'v-info-darken4': parsedTheme.info.darken4,
		'v-success-base': parsedTheme.success.base,
		'v-success-lighten5': parsedTheme.success.lighten5,
		'v-success-lighten4': parsedTheme.success.lighten4,
		'v-success-lighten3': parsedTheme.success.lighten3,
		'v-success-lighten2': parsedTheme.success.lighten2,
		'v-success-lighten1': parsedTheme.success.lighten1,
		'v-success-darken1': parsedTheme.success.darken1,
		'v-success-darken2': parsedTheme.success.darken2,
		'v-success-darken3': parsedTheme.success.darken3,
		'v-success-darken4': parsedTheme.success.darken4,
		'v-warning-base': parsedTheme.warning.base,
		'v-warning-lighten5': parsedTheme.warning.lighten5,
		'v-warning-lighten4': parsedTheme.warning.lighten4,
		'v-warning-lighten3': parsedTheme.warning.lighten3,
		'v-warning-lighten2': parsedTheme.warning.lighten2,
		'v-warning-lighten1': parsedTheme.warning.lighten1,
		'v-warning-darken1': parsedTheme.warning.darken1,
		'v-warning-darken2': parsedTheme.warning.darken2,
		'v-warning-darken3': parsedTheme.warning.darken3,
		'v-warning-darken4': parsedTheme.warning.darken4
	}
});

export default vuetify;
