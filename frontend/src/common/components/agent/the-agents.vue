<template>
	<PageContent fullwidth>
		<PageHeader title="Agents" />
		<PageSection>
			<div class="text-right mb-8">
				<v-btn color="primary" link to="/agents/create">Create Agent</v-btn>
			</div>
			<AgentOverview />
		</PageSection>
	</PageContent>
</template>

<script lang="ts">
import Vue from 'vue';
import PageHeader from '@/common/components/layout/page-header.vue';
import PageContent from '@/common/components/layout/page-content.vue';
import PageSection from '@/common/components/layout/page-section.vue';
import AgentOverview from '@/common/components/agent/agent-overview.vue';

export default Vue.extend({
	name: 'TheAgents',
	components: {
		PageContent,
		PageHeader,
		PageSection,
		AgentOverview
	}
});
</script>
