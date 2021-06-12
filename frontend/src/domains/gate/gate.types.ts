export interface GateData {
	conditions: GateCondition[];
}

export interface GateCondition {
	id: string;
	metric: GateConditionMetric;
	operator: GateConditionOperator;
	value: string;
}

export enum GateConditionMetric {
	RATING = 'rating',
	NUMBER_OF_HIGH_VUL = 'nohighvul',
	NUMBER_OF_MOD_VUL = 'nomoderatevul',
	NUMBER_OF_LOW_VUL = 'nolowvul'
}

export enum GateConditionOperator {
	LESS_THAN = 'lt',
	GREATER_THAN = 'gt',
	WORSE_THAN = 'wt',
	BETTER_THAN = 'bt'
}

export enum GateHealth {
	NA = 'na',
	PASSING = 'passing',
	NOT_PASSING = 'not passing'
}
