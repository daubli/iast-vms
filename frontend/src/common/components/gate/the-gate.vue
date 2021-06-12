<template>
	<PageContent fullwidth>
		<PageHeader title="Security Gate"></PageHeader>
		<PageSection>
			You can define specific conditions that will break your pipeline, if they are not fulfilled. Therefore a
			special endpoint can be requested to fetch the current status of the build (click
			<a @click="panel = 0">here</a> to see details). All specified conditions must be fulfilled to pass the
			security gate.

			<div class="text-right mt-8 mb-8">
				<v-btn color="primary" @click="onCreateCondition">Create Condition</v-btn>
			</div>
			<GateConditionDialog
				:dialog="conditionDialogOpen"
				:is-edit-active="isEditActive"
				:is-create-active="isCreateActive"
				:gate-condition="gateCondition"
				@create-confirmed="onCreateConditionConfirmed"
				@edit-confirmed="onEditConditionConfirmed"
				@closed="onCreateConditionClosed"
			/>
			<v-row>
				<v-col sm="12">
					<GateConditionTable :conditions="gateConditions" @onEdit="onEditCondition" />
				</v-col>
			</v-row>

			<v-row>
				<v-col md="12">
					<v-expansion-panels v-model="panel">
						<v-expansion-panel>
							<v-expansion-panel-header>Add Gate-Status to your Pipeline</v-expansion-panel-header>
							<v-expansion-panel-content>
								<v-row>
									<v-col sm="12" lg="6"
										>The bash script beneath can be used to determine the gate status in the context
										of a CI/CD pipeline. To use this script certain preconditions must be fulfilled.
										The system where the script is executed needs a current version of jq and curl.
										Please ensure, that these programs are installed. The script has to be triggered
										from the build job. If the gate is closed, the script returns a
										<code>1</code> which the job can interpret as a build-failure. If the gate can
										be passed, the script returns a <code>0</code>. The job knows then that the gate
										is passed. It is recommended to use a separate job to check weather the gate is
										open or closed.
									</v-col>
									<v-col sm="12" lg="6">
										<v-textarea
											id="codeTextArea"
											class="consolas-font"
											rows="10"
											readonly
											auto-grow
											:append-icon="mdiContentCopy"
											:value="getScript()"
											@click:append="copyCode"
										></v-textarea>
									</v-col>
								</v-row>
							</v-expansion-panel-content>
						</v-expansion-panel>
					</v-expansion-panels>
				</v-col>
			</v-row>
			<v-snackbar v-model="snackbar" timeout="2500" bottom color="green" content-class="text-center">
				Copied to Clipboard!
			</v-snackbar>
		</PageSection>
	</PageContent>
</template>
<script lang="ts">
import Vue from 'vue';
import PageHeader from '@/common/components/layout/page-header.vue';
import PageContent from '@/common/components/layout/page-content.vue';
import PageSection from '@/common/components/layout/page-section.vue';
import GateConditionDialog from '@/common/components/gate/gate-condition-dialog.vue';
import GateConditionTable from '@/common/components/gate/gate-condition-table.vue';
import { GateCondition, GateConditionMetric, GateConditionOperator } from '@/domains/gate/gate.types';
import GateApi from '@/domains/gate/gateApi';
import { mdiContentCopy } from '@mdi/js';

export default Vue.extend({
	name: 'TheGate',
	components: {
		PageContent,
		PageHeader,
		PageSection,
		GateConditionDialog,
		GateConditionTable
	},
	data() {
		return {
			panel: [0, 1],
			conditionDialogOpen: false,
			gateConditions: [] as Array<GateCondition>,
			gateCondition: {
				metric: GateConditionMetric.NUMBER_OF_HIGH_VUL as GateConditionMetric,
				operator: {} as GateConditionOperator,
				value: ''
			} as GateCondition,
			isEditActive: false,
			isCreateActive: false,
			snackbar: false,
			mdiContentCopy: mdiContentCopy
		};
	},
	mounted(): void {
		this.fetchGateData();
	},
	methods: {
		async fetchGateData() {
			this.gateConditions = (await GateApi.getGateData()).data.conditions;
		},
		async onCreateConditionConfirmed(condition: GateCondition) {
			this.conditionDialogOpen = false;
			this.isCreateActive = false;
			const createdCondition = (await GateApi.createNewGateCondition(condition)).data;
			this.gateConditions.push(createdCondition);
		},
		async onEditConditionConfirmed(condition: GateCondition) {
			this.conditionDialogOpen = false;
			this.isEditActive = false;
			const editedCondition = (await GateApi.updateGateCondition(condition)).data;
			const indexOfEditedCondition = this.gateConditions.findIndex(cond => cond.id === editedCondition.id);
			this.gateConditions.splice(indexOfEditedCondition, 1, editedCondition);
		},
		onCreateConditionClosed() {
			this.conditionDialogOpen = false;
			this.isCreateActive = false;
			this.isEditActive = false;
		},
		onCreateCondition() {
			this.conditionDialogOpen = true;
			this.isCreateActive = true;
		},
		onEditCondition(item: GateCondition) {
			this.gateCondition = item;
			this.conditionDialogOpen = true;
			this.isEditActive = true;
		},
		getGateEndpoint(): string {
			return window.location.origin + '/api/gates/status';
		},
		getScript(): string {
			return (
				'#!/bin/bash\n' +
				'function get_security_gate_status {\n' +
				'\tcurl -s ' +
				this.getGateEndpoint() +
				' | jq -r .gateHealth\n' +
				'}\n\n' +
				'GATE_STATUS=$(get_security_gate_status)\n' +
				// eslint-disable-next-line no-template-curly-in-string
				'echo "Gate status fetched: ${GATE_STATUS}"\n\n' +
				// eslint-disable-next-line no-template-curly-in-string
				'if [[ ${GATE_STATUS}" == "not passing" ]]; then\n' +
				'\texit 1;\n' +
				'fi'
			);
		},
		copyCode(): void {
			const codeElement = document.getElementById('codeTextArea');
			(codeElement as HTMLTextAreaElement).select();
			document.execCommand('copy');
			(codeElement as HTMLTextAreaElement).setSelectionRange(0, 0);
			this.snackbar = true;
		}
	}
});
</script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Source+Code+Pro&display=swap');

.consolas-font {
	font-family: 'Source Code Pro', monospace;
}
</style>
