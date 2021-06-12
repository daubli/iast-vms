<template>
	<PageContent fullwidth>
		<PageHeader title="Security Policy Editor"></PageHeader>
		<PageSection>
			<v-card>
				<v-card-title>SQL Injection Detection</v-card-title>
				<v-card-text>
					<v-checkbox v-model="policy.sqlInjectionDetectionEnabled" label="Enable"></v-checkbox>
				</v-card-text>
			</v-card>
			<v-card class="mt-5">
				<v-card-title>CSRF-Check</v-card-title>
				<v-card-text>
					<v-checkbox v-model="policy.csrfCheckEnabled" label="Enable"></v-checkbox>
					<v-combobox
						v-if="policy.csrfCheckEnabled"
						v-model="policy.possibleCsrfHeaders"
						multiple
						label="Possible CSRF-Tokens"
						append-icon
						chips
						deletable-chips
						class="tag-input"
						:search-input.sync="search"
					>
					</v-combobox>
				</v-card-text>
			</v-card>
			<v-card flat class="mt-5">
				<v-card-actions>
					<v-btn @click="submitPolicy()">Save</v-btn>
				</v-card-actions>
			</v-card>
		</PageSection>
		<v-snackbar v-model="snackbar" content-class="text-center" :timeout="snackbarTimeout" :color="snackbarColor">
			{{ snackbarText }}
		</v-snackbar>
	</PageContent>
</template>

<script lang="ts">
import Vue from 'vue';

import PageHeader from '@/common/components/layout/page-header.vue';
import PageContent from '@/common/components/layout/page-content.vue';
import PageSection from '@/common/components/layout/page-section.vue';
import PolicyApi from '@/domains/policy/policyApi';
import { PolicySettings } from '@/domains/policy/policy.types';

export default Vue.extend({
	name: 'ThePolicyEditor',
	components: {
		PageHeader,
		PageContent,
		PageSection
	},
	data() {
		return {
			search: '',
			policy: {} as PolicySettings,
			snackbar: false,
			snackbarColor: '',
			snackbarText: '',
			snackbarTimeout: 5000
		};
	},
	mounted() {
		this.fetchPolicy();
	},
	methods: {
		async fetchPolicy() {
			await PolicyApi.getPolicy().then(response => {
				this.policy = response.data;
			});
		},
		async submitPolicy() {
			await PolicyApi.savePolicy(this.policy).then(() => {
				this.snackbarText =
					'Saved! This settings become effective after restarting the application with the attached agent.';
				this.snackbarColor = 'green';
				this.snackbar = true;
			});
		}
	}
});
</script>
