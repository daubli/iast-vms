<template>
	<v-row>
		<v-col xs="12" sm="7" md="8" lg="9" xl="10">
			<VulnerabilitiesOverview
				title="Latest Vulnerabilities"
				:hide-default-footer="true"
				:hide-filter="true"
				:hide-status-column="true"
				:vulnerabilities="latestVulnerabilitiesOrEmpty"
				:loading="loading"
				disable-sort
				outlined
			></VulnerabilitiesOverview>
		</v-col>
		<v-col xs="12" sm="5" md="4" lg="3" xl="2">
			<AgentsStatus
				:agents-online="dashboardData.agentsOnline"
				:agents-registered="dashboardData.agentsRegistered"
			/>
			<VulnerabilityScore :score-value="dashboardData.score" />
			<GateInfo :health="dashboardData.gateHealth" />
		</v-col>
	</v-row>
</template>

<script lang="ts">
import Vue from 'vue';
import AgentsStatus from '@/common/components/dashboard/agents-status.vue';
import VulnerabilityScore from '@/common/components/dashboard/vulnerability-score.vue';
import VulnerabilitiesOverview from '@/common/components/vulnerability/vulnerabilities-overview.vue';
import GateInfo from '@/common/components/dashboard/gate-info.vue';
import DashboardApi from '@/domains/dashboard/dashboardApi';
import { DashboardData } from '@/domains/dashboard/dashboard.types';
import { Vulnerability } from '@/domains/vulnerability/vulnerability.types';

export default Vue.extend({
	name: 'TheDashboard',
	components: {
		AgentsStatus,
		VulnerabilityScore,
		VulnerabilitiesOverview,
		GateInfo
	},
	data() {
		return {
			dashboardData: {} as DashboardData,
			loading: true
		};
	},
	computed: {
		latestVulnerabilitiesOrEmpty(): Vulnerability[] {
			return !this.dashboardData.latestVulnerabilities ? [] : this.dashboardData.latestVulnerabilities;
		}
	},
	mounted(): void {
		this.fetchDashboardData();
	},
	methods: {
		async fetchDashboardData() {
			await DashboardApi.getDashboardData().then(response => {
				this.dashboardData = response.data;
				this.loading = false;
			});
		}
	}
});
</script>
