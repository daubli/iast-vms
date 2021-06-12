import { AxiosPromise } from 'axios';
import axios from '@/common/util/axios/initialized-axios-wrapper';
import { DashboardData } from '@/domains/dashboard/dashboard.types';

const dashboardApi = {
	getDashboardData(): AxiosPromise<DashboardData> {
		return axios.get('/dashboard/');
	}
};

export default dashboardApi;
