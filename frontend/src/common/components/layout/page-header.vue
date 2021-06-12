<template>
	<v-card flat class="mb-5" color="transparent" v-bind="$attrs">
		<div class="d-flex justify-space-between">
			<div>
				<v-clamp :max-lines="1" :autoresize="true" class="text-h4">
					<template slot="before"> <slot name="icon"/></template>
					{{ title }}
				</v-clamp>
			</div>
			<div>
				<slot name="right" />
			</div>
		</div>
	</v-card>
</template>

<script lang="ts">
import Vue from 'vue';
import VClamp from 'vue-clamp';

export default Vue.extend({
	name: 'PageHeader',
	components: {
		VClamp
	},
	props: {
		title: {
			type: String,
			default: ''
		}
	}
});
</script>
