import { AxiosPromise } from 'axios';
import axios from '@/common/util/axios/initialized-axios-wrapper';
import Agents, { Agent } from '@/domains/agent/agent.types';

const agentApi = {
	getAgents(): AxiosPromise<Agents> {
		return axios.get('/agents/');
	},
	getAgent(agentId: string): AxiosPromise<Agent> {
		return axios.get(`/agents/${agentId}`);
	},
	createAgent(agent: Agent): AxiosPromise {
		return axios.post('/agents/', agent);
	},
	deleteAgent(agentId: string): AxiosPromise {
		return axios.delete(`/agents/${agentId}`);
	},
	getAgentFile(agentId: string): AxiosPromise {
		return axios.get(`/agents/${agentId}/download-agent`, { responseType: 'blob', timeout: 120000 });
	},
	getAgentPatch(): AxiosPromise {
		return axios.get('/agents/download-patch', { responseType: 'blob', timeout: 120000 });
	}
};

export default agentApi;
