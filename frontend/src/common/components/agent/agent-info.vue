<template>
	<v-card elevation="1">
		<v-card-title>Info</v-card-title>
		<div class="ml-4">
			<v-row>
				<v-col lg="1" md="2" sm="3">
					Last seen:
				</v-col>
				<v-col lg="11" md="10" sm="9">
					{{ agentLastSeen }}
				</v-col>
			</v-row>
		</div>
	</v-card>
</template>

<script lang="ts">
import Vue from 'vue';
import moment from 'moment';

export default Vue.extend({
	name: 'AgentInfo',
	props: {
		lastSeen: { default: '', type: String }
	},
	computed: {
		agentLastSeen(): string {
			return this.lastSeen === ''
				? 'never'
				: moment
						.utc(this.lastSeen)
						.local()
						.format('YYYY-MM-DD HH:mm:ss');
		}
	}
});
</script>
