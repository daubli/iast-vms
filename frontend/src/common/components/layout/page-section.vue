<template>
	<v-card flat class="mb-10">
		<v-divider v-if="divider" class="mb-10 grey lighten-3"></v-divider>
		<v-card-title v-if="title" class="px-0 pt-0 text-body-1 font-weight-bold">{{ title }}</v-card-title>
		<v-card-text class="pa-0 text-body-1 secondary--text">
			<slot />
		</v-card-text>
	</v-card>
</template>

<script lang="ts">
import Vue from 'vue';

export default Vue.extend({
	name: 'PageSection',
	props: {
		title: { type: String, default: '' },
		divider: { type: Boolean, default: true }
	}
});
</script>
