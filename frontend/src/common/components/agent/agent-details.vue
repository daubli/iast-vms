<template>
	<PageContent fullwidth>
		<PageHeader :title="agent.name">
			<template slot="icon">
				<v-icon :color="getAgentBadgeColor(agent.lastSeen)" :size="20" class="mr-3">{{ mdiCircle }}</v-icon>
			</template>
			<template slot="right">
				<v-tooltip bottom>
					<template v-slot:activator="{ on }">
						<div class="d-inline-block" v-on="on">
							<v-btn class="mr-1" outlined @click="dialogDelete = true">
								<v-icon>{{ mdiTrashCan }}</v-icon>
							</v-btn>
						</div>
					</template>
					<span>Delete Agent</span>
				</v-tooltip>
			</template>
		</PageHeader>
		<PageSection>
			<AgentInfo :last-seen="agent.lastSeen" />
			<AgentDownload :agent-id="agent.id" />
		</PageSection>
		<AgentDeleteDialog
			:dialog-delete="dialogDelete"
			:agent-id-to-delete="agent.id"
			@closed="dialogDelete = false"
			@deleted="deleteItemConfirmed"
		/>
	</PageContent>
</template>

<script lang="ts">
import PageHeader from '@/common/components/layout/page-header.vue';
import PageContent from '@/common/components/layout/page-content.vue';
import PageSection from '@/common/components/layout/page-section.vue';
import AgentInfo from '@/common/components/agent/agent-info.vue';
import AgentDownload from '@/common/components/agent/agent-download.vue';
import AgentDeleteDialog from '@/common/components/agent/agent-delete-dialog.vue';
import { Agent } from '@/domains/agent/agent.types';
import AgentApi from '@/domains/agent/agentApi';
import { mdiCircle, mdiTrashCan } from '@mdi/js';

import AgentMixings from '@/common/mixin/agent-mixin';

export default AgentMixings.extend({
	name: 'AgentDetails',
	components: {
		PageContent,
		PageHeader,
		PageSection,
		AgentInfo,
		AgentDownload,
		AgentDeleteDialog
	},
	props: {
		agentId: { default: null, type: String }
	},
	data() {
		return {
			agent: {} as Agent,
			dialogDelete: false,
			mdiTrashCan: mdiTrashCan,
			mdiCircle: mdiCircle
		};
	},
	mounted() {
		this.fetchAgent(this.agentId);
	},
	methods: {
		async fetchAgent(agentId: string) {
			this.agent = (await AgentApi.getAgent(agentId)).data;
		},
		async deleteItemConfirmed() {
			this.dialogDelete = false;
			this.$router.push('/agents');
		}
	}
});
</script>
