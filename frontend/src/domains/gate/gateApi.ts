import { AxiosPromise } from 'axios';
import axios from '@/common/util/axios/initialized-axios-wrapper';
import { GateCondition, GateData } from '@/domains/gate/gate.types';

const gateApi = {
	getGateData(): AxiosPromise<GateData> {
		return axios.get(`/gates/`);
	},
	createNewGateCondition(condition: GateCondition): AxiosPromise<GateCondition> {
		return axios.post('/gates/conditions', condition);
	},
	updateGateCondition(condition: GateCondition): AxiosPromise<GateCondition> {
		return axios.put('/gates/conditions', condition);
	},
	deleteGateCondition(conditionId: string) {
		return axios.delete(`/gates/conditions/${conditionId}`);
	}
};

export default gateApi;
