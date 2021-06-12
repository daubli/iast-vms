import moment from 'moment';

import Vue from 'vue';

export default Vue.extend({
	methods: {
		getAgentBadgeColor(lastSeen: Date | null): string {
			if (lastSeen == null) {
				return 'red';
			}
			const diffInMinutes = moment().diff(moment.utc(lastSeen).local(), 'minutes');

			if (diffInMinutes < 5) {
				return 'green';
			} else if (diffInMinutes > 10 && diffInMinutes < 30) {
				return 'orange';
			} else {
				return 'red';
			}
		}
	}
});
