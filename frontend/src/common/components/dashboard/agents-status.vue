<template>
	<v-card elevation="2" outlined tile>
		<v-card-title>Agents available</v-card-title>
		<v-card-text>{{ agentsOnline }} / {{ agentsRegistered }}</v-card-text>
	</v-card>
</template>

<script lang="ts">
import Vue from 'vue';

export default Vue.extend({
	name: 'AgentsStatus',
	props: {
		agentsRegistered: { type: Number, required: true, default: 0 },
		agentsOnline: { type: Number, required: true, default: 0 }
	}
});
</script>
