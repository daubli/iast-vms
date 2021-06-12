import vuetify from '@/plugins/vuetify';

describe('vuetify.ts', () => {
	test('derived theme colours accessible', async () => {
		// Make sure that the parsedTheme getter - which is strictly speaking not part of the public API of the theme -
		// is still accessible and the derived value (in this case just the base value that we enter ourselves, but that
		// is in a separate property here) is what we expect.
		expect(vuetify.framework.theme.parsedTheme.secondary.base).toEqual('#424242');
	});
});
