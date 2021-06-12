import { AxiosPromise } from 'axios';
import axios from '@/common/util/axios/initialized-axios-wrapper';
import { PolicySettings } from '@/domains/policy/policy.types';

const policyApi = {
	getPolicy(): AxiosPromise<PolicySettings> {
		return axios.get(`/policy/`);
	},
	savePolicy(policy: PolicySettings): AxiosPromise<PolicySettings> {
		return axios.put(`/policy/`, policy);
	}
};

export default policyApi;
