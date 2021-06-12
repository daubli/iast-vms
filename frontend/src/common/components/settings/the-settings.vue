<template>
	<PageContent fullwidth>
		<PageHeader title="Settings" />
		<PageSection title="Instance Settings">
			<v-row>
				<v-col cols="5" lg="6" md="8" sm="12">
					<v-card-text class="pa-0 text-body-1 secondary--text">
						<v-text-field v-model="settings.instanceURL" filled label="Instance URL"></v-text-field>
					</v-card-text>
					<v-card-actions>
						<v-btn @click="submitSettings()">Save</v-btn>
					</v-card-actions>
				</v-col>
			</v-row>
		</PageSection>
		<v-snackbar v-model="snackbar" content-class="text-center" :timeout="snackbarTimeout" :color="snackbarColor">
			{{ snackbarText }}
		</v-snackbar>
	</PageContent>
</template>

<script lang="ts">
import Vue from 'vue';
import SettingApi from '@/domains/setting/settingApi';
import { Settings } from '@/domains/setting/setting.types';
import PageHeader from '@/common/components/layout/page-header.vue';
import PageContent from '@/common/components/layout/page-content.vue';
import PageSection from '@/common/components/layout/page-section.vue';

export default Vue.extend({
	name: 'TheSettings',
	components: {
		PageHeader,
		PageContent,
		PageSection
	},
	data() {
		return {
			settings: {} as Settings,
			snackbar: false,
			snackbarColor: '',
			snackbarText: '',
			snackbarTimeout: 2000
		};
	},
	mounted() {
		this.fetchSettings();
	},
	methods: {
		async fetchSettings() {
			await SettingApi.getSettings().then(response => {
				this.settings = response.data;
			});
		},
		async submitSettings() {
			await SettingApi.saveSettings(this.settings).then(() => {
				this.snackbarText = 'Saved!';
				this.snackbarColor = 'green';
				this.snackbar = true;
			});
		}
	}
});
</script>
