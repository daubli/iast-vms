export default interface Error {
	path: string;
	timestamp: string;
	status: number;
	details?: { [key: string]: any };
}
