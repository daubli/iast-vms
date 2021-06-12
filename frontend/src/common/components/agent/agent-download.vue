<template>
	<v-card elevation="1" class="mt-5">
		<v-card-title>Download Agent Artifacts</v-card-title>
		<div class="ml-4">
			<v-row>
				<v-col lg="1" md="2" sm="3">
					Agent:
				</v-col>
				<v-col lg="11" md="10" sm="9">
					<v-btn class="ml-5" color="secondary" :ripple="false" @click="downloadAgent()">Download</v-btn>
					<v-btn class="ml-3" color="secondary" :ripple="false" @click="copyAgentDownloadLink()"
						>Copy Link to Clipboard
					</v-btn>
				</v-col>
			</v-row>
			<v-row>
				<v-col lg="1" md="2" sm="3">
					JVM Module Patches:
				</v-col>
				<v-col lg="11" md="10" sm="9">
					<v-btn class="ml-5" color="secondary" :ripple="false" @click="downloadPatches()">
						Download
					</v-btn>
					<v-btn class="ml-3" color="secondary" :ripple="false" @click="copyPatchDownloadLink()"
						>Copy Link to Clipboard
					</v-btn>
				</v-col>
			</v-row>
		</div>
		<v-snackbar v-model="snackbar" timeout="2500" bottom color="green" content-class="text-center">
			Copied to Clipboard!
		</v-snackbar>
	</v-card>
</template>

<script lang="ts">
import Vue from 'vue';
import AgentApi from '@/domains/agent/agentApi';
import downloadFromResponse from '@/common/util/download-util';

export default Vue.extend({
	name: 'AgentDownload',
	props: {
		agentId: { default: '', type: String }
	},
	data: () => ({
		snackbar: false
	}),
	methods: {
		async downloadAgent() {
			await AgentApi.getAgentFile(this.agentId).then(function(response) {
				downloadFromResponse(response);
			});
		},
		async downloadPatches() {
			await AgentApi.getAgentPatch().then(function(response) {
				downloadFromResponse(response);
			});
		},
		copyAgentDownloadLink() {
			const input = document.createElement('input');
			input.setAttribute('value', window.location.origin + '/api/agents/' + this.agentId + '/download-agent');
			document.body.appendChild(input);
			(input as HTMLInputElement).select();
			document.execCommand('copy');
			document.body.removeChild(input);
			this.snackbar = true;
		},
		copyPatchDownloadLink() {
			const input = document.createElement('input');
			input.setAttribute('value', window.location.origin + '/api/agents/download-patch');
			document.body.appendChild(input);
			(input as HTMLInputElement).select();
			document.execCommand('copy');
			document.body.removeChild(input);
			this.snackbar = true;
		}
	}
});
</script>
