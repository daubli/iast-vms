export interface Agent {
	id: string;
	identifier: string;
	name: string;
	lastSeen: Date;
}

export default interface Agents {
	agents: Agent[];
}
