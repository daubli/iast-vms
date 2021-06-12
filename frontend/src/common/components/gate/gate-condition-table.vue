<template>
	<v-container class="mt-2 pa-0" fluid>
		<v-data-table :headers="headers" :items="conditions" class="elevation-1" @click:row="editItem">
			<template v-slot:item.metric="{ item }">
				{{ metricDisplayText(item) }}
			</template>
			<template v-slot:item.operator="{ item }">
				{{ operatorDisplayText(item) }}
			</template>
			<template v-slot:item.actions="{ item }">
				<v-icon small @click.stop="editItem(item)">
					{{ mdiPencil }}
				</v-icon>
				<v-icon class="ml-2" small @click.stop="deleteItem(item)">
					{{ mdiDelete }}
				</v-icon>
			</template>
		</v-data-table>
		<v-dialog v-model="dialogDelete" max-width="500px" @keydown.esc="dialogDelete = false">
			<v-card>
				<v-card-title class="justify-center">Are you sure you want to delete this condition?</v-card-title>
				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn color="blue darken-1" text @click="closeDelete">Cancel</v-btn>
					<v-btn color="blue darken-1" text @click="deleteItemConfirm">OK</v-btn>
					<v-spacer></v-spacer>
				</v-card-actions>
			</v-card>
		</v-dialog>
	</v-container>
</template>
<script lang="ts">
import Vue, { PropType } from 'vue';
import { GateCondition, GateConditionOperator, GateConditionMetric } from '@/domains/gate/gate.types';
import GateApi from '@/domains/gate/gateApi';
import { mdiDelete, mdiPencil } from '@mdi/js';

export default Vue.extend({
	name: 'GatewayConditionTable',
	props: {
		conditions: { default: (): Array<GateCondition> => [], type: Array as PropType<Array<GateCondition>> }
	},
	data: function() {
		return {
			headers: [
				{
					text: 'Metric',
					value: 'metric'
				},
				{
					text: 'Operator',
					value: 'operator'
				},
				{
					text: 'Value',
					value: 'value'
				},
				{
					text: 'Actions',
					value: 'actions',
					sortable: false
				}
			],
			mdiPencil: mdiPencil,
			mdiDelete: mdiDelete,
			deletedConditionId: '',
			dialogDelete: false,
			deletedIndex: -1
		};
	},
	watch: {
		dialogDelete(val: boolean) {
			// eslint-disable-next-line no-unused-expressions
			val || this.closeDelete();
		}
	},
	methods: {
		operatorDisplayText(condition: GateCondition) {
			switch (condition.operator) {
				case GateConditionOperator.WORSE_THAN:
					return 'Worse Than';
				case GateConditionOperator.BETTER_THAN:
					return 'Better Than';
				case GateConditionOperator.LESS_THAN:
					return 'Less Than';
				case GateConditionOperator.GREATER_THAN:
					return 'Greater Than';
			}
			return '';
		},
		metricDisplayText(condition: GateCondition) {
			switch (condition.metric) {
				case GateConditionMetric.RATING:
					return 'Vulnerability Score';
				case GateConditionMetric.NUMBER_OF_HIGH_VUL:
					return 'Number of High Vulnerabilities';
				case GateConditionMetric.NUMBER_OF_MOD_VUL:
					return 'Number of Moderate Vulnerabilities';
				case GateConditionMetric.NUMBER_OF_LOW_VUL:
					return 'Number of Low Vulnerabilities';
			}
			return '';
		},
		closeDelete() {
			this.dialogDelete = false;
		},
		editItem(item: GateCondition) {
			this.$emit('onEdit', item);
		},
		deleteItem(item: GateCondition) {
			this.deletedIndex = this.conditions.indexOf(item);
			this.deletedConditionId = item.id;
			this.dialogDelete = true;
		},
		async deleteItemConfirm() {
			try {
				await GateApi.deleteGateCondition(this.deletedConditionId);
				this.conditions.splice(this.deletedIndex, 1);
				this.deletedConditionId = '';
				this.closeDelete();
			} catch (e) {
				console.log(e);
				this.closeDelete();
			}
		}
	}
});
</script>
