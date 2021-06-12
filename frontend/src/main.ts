import Vue from 'vue';
import VClamp from 'vue-clamp';

import router from '@/vue-router';
import vuetify from '@/plugins/vuetify';
import TheApp from '@/common/components/app-core/the-app.vue';

Vue.use(VClamp);
Vue.config.productionTip = false;

if (process.env.NODE_ENV === 'development') {
	// This turns on timing measurements for components in the browser's dev tools.
	// According to the documentation this will only have an effect in development mode anyway, so placing it inside
	// this if-block is not strictly necessary. But the documentation is not clear on the implications of this setting
	// and why the default is not 'true' (as it is in development mode for Vue.config.devtools which allows the Vue
	// Devtools to do inspections). So I'd rather be on the safe side.
	Vue.config.performance = true;
}

document.addEventListener('DOMContentLoaded', function() {
	new Vue({
		router,
		vuetify,
		render(createElement) {
			return createElement(TheApp);
		}
	}).$mount('#app');
});
