import { Vulnerability } from '@/domains/vulnerability/vulnerability.types';
import { GateHealth } from '@/domains/gate/gate.types';

export interface DashboardData {
	latestVulnerabilities: Vulnerability[];
	agentsOnline: number;
	agentsRegistered: number;
	score: string;
	gateHealth: GateHealth;
}
