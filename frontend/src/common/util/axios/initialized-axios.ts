import axios, { AxiosInstance } from 'axios';
import Error from '@/common/util/axios/error.types';

const creatorFunction = function(baseURL: string): AxiosInstance {
	const initializedAxios = axios.create({
		// Use this to add static headers to send with every AJAX request.
		// headers: {'X-Requested-With': 'XMLHttpRequest'}

		// prefix any request to rest API accordingly:
		baseURL: baseURL,

		// After 10s, we'd like to stop...
		timeout: 10000
	});

	initializedAxios.interceptors.response.use(
		function(response) {
			return response;
		},
		function(error) {
			if (error.response) {
				const vmsError: Error = error.response.data;
				console.log(vmsError.status);
				return Promise.reject(error);
			} else {
				// in case when there is no response, we simply reject Promise
				return Promise.reject(error);
			}
		}
	);
	return initializedAxios;
};

export default creatorFunction;
