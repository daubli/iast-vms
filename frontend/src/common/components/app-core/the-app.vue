<template>
	<v-app>
		<v-navigation-drawer v-model="drawer" app permanent :mini-variant="this.$vuetify.breakpoint.mobile">
			<v-sheet color="grey lighten-4" class="pa-4">
				<v-img max-height="250" max-width="250" src="@/assets/logo.png"></v-img>
			</v-sheet>

			<v-divider></v-divider>

			<v-list>
				<v-list-item v-for="[icon, text, link] in links" :key="icon" link :to="link">
					<v-list-item-icon>
						<v-icon>{{ icon }}</v-icon>
					</v-list-item-icon>

					<v-list-item-content>
						<v-list-item-title>{{ text }}</v-list-item-title>
					</v-list-item-content>
				</v-list-item>
			</v-list>
		</v-navigation-drawer>

		<v-main>
			<v-container class="py-8 px-6" fluid>
				<router-view></router-view>
			</v-container>
		</v-main>
	</v-app>
</template>

<script lang="ts">
import { mdiViewDashboard, mdiLanguageJava, mdiCog, mdiShieldEdit, mdiBug, mdiGate } from '@mdi/js';
import Vue from 'vue';

export default Vue.extend({
	name: 'TheApp',
	data: () => ({
		drawer: true,
		mini: false,
		links: [
			[mdiViewDashboard, 'Dashboard', '/'],
			[mdiBug, 'Vulnerabilities', '/vulnerabilities'],
			[mdiLanguageJava, 'Agents', '/agents'],
			[mdiShieldEdit, 'Security Policy', '/policy'],
			[mdiGate, 'Security Gate', 'gate'],
			[mdiCog, 'Settings', '/settings']
		]
	})
});
</script>
