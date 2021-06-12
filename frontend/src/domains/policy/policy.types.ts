export interface PolicySettings {
	id: string;
	sqlInjectionDetectionEnabled: boolean;
	csrfCheckEnabled: boolean;
	possibleCsrfHeaders: string[];
}
