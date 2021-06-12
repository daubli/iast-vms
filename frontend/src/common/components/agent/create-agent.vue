<template>
	<PageContent>
		<PageHeader title="Create a new Agent"></PageHeader>
		<PageSection>
			<v-stepper v-model="stepIndex" class="elevation-1">
				<v-stepper-header>
					<v-stepper-step :complete="stepIndex > 1" step="1" @click="stepIndex = 1">
						Create Agent
					</v-stepper-step>

					<v-divider></v-divider>

					<v-divider></v-divider>

					<v-stepper-step :complete="stepIndex > 2" step="2">
						Download Agent
					</v-stepper-step>

					<v-divider></v-divider>

					<v-stepper-step step="3">
						Check Configuration
					</v-stepper-step>
				</v-stepper-header>

				<v-stepper-items>
					<v-stepper-content step="1">
						<v-card class="mb-12" color="lighten-1" outlined height="auto">
							<v-form ref="form" class="pa-5" lazy-validation>
								<h4>Please specify a unique name to your agent instance</h4>
								<v-text-field
									v-model="agent.name"
									:counter="32"
									label="Name of agent instance"
									required
								></v-text-field>
							</v-form>
						</v-card>

						<v-btn color="primary" class="mr-3" @click="completeStep1()">
							Continue
						</v-btn>

						<v-btn text link to="/agents">
							Cancel
						</v-btn>
					</v-stepper-content>

					<v-stepper-content step="2">
						<v-card class="mb-12" color="lighten-1" outlined>
							<v-card-title>Download the Agent</v-card-title>
							<v-card-text
								><p>
									Please download the VMS Java-Agent and the Java-Module Patches. These components are
									important to instrument the application under test so the security analysis can
									happen. Attach the agent and the java module patch to your application under test by
									passing the following parameters to the JVM:
								</p>
								<p>
									<code
										>--patch-module java.base=base-patch.jar -javaagent: /path/to/server/{{
											agentFileName
										}}</code
									>
								</p>

								<v-btn class="ml-5" color="secondary" :ripple="false" @click="downloadAgent()"
									>Download the Agent
								</v-btn>
								<v-btn class="ml-5" color="secondary" :ripple="false" @click="downloadPatches()"
									>Download Java-Patches
								</v-btn>
							</v-card-text>
						</v-card>

						<v-btn color="primary" class="mr-3" @click="stepIndex = 3">
							Continue
						</v-btn>

						<v-btn text link to="/agents">
							Cancel
						</v-btn>
					</v-stepper-content>

					<v-stepper-content step="3">
						<v-card class="mb-12" color="grey lighten-1" height="200px">
							<v-card-title>Check Configuration and Agent</v-card-title>
							<v-card-text>
								<p v-if="agent.lastSeen != null">
									Congratulations. Your Agent seems up and running. You can finish the setup process
									by click on the button below.
								</p>
								<p v-if="agent.lastSeen == null">
									Oh no! Something went wrong. The software has not discovered your agent so far. Your
									can hit the button to ask the server if your again is now connected.
								</p>

								<v-btn v-if="agent.lastSeen == null" color="primary" class="mt-5" @click="fetchAgent()"
									>Try again</v-btn
								>
							</v-card-text>
						</v-card>

						<v-btn color="primary" class="mr-3" :disabled="agent.lastSeen == null" link to="/agents">
							Finish
						</v-btn>

						<v-btn text link to="/agents">
							Cancel
						</v-btn>
					</v-stepper-content>
				</v-stepper-items>
			</v-stepper>
		</PageSection>
	</PageContent>
</template>

<script lang="ts">
import Vue from 'vue';
import AgentApi from '@/domains/agent/agentApi';
import { Agent } from '@/domains/agent/agent.types';
import downloadFromResponse from '@/common/util/download-util';
import PageHeader from '@/common/components/layout/page-header.vue';
import PageContent from '@/common/components/layout/page-content.vue';
import PageSection from '@/common/components/layout/page-section.vue';

export default Vue.extend({
	name: 'CreateAgent',
	components: {
		PageContent,
		PageHeader,
		PageSection
	},
	data() {
		return {
			stepIndex: 1,
			agent: {} as Agent
		};
	},
	computed: {
		agentFileName(): string {
			if (this.agent.name) {
				return this.agent.name.replace(' ', '').toLowerCase() + '-agent.jar';
			} else {
				return '';
			}
		}
	},
	methods: {
		async fetchAgent() {
			await AgentApi.getAgent(this.agent.id).then(response => {
				this.agent = response.data;
			});
		},
		async completeStep1() {
			await AgentApi.createAgent(this.agent)
				.then(response => {
					return AgentApi.getAgent(response.data.id);
				})
				.then(response => {
					this.agent = response.data;
					this.stepIndex++;
				});
		},
		async downloadAgent() {
			await AgentApi.getAgentFile(this.agent.id).then(function(response) {
				downloadFromResponse(response);
			});
		},
		async downloadPatches() {
			await AgentApi.getAgentPatch().then(function(response) {
				downloadFromResponse(response);
			});
		}
	}
});
</script>
