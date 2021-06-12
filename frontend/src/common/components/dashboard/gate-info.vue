<template>
	<v-card class="mt-2" elevation="2" outlined tile :color="backgroundColor">
		<v-card-title class="white--text">Gate Status</v-card-title>
		<v-card-text>
			<h1 class="text-h5 white--text">{{ info }}</h1>
		</v-card-text>
	</v-card>
</template>

<script lang="ts">
import Vue from 'vue';
import { GateHealth } from '@/domains/gate/gate.types';

export default Vue.extend({
	name: 'GateInfo',
	props: {
		health: { type: String, required: true, default: GateHealth.NA }
	},
	computed: {
		info(): string {
			switch (this.health) {
				case GateHealth.NA:
					return 'Not Available';
				case GateHealth.PASSING:
					return 'Passing';
				case GateHealth.NOT_PASSING:
					return 'Not Passing';
				default:
					return '';
			}
		},
		backgroundColor(): string {
			switch (this.health) {
				case GateHealth.NA:
					return 'grey';
				case GateHealth.PASSING:
					return 'green';
				case GateHealth.NOT_PASSING:
					return 'red';
				default:
					return '';
			}
		}
	}
});
</script>
