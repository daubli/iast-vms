import { RouteConfig } from 'vue-router';
import TheDashboard from '@/common/components/dashboard/the-dashboard.vue';
import TheVulnerabilities from '@/common/components/vulnerability/the-vulnerabilities.vue';
import TheAgents from '@/common/components/agent/the-agents.vue';
import CreateAgent from '@/common/components/agent/create-agent.vue';
import TheSettings from '@/common/components/settings/the-settings.vue';
import ThePolicyEditor from '@/common/components/policy/the-policy-editor.vue';
import VulnerabilitiesDetails from '@/common/components/vulnerability/vulnerability-details.vue';
import AgentDetails from '@/common/components/agent/agent-details.vue';
import TheGate from '@/common/components/gate/the-gate.vue';

const routes: RouteConfig[] = [
	{
		path: '/',
		name: 'dashboard',
		component: TheDashboard
	},
	{
		path: '/vulnerabilities',
		name: 'vulnerabilities',
		component: TheVulnerabilities
	},
	{
		path: '/vulnerabilities/:vulnerabilityId',
		name: 'vulnerability-details',
		component: VulnerabilitiesDetails,
		props: true
	},
	{
		path: '/agents',
		name: 'agents',
		component: TheAgents
	},
	{
		path: '/agents/create',
		name: 'create',
		component: CreateAgent
	},
	{
		path: '/agents/:agentId',
		name: 'agent-details',
		component: AgentDetails,
		props: true
	},
	{
		path: '/gate',
		name: 'gate',
		component: TheGate
	},
	{
		path: '/policy',
		name: 'policy',
		component: ThePolicyEditor
	},
	{
		path: '/settings',
		name: 'settings',
		component: TheSettings
	}
];

export default routes;
