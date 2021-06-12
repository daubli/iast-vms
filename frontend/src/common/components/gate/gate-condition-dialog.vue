<template>
	<v-row justify="center">
		<v-dialog v-model="dialog" persistent max-width="600px" @keydown.esc="dialog = false">
			<v-card>
				<v-card-title>
					<span class="headline">{{ dialogTitle }}</span>
				</v-card-title>
				<v-card-text>
					<v-container>
						<v-row>
							<v-col cols="12" sm="6" md="4">
								<v-select
									v-model="metricValue"
									:items="metric"
									label="Metric"
									@change="forgetSelection"
								></v-select>
							</v-col>
							<v-col cols="12" sm="6" md="4">
								<v-select
									v-if="isText"
									v-model="operatorValue"
									:items="operatorNumberOfVulnerabilities"
									label="Operator"
									:disabled="disabledField"
								></v-select>
								<v-select
									v-if="isSelect"
									v-model="operatorValue"
									:items="operatorRating"
									label="Operator"
									:disabled="disabledField"
								></v-select>
							</v-col>
							<v-col cols="12" sm="6" md="4">
								<v-text-field
									v-if="isText"
									v-model="conditionValue"
									label="Value"
									:disabled="disabledField"
									:rules="numberRule"
								></v-text-field>
								<v-select
									v-if="isSelect"
									v-model="conditionValue"
									label="Rating"
									:items="rating"
								></v-select>
							</v-col>
						</v-row>
					</v-container>
				</v-card-text>
				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn color="blue darken-1" text @click="onClose">
						Close
					</v-btn>
					<v-btn :disabled="!canSave" color="blue darken-1" text @click="onSave">
						Save
					</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>
	</v-row>
</template>
<script lang="ts">
import Vue from 'vue';
import { PropType } from 'vue/types/options';
import { GateCondition, GateConditionMetric, GateConditionOperator } from '@/domains/gate/gate.types';

export default Vue.extend({
	name: 'GateConditionDialog',
	props: {
		dialog: { default: false, type: Boolean },
		isEditActive: { default: false, type: Boolean },
		isCreateActive: { default: false, type: Boolean },
		gateCondition: { required: true, type: Object as PropType<GateCondition> }
	},
	data: () => ({
		metricValue: GateConditionMetric.NUMBER_OF_HIGH_VUL as GateConditionMetric,
		operatorValue: {} as GateConditionOperator,
		conditionValue: '',
		metric: [
			{
				text: 'Number of High Severity Vulnerabilities',
				value: GateConditionMetric.NUMBER_OF_HIGH_VUL
			},
			{
				text: 'Number of Moderate Severity Vulnerabilities',
				value: GateConditionMetric.NUMBER_OF_MOD_VUL
			},
			{
				text: 'Number of Low Severity Vulnerabilities',
				value: GateConditionMetric.NUMBER_OF_LOW_VUL
			},
			{
				text: 'Vulnerability Score',
				value: GateConditionMetric.RATING
			}
		],
		operatorRating: [
			{
				text: 'Better than',
				value: GateConditionOperator.BETTER_THAN
			},
			{
				text: 'Worse than',
				value: GateConditionOperator.WORSE_THAN
			}
		],
		operatorNumberOfVulnerabilities: [
			{
				text: 'Greater than',
				value: GateConditionOperator.GREATER_THAN
			},
			{
				text: 'Less than',
				value: GateConditionOperator.LESS_THAN
			}
		],
		rating: ['A', 'B', 'C', 'D', 'E', 'F'],
		numberRule: [
			(value: string) => {
				if (value === '') {
					return true;
				}
				return /^-?\d+$/.test(value) ? true : 'Has to be a valid number';
			}
		]
	}),
	computed: {
		dialogTitle(): string {
			return this.isCreateActive ? 'Add Condition' : 'Edit Condition';
		},
		disabledField(): boolean {
			return this.metricValue === undefined;
		},
		isText(): boolean {
			return (
				this.metricValue === undefined ||
				this.metricValue === GateConditionMetric.NUMBER_OF_HIGH_VUL ||
				this.metricValue === GateConditionMetric.NUMBER_OF_MOD_VUL ||
				this.metricValue === GateConditionMetric.NUMBER_OF_LOW_VUL
			);
		},
		isSelect(): boolean {
			return this.metricValue === GateConditionMetric.RATING;
		},
		canSave(): boolean {
			return (
				typeof this.operatorValue !== 'object' &&
				this.conditionValue !== '' &&
				(this.metricValue === GateConditionMetric.RATING || this.numberRule[0](this.conditionValue) === true)
			);
		}
	},
	watch: {
		gateCondition: function() {
			this.updateFromProps();
		}
	},
	methods: {
		onSave() {
			const condition = {
				metric: this.metricValue,
				operator: this.operatorValue,
				value: this.conditionValue
			} as GateCondition;
			if (this.isEditActive) {
				this.$emit('edit-confirmed', { ...condition, id: this.gateCondition.id });
			} else if (this.isCreateActive) {
				this.$emit('create-confirmed', { ...condition });
			}
		},
		onClose() {
			this.$emit('closed');
		},
		forgetSelection() {
			this.operatorValue = {} as GateConditionOperator;
			this.conditionValue = '';
		},
		updateFromProps() {
			this.metricValue = this.gateCondition.metric;
			this.operatorValue = this.gateCondition.operator;
			this.conditionValue = this.gateCondition.value;
		}
	}
});
</script>
