<template>
	<v-row justify="center" no-gutters>
		<v-col :xl="xl" :lg="lg" :md="md" :sm="sm">
			<slot />
		</v-col>
	</v-row>
</template>

<script lang="ts">
import Vue from 'vue';

export default Vue.extend({
	name: 'PageContent',
	props: {
		fullwidth: {
			type: Boolean,
			default: false
		},
		narrow: {
			type: Boolean,
			default: false
		}
	},
	computed: {
		xl: function() {
			return this.fullwidth ? 12 : this.narrow ? 5 : 6;
		},
		lg: function() {
			return this.fullwidth ? 12 : this.narrow ? 7 : 8;
		},
		md: function() {
			return this.fullwidth ? 12 : this.narrow ? 7 : 8;
		},
		sm: function() {
			return this.fullwidth ? 12 : this.narrow ? 8 : 9;
		}
	}
});
</script>
