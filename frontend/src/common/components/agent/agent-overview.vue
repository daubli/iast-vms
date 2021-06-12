<template>
	<v-responsive>
		<v-card>
			<v-data-table
				title="Agents"
				:headers="headers"
				:items="tableItems"
				class="elevation-1"
				:items-per-page="itemsPerPage"
				:loading="loading"
				loading-text="Loading... Please wait"
				@click:row="handleRowClick"
			>
				<template v-slot:item.name="{ item }">
					<v-badge :color="getAgentBadgeColor(item.lastSeen)" dot>{{ item.name }} </v-badge>
				</template>
				<template v-slot:item.actions="{ item }">
					<v-icon small @click.stop="deleteItem(item)">
						{{ mdiDelete }}
					</v-icon>
				</template>
			</v-data-table>
		</v-card>

		<AgentDeleteDialog
			:dialog-delete="dialogDelete"
			:agent-id-to-delete="agentIdToDelete"
			@closed="dialogDelete = false"
			@deleted="deleteItemConfirmed"
		/>
	</v-responsive>
</template>

<script lang="ts">
import AgentApi from '@/domains/agent/agentApi';
import { Agent } from '@/domains/agent/agent.types';
import moment from 'moment';
import { mdiDelete } from '@mdi/js';
import AgentDeleteDialog from '@/common/components/agent/agent-delete-dialog.vue';
import AgentMixings from '@/common/mixin/agent-mixin';

export default AgentMixings.extend({
	name: 'AgentOverview',
	components: {
		AgentDeleteDialog
	},
	data() {
		return {
			mdiDelete: mdiDelete,
			headers: [
				{
					text: 'Agent name',
					value: 'name'
				},
				{
					text: 'Last seen',
					value: 'lastSeenDesc'
				},
				{ text: 'Actions', value: 'actions', sortable: false }
			],
			agents: [] as Agent[],
			tableItems: [] as any[],
			dialogDelete: false,
			deletedIndex: -1,
			agentIdToDelete: '',
			itemsPerPage: 5,
			loading: true
		};
	},
	watch: {
		dialogDelete(val: boolean) {
			// eslint-disable-next-line no-unused-expressions
			val || this.closeDelete();
		}
	},
	mounted() {
		this.fetchAgents();
	},
	methods: {
		async fetchAgents() {
			await AgentApi.getAgents().then(response => {
				this.agents = response.data.agents;
			});
			this.tableItems = this.agents.map((agent: Agent) => ({
				id: agent.id,
				name: agent.name,
				lastSeenDesc: this.getLastSeenDesc(agent.lastSeen),
				lastSeen: agent.lastSeen
			}));
			this.loading = false;
		},
		getLastSeenDesc(lastSeen: Date | null): string {
			if (lastSeen == null) {
				return 'never';
			} else {
				return moment
					.utc(lastSeen)
					.local()
					.fromNow();
			}
		},
		deleteItem(item: any) {
			this.deletedIndex = this.tableItems.indexOf(item);
			this.agentIdToDelete = item.id;
			this.dialogDelete = true;
		},
		closeDelete() {
			this.dialogDelete = false;
		},
		async deleteItemConfirmed() {
			try {
				this.tableItems.splice(this.deletedIndex, 1);
				this.agentIdToDelete = '';
				this.closeDelete();
			} catch (e) {
				console.log(e);
				this.closeDelete();
			}
		},
		handleRowClick(item: Agent) {
			this.$router.push({ name: 'agent-details', params: { agentId: item.id } });
		}
	}
});
</script>
