import { AxiosPromise } from 'axios';
import axios from '@/common/util/axios/initialized-axios-wrapper';
import { Settings } from '@/domains/setting/setting.types';

const settingApi = {
	getSettings(): AxiosPromise<Settings> {
		return axios.get('/settings/');
	},
	saveSettings(settings: Settings): AxiosPromise {
		return axios.put(`/settings/`, settings);
	}
};

export default settingApi;
