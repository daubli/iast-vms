import Vue from 'vue';
import routes from '@/routes';
import VueRouter from 'vue-router';

function buildRouter() {
	return new VueRouter({
		mode: 'history',
		routes: routes,
		linkActiveClass: 'active'
	});
}

Vue.use(VueRouter);

const router = buildRouter();

export default router;
