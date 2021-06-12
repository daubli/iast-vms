<template>
	<v-dialog v-model="dialogDelete" max-width="500px">
		<v-card>
			<v-card-title class="justify-center"
				>If you delete this agent, all associated vulnerabilities are also gone. Are you sure?</v-card-title
			>
			<v-card-actions>
				<v-spacer></v-spacer>
				<v-btn color="blue darken-1" text @click="closeDelete">Cancel</v-btn>
				<v-btn color="blue darken-1" text @click="deleteItemConfirm">OK</v-btn>
				<v-spacer></v-spacer>
			</v-card-actions>
		</v-card>
	</v-dialog>
</template>

<script lang="ts">
import Vue from 'vue';
import AgentApi from '@/domains/agent/agentApi';

export default Vue.extend({
	name: 'AgentDeleteDialog',
	props: {
		dialogDelete: {
			type: Boolean,
			default: false
		},
		agentIdToDelete: {
			type: String,
			default: ''
		}
	},
	methods: {
		async deleteItemConfirm() {
			try {
				await AgentApi.deleteAgent(this.agentIdToDelete);
				this.$emit('deleted');
			} catch (e) {
				console.log(e);
				this.closeDelete();
			}
		},
		closeDelete() {
			this.$emit('closed');
		}
	}
});
</script>
